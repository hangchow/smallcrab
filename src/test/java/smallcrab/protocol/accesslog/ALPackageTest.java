package smallcrab.protocol.accesslog;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

import smallcrab.protocol.accesslog.ALPackage;

public class ALPackageTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDefaultSplit() {
		String log = "49.71.178.171 - - [19/Jan/2015:00:00:01 +0800] \"POST" + //
				" /app/task/uploadcontact?_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5" + //
				" HTTP/1.1\"" + " 200 134 \"-\" \"Mozilla/4.0\" in.itugo.com - - 5ecba6c8e90662978e5fe33a53f06119 - - 4abff73199909db5 0.158";
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
	
	
	@Test 	
	public void tesGPSMerge() {
		String log = "49.71.178.171 - - [19/Jan/2015:00:00:01 +0800] \"POST" + //
				" /app/task/uploadcontact?_net=WIFI&_token=5ecba6c8e90662978e5fe33a53f06119&_uuid=4abff73199909db5&action_gps=113.006482%2C28.117761" + //
				" HTTP/1.1\"" + " 200 134 \"-\" \"Mozilla/4.0\" in.itugo.com - - 5ecba6c8e90662978e5fe33a53f06119 - - 4abff73199909db5 0.158";
		ALPackage pac = new ALPackage();
		pac.split(log);
		log = "223.83.216.58 - - [19/Jan/2015:00:00:01 +0800] \"GET"+ // 
		" /app/paster/revision?_net=wifi&_platform=iPhone&_promotion_channel=App%20Store&_req_from=oc&_source=ios" + //
		"&_token=29037923ca33f0454c0c4d933f641a51&_uiid=2A0C290E-BCF4-43F2-9060-661A724D7F1C&_uuid=2A0C290E-BCF4-43F2-9060-661A724D7F1C&_version=1.6.8" + //
		"&action_gps=0.000000%2C0.000000&sign=1.0cdaa0c803ac218a58e37d70c8c6dcf3b1421596798 HTTP/1.1\"" + //
		" 200 106 \"-\" \"b538b/1.6.8 (iPhone; iOS 8.1; Scale/2.00)\"" + //
		" in.itugo.com rmornpln2leiov4vd2brj61gr4 29037923ca33f0454c0c4d933f641a51 29037923ca33f0454c0c4d933f641a51 - - 2A0C290E-BCF4-43F2-9060-661A724D7F1C 0.027";
	}

}
