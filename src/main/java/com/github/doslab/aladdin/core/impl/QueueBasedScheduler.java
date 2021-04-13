/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.Scheduler;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021��4��13��
 */
public class QueueBasedScheduler extends Scheduler {

	public QueueBasedScheduler(KubernetesClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	public String doScheduling(JsonNode pod) {
		return null;
	}

}