/**
 * 
 */
package smallcrab.config.matcher.accesslog;

import smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALAgentMatcher extends AbstractContainALMatcher {

	/**
	 * 
	 */
	public ALAgentMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALAgentMatcher(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheMatcher#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getAgent();
	}

}
