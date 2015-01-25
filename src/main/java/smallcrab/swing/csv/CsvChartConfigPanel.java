/**
 * 
 */
package smallcrab.swing.csv;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import smallcrab.config.chart.ChartConfig;


/**
 * @author seanlinwang at gmail dot com
 * @date 2011-7-18
 */
public class CsvChartConfigPanel extends JPanel {
	private static final long serialVersionUID = 6854447303405175550L;
	private JCheckBox drawFrequencyButton;
	private JCheckBox drawFrequencyHistogramButton;
	private JCheckBox drawFrequencyAverageButton;
	private JCheckBox drawYButton;
	private JCheckBox drawYAverageButton;
	private JCheckBox drawFrequencyLineButton;

	public ChartConfig createChartConfig() {
		ChartConfig cc = new ChartConfig();
		cc.setDrawFrequency(this.drawFrequencyButton == null ? false : this.drawFrequencyButton.isSelected());
		cc.setDrawFrequencyAverage(this.drawFrequencyAverageButton == null ? false : this.drawFrequencyAverageButton.isSelected());
		cc.setDrawY(this.drawYButton == null ? false : this.drawYButton.isSelected());
		cc.setDrawYAverage(this.drawYAverageButton == null ? false : this.drawYAverageButton.isSelected());
		cc.setDrawFrequencyHistogram(this.drawFrequencyHistogramButton == null ? false : this.drawFrequencyHistogramButton.isSelected());
		cc.setDrawFrequencyLine(this.drawFrequencyLineButton == null ? false : this.drawFrequencyLineButton.isSelected());
		return cc;
	}

	public void setChartConfigListener(ItemListener chartConfigListener) {
		this.drawFrequencyButton.addItemListener(chartConfigListener);
		this.drawFrequencyAverageButton.addItemListener(chartConfigListener);
		this.drawYAverageButton.addItemListener(chartConfigListener);
		this.drawYButton.addItemListener(chartConfigListener);
		this.drawFrequencyHistogramButton.addItemListener(chartConfigListener);
		this.drawFrequencyLineButton.addItemListener(chartConfigListener);
	}

	public CsvChartConfigPanel() {
		JCheckBox frequencyButton = new JCheckBox("draw frequency", true);
		this.drawFrequencyButton = frequencyButton;

		final JCheckBox frequencyHistogramButton = new JCheckBox("histogram", false);
		this.drawFrequencyHistogramButton = frequencyHistogramButton;

		final JCheckBox frequencyLineButton = new JCheckBox("line", false);
		ItemListener onlyOneListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
					if (itemEvent.getSource() == frequencyLineButton) {
						frequencyHistogramButton.setSelected(false);
					} else if (itemEvent.getSource() == frequencyHistogramButton) {
						frequencyLineButton.setSelected(false);
					}
				}

			}
		};
		frequencyLineButton.addItemListener(onlyOneListener);
		frequencyHistogramButton.addItemListener(onlyOneListener);
		
		this.drawFrequencyLineButton = frequencyLineButton;

		JCheckBox frequencyAverageButton = new JCheckBox("draw frequency average", false);
		this.drawFrequencyAverageButton = frequencyAverageButton;

		JCheckBox yButton = new JCheckBox("draw y", true);
		this.drawYButton = yButton;

		JCheckBox averageButton = new JCheckBox("draw y average", false);
		this.drawYAverageButton = averageButton;

		JPanel checkPanel = new JPanel(new GridLayout(4, 1));
		Border border = BorderFactory.createTitledBorder("chart settings");
		checkPanel.setBorder(border);
		JPanel frequencyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		frequencyPanel.add(this.drawFrequencyButton);
		frequencyPanel.add(this.drawFrequencyHistogramButton);
		frequencyPanel.add(this.drawFrequencyLineButton);
		checkPanel.add(frequencyPanel);
		checkPanel.add(this.drawFrequencyAverageButton);
		checkPanel.add(this.drawYButton);
		checkPanel.add(this.drawYAverageButton);
		checkPanel.setPreferredSize(new Dimension(460, 200));
		this.add(checkPanel);
	}

}
