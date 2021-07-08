/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public abstract class Scheduler {

	protected final String name;
	
	protected final KubernetesClient client;
	
	public Scheduler(KubernetesClient client, String name) throws Exception {
		super();
		this.name = name;
		this.client = client;
	}

	public abstract String doScheduling(JsonNode pod);
}
