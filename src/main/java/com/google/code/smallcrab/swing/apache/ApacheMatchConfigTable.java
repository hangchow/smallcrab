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
public class ApacheMatchConfigTable extends ApacheConfigTable {
	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * 
	 */
	public ApacheMatchConfigTable() {
		super(new ApacheMatchTableModel());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setFixColumnWidth(1, 200);
		this.paintRow();
	}

}
