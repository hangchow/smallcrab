/**
 * 
 */
package smallcrab.swing.accesslog;

import static smallcrab.swing.accesslog.ALConfigTableModel.AGENT;
import static smallcrab.swing.accesslog.ALConfigTableModel.ALL;
import static smallcrab.swing.accesslog.ALConfigTableModel.HOST;
import static smallcrab.swing.accesslog.ALConfigTableModel.IP;
import static smallcrab.swing.accesslog.ALConfigTableModel.METHOD;
import static smallcrab.swing.accesslog.ALConfigTableModel.PATH;
import static smallcrab.swing.accesslog.ALConfigTableModel.QUERY;
import static smallcrab.swing.accesslog.ALConfigTableModel.REFERER;
import static smallcrab.swing.accesslog.ALConfigTableModel.STATUS;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import smallcrab.config.ConfigException;
import smallcrab.config.chart.ChartConfig;
import smallcrab.config.matcher.accesslog.ALAgentMatcher;
import smallcrab.config.matcher.accesslog.ALCodeMatcher;
import smallcrab.config.matcher.accesslog.ALDomainMatcher;
import smallcrab.config.matcher.accesslog.ALIPMatcher;
import smallcrab.config.matcher.accesslog.ALLineMatcher;
import smallcrab.config.matcher.accesslog.ALMethodMatcher;
import smallcrab.config.matcher.accesslog.ALPathMatcher;
import smallcrab.config.matcher.accesslog.ALQueryMatcher;
import smallcrab.config.matcher.accesslog.ALReferrerMatcher;
import smallcrab.config.viewer.accesslog.ALAgentViewer;
import smallcrab.config.viewer.accesslog.ALAllViewer;
import smallcrab.config.viewer.accesslog.ALCodeViewer;
import smallcrab.config.viewer.accesslog.ALHostViewer;
import smallcrab.config.viewer.accesslog.ALIPViewer;
import smallcrab.config.viewer.accesslog.ALLineViewer;
import smallcrab.config.viewer.accesslog.ALMethodViewer;
import smallcrab.config.viewer.accesslog.ALPathViewer;
import smallcrab.config.viewer.accesslog.ALQueryViewer;
import smallcrab.config.viewer.accesslog.ALRefererViewer;
import smallcrab.mapper.accesslog.ALCountMapper;
import smallcrab.reducer.FileLineAnalyzer;
import smallcrab.swing.AnalyzeConfigPanel;
import smallcrab.swing.utils.ColumnResizer;
import smallcrab.utils.StringKit;


