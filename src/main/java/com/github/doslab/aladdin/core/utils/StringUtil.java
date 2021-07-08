/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin.core.utils;

import java.util.Set;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class StringUtil  {

	public static void checkNull(String name, String inputValue) throws Exception {
		if ( inputValue == null || "".equals(inputValue)) {
			throw new Exception("missing the parameter '" + name + "'");
		}
	}
	
	public static void checkItem(String name, Set<String> supportValues, String inputValue) throws Exception {
		if ( !supportValues.contains(inputValue)) {
			throw new Exception("missing the parameter '" + name + "', or it's value should be in " + supportValues);
		}
	}
}
