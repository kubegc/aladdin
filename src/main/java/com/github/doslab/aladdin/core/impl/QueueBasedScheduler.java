/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.Scheduler;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class QueueBasedScheduler extends Scheduler {

	public QueueBasedScheduler(KubernetesClient client, String pkg) throws Exception {
		super(client, pkg);
	}

	public String doScheduling(JsonNode pod) {
		return getNodeSelector(pod).getHost(nodeCache.getNodes());
	}

}
