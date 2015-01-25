/**
 * 
 */
package smallcrab.swing.csv;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class CsvChartTableModel extends AbstractTableModel implements TableModelListener {
	private final String[] header = { "column", "axis", "used" };

	public static final String[] axises = { "select axis", "x-axis", "y-axis" };

	/**
	 * table model
	 */
	private Object[][] data;

	private static final long serialVersionUID = 1035774581277627811L;

	public CsvChartTableModel(String[] csvColumns) {
		super();
		this.data = new Object[csvColumns.length][3];
		for (int i = 0; i < csvColumns.length; i++) {
			this.data[i][0] = csvColumns[i];
			this.data[i][1] = axises[0];
			this.data[i][2] = false;
		}
	}

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
		if (columnIndex == 1 || columnIndex == 2) {
			return true;
		} else {
			return false;
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
