package com.google.code.smallcrab.scan.apache;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JComboBoxDemo extends JPanel {

	public JComboBoxDemo() {
		String[] comboTypes = { "Numbers", "Alphabets", "Symbols" };
		// Create the combo box, and set 2nd item as Default
		JComboBox comboTypesList = new JComboBox(comboTypes);
		comboTypesList.setSelectedIndex(2);
		// Layout the demo
		setLayout(new BorderLayout());
		add(comboTypesList, BorderLayout.NORTH);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	public static void main(String s[]) {
		JFrame frame = new JFrame("JComboBox Usage Demo");
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setContentPane(new JComboBoxDemo());
		frame.pack();
		frame.setVisible(true);
	}
}