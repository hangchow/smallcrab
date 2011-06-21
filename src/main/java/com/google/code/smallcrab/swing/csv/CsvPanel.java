/**
 * 
 */
package com.google.code.smallcrab.swing.csv;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.code.smallcrab.analyze.FileLineAnalyzer;
import com.google.code.smallcrab.analyze.java.CsvJavaScanner;
import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.matcher.csv.CsvLineMatcher;
import com.google.code.smallcrab.swing.AnalyzeConfigPanel;
import com.google.code.smallcrab.utils.SwingKit;
import com.google.code.smallcrab.viewer.csv.CsvLineViewer;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class CsvPanel extends AnalyzeConfigPanel<CsvLineViewer, CsvLineMatcher> {

	private static final long serialVersionUID = -1486532994587869954L;

	public CsvPanel() {
		super(new GridBagLayout());
		setName("csv");

		JPanel optionPanel = new JPanel();
		JLabel spliterLabel = new JLabel("spliter:");
		spliterLabel.setHorizontalAlignment(SwingConstants.LEFT);
		JTextField spliterTF = new JTextField();
		spliterTF.setText(",");
		optionPanel.add(spliterLabel);
		optionPanel.add(spliterTF);
		add(optionPanel, SwingKit.createVerticalGridBagConstraint(0, 0));

		JLabel oneLabel = new JLabel("global variables:");
		add(oneLabel, SwingKit.createVerticalGridBagConstraint(0, 1));

		JTextArea scriptOne = new JTextArea(10, 20);
		add(scriptOne, SwingKit.createVerticalGridBagConstraint(0, 2));

		JLabel twoLabel = new JLabel("line iterate:");
		add(twoLabel, SwingKit.createVerticalGridBagConstraint(0, 3));

		JTextArea scriptTwo = new JTextArea(10, 20);
		add(scriptTwo, SwingKit.createVerticalGridBagConstraint(0, 4));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#resetConfigOutput()
	 */
	@Override
	public void resetConfigOutput() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepareViewers()
	 */
	@Override
	protected List<CsvLineViewer> prepareViewers() throws ConfigException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#createFileLineAnalyzer()
	 */
	@Override
	public FileLineAnalyzer createFileLineAnalyzer() {
		CsvJavaScanner scanner = new CsvJavaScanner();
		FileLineAnalyzer analyzer = new FileLineAnalyzer(scanner);
		return analyzer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepareMatchers()
	 */
	@Override
	protected List<CsvLineMatcher> prepareMatchers() throws ConfigException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepared()
	 */
	@Override
	public boolean isPrepared() {
		return true;
	}

	@Override
	public boolean isAnalyzeAppend() {
		return true;
	}

	@Override
	public boolean isAnalyzeCount() {
		return false;
	}

}
