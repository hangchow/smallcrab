/**
 * 
 */
package com.google.code.smallcrab.swing;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import com.google.code.smallcrab.swing.apache.ApachePanel;
import com.google.code.smallcrab.swing.csv.CsvPanel;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class ConfigTabbedPanel extends JTabbedPane {
	private static final long serialVersionUID = 1402513027159984002L;

	public ConfigTabbedPanel(ChangeListener changeListener) {
		super();
		CsvPanel csvPanel = new CsvPanel();
		add(csvPanel);
		
		ApachePanel apacheLogPanel = new ApachePanel();
		add(apacheLogPanel);

		setSelectedIndex(0);
		addChangeListener(changeListener);
	}

}
