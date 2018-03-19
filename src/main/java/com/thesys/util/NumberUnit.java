package com.thesys.util;

import java.text.DecimalFormat;

public class NumberUnit {

	/**
	 * 数字千位符显示
	 * @param formatStr 转换格式
	 * @param number
	 * @return
	 */
	public static String format(Double number,String formatStr) {
		DecimalFormat df = new DecimalFormat(formatStr);
		return df.format(number);
	}
}