/**
 * Apache log analyze config panel.
 * 
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class ALPanel extends AnalyzeConfigPanel<ALLineViewer, ALLineMatcher> {

	private static final long serialVersionUID = 2761139314517046734L;

	private JTable apacheViewTable;

	private JTable apacheMatchTable;

	private JLabel apacheConfigOutput;

	public ALPanel() {
		super(new GridBagLayout());
		setName("accesslog");

		GridBagConstraints vlc = new GridBagConstraints();
		vlc.fill = GridBagConstraints.VERTICAL;
		vlc.gridx = 0;
		vlc.gridy = 0;
		add(new JLabel("view config", SwingConstants.LEFT), vlc);

		GridBagConstraints vc = new GridBagConstraints();
		vc.fill = GridBagConstraints.VERTICAL;
		vc.gridx = 0;
		vc.gridy = 1;
		this.apacheViewTable = new ALViewConfigTable();
		ColumnResizer.setFixColumnWidth(this.apacheViewTable, 1, 200);
		add(this.apacheViewTable, vc);

		GridBagConstraints mlc = new GridBagConstraints();
		mlc.fill = GridBagConstraints.VERTICAL;
		mlc.gridx = 0;
		mlc.gridy = 2;
		add(new JLabel("match config", SwingConstants.LEFT), mlc);

		GridBagConstraints mc = new GridBagConstraints();
		mc.fill = GridBagConstraints.VERTICAL;
		mc.gridx = 0;
		mc.gridy = 3;
		this.apacheMatchTable = new ALMatchConfigTable();
		ColumnResizer.setFixColumnWidth(this.apacheMatchTable, 1, 200);
		add(this.apacheMatchTable, mc);

		GridBagConstraints mcerror = new GridBagConstraints();
		mcerror.fill = GridBagConstraints.VERTICAL;
		mcerror.gridx = 0;
		mcerror.gridy = 4;
		this.apacheConfigOutput = new JLabel();
		this.apacheConfigOutput.setForeground(Color.RED);
		this.apacheConfigOutput.setBackground(Color.LIGHT_GRAY);
		this.apacheConfigOutput.setPreferredSize(new Dimension(300, 20));
		this.resetConfigOutput();
		add(this.apacheConfigOutput, mcerror);

	}

	@Override
	protected void notifyFileChange(File logFile) throws IOException {

	}

	public void resetConfigOutput() {
		this.apacheConfigOutput.setText("");
	}

	/**
	 * @return
	 * @throws ConfigException
	 * 
	 */
	protected List<ALLineViewer> prepareViewers() {
		List<ALLineViewer> lineViewers = new ArrayList<ALLineViewer>();
		TableModel viewModel = this.apacheViewTable.getModel();
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			String option = (String) viewModel.getValueAt(rowIndex, 0);
			String value = (String) viewModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			ALLineViewer viewer = null;
			if (HOST.equals(option)) {
				viewer = new ALHostViewer(value);
			} else if (QUERY.equals(option)) {
				viewer = new ALQueryViewer(value);
			} else if (PATH.equals(option)) {
				viewer = new ALPathViewer(value);
			} else if (REFERER.equals(option)) {
				viewer = new ALRefererViewer(value);
			} else if (STATUS.equals(option)) {
				viewer = new ALCodeViewer(value);
			} else if (METHOD.equals(option)) {
				viewer = new ALMethodViewer(value);
			} else if (AGENT.equals(option)) {
				viewer = new ALAgentViewer(value);
			} else if (IP.equals(option)) {
				viewer = new ALIPViewer(value);
			} else if (ALL.equals(option)) {
				viewer = new ALAllViewer(value);
			}
			lineViewers.add(viewer);
		}
		return lineViewers;
	}

	protected List<ALLineMatcher> prepareMatchers() {
		List<ALLineMatcher> lineMatchers = new ArrayList<ALLineMatcher>();
		TableModel matchModel = this.apacheMatchTable.getModel();
		for (int rowIndex = 0; rowIndex < matchModel.getRowCount(); rowIndex++) {
			String option = (String) matchModel.getValueAt(rowIndex, 0);
			String value = (String) matchModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) matchModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			ALLineMatcher matcher = null;
			if (HOST.equals(option)) {
				matcher = new ALDomainMatcher(value);
			} else if (QUERY.equals(option)) {
				String[] kv = StringKit.split(value, '=');
				matcher = new ALQueryMatcher(kv[0], kv[1]);
			} else if (PATH.equals(option)) {
				matcher = new ALPathMatcher(value);
			} else if (REFERER.equals(option)) {
				matcher = new ALReferrerMatcher(value);
			} else if (STATUS.equals(option)) {
				matcher = new ALCodeMatcher(value);
			} else if (METHOD.equals(option)) {
				matcher = new ALMethodMatcher(value);
			} else if (AGENT.equals(option)) {
				matcher = new ALAgentMatcher(value);
			} else if (IP.equals(option)) {
				matcher = new ALIPMatcher(value);
			}
			lineMatchers.add(matcher);
		}
		return lineMatchers;
	}

	private void outputMatchConfigError(String option, String value) {
		String output = null;
		if (option != null) {
			output = String.format("Please check match config [option:%s,value:%s].", option, value);
		} else {
			output = "Please check match configs.";
		}
		this.apacheConfigOutput.setText(output);
	}

	private void outputViewConfigError(String option, String value) {
		String output = null;
		if (option != null) {
			output = String.format("Please check view config [option:%s,value:%s].", option, value);
		} else {
			output = "Please check view configs.";
		}
		this.apacheConfigOutput.setText(output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepareScanner()
	 */
	@Override
	public FileLineAnalyzer createFileLineAnalyzer() {
		ALCountMapper scanner = new ALCountMapper();
		scanner.setLineViewers(this.prepareViewers());
		scanner.setLineMatchers(this.prepareMatchers());
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
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepared()
	 */
	@Override
	public boolean isPrepared() throws ConfigException {
		// check if view is prepared
		TableModel viewModel = this.apacheViewTable.getModel();
		int checkedNum = 0;
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			String option = (String) viewModel.getValueAt(rowIndex, 0);
			String value = (String) viewModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (checked)
				checkedNum++;
			if (HOST.equals(option)) {
			} else if (QUERY.equals(option)) {
			} else if (PATH.equals(option)) {
			} else if (REFERER.equals(option)) {
			} else if (STATUS.equals(option)) {
			} else if (METHOD.equals(option)) {
			} else if (AGENT.equals(option)) {
			} else if (IP.equals(option)) {
			} else if (ALL.equals(option)) {
			} else {
				outputViewConfigError(option, value);
				return false;
			}
		}
		if (checkedNum == 0) {
			outputViewConfigError(null, null);
			return false;
		}
		// check if config is prepared
		TableModel matchModel = this.apacheMatchTable.getModel();
		for (int rowIndex = 0; rowIndex < matchModel.getRowCount(); rowIndex++) {
			String option = (String) matchModel.getValueAt(rowIndex, 0);
			String value = (String) matchModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) matchModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			if (StringKit.isEmpty(value)) {
				outputMatchConfigError(option, value);
				return false;
			}
			if (HOST.equals(option)) {
			} else if (QUERY.equals(option)) {
				String[] kv = StringKit.split(value, '=');
				if (kv.length != 2) {
					outputMatchConfigError(option, value);
					return false;
				}
			} else if (PATH.equals(option)) {
			} else if (REFERER.equals(option)) {
			} else if (STATUS.equals(option)) {
			} else if (METHOD.equals(option)) {
			} else if (AGENT.equals(option)) {
			} else if (IP.equals(option)) {
			} else {
				outputMatchConfigError(option, value);
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAnalyzeCount() {
		return true;
	}

}
