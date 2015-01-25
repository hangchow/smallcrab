/**
 * 
 */
package smallcrab.swing.accesslog;

import smallcrab.swing.ConfigTable;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ALMatchConfigTable extends ConfigTable {
	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * 
	 */
	public ALMatchConfigTable() {
		super(new ALMatchTableModel());
	}

}
