/**
 * 
 */
package smallcrab.config.viewer.accesslog;

import smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALMethodViewer extends AbstractContainALViewer {

	/**
	 * 
	 */
	public ALMethodViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALMethodViewer(String contain) {
		super(contain);
	}

	/* (non-Javadoc)
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getMethod();
	}

}
