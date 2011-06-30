package com.google.code.smallcrab.swing.demo.jtable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

class ComboTableCellRenderer implements ListCellRenderer, TableCellRenderer {
	DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();

	DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();

	private void configureRenderer(JLabel renderer, Object value) {
		if ((value != null) && (value instanceof Color)) {
			renderer.setText(value.toString());
			renderer.setBackground((Color) value);
		} else {
			renderer.setText((String) value);
		}
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		listRenderer = (DefaultListCellRenderer) listRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		configureRenderer(listRenderer, value);
		return listRenderer;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		tableRenderer = (DefaultTableCellRenderer) tableRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		configureRenderer(tableRenderer, value);
		return tableRenderer;
	}
}

class ColorTableModel extends AbstractTableModel {

	Object rowData[][] = { { "1", Color.RED, true }, { "2", Color.BLUE, false }, { "3", Color.GREEN, false }, { "4", Color.MAGENTA, false }, { "5", Color.PINK, false } };

	String columnNames[] = { "English", "Color", "checked" };

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getRowCount() {
		return rowData.length;
	}

	public Object getValueAt(int row, int column) {
		return rowData[row][column];
	}

	public Class getColumnClass(int column) {
		return (getValueAt(0, column).getClass());
	}

	public void setValueAt(Object value, int row, int column) {
		rowData[row][column] = value;
	}

	public boolean isCellEditable(int row, int column) {
		return (column != 0);
	}
}

public class EditableColorColumn {

	public static void main(String args[]) {
		Color choices[] = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA };
		ComboTableCellRenderer renderer = new ComboTableCellRenderer();
		JComboBox comboBox = new JComboBox(choices);
		comboBox.setRenderer(renderer);

		TableCellEditor editor = new DefaultCellEditor(comboBox);

		JFrame frame = new JFrame("Editable Color Table");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TableModel model = new ColorTableModel();
		JTable table = new JTable(model);
		TableColumn column = table.getColumnModel().getColumn(1);
		column.setCellRenderer(renderer);
		column.setCellEditor(editor);

		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 150);
		frame.setVisible(true);
	}
}
