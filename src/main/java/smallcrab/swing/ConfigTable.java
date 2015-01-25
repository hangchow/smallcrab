/**
 * 
 */
package smallcrab.swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public abstract class ConfigTable extends JTable {

	private static final long serialVersionUID = -250334773896895882L;

	/**
	 * @param tableModel
	 * @param dm
	 * @param cm
	 */
	public ConfigTable(TableModel tableModel) {
		super(tableModel);
		this.setRowHeight(20);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.paintRow();
	}
	
	private static final TableRowRenderer cellRenderer = new TableRowRenderer();

	public void paintRow() {
		TableColumnModel tcm = this.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n - 1; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(cellRenderer);
		}
	}

}
