package com.google.code.smallcrab.swing.demo;

/*
Definitive Guide to Swing for Java 2, Second Edition
By John Zukowski     
ISBN: 1-893115-78-X
Publisher: APress
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
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

public class ChooserTableSample {

  public static void main(String args[]) {

    JFrame frame = new JFrame("Editable Color Table");
    TableModel model = new ColorTableModel();
    JTable table = new JTable(model);

    TableColumn column = table.getColumnModel().getColumn(3);

    ComboTableCellRenderer renderer = new ComboTableCellRenderer();
    column.setCellRenderer(renderer);

    TableCellEditor editor = new ColorChooserEditor();
    column.setCellEditor(editor);

    JScrollPane scrollPane = new JScrollPane(table);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    frame.setSize(400, 150);
    frame.setVisible(true);
  }
}

class ComboTableCellRenderer implements ListCellRenderer, TableCellRenderer {
  DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();

  DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();

  private void configureRenderer(JLabel renderer, Object value) {
    if ((value != null) && (value instanceof Color)) {
      renderer.setIcon(new DiamondIcon((Color) value));
      renderer.setText("");
    } else {
      renderer.setIcon(null);
      renderer.setText((String) value);
    }
  }

  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    listRenderer = (DefaultListCellRenderer) listRenderer
        .getListCellRendererComponent(list, value, index, isSelected,
            cellHasFocus);
    configureRenderer(listRenderer, value);
    return listRenderer;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    tableRenderer = (DefaultTableCellRenderer) tableRenderer
        .getTableCellRendererComponent(table, value, isSelected,
            hasFocus, row, column);
    configureRenderer(tableRenderer, value);
    return tableRenderer;
  }
}

class ColorTableModel extends AbstractTableModel {

  Object rowData[][] = { { "1", "ichi - \u4E00", Boolean.TRUE, Color.red },
      { "2", "ni - \u4E8C", Boolean.TRUE, Color.blue },
      { "3", "san - \u4E09", Boolean.FALSE, Color.green },
      { "4", "shi - \u56DB", Boolean.TRUE, Color.magenta },
      { "5", "go - \u4E94", Boolean.FALSE, Color.pink }, };

  String columnNames[] = { "English", "Japanese", "Boolean", "Color" };

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

class ColorChooserEditor extends AbstractCellEditor implements TableCellEditor {

  private JButton delegate = new JButton();

  Color savedColor;

  public ColorChooserEditor() {
    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        Color color = JColorChooser.showDialog(delegate,
            "Color Chooser", savedColor);
        ColorChooserEditor.this.changeColor(color);
      }
    };
    delegate.addActionListener(actionListener);
  }

  public Object getCellEditorValue() {
    return savedColor;
  }

  private void changeColor(Color color) {
    if (color != null) {
      savedColor = color;
      delegate.setIcon(new DiamondIcon(color));
    }
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    changeColor((Color) value);
    return delegate;
  }
}

class DiamondIcon implements Icon {
  private Color color;

  private boolean selected;

  private int width;

  private int height;

  private Polygon poly;

  private static final int DEFAULT_WIDTH = 10;

  private static final int DEFAULT_HEIGHT = 10;

  public DiamondIcon(Color color) {
    this(color, true, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  public DiamondIcon(Color color, boolean selected) {
    this(color, selected, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  public DiamondIcon(Color color, boolean selected, int width, int height) {
    this.color = color;
    this.selected = selected;
    this.width = width;
    this.height = height;
    initPolygon();
  }

  private void initPolygon() {
    poly = new Polygon();
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    poly.addPoint(0, halfHeight);
    poly.addPoint(halfWidth, 0);
    poly.addPoint(width, halfHeight);
    poly.addPoint(halfWidth, height);
  }

  public int getIconHeight() {
    return height;
  }

  public int getIconWidth() {
    return width;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    g.setColor(color);
    g.translate(x, y);
    if (selected) {
      g.fillPolygon(poly);
    } else {
      g.drawPolygon(poly);
    }
    g.translate(-x, -y);
  }
}
