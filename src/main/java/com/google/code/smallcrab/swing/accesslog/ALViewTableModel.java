/**
 * 
 */
package com.google.code.smallcrab.swing.accesslog;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ALViewTableModel extends ALConfigTableModel implements TableModelListener {

	private static final long serialVersionUID = 1088594001477114033L;
	private final String[] header = new String[] { "option", "value", "show" };
	private final Object[][] data = { { DOMAIN, "", false }, { QUERY, "", false }, { PATH, "", false }, { REFERRER, "", false }, { CODE, "", false }, { METHOD, "", false }, { AGENT, "", false }, { IP, "", false }, { ALL, "", false } };

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return header[columnIndex];
	}

	@Override
	public int getRowCount() {
		return getData().length;
	}

	protected Object[][] getData() {
		return data;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getData()[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		getData()[rowIndex][columnIndex] = aValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.DELETE:
		case TableModelEvent.UPDATE:
			if (e.getColumn() == TableModelEvent.ALL_COLUMNS)
				;
			else
				;
			break;
		case TableModelEvent.INSERT:
			break;
		default:
		}

	}

}
