/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin;

import com.github.doslab.aladdin.core.Scheduler;
import com.github.doslab.aladdin.core.impl.QueueBasedScheduler;
import com.github.doslab.aladdin.core.observers.PodObserver;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class Aladdin {
    
	public static void main( String[] args ) throws Exception {
		
		KubernetesClient client = new KubernetesClient(
				System.getenv("kubeUrl"), 
				System.getenv("token"));
		
		client.watchResources("Pod", new PodObserver(client, 
							new QueueBasedScheduler(client), 
							new QueueBasedScheduler(client)));
    }
}
