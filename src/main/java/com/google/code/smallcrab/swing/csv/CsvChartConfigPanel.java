/**
 * 
 */
package com.google.code.smallcrab.swing.csv;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.google.code.smallcrab.config.chart.ChartConfig;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-7-18
 */
public class CsvChartConfigPanel extends JPanel {
	private static final long serialVersionUID = 6854447303405175550L;
	private JCheckBox frequencyButton;
	private JCheckBox averageButton;

	public ChartConfig createChartConfig() {
		ChartConfig cc = new ChartConfig();
		cc.setNeedFrequency(this.frequencyButton == null ? false : this.frequencyButton.isSelected());
		cc.setNeedAverage(this.averageButton == null ? false : this.averageButton.isSelected());
		return cc;
	}

	public CsvChartConfigPanel() {
		JPanel checkPanel = new JPanel(new GridLayout(2, 1));
		Border border = BorderFactory.createTitledBorder("chart settints");
		checkPanel.setBorder(border);

		JCheckBox frequencyButton = new JCheckBox("draw frequency line");
		frequencyButton.setSelected(true);
		this.frequencyButton = frequencyButton;

		JCheckBox averageButton = new JCheckBox("take y average interval");
		averageButton.setSelected(true);
		this.averageButton = averageButton;

		checkPanel.add(frequencyButton);
		checkPanel.add(averageButton);
		checkPanel.setPreferredSize(new Dimension(460, 200));
		this.add(checkPanel);
	}

}
