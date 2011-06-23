package com.google.code.smallcrab.swing.csv;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.google.code.smallcrab.swing.TableRowRenderer;
import com.google.code.smallcrab.swing.utils.ColumnResizer;

public class CsvViewTable extends JTable {
	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * @param tableModel
	 * @param dm
	 * @param cm
	 */
	public CsvViewTable(AbstractTableModel tableModel) {
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
			tc.setCellRenderer(new TableRowRenderer());
		}
	}

}