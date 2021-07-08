/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.watchers;

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
public class PodWatcher extends KubernetesWatcher {

	protected final Scheduler scheduler;
	
	public PodWatcher(KubernetesClient kubeClient, 
			      	Scheduler scheduler) {
		super(kubeClient);
		this.scheduler = scheduler;
	}

	@Override
	public void doAdded(JsonNode node) {
		
		if (node.get("spec").has("nodeName") ||
				!"aladdin-scheduler".equals(getSchdulerName(node))) {
			return;
		}
		
		long start = System.currentTimeMillis();
		
		String host = scheduler.doScheduling(node);
		
		long end   = System.currentTimeMillis();
		
		{
			ObjectNode annotations = node.get("metadata").has("annotations") 
						? (ObjectNode) node.get("metadata").get("annotations") 
							: new ObjectMapper().createObjectNode();
			annotations.put("schedulerLatency", (end - start) + "ms");
			((ObjectNode) node.get("metadata")).remove("annotations");
			((ObjectNode) node.get("metadata")).set("annotations", annotations);
		}
		
		try {
			kubeClient.updateResource(node);
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
		return (node.get("metadata").has("annotations") && node.get("metadata").get("annotations").has("schedulerType"))
					? node.get("metadata").get("annotations").get("schedulerType").asText() : "queue";
	}
}
