/**
 * 
 */
package smallcrab.swing.csv;

import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import smallcrab.config.chart.ChartConfig;
import smallcrab.config.matcher.csv.CsvLineMatcher;
import smallcrab.config.viewer.csv.CsvLineViewer;
import smallcrab.mapper.csv.CsvXYSplotsMapper;
import smallcrab.reducer.FileLineAnalyzer;
import smallcrab.swing.AnalyzeConfigPanel;
import smallcrab.utils.StringKit;


/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class CsvPanel extends AnalyzeConfigPanel<CsvLineViewer, CsvLineMatcher> {

	private static final long serialVersionUID = -1486532994587869954L;

	private CsvDataSourceConfigPanel dataSourceConfigPanel;

	private CsvChartConfigPanel chartConfigPanel;

	private ItemListener chartConfigListener;

	public CsvPanel(ItemListener chartConfiglistener) {
		super(new BorderLayout());
		setName("csv");
		this.chartConfigListener = chartConfiglistener;
		this.dataSourceConfigPanel = new CsvDataSourceConfigPanel(new String[] { "column one", "column two", "column three" });
		this.add(this.dataSourceConfigPanel, BorderLayout.NORTH);
		this.chartConfigPanel = new CsvChartConfigPanel();
		this.chartConfigPanel.setChartConfigListener(this.chartConfigListener);
		this.add(this.chartConfigPanel, BorderLayout.SOUTH);
	}

	@Override
	public void notifyFileChange(File file) throws IOException {

		InputStream in = null;
		BufferedReader br = null;
		String line = null;
		try {
			in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in));
			line = br.readLine();
		} finally {
			if (br != null) {
				br.close();
			}
			if (in != null) {
				in.close();
			}
		}
		String[] csvColumns = StringKit.splitPreserveAllTokens(line, ',');

		this.dataSourceConfigPanel.setCsvColuns(csvColumns);
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
	protected List<CsvLineViewer> prepareViewers() {
		List<CsvLineViewer> viewers = new ArrayList<CsvLineViewer>();
		TableModel viewModel = this.dataSourceConfigPanel.getDataSourceModel();
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			boolean used = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (used) {
				String axisSelect = (String) viewModel.getValueAt(rowIndex, 1);
				if (axisSelect.equals(CsvDataSourceTableModel.axises[1])) { // x
																			// axis
					CsvLineViewer xAxislineViewer = new CsvLineViewer(rowIndex);
					xAxislineViewer.setXAxis(true);
					viewers.add(xAxislineViewer);
				} else if (axisSelect.equals(CsvDataSourceTableModel.axises[2])) {// y
																					// axis
					CsvLineViewer yAxisLineViewer = new CsvLineViewer(rowIndex);
					yAxisLineViewer.setYAxis(true);
					viewers.add(yAxisLineViewer);
				}
			}
		}
		return viewers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#createFileLineAnalyzer ()
	 */
	@Override
	public FileLineAnalyzer createFileLineAnalyzer() {
		CsvXYSplotsMapper scanner = new CsvXYSplotsMapper();
		scanner.setLineViewers(this.prepareViewers());
		scanner.setLineMatchers(this.prepareMatchers());
		FileLineAnalyzer analyzer = new FileLineAnalyzer(scanner);
		return analyzer;
	}

	@Override
	protected ChartConfig createChartConfig() {
		return this.chartConfigPanel == null ? null : this.chartConfigPanel.createChartConfig();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepareMatchers()
	 */
	@Override
	protected List<CsvLineMatcher> prepareMatchers() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepared()
	 */
	@Override
	public boolean isPrepared() {
		TableModel viewModel = this.dataSourceConfigPanel.getDataSourceModel();
		int xAxisCount = 0;
		int yAxisCount = 0;
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			boolean used = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (used) {
				String axisSelect = (String) viewModel.getValueAt(rowIndex, 1);
				if (axisSelect.equals(CsvDataSourceTableModel.axises[1])) { // x axis
					xAxisCount++;
				} else if (axisSelect.equals(CsvDataSourceTableModel.axises[2])) { // y axis
					yAxisCount++;
				}
			}
		}
		if (xAxisCount == 1 && yAxisCount >= 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isAnalyzeXYSplots() {
		return true;
	}

}
