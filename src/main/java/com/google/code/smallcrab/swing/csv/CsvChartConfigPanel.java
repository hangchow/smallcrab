/**
 * 
 */
package com.google.code.smallcrab.swing.csv;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemListener;

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
	private JCheckBox drawFrequencyButton;
	private JCheckBox drawFrequencyAverageButton;
	private JCheckBox drawYButton;
	private JCheckBox drawYAverageButton;

	public ChartConfig createChartConfig() {
		ChartConfig cc = new ChartConfig();
		cc.setDrawFrequency(this.drawFrequencyButton == null ? false : this.drawFrequencyButton.isSelected());
		cc.setDrawFrequencyAverage(this.drawFrequencyAverageButton == null ? false : this.drawFrequencyAverageButton.isSelected());
		cc.setDrawY(this.drawYButton == null ? false : this.drawYButton.isSelected());
		cc.setDrawYAverage(this.drawYAverageButton == null ? false : this.drawYAverageButton.isSelected());
		return cc;
	}

	public void setFrequencyListener(ItemListener frequencyListener) {
		this.drawFrequencyButton.addItemListener(frequencyListener);
	}

	public void setFrequencyAverageListener(ItemListener frequencyAverageListener) {
		this.drawFrequencyAverageButton.addItemListener(frequencyAverageListener);
	}

	public void setYListener(ItemListener yListener) {
		this.drawYButton.addItemListener(yListener);
	}

	public void setYAverageListener(ItemListener yAverageListener) {
		this.drawYAverageButton.addItemListener(yAverageListener);
	}

	public CsvChartConfigPanel() {
		JCheckBox frequencyButton = new JCheckBox("draw frequency");
		frequencyButton.setSelected(true);
		this.drawFrequencyButton = frequencyButton;

		JCheckBox frequencyAverageButton = new JCheckBox("draw frequency average");
		frequencyAverageButton.setSelected(false);
		this.drawFrequencyAverageButton = frequencyAverageButton;

		JCheckBox yButton = new JCheckBox("draw y");
		yButton.setSelected(true);
		this.drawYButton = yButton;

		JCheckBox averageButton = new JCheckBox("draw y average");
		averageButton.setSelected(false);
		this.drawYAverageButton = averageButton;

		JPanel checkPanel = new JPanel(new GridLayout(4, 1));
		Border border = BorderFactory.createTitledBorder("chart settings");
		checkPanel.setBorder(border);
		checkPanel.add(this.drawFrequencyButton);
		checkPanel.add(this.drawFrequencyAverageButton);
		checkPanel.add(this.drawYButton);
		checkPanel.add(this.drawYAverageButton);
		checkPanel.setPreferredSize(new Dimension(460, 200));
		this.add(checkPanel);
	}

}
