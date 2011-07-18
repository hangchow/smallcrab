package com.google.code.smallcrab.utils;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author seanlinwang
 * @email xalinx at gmail dot com
 * @date Mar 10, 2011
 * 
 */
public class StringKitTest {

	@Test
	public void testCapitalize() {
		Assert.assertEquals(null, StringKit.capitalize(null));
		Assert.assertEquals("", StringKit.capitalize(""));
		Assert.assertEquals(" ", StringKit.capitalize(" "));
		Assert.assertEquals("Test", StringKit.capitalize("test"));
		Assert.assertEquals("Test", StringKit.capitalize("Test"));
		Assert.assertEquals("T", StringKit.capitalize("t"));
		Assert.assertEquals("T", StringKit.capitalize("T"));
		Assert.assertEquals("ATest", StringKit.capitalize("aTest"));
		Assert.assertEquals("ATest", StringKit.capitalize("ATest"));
	}

	@Test
	public void testSplit() {
		String[] items = StringKit.split("a b c", ' ');
		Assert.assertTrue(Arrays.equals(items, new String[] { "a", "b", "c" }));
		items = StringKit.split("1860112241940862,10224261,,0,2011-7-18 0:00:02", ',');
		Assert.assertTrue(Arrays.equals(items, new String[] { "1860112241940862", "10224261", "0", "2011-7-18 0:00:02" }));
	}

	@Test
	public void testSplitPreserveAllTokens() {
		String[] items = StringKit.splitPreserveAllTokens("a b c", ' ');
		Assert.assertTrue(Arrays.equals(items, new String[] { "a", "b", "c" }));
		items = StringKit.splitPreserveAllTokens("1860112241940862,10224261,,0,2011-7-18 0:00:02", ',');
		Assert.assertTrue(Arrays.equals(items, new String[] { "1860112241940862", "10224261", "", "0", "2011-7-18 0:00:02" }));
	}

	public static void main(String[] args) {
	}
}
