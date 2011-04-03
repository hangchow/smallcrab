/**
 * 
 */
package com.google.code.smallcrab.swing.apache;

import java.awt.Color;

import javax.swing.BorderFactory;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ApacheViewConfigTable extends ApacheConfigTable {

	/**
	 * @param tableModel
	 */
	public ApacheViewConfigTable() {
		super(new ApacheViewTableModel());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		//this.setFixColumnWidth(0, 70);
		this.setFixColumnWidth(1, 200);
		//this.setFixColumnWidth(2, 30);
		this.paintRow();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2257646661345167680L;
}
