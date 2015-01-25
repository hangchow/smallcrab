package smallcrab.utils;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class SwingKit {
	public static JPanel createPanelForComponent(JComponent comp, String title) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(comp, BorderLayout.CENTER);
		if (title != null) {
			panel.setBorder(BorderFactory.createTitledBorder(title));
		}
		return panel;
	}
	
	public static GridBagConstraints createVerticalGridBagConstraint(int gridx, int gridy) {
		GridBagConstraints vlc = new GridBagConstraints();
		vlc.fill = GridBagConstraints.VERTICAL;
		vlc.gridx = gridx;
		vlc.gridy = gridy;
		return vlc;
	}
	
	public static GridBagConstraints createHorizontalGridBagConstraint(int gridx, int gridy) {
		GridBagConstraints vlc = new GridBagConstraints();
		vlc.fill = GridBagConstraints.HORIZONTAL;
		vlc.gridx = gridx;
		vlc.gridy = gridy;
		return vlc;
	}
}
