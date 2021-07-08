/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.watchers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.Scheduler;
import com.github.kubesys.KubernetesClient;
import com.github.kubesys.KubernetesWatcher;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class PodWatcher extends KubernetesWatcher {

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
		
		if ((node.get("spec").has("schdulerName")
				&& node.get("spec").get("schdulerName")
						.asText().equals(scheduler.getName())) 
				&& !node.get("spec").has("nodeName")) {
			try {
				String host = scheduler.doScheduling(node);
				kubeClient.bindingResource(node, host);
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
