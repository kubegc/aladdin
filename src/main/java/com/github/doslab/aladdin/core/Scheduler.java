/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date 2021Äê4ÔÂ13ÈÕ
 */
public abstract class Scheduler {

	protected final String name;

	protected Algorithm algorithm;

	protected final KubernetesClient client;

	public Scheduler(KubernetesClient client, String name) throws Exception {
		super();
		this.name = name;
		this.client = client;
		initAlgorithm(getSchedulerType() + "-scheduler-cm");
	}

	public String getName() {
		return name;
	}

	public KubernetesClient getClient() {
		return client;
	}

	public String doScheduling(JsonNode pod) throws Exception {
		JsonNode nodes = client.listResources("Node").get("items");
		Set<JsonNode> set = new HashSet<>();
		for (int i = 0; i < nodes.size(); i++) {
			set.add(nodes.get(i));
		}
		return algorithm.getHost(set);
	}

	public abstract void initAlgorithm(String configMap) throws Exception;

	public abstract String getSchedulerType();

}
