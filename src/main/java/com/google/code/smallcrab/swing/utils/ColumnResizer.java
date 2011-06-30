/**
 * 
 */
package com.google.code.smallcrab.swing.utils;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * @author xalinx at gmail dot com
 * @date Jan 5, 2011
 */
public class ColumnResizer {
	/**
	 * @param table
	 * @param pad
	 * @return
	 */
	public static void adjustColumnPreferredWidths(JTable table, int pad) {
		// strategy - get max width for cells in column and make that the preferred width
		TableColumnModel columnModel = table.getColumnModel();
		for (int col = 0; col < table.getColumnCount(); col++) {
			int maxwidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer rend = table.getCellRenderer(row, col);
				Object value = table.getValueAt(row, col);
				Component comp = rend.getTableCellRendererComponent(table, value, false, false, row, col);
				maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
			} // for row
			TableColumn column = columnModel.getColumn(col);
			column.setPreferredWidth(maxwidth + pad * 2);
		} // for col
	}

	/**
	 * @param table
	 * @param pad
	 * @return
	 */
	public static int getTablePreferredWidth(JTable table) {
		TableColumnModel columnModel = table.getColumnModel();
		int allPreferredWidth = 0;
		for (int col = 0; col < table.getColumnCount(); col++) {
			TableColumn column = columnModel.getColumn(col);
			allPreferredWidth += column.getPreferredWidth();
		} // for col
		return allPreferredWidth;
	}

	/**
	 * 将列设置为固定宽度。//fix table column width
	 * 
	 */
	public static void setFixColumnWidth(JTable table, int columnIndex, int width) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		TableColumn tc = tcm.getColumn(columnIndex);
		tc.setPreferredWidth(width);
	}

}
