package smallcrab.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRowRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 2436478513031618088L;

	public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (row % 2 == 0)
			setBackground(Color.LIGHT_GRAY);
		else
			setBackground(Color.WHITE);
		return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
	}
}