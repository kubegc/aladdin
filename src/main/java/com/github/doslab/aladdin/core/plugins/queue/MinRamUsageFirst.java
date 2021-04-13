/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.plugins.queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.doslab.aladdin.core.plugins.NodeSelector;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class MinRamUsageFirst extends NodeSelector  {

	@Override
	public String getHost(Collection<JsonNode> nodes) {
		JsonNode[] nodeArray = nodes.toArray(new JsonNode[] {});
		Arrays.sort(nodeArray, new Comparator<JsonNode>() {

			@Override
			public int compare(JsonNode o1, JsonNode o2) {
				
				return (getFreeCPUNum(o2) - getFreeCPUNum(o1) < 0) ? -1 : 1;
			}
			
		});
		return null;
	}
}
