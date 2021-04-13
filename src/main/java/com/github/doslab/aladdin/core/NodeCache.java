/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class NodeCache {

	protected static NodeCache nodeCache = null;
	
	protected final KubernetesClient client;

	protected final Map<String, JsonNode> nodeMapper = new HashMap<>();
	
	public NodeCache(KubernetesClient client) throws Exception {
		super();
		this.client = client;
		JsonNode nodes =  client.listResources("Node").get("items");
		for (int i = 0; i < nodes.size(); i++) {
			JsonNode node = nodes.get(i);
			nodeMapper.put(node.get("metadata").get("name").asText(), node);
		}
	}

	public Collection<JsonNode> getNodes() {
		return nodeMapper.values();
	}
	
	public void addNode(String name, JsonNode node) {
		this.nodeMapper.put(name, node);
	}
	
	public void removeNode(String name) {
		this.nodeMapper.remove(name);
	}
	
	public static NodeCache createNodeCache(KubernetesClient client) throws Exception {
		
		if (nodeCache == null) {
			nodeCache = new NodeCache(client);
		}
		
		return nodeCache;
	}
}
