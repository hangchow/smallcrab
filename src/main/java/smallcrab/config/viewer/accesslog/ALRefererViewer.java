/**
 * 
 */
package smallcrab.config.viewer.accesslog;

import smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALRefererViewer extends AbstractContainALViewer {

	/**
	 * 
	 */
	public ALRefererViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALRefererViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView()
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getReferer();
	}

}
