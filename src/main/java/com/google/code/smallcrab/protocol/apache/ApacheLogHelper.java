/**
 * 
 */
package com.google.code.smallcrab.protocol.apache;

import com.google.code.smallcrab.utils.UrlKit;

/**
 * @author lin.wangl
 * 
 */
public class ApacheLogHelper {
	private static final char SEPARATOR_CHAR = ' ';

	public static void fillDefaultSplits(String line, String[] target) {
		split(line, target);
	}

	public static void fillDetailSplits(String line, String[] target) {
		split(line, target);
	}

	/**
	 * @param line
	 *            e.g: 10.13.42.182 13322 - [04/Nov/2010:15:55:09 +0800] "GET http://10.232.37.21/container/abc" 404 334 "www.test.com" "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8 QQDownload/1.7"
	 *            -->
	 *            %h %D - %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-agent}i\""	
	 * 
	 * @return String[]{host0, period1, time2, method3, url4, code5, size6, referrer7, agent8}
	 */
	public static String[] defaultSplit(String line) {
		String[] segs = new String[9];
		split(line, segs);
		return segs;
	}

	private static void split(String line, String[] segs) {
		int start = 0;
		int end = line.indexOf(SEPARATOR_CHAR);
		int segIndex = 0;
		// host
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 1;
		end = line.indexOf(SEPARATOR_CHAR, start);
		// period
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 4;// ' - ['
		end = line.indexOf(']', start);
		// time
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 3; // '] "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		// method
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 1;// ' '
		end = UrlKit.findQut(start, line);
		// url
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 2; // '" '
		end = line.indexOf(SEPARATOR_CHAR, start);
		// code
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 1;
		end = line.indexOf(SEPARATOR_CHAR, start);
		// size
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 2; // ' "'
		end = UrlKit.findQut(start, line);
		// referrer
		buildSplits(line, segs, segIndex++, start, end);
		start = end + 3;// '" "
		end = UrlKit.findQut(start, line);
		// agent
		buildSplits(line, segs, segIndex, start, end);
	}

	private static void buildSplits(String line, String[] segs, int segIndex, int start, int end) {
		segs[segIndex] = line.substring(start, end);
	}

}
