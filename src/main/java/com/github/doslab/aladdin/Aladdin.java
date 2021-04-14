/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin;

import com.github.doslab.aladdin.core.impl.FlowBasedScheduler;
import com.github.doslab.aladdin.core.impl.QueueBasedScheduler;
import com.github.doslab.aladdin.core.watchers.NodeWatcher;
import com.github.doslab.aladdin.core.watchers.PodWatcher;
import com.github.kubesys.KubernetesClient;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class Aladdin {
    
	/**
	 * schedulerName
	 * schedulerLatency
	 * schedulerPolicy
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		
		KubernetesClient client = new KubernetesClient(
				System.getenv("kubeUrl"), 
				System.getenv("token"));
		
		
		client.watchResources("Node", new NodeWatcher(client));
		client.watchResources("Pod", new PodWatcher(client, 
							new QueueBasedScheduler(client, "com.github.doslab.aladdin.core.plugins.queue"), 
							new FlowBasedScheduler(client, "com.github.doslab.aladdin.core.plugins.flow")));
    }
}
