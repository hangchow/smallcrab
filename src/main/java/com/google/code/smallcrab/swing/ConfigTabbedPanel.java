/**
 * 
 */
package com.google.code.smallcrab.swing;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import com.google.code.smallcrab.swing.apache.ApacheLogPanel;
import com.google.code.smallcrab.swing.csv.CsvPanel;
import com.google.code.smallcrab.swing.iis.IisLogPanel;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class ConfigTabbedPanel extends JTabbedPane {
	private static final long serialVersionUID = 1402513027159984002L;

	public ConfigTabbedPanel(ChangeListener changeListener) {
		super();
		ApacheLogPanel apacheLogPanel = new ApacheLogPanel();
		add(apacheLogPanel);

		IisLogPanel iisLogPanel = new IisLogPanel();
		add(iisLogPanel);

		CsvPanel csvPanel = new CsvPanel();
		add(csvPanel);

		setSelectedIndex(0);// set apache log panel as default
		addChangeListener(changeListener);
	}

}
