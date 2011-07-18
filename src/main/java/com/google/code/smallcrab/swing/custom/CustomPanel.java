/**
 * 
 */
package com.google.code.smallcrab.swing.custom;

import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.config.chart.ChartConfig;
import com.google.code.smallcrab.config.matcher.csv.CsvLineMatcher;
import com.google.code.smallcrab.config.viewer.csv.CsvLineViewer;
import com.google.code.smallcrab.mapper.java.CsvJavaMapper;
import com.google.code.smallcrab.reducer.FileLineAnalyzer;
import com.google.code.smallcrab.swing.AnalyzeConfigPanel;
import com.google.code.smallcrab.utils.StringKit;
import com.google.code.smallcrab.utils.SwingKit;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class CustomPanel extends AnalyzeConfigPanel<CsvLineViewer, CsvLineMatcher> {

	private static final long serialVersionUID = -1486532994587869954L;

	public CustomPanel() {
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

		JLabel oneLabel = new JLabel("preproccess:");
		add(oneLabel, SwingKit.createVerticalGridBagConstraint(0, 1));

		JLabel scriptLabel = new JLabel("script:");
		add(scriptLabel, SwingKit.createVerticalGridBagConstraint(0, 3));

		JTextArea scriptOne = new JTextArea(10, 20);
		add(scriptOne, SwingKit.createVerticalGridBagConstraint(0, 4));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#resetConfigOutput()
	 */
	@Override
	public void resetConfigOutput() {

	}

	@Override
	protected void notifyFileChange(File logFile) throws IOException {
		
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
		CsvJavaMapper scanner = new CsvJavaMapper();
		FileLineAnalyzer analyzer = new FileLineAnalyzer(scanner);
		return analyzer;
	}

	@Override
	protected ChartConfig createChartConfig() {
		return null;
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

	private Object[][] data;
	String[] comboTypes = { "", "selected", "merge" };

	public void initConfig(File file) throws IOException {
		InputStream in = null;
		String line = null;
		try {
			in = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			line = br.readLine();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		String[] lineOneSegs = StringKit.split(line, ',');
		data = new Object[lineOneSegs.length][2];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = lineOneSegs[i];
			JComboBox comboTypesList = new JComboBox(comboTypes);
			data[i][1] = comboTypesList;
			comboTypesList.setSelectedIndex(0);
		}

	}

	class CsvViewTableModel extends AbstractTableModel implements TableModelListener {

		private static final long serialVersionUID = 1088594001477114033L;
		private final String[] header = new String[] { "column", "option" };

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return getValueAt(0, columnIndex).getClass();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int columnIndex) {
			return header[columnIndex];
		}

		@Override
		public int getRowCount() {
			return getData().length;
		}

		protected Object[][] getData() {
			return data;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return getData()[rowIndex][columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			getData()[rowIndex][columnIndex] = aValue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent e) {
			switch (e.getType()) {
			case TableModelEvent.DELETE:
			case TableModelEvent.UPDATE:
				if (e.getColumn() == TableModelEvent.ALL_COLUMNS)
					;
				else
					;
				break;
			case TableModelEvent.INSERT:
				break;
			default:
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepared()
	 */
	@Override
	public boolean isPrepared() {
		if (data != null) {
			return true;
		} else {
			return false;
		}
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
