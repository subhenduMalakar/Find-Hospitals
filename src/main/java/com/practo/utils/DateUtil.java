package com.practo.utils;

import java.util.Date;

public class DateUtil {
	//Get TimeStamp
	public static String getTimeStamp() {
		Date date = new Date();
		return date.toString().replaceAll(":", "_").replaceAll(" ", "_");
	}
}
