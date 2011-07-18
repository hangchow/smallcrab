/**
 * 
 */
package com.google.code.smallcrab.swing.csv;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-7-18
 */
public class CsvDataSourceConfigPanel extends JPanel {
	private static final long serialVersionUID = 6854447303405175550L;

	private CsvDataSourceTable csvDataSourceConfigTable;

	private JScrollPane scrollPane;

	public CsvDataSourceConfigPanel(String[] csvColumns) {
		this.csvDataSourceConfigTable = new CsvDataSourceTable(csvColumns);
		this.scrollPane = new JScrollPane(csvDataSourceConfigTable);
		Border border = BorderFactory.createTitledBorder("data source settings");
		this.scrollPane.setBorder(border);
		this.add(scrollPane);

	}

	public TableModel getDataSourceModel() {
		return this.csvDataSourceConfigTable.getModel();
	}

	public CsvDataSourceTable getDataSourceTable() {
		return this.csvDataSourceConfigTable;
	}

	public void setCsvColuns(String[] csvColumns) {
		this.csvDataSourceConfigTable.setModel(new CsvDataSourceTableModel(csvColumns));
		this.csvDataSourceConfigTable.paintRow();
		this.csvDataSourceConfigTable.repaint();
	}

}
