package com.google.code.smallcrab.swing;

import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.AGENT;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.ALL;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.CODE;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.DOMAIN;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.IP;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.METHOD;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.PATH;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.QUERY;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.REFERRER;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;

import com.google.code.smallcrab.analyze.AnalyzeCallback;
import com.google.code.smallcrab.analyze.impl.FileLineAnalyzer;
import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.scan.LineMatcher;
import com.google.code.smallcrab.scan.LineViewer;
import com.google.code.smallcrab.scan.apache.AbstractApacheMatcher;
import com.google.code.smallcrab.scan.apache.AbstractApacheViewer;
import com.google.code.smallcrab.scan.apache.ApacheAgentMatcher;
import com.google.code.smallcrab.scan.apache.ApacheAgentViewer;
import com.google.code.smallcrab.scan.apache.ApacheAllViewer;
import com.google.code.smallcrab.scan.apache.ApacheCodeMatcher;
import com.google.code.smallcrab.scan.apache.ApacheCodeViewer;
import com.google.code.smallcrab.scan.apache.ApacheDomainMatcher;
import com.google.code.smallcrab.scan.apache.ApacheDomainViewer;
import com.google.code.smallcrab.scan.apache.ApacheIPMatcher;
import com.google.code.smallcrab.scan.apache.ApacheIPViewer;
import com.google.code.smallcrab.scan.apache.ApacheMethodMatcher;
import com.google.code.smallcrab.scan.apache.ApacheMethodViewer;
import com.google.code.smallcrab.scan.apache.ApachePathMatcher;
import com.google.code.smallcrab.scan.apache.ApachePathViewer;
import com.google.code.smallcrab.scan.apache.ApacheQueryMatcher;
import com.google.code.smallcrab.scan.apache.ApacheQueryViewer;
import com.google.code.smallcrab.scan.apache.ApacheReferrerMatcher;
import com.google.code.smallcrab.scan.apache.ApacheReferrerViewer;
import com.google.code.smallcrab.scan.apache.ApacheScanner;
import com.google.code.smallcrab.scan.apache.ApacheSpliter;
import com.google.code.smallcrab.swing.apache.ApacheMatchConfigTable;
import com.google.code.smallcrab.swing.apache.ApacheViewConfigTable;
import com.google.code.smallcrab.utils.CrabKit;
import com.google.code.smallcrab.utils.StringKit;

/**
 * Control panel with analyze task.
 * 
 * @author xalinx at gmail dot com
 * @date Dec 28, 2010
 */
public class ControlPanel extends JPanel implements ActionListener {

	/**
	 * Analyze task.
	 * 
	 * @author xalinx at gmail dot com
	 * @date Dec 28, 2010
	 */
	private class AnalyzeTask extends SwingWorker<Void, Void> {
		private final ReentrantLock sleepLock = new ReentrantLock();
		private final Condition wakeupCondition = sleepLock.newCondition();
		private final static long PROGRESS_BAR_MAX = 100;

		private void wakeup() {
			sleepLock.lock();
			try {
				wakeupCondition.signal();
			} finally {
				sleepLock.unlock();
			}
		}

		private void sleep() {
			sleepLock.lock();
			try {
				wakeupCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sleepLock.unlock();
			}
		}

		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			while (true) {
				try {
					sleep();
					if (lineViewers.length == 0) {
						continue;
					}
					ApacheScanner scanner = new ApacheScanner();
					scanner.setLineViewers(lineViewers);
					scanner.setLineMatchers(lineMatchers);
					FileLineAnalyzer analyzer = new FileLineAnalyzer(scanner);
					final long totalLength = sourceFile.length();
					this.setProgress(0);

					outputAnalyzeStart(totalLength);

					final Map<String, Integer> result = new HashMap<String, Integer>(512);
					final AnalyzeCallback ac = new AnalyzeCallback() {
						private long bufferLineSize = setBufferLineSize();

						@Override
						public void callback() {
							long p1 = this.getAnalyzedSize() * PROGRESS_BAR_MAX;
							long p2 = totalLength * PROGRESS_BAR_MAX;
							int progress = (int) (p1 == p2 ? PROGRESS_BAR_MAX : p1 / totalLength);
							if (progress < 0 || progress > 100) {
								throw new IllegalArgumentException("the progress value should be from 0 to 100:" + progress);
							}
							setProgress(progress);
						}

						@Override
						public long getBufferLineSize() {
							return bufferLineSize;
						}

						private long setBufferLineSize() {
							long n = totalLength / 1024 / 100;
							n = n == 0 ? 1 : n;
							return n;
						}
					};
					analyzer.analyze(sourceFile, result, ac);
					outputAnalyzeResult(analyzer, totalLength, result, ac);
					clickStopButton();
				} catch (Throwable e) {
					taskOutput.append(e.toString());
					e.printStackTrace();
				}
			}
		}

