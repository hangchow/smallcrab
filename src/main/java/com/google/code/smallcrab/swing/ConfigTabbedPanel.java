/**
 * 
 */
package com.google.code.smallcrab.swing;

import java.awt.event.ItemListener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import com.google.code.smallcrab.swing.accesslog.ALPanel;
import com.google.code.smallcrab.swing.csv.CsvPanel;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class ConfigTabbedPanel extends JTabbedPane {
	private static final long serialVersionUID = 1402513027159984002L;

	public ConfigTabbedPanel(ChangeListener changeListener, ItemListener frequencyListener) {
		super();

		ALPanel apacheLogPanel = new ALPanel();
		add(apacheLogPanel);

		CsvPanel csvPanel = new CsvPanel(frequencyListener);
		add(csvPanel);

		setSelectedIndex(0);
		addChangeListener(changeListener);
	}

}
