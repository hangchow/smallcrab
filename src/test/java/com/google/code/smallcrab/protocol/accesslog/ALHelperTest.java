package com.google.code.smallcrab.protocol.accesslog;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ALHelperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefaultSplit() {
		String log = "49.71.178.171 - - [19/Jan/2015:00:00:01 +0800] \"POST" +
				" /app/task/uploadcontact?_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5" +
				" HTTP/1.1\"" +
				" 200 134 \"-\" \"Mozilla/4.0\" in.itugo.com - - 5ecba6c8e90662978e5fe33a53f06119 - - 4abff73199909db5 0.158";
		Map<String, String> segs = ALHelper.defaultSplit(log);
		assertEquals("in.itugo.com", segs.get("host"));
		assertEquals("49.71.178.171", segs.get("ip"));
		assertEquals("0.158", segs.get("period"));
		assertEquals("19/Jan/2015:00:00:01 +0800", segs.get("time"));
		assertEquals("POST", segs.get("method"));
		assertEquals("/app/task/uploadcontact?_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5", segs.get("request"));
		assertEquals("200", segs.get("status"));
		assertEquals("134", segs.get("size"));
		assertEquals("Mozilla/4.0",segs.get("agent"));
	}
}
