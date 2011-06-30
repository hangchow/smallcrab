package com.google.code.smallcrab.protocol.apache;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.smallcrab.protocol.apache.ApacheLogHelper;

public class ApacheLogHelperTest {

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
		String log = "125.92.125.140 18172 - [04/Nov/2010:09:54:10 +0800] \"GET http://container.api.taobao.com/topcontainer/container?appkey=12029234&tracelog=jhleftmenu\" 302 - \"http://jianghu.taobao.com/admin/plugin.htm?appkey=12029234&tracelog=jhleftmenu\" \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; QQDownload 663; znwb6500; InfoPath.2; .NET CLR 2.0.50727; TheWorld)\"";
		String[] segs = ApacheLogHelper.defaultSplit(log);
		assertEquals("125.92.125.140", segs[0]);
		assertEquals("18172", segs[1]);
		assertEquals("04/Nov/2010:09:54:10 +0800", segs[2]);
		assertEquals("GET", segs[3]);
		assertEquals(
				"http://container.api.taobao.com/topcontainer/container?appkey=12029234&tracelog=jhleftmenu",
				segs[4]);
		assertEquals("302", segs[5]);
		assertEquals("-", segs[6]);
		assertEquals(
				"http://jianghu.taobao.com/admin/plugin.htm?appkey=12029234&tracelog=jhleftmenu",
				segs[7]);
		assertEquals(
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; QQDownload 663; znwb6500; InfoPath.2; .NET CLR 2.0.50727; TheWorld)",
				segs[8]);
	}
}
