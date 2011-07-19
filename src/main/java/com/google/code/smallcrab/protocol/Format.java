/**
 * 
 */
package com.google.code.smallcrab.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-7-5
 */
public class Format {

	public static DateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public static DateFormat getShortDateFormat() {
		return new SimpleDateFormat("yyMMdd HH:mm:ss");
	}

	public static Calendar getCalendar() {
		return new GregorianCalendar();
	}

}
