/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core;

import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public abstract class Algorithm {

	public abstract String getHost(Collection<JsonNode> nodes);
	
	public long getFreeCPUNum(JsonNode json) {
		return stringToLong(json.get("status")
				.get("allocatable").get("cpu").asText());
	}
	
	public long getFreeRAMSize(JsonNode json) {
		return stringToLong(json.get("status")
				.get("allocatable").get("memory").asText());
	}
	
	/**
	 * @param value           node available capacity, such as CPU or RAM
	 * @return                real value    
	 */
	protected long stringToLong(String value) {
		long weight = 1;
		if (value.endsWith("Ki")) {
			value = value.substring(0, value.length() - 2);
			weight = 1;
		} else if (value.endsWith("Mi")) {
			value = value.substring(0, value.length() - 2);
			weight = 1024;
		} else if (value.endsWith("Gi")) {
			value = value.substring(0, value.length() - 2);
			weight = 1024*1024;
		} else if (value.endsWith("Ti")) {
			value = value.substring(0, value.length() - 2);
			weight = 1024*1024*1024;
		} 
		
		return Long.parseLong(value) * weight;
	}
}
