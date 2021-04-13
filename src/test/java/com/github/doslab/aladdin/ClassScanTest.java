/**
 * Copyright (2021, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.doslab.aladdin;


import com.github.doslab.aladdin.core.plugins.queue.MaxCPUUsageFirst;
import com.github.doslab.aladdin.core.utils.ClassUtil;

/**
 * @author wuheng@iscas.ac.cn
 * @date   2021Äê4ÔÂ13ÈÕ
 */
public class ClassScanTest  {

	public static void main(String[] args) {
		System.out.println(ClassUtil.scan(MaxCPUUsageFirst.class.getPackage().getName()));
	}

}