		private void outputAnalyzeStart(final long totalLength) {
			taskOutput.append("===============================================\n");
			taskOutput.append("= Size:" + totalLength + "\n");
		}

		private void outputAnalyzeResult(FileLineAnalyzer ala, final long totalLength, final Map<String, Integer> result, final AnalyzeCallback ac) {
			taskOutput.append("= totalLines:" + ac.getTotalLines() + "\n");
			taskOutput.append("= invalidLines:" + ac.getInvalidLines() + "\n");
			taskOutput.append("= Consuming:" + ala.getAnalyzePeriod() + "ms\n");
			taskOutput.append("= Speed:" + totalLength / 1024.0 / 1024 / ala.getAnalyzePeriod() * 1000 + "M/S\n");
			taskOutput.append("===============================================\n");
			Object[] resultArray = result.entrySet().toArray();
			Arrays.sort(resultArray, new Comparator<Object>() {
				@SuppressWarnings("unchecked")
				@Override
				public int compare(Object o1, Object o2) {
					return ((Entry<String, Integer>) o2).getValue() - ((Entry<String, Integer>) o1).getValue();
				}
			});
			for (int i = 0; i < resultArray.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, Integer> next = (Entry<String, Integer>) resultArray[i];
				taskOutput.append(next.getKey() + "," + next.getValue() + "\n");
			}
		}
	}

	private static final String CMD_PAUSE = "cmd_pause";
	private static final String CMD_STOP = "cmd_stop";
	private static final String CMD_START = "cmd_start";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1374625498800015010L;
	private JButton fileChooseButton, startButton, pauseButton, stopButton;
	private JFileChooser fileChooser;
	private File sourceFile;
	private JTextArea taskOutput;
	private AnalyzeTask analyzeTask;
	private JTable apacheMatchTable;
	private JTable apacheViewTable;
	private LineMatcher<ApacheSpliter>[] lineMatchers;
	private LineViewer<ApacheSpliter>[] lineViewers;
	private JLabel apacheConfigOutput;

	public ControlPanel(JTextArea taskOutput, PropertyChangeListener taskListener) {
		this.setLayout(new BorderLayout());

		analyzeTask = new AnalyzeTask();
		analyzeTask.addPropertyChangeListener(taskListener);
		analyzeTask.execute();

		this.taskOutput = taskOutput;

		ImageIcon fileChooseButtonIcon = CrabKit.createImageIcon("24/EjectHot.png");
		ImageIcon leftButtonIcon = CrabKit.createImageIcon("24/PlayHot.png");
		ImageIcon middleButtonIcon = CrabKit.createImageIcon("24/PauseHot.png");
		ImageIcon rightButtonIcon = CrabKit.createImageIcon("24/StopHot.png");

		fileChooseButton = new JButton(fileChooseButtonIcon);
		fileChooser = new JFileChooser();
		fileChooseButton.setMnemonic(KeyEvent.VK_F);

		startButton = new JButton(leftButtonIcon);
		startButton.setMnemonic(KeyEvent.VK_S);
		startButton.setActionCommand(CMD_START);

		pauseButton = new JButton(middleButtonIcon);
		pauseButton.setMnemonic(KeyEvent.VK_P);
		pauseButton.setActionCommand(CMD_PAUSE);

		stopButton = new JButton(rightButtonIcon);
		stopButton.setMnemonic(KeyEvent.VK_T);
		stopButton.setActionCommand(CMD_STOP);

		// Listen for actions on buttons.
		fileChooseButton.addActionListener(this);
		startButton.addActionListener(this);
		pauseButton.addActionListener(this);
		stopButton.addActionListener(this);
		initButtons();

		fileChooseButton.setToolTipText("Choose log file.");
		startButton.setToolTipText("Start task.");
		pauseButton.setToolTipText("Pause task.");
		stopButton.setToolTipText("Stop task.");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(fileChooseButton);
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(stopButton);
		add(buttonPanel, BorderLayout.NORTH);

		JTabbedPane configPane = new JTabbedPane();
		configPane.setSize(new Dimension(100, 200));
		JPanel apachePanel = new JPanel(new GridBagLayout());
		apachePanel.setSize(new Dimension(100, 200));
		configPane.add("apache", apachePanel);

		GridBagConstraints vlc = new GridBagConstraints();
		vlc.fill = GridBagConstraints.VERTICAL;
		vlc.gridx = 0;
		vlc.gridy = 0;
		apachePanel.add(new JLabel("view config"), vlc);

		GridBagConstraints vc = new GridBagConstraints();
		vc.fill = GridBagConstraints.VERTICAL;
		vc.gridx = 0;
		vc.gridy = 1;
		this.apacheViewTable = new ApacheViewConfigTable();
		apachePanel.add(this.apacheViewTable, vc);

		GridBagConstraints mlc = new GridBagConstraints();
		mlc.fill = GridBagConstraints.VERTICAL;
		mlc.gridx = 0;
		mlc.gridy = 2;
		apachePanel.add(new JLabel("match config"), mlc);

		GridBagConstraints mc = new GridBagConstraints();
		mc.fill = GridBagConstraints.VERTICAL;
		mc.gridx = 0;
		mc.gridy = 3;
		this.apacheMatchTable = new ApacheMatchConfigTable();
		apachePanel.add(this.apacheMatchTable, mc);

		GridBagConstraints mcerror = new GridBagConstraints();
		mcerror.fill = GridBagConstraints.VERTICAL;
		mcerror.gridx = 0;
		mcerror.gridy = 4;
		this.apacheConfigOutput = new JLabel();
		this.apacheConfigOutput.setForeground(Color.RED);
		this.apacheConfigOutput.setBackground(Color.LIGHT_GRAY);
		this.apacheConfigOutput.setPreferredSize(new Dimension(300, 20));
		this.resetConfigOutput();
		apachePanel.add(apacheConfigOutput, mcerror);

		JPanel csvPanel = new JPanel();
		configPane.add("csv", csvPanel);
		add(configPane, BorderLayout.CENTER);
	}

	private void resetConfigOutput() {
		this.apacheConfigOutput.setText("");
	}

	public void actionPerformed(ActionEvent ae) {
		if (CMD_START.equals(ae.getActionCommand())) {
			try {
				prepareAnalyzing();
				wakeupAnalyzer();
				clickStartButton();
			} catch (ConfigException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (CMD_STOP.equals(ae.getActionCommand())) {
			stopAnalyzer();
			clickStopButton();
		} else if (CMD_PAUSE.equals(ae.getActionCommand())) {
			pauseAnalyzer();
			clickPauseButton();
		} else if (ae.getSource() == fileChooseButton) {
			choseFile();
			clickStopButton();
		}

	}

	/**
	 * 
	 */
	private void pauseAnalyzer() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	private void stopAnalyzer() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * prepare everything before analyzing:
	 * <ul>
	 * <li>scan match rules</li>
	 * <li>scan filter rules</li>
	 * </ul>
	 * 
	 * @throws ConfigException
	 */
	private void prepareAnalyzing() throws ConfigException {
		resetConfigOutput();
		prepareViewers();
		prepareMatchers();
	}

	private void initViewers(TableModel viewModel) throws ConfigException {
		int checkedNum = 0;
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			boolean checked = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (checked)
				checkedNum++;
		}
		if (checkedNum == 0) {
			outputViewConfigError(null);
		}
		this.lineViewers = new AbstractApacheViewer[checkedNum];
	}

	/**
	 * @throws ConfigException
	 * 
	 */
	private void prepareViewers() throws ConfigException {
		TableModel viewModel = this.apacheViewTable.getModel();
		initViewers(viewModel);
		int line = 0;
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			String option = (String) viewModel.getValueAt(rowIndex, 0);
			String value = (String) viewModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			AbstractApacheViewer viewer = null;
			if (DOMAIN.equals(option)) {
				viewer = new ApacheDomainViewer(value);
			} else if (QUERY.equals(option)) {
				viewer = new ApacheQueryViewer(value);
			} else if (PATH.equals(option)) {
				viewer = new ApachePathViewer(value);
			} else if (REFERRER.equals(option)) {
				viewer = new ApacheReferrerViewer(value);
			} else if (CODE.equals(option)) {
				viewer = new ApacheCodeViewer(value);
			} else if (METHOD.equals(option)) {
				viewer = new ApacheMethodViewer(value);
			} else if (AGENT.equals(option)) {
				viewer = new ApacheAgentViewer(value);
			} else if (IP.equals(option)) {
				viewer = new ApacheIPViewer(value);
			} else if (ALL.equals(option)) {
				viewer = new ApacheAllViewer(value);
			} else {
				outputViewConfigError(option);
			}
			this.lineViewers[line] = viewer;
			line++;
		}
	}

	private void initMatchers(TableModel matchModel) {
		int checkedNum = 0;
		for (int rowIndex = 0; rowIndex < matchModel.getRowCount(); rowIndex++) {
			boolean checked = (Boolean) matchModel.getValueAt(rowIndex, 2);
			if (checked)
				checkedNum++;
		}
		this.lineMatchers = new AbstractApacheMatcher[checkedNum];
	}

	private void prepareMatchers() throws ConfigException {
		TableModel matchModel = this.apacheMatchTable.getModel();
		initMatchers(matchModel);
		int line = 0;
		for (int rowIndex = 0; rowIndex < matchModel.getRowCount(); rowIndex++) {
			String option = (String) matchModel.getValueAt(rowIndex, 0);
			String value = (String) matchModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) matchModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			if (StringKit.isEmpty(value)) {
				outputMatchConfigError(option);
			}
			AbstractApacheMatcher matcher = null;
			if (DOMAIN.equals(option)) {
				matcher = new ApacheDomainMatcher(value);
			} else if (QUERY.equals(option)) {
				String[] kv = StringKit.split(value, '=');
				if (kv.length == 2) {
					matcher = new ApacheQueryMatcher(kv[0], kv[1]);
				} else {
					outputMatchConfigError(option);
				}
			} else if (PATH.equals(option)) {
				matcher = new ApachePathMatcher(value);
			} else if (REFERRER.equals(option)) {
				matcher = new ApacheReferrerMatcher(value);
			} else if (CODE.equals(option)) {
				matcher = new ApacheCodeMatcher(value);
			} else if (METHOD.equals(option)) {
				matcher = new ApacheMethodMatcher(value);
			} else if (AGENT.equals(option)) {
				matcher = new ApacheAgentMatcher(value);
			} else if (IP.equals(option)) {
				matcher = new ApacheIPMatcher(value);
			} else {
				outputMatchConfigError(option);
			}
			this.lineMatchers[line] = matcher;
			line++;
		}
	}

	private void outputMatchConfigError(String option) throws ConfigException {
		String output = null;
		if (option != null) {
			output = "Please check match config: " + option + ".";
		} else {
			output = "Please check match configs.";
		}
		this.apacheConfigOutput.setText(output);
		throw new ConfigException();
	}

	private void outputViewConfigError(String option) throws ConfigException {
		String output = null;
		if (option != null) {
			output = "Please check view config: " + option + ".";
		} else {
			output = "Please check view configs.";
		}
		this.apacheConfigOutput.setText(output);
		throw new ConfigException();
	}

	private void wakeupAnalyzer() {
		analyzeTask.wakeup();
	}

	private void choseFile() {
		int returnVal = fileChooser.showOpenDialog(ControlPanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			this.sourceFile = file;
		} else {
			taskOutput.append("open file error\n");
		}
	}

	private void clickStartButton() {
		startButton.setEnabled(false);
		pauseButton.setEnabled(true);
		stopButton.setEnabled(true);
		fileChooseButton.setEnabled(false);
	}

	private void clickPauseButton() {
		startButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(true);
		fileChooseButton.setEnabled(false);
	}

	private void clickStopButton() {
		startButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		fileChooseButton.setEnabled(true);
	}

	private void initButtons() {
		startButton.setEnabled(false);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
	}

}
