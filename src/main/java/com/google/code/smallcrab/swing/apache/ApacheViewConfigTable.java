/**
 * 
 */
package com.google.code.smallcrab.swing.apache;

import com.google.code.smallcrab.swing.ConfigTable;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ApacheViewConfigTable extends ConfigTable {
	private static final long serialVersionUID = 2257646661345167680L;
	
	/**
	 * @param tableModel
	 */
	public ApacheViewConfigTable() {
		super(new ApacheViewTableModel());
	}

	
}
