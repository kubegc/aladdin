/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.observers;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.NodeCache;
import com.github.kubesys.KubernetesClient;
import com.github.kubesys.KubernetesWatcher;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class NodeObserver extends KubernetesWatcher {

	protected final NodeCache nodeCache;
	
	public NodeObserver(KubernetesClient kubeClient) throws Exception {
		super(kubeClient);
		this.nodeCache = NodeCache.createNodeCache(kubeClient);
	}

	@Override
	public void doAdded(JsonNode node) {
		nodeCache.addNode(node.get("metadata")
				.get("name").asText(), node);
	}

	@Override
	public void doModified(JsonNode node) {
		nodeCache.addNode(node.get("metadata")
				.get("name").asText(), node);
	}

	@Override
	public void doDeleted(JsonNode node) {
		nodeCache.removeNode(node.get("metadata")
				.get("name").asText());
	}

	@Override
	public void doClose() {
		
	}

}
