package com.google.code.smallcrab.swing.csv;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.google.code.smallcrab.swing.ConfigTable;

public class CsvDataSourceTable extends ConfigTable {
	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * @param csvColumns
	 */
	public CsvDataSourceTable(String[] csvColumns) {
		super(new CsvDataSourceTableModel(csvColumns));
	}

	public void paintRow() {
		super.paintRow();
		TableColumn column = this.getColumnModel().getColumn(1);
		TableCellEditor editor = new DefaultCellEditor(new JComboBox(CsvDataSourceTableModel.axises));
		column.setCellEditor(editor);
	}

}