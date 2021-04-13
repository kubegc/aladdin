/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.plugins.NodeSelector;
import com.github.doslab.aladdin.core.plugins.queue.MaxCPUUsageFirst;
import com.github.doslab.aladdin.core.utils.ClassUtil;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public abstract class Scheduler {

	protected final NodeCache nodeCache;
	
	protected final KubernetesClient client;
	
	protected final Map<String, NodeSelector> pluginMapper = new HashMap<>();
	
	public Scheduler(KubernetesClient client, String pkg) throws Exception {
		super();
		this.client = client;
		this.nodeCache = NodeCache.createNodeCache(client);
		
		for (Class<?> clazz : ClassUtil.scan(pkg)) {
			if (clazz.getSimpleName().equals("")) {
				continue;
			}
			
			pluginMapper.put(clazz.getSimpleName().toLowerCase(), 
							(NodeSelector) clazz.newInstance());
		}
	}

	public NodeSelector getNodeSelector(JsonNode node) {
		if (node.has("annotations") && node.get("annotations").has("schedulerPolicy")) {
			String policy = node.get("annotations").get("schedulerPolicy").asText();
			return pluginMapper.get(policy);
		}
		return pluginMapper.get(MaxCPUUsageFirst.class.getSimpleName().toLowerCase());
	}
	
	public abstract String doScheduling(JsonNode pod);
}
