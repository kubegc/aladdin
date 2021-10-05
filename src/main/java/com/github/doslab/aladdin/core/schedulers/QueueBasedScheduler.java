/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.schedulers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.Algorithm;
import com.github.doslab.aladdin.core.Scheduler;
import com.github.doslab.aladdin.core.algorithms.MaxCPUUsageFirst;

import io.github.kubesys.kubeclient.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class QueueBasedScheduler extends Scheduler {

	public QueueBasedScheduler(KubernetesClient client, String name) throws Exception {
		super(client, name);
	}

	@Override
	public void initAlgorithm(String configMap) throws Exception{
		try {
			JsonNode node = client.getResource("ConfigMap", "default", configMap);
			String classname = "com.github.doslab.aladdin.core.algorithms."
					+ node.get("data").get("policy").asText() + "UsageFirst";
			algorithm = (Algorithm) Class.forName(classname).newInstance();
		} catch (Exception ex) {
			System.err.println("unsupport policy, use default policy");
			algorithm = new MaxCPUUsageFirst();
		}
	}

	@Override
	public String getSchedulerType() {
		return "queue";
	}
}
