/**
 * 
 */
package com.google.code.smallcrab.protocol.apache;

import java.text.ParseException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.google.code.smallcrab.protocol.Format;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-7-18
 */
public class FormatTest {

	@Test
	public void testFormatDate() {
		Date date = null;
		try {
			date = Format.getDateFormat().parse("2011-7-18 0:00:02");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assert.assertEquals("2011-07-18 00:00:02", Format.getDateFormat().format(date));
		try {
			date = Format.getDateFormat().parse("2011-07-18 0:00:02");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assert.assertEquals("2011-07-18 00:00:02", Format.getDateFormat().format(date));
		try {
			date = Format.getDateFormat().parse("2011-07-18 00:00:02");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assert.assertEquals("2011-07-18 00:00:02", Format.getDateFormat().format(date));
		
	}

}
