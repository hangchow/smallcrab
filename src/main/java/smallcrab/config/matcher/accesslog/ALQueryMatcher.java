/**
 * 
 */
package smallcrab.config.matcher.accesslog;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.UrlKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public class ALQueryMatcher extends ALLineMatcher {
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param key
	 * @param value
	 */
	public ALQueryMatcher(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.LineMatcher#match(java.lang.String, com.google.code.smallcrab.scan.Linepac)
	 */
	public boolean match(ALPackage pac) {
		String query = pac.getQuery();
		String pv = UrlKit.extractParameterValue(query, key);
		return value.equals(pv);
	}
}
