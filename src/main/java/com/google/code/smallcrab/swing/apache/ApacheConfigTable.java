/**
 * 
 */
package com.google.code.smallcrab.swing.apache;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.google.code.smallcrab.swing.utils.ColumnResizer;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public abstract class ApacheConfigTable extends JTable {

	private static class RowRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 2436478513031618088L;

		public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (row % 2 == 0)
				setBackground(Color.LIGHT_GRAY);
			else
				setBackground(Color.WHITE);
			return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
		}
	}

	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * @param tableModel
	 * @param dm
	 * @param cm
	 */
	public ApacheConfigTable(ApacheViewTableModel tableModel) {
		super(tableModel);
		this.setRowHeight(20);
		ColumnResizer.adjustColumnPreferredWidths(this, 1);
	}

	/**
	 * 将列设置为固定宽度。//fix table column width
	 * 
	 */
	public void setFixColumnWidth(int columnIndex, int width) {
		this.setAutoResizeMode(AUTO_RESIZE_OFF);
		TableColumnModel tcm = this.getTableHeader().getColumnModel();
		TableColumn tc = tcm.getColumn(columnIndex);
		tc.setPreferredWidth(width);
	}

	public void paintRow() {
		TableColumnModel tcm = this.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n - 1; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new RowRenderer());
		}
	}

}
