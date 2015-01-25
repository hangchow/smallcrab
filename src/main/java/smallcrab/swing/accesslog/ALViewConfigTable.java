/**
 * 
 */
package smallcrab.swing.accesslog;

import smallcrab.swing.ConfigTable;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ALViewConfigTable extends ConfigTable {
	private static final long serialVersionUID = 2257646661345167680L;
	
	/**
	 * @param tableModel
	 */
	public ALViewConfigTable() {
		super(new ALViewTableModel());
	}

	
}
