/**
 * 
 */
package com.google.code.smallcrab.swing;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author lin.wangl
 * 
 */
public class OutputPanel extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2275553061529131620L;

	private JProgressBar progressBar;

	private JTextArea taskOutput;

	private ChartPanel chartPanel;

	public JTextArea getTaskOutput() {
		return taskOutput;
	}

	public OutputPanel() {
		super(new BorderLayout());

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(progressBar);
		add(panel, BorderLayout.NORTH);

		taskOutput = new JTextArea(20, 40);
		taskOutput.setMargin(new Insets(0, 0, 0, 0));
		taskOutput.setEditable(false);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);

		chartPanel = new ChartPanel();
		add(new JScrollPane(chartPanel), BorderLayout.SOUTH);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans. PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

}
