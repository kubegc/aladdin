/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core;

import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class PodQueue extends LinkedList<JsonNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1229338918951444005L;

	@Override
	public void addLast(JsonNode e) {
		super.addLast(e);
	}

	@Override
	public JsonNode poll() {
		return super.poll();
	}

}
