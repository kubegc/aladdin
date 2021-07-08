/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin;

import java.util.HashMap;
import java.util.Map;

import com.github.doslab.aladdin.core.Scheduler;
import com.github.doslab.aladdin.core.schedulers.FlowBasedScheduler;
import com.github.doslab.aladdin.core.schedulers.QueueBasedScheduler;
import com.github.doslab.aladdin.core.utils.StringUtil;
import com.github.doslab.aladdin.core.watchers.PodWatcher;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class Aladdin {
    
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		
		String kubeUrl   = System.getenv("kubeUrl");
		StringUtil.checkNull("kubeUrl", kubeUrl);
		
		String kubeToken = System.getenv("token");
		StringUtil.checkNull("token", kubeToken);
		
		String schedName = System.getenv("schedulerName");
		StringUtil.checkNull("schedulerName", schedName);
		
		KubernetesClient client = new KubernetesClient(kubeUrl, kubeToken);
		Map<String, Scheduler> schedulers = initSchedulers(schedName, client);
		
		String schedType = System.getenv("schedulerType");
		StringUtil.checkItem("schedulerType", schedulers.keySet(), schedType);
		
		client.watchResources("Pod", new PodWatcher(schedulers.get("schedType")));
    }

	private static Map<String, Scheduler> initSchedulers(String schedName, KubernetesClient client)
			throws Exception {
		Map<String, Scheduler> schedulers = new HashMap<>();
		schedulers.put("queue", new QueueBasedScheduler(client, schedName));
		schedulers.put("flow", new FlowBasedScheduler(client, schedName));
		return schedulers;
	}
}
