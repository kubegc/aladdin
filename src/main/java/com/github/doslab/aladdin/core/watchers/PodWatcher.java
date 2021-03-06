/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.watchers;

import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.Scheduler;

import io.github.kubesys.kubeclient.KubernetesClient;
import io.github.kubesys.kubeclient.KubernetesWatcher;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021??4??13??
 */
public class PodWatcher extends KubernetesWatcher {

	protected final static Logger m_logger = Logger.getLogger(PodWatcher.class.getName());
	
	protected final Scheduler scheduler;
	
	public PodWatcher(Scheduler scheduler) {
		super(scheduler.getClient());
		this.scheduler = scheduler;
}
	
	public PodWatcher(KubernetesClient kubeClient, 
			      	Scheduler scheduler) {
		super(kubeClient);
		this.scheduler = scheduler;
	}

	@Override
	public void doAdded(JsonNode node) {
		
		if (node.get("spec").has("schedulerName")
				&& node.get("spec").get("schedulerName")
						.asText().equals(scheduler.getName())
				&& !node.get("spec").has("nodeName")) {
			try {
				long start = System.currentTimeMillis();
				String host = scheduler.doScheduling(node);
				client.bindingResource(node, host);
				System.out.println("bind " + node.get("metadata")
							.get("name").asText() + " to " + host);
				long end = System.currentTimeMillis();
				m_logger.info(node.get("metadata").get("name").asText() 
						+ ":" + String.format("%.2f", (double)(end - start)));
			} catch (Exception e) {
				System.err.println(e);
			}
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

}
