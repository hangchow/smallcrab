package com.google.code.smallcrab.protocol.accesslog;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

public class ALPackageTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDefaultSplit() {
		String log = "49.71.178.171 - - [19/Jan/2015:00:00:01 +0800] \"POST" + " /app/task/uploadcontact?_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5" + " HTTP/1.1\"" + " 200 134 \"-\" \"Mozilla/4.0\" in.itugo.com - - 5ecba6c8e90662978e5fe33a53f06119 - - 4abff73199909db5 0.158";
		ALPackage pac = new ALPackage();
		pac.split(log);
		assertEquals("in.itugo.com", pac.getHost());
		assertEquals("49.71.178.171", pac.getIP());
		assertEquals("0.158", pac.getPeriod());
		assertEquals("19/Jan/2015:00:00:01 +0800", pac.getTime());
		assertEquals("POST", pac.getMethod());
		assertEquals("/app/task/uploadcontact?_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5", pac.getRequest());
		assertEquals("/app/task/uploadcontact", pac.getPath());
		assertEquals("_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5", pac.getQuery());
		assertEquals("200", pac.getStatus());
		assertEquals("134", pac.getSize());
		assertEquals("Mozilla/4.0", pac.getAgent());
	}

}
