/**
 * 
 */
package com.google.code.smallcrab.convert.apache;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.smallcrab.convert.apache.ApacheLogFormatAnalyzerConverter;

/**
 * @author lin.wangl
 * 
 */
public class ApacheLogFormatJavaConverterTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	private final static String logFormat1 = "%h %D - %t \"%m http://%V%U%q\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"";

	/**
	 * Test method for
	 * {@link com.google.code.smallcrab.convert.apache.ApacheLogFormatAnalyzerConverter#convert(java.lang.String, java.lang.String, String)}
	 * .
	 */
	@Test
	public void testConvert() {
		String cmd = "output[0] = %q.split('&').match(\"appkey=\")\n"
				+ "filter[0] = %V.equals(\"container.open.taobao.com\")";
		ApacheLogFormatAnalyzerConverter javaConvert = new ApacheLogFormatAnalyzerConverter();
		String java = javaConvert.convert(logFormat1, cmd, null);
	}
}
