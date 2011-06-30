package com.google.code.smallcrab.swing.csv;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;

import com.google.code.smallcrab.swing.ConfigTable;

public class CsvViewConfigTable extends ConfigTable {
	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * @param csvColumns
	 */
	public CsvViewConfigTable(String[] csvColumns) {
		super(new CsvViewTableModel(csvColumns));
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		Object value = super.getValueAt(row, column);
		if (value != null) {
			if (value instanceof JComboBox) {
				return new DefaultCellEditor((JComboBox) value);
			}
			return getDefaultEditor(value.getClass());
		}
		return super.getCellEditor(row, column);
	}

}