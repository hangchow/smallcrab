/**
 * 
 */
package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.scan.LineSpliter;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public class ApacheSpliter implements LineSpliter {
	/**
	 * String[]{host0, period1, time2, method3, url4, code5, size6, referrer7, agent8}
	 */
	private String[] segs;

	private String line;

	private String path;

	private String query;

	private String domain;

	/*
	 * @notthreadsafe
	 * 
	 * @see com.google.code.smallcrab.scan.LineSpliter#split(java.lang.String, int)
	 */
	@Override
	public void split(String line) {
		this.segs = ApacheLogHelper.defaultSplit(line);
		this.line = line;
	}

	/**
	 * @return query from request url
	 */
	public String getQuery() {
		if (query == null) {
			query = UrlKit.extractQuery(segs[4]);
		}
		return query;
	}

	/**
	 * @return path from request url
	 */
	public String getPath() {
		if (path == null) {
			path = UrlKit.extractPath(segs[4]);
		}
		return path;
	}

	/**
	 * @return
	 */
	public String getDomain() {
		if (domain == null) {
			domain = UrlKit.extractDomain(segs[4]);
		}
		return domain;
	}

	/**
	 * @return request host/ip
	 */
	public String getIP() {
		return segs[0];
	}

	/**
	 * @return request period
	 */
	public String getPeriod() {
		return segs[1];
	}

	/**
	 * @return request time
	 */
	public String getTime() {
		return segs[2];
	}

	/**
	 * @return request method
	 */
	public String getMethod() {
		return segs[3];
	}

	/**
	 * @return request url
	 */
	public String getUrl() {
		return segs[4];
	}

	/**
	 * @return response code
	 */
	public String getCode() {
		return segs[5];
	}

	/**
	 * @return request body size
	 */
	public String getSize() {
		return segs[6];
	}

	/**
	 * @return referrer url
	 */
	public String getReferrer() {
		return segs[7];
	}

	/**
	 * @return http client agent
	 */
	public String getAgent() {
		return segs[8];
	}

	/**
	 * @return
	 */
	public String getLine() {
		return line;
	}

}
