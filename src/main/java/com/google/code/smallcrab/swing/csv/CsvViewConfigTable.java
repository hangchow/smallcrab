package com.google.code.smallcrab.swing.csv;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.google.code.smallcrab.swing.ConfigTable;
public class CsvViewConfigTable extends ConfigTable {
	private static final long serialVersionUID = -250334773896895882L;
	

	/**
	 * @param csvColumns
	 */
	public CsvViewConfigTable(String[] csvColumns) {
		super(new CsvViewTableModel(csvColumns));
	    TableColumn column = this.getColumnModel().getColumn(1);
	    TableCellEditor editor = new DefaultCellEditor(new JComboBox(CsvViewTableModel.axises));
	    column.setCellEditor(editor);
	}

	
	 

}