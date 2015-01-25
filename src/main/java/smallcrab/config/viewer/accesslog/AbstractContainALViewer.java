/**
 * 
 */
package smallcrab.config.viewer.accesslog;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public abstract class AbstractContainALViewer extends ALLineViewer {

	protected String contain;
	
	protected boolean contained;

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
		if (StringKit.isNotEmpty(contain)) {
			this.contained = true;
		}
	}

	/**
	 * @param contain
	 */
	public AbstractContainALViewer(String contain) {
		super();
		this.setContain(contain);
	}

	/**
	 * 
	 */
	public AbstractContainALViewer() {
		super();
	}

	@Override
	public String view(ALPackage pac) {
		String allView = this.getAllView(pac);
		String view = allView;
		if (contained && StringKit.isNotEmpty(allView)) { // need check allview contains
			if (!allView.contains(contain)) {
				view = null; // allview not contains, set view null
			}
		}
		return view;
	}

	protected abstract String getAllView(ALPackage pac);

}
