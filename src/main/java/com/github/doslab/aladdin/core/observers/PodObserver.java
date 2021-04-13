/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.observers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.doslab.aladdin.core.Scheduler;
import com.github.kubesys.KubernetesClient;
import com.github.kubesys.KubernetesWatcher;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class PodObserver extends KubernetesWatcher {

	protected final Map<String, Scheduler> schedulers = new HashMap<>();
	
	public PodObserver(KubernetesClient kubeClient, 
			      	Scheduler queue, Scheduler flow) {
		super(kubeClient);
		schedulers.put("queue", queue);
		schedulers.put("flow", flow);
	}

	@Override
	public void doAdded(JsonNode node) {
		
		if (node.get("spec").has("nodeName") ||
				!"aladdin".equals(getSchdulerName(node))) {
			return;
		}
		
		long start = System.currentTimeMillis();
		
		String host = schedulers.get(getSchedulerType(node))
						.doScheduling(node);
		
		long end   = System.currentTimeMillis();
		
		{
			ObjectNode annotations = node.has("annotations") 
						? (ObjectNode) node.get("annotations") 
							: new ObjectMapper().createObjectNode();
			annotations.put("schedulerTime", (end - start) + "ms");
			((ObjectNode) node).set("annotations", annotations);
		}
		
		try {
			kubeClient.bindingResource(node, host);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doModified(JsonNode node) {
		// ignore here
	}

	@Override
	public void doDeleted(JsonNode node) {
		// ignore here
	}

	@Override
	public void doClose() {
		
	}

	protected String getSchdulerName(JsonNode node) {
		return node.get("spec").get("schedulerName").asText();
	}
	
	protected String getSchedulerType(JsonNode node) {
		return (node.has("annotations") && node.get("annotations").has("schedulerType"))
					? node.get("annotations").get("schedulerType").asText() : "queue";
	}
}
