/**
 * 
 */
package smallcrab.config.matcher.accesslog;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public abstract class AbstractContainALMatcher extends ALLineMatcher {

	protected String contain;

	protected boolean contained;

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		if (StringKit.isEmpty(contain)) {
			throw new IllegalArgumentException(contain);
		}
		this.contain = contain;
	}

	/**
	 * @param contain
	 */
	public AbstractContainALMatcher(String contain) {
		super();
		this.setContain(contain);
	}

	/**
	 * 
	 */
	public AbstractContainALMatcher() {
		super();
	}

	@Override
	public boolean match(ALPackage pac) {
		String v = getAllView(pac);
		if (StringKit.isEmpty(v)) {
			return false; // v is empty but contain is not empty, not equals
		}
		return v.contains(contain);
	}

	protected abstract String getAllView(ALPackage pac);

}
