package com.google.code.smallcrab.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.code.smallcrab.config.chart.ChartConfig;
import com.google.code.smallcrab.protocol.Format;
import com.google.code.smallcrab.reducer.AnalyzeCallback;
import com.google.code.smallcrab.reducer.FileAnalyzer;

/**
 * Control panel with analyze task.
 * 
 * @author xalinx at gmail dot com
 * @date Dec 28, 2010
 */
public class ControlPanel extends JPanel implements ActionListener {
	private void println(String msg) {
		System.err.println(Thread.currentThread().getName() + " -- " + msg);
	}

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

		private boolean isPaused() {
			boolean isPaused = false;
			if (analyzer != null) {
				isPaused = analyzer.isPaused();
			}
			return isPaused;
		}

		private FileAnalyzer analyzer;

		private ChartConfig chartConfig;

		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			while (true) {
				try {
					println("sleepping");
					sleep();
					setTitle("Analyzing");
					final long totalLength = sourceFile.length();
					AnalyzeConfigPanel<?, ?> selectedConfig = getSelectedConfigPanel();
					this.analyzer = selectedConfig.createFileLineAnalyzer();
					this.chartConfig = selectedConfig.createChartConfig();
					setProgress(0);
					outputClear();
					outputAnalyzeStart(totalLength);

					final AnalyzeCallback ac = new AnalyzeCallback() {
						private long bufferLineSize = setBufferLineSize();

						@Override
						public void callback() {
							long p1 = this.getAnalyzedSize() * PROGRESS_BAR_MAX;
							long p2 = totalLength * PROGRESS_BAR_MAX;
							int progress = (int) (p1 == p2 ? PROGRESS_BAR_MAX : p1 / totalLength);
							if (progress < 0 || progress > 100) {
								System.out.println("the progress value should be from 0 to 100:" + progress);
							} else {
								setProgress(progress);
							}
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

					if (selectedConfig.isAnalyzeCount()) {
						final Map<String, Integer> result = new HashMap<String, Integer>(10240);
						analyzer.analyzeCount(sourceFile, result, ac);
						outputResultHeader(analyzer, totalLength, ac);
						outputResultCount(analyzer, totalLength, result, ac);
					} else if (selectedConfig.isAnalyzeAppend()) {
						final Map<String, Set<String>> result = new HashMap<String, Set<String>>(10240);
						analyzer.analyzeAppend(sourceFile, result, ac);
						outputResultHeader(analyzer, totalLength, ac);
						outputResultAppend(analyzer, totalLength, result, ac);
					} else if (selectedConfig.isAnalyzeXYSplots()) {
						final List<List<Double>> xySpots = new ArrayList<List<Double>>(10240);
						final Map<Double, Integer> xCount = new HashMap<Double, Integer>(10240);
						analyzer.analyzeXYSplots(sourceFile, xySpots, xCount, ac);
						outputResultHeader(analyzer, totalLength, ac);
						outputResultXYSplots(analyzer, chartConfig, totalLength, xySpots, xCount, ac);
					}
					toolBarPanel.clickStopButton();
					setTitle("Stop");
				} catch (Throwable e) {
					taskOutput.append(e.toString());
					e.printStackTrace();
				}
			}
		}

		/**
		 * clear output result
		 */
		private void outputClear() {
			taskOutput.setText("");
		}

		private void outputAnalyzeStart(final long totalLength) {
			taskOutput.append("===============================================\n");
			taskOutput.append("= Size:" + totalLength + "bytes\n");
		}

		private void outputResultHeader(final FileAnalyzer fl, final long totalLength, final AnalyzeCallback ac) {
			taskOutput.append("= TotalLines:" + ac.getTotalLines() + "\n");
			taskOutput.append("= InvalidLines:" + ac.getInvalidLines() + "\n");
			taskOutput.append("= Consuming:" + fl.getAnalyzePeriod() + "ms\n");
			taskOutput.append("= Speed:" + totalLength / 1024.0 / 1024 / fl.getAnalyzePeriod() * 1000 + "M/S\n");
			taskOutput.append("===============================================\n");
		}

		private void outputResultCount(FileAnalyzer ala, final long totalLength, Map<String, Integer> result, final AnalyzeCallback callback) {
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

		private void outputResultAppend(FileAnalyzer ala, final long totalLength, Map<String, Set<String>> result, final AnalyzeCallback callback) {
			for (Iterator<Entry<String, Set<String>>> itt = result.entrySet().iterator(); itt.hasNext();) {
				Entry<String, Set<String>> next = itt.next();
				taskOutput.append(next.getKey() + "," + next.getValue() + "\n");
			}
		}

		private DateFormat dateFormat = Format.getDateFormat();
		private Calendar cal = Format.getCalendar();

		public void outputResultXYSplots(FileAnalyzer fileAnalyzer, ChartConfig chartConfig, long totalLength, List<List<Double>> yList, Map<Double, Integer> xMapFrequency, AnalyzeCallback analyzeCallback) {
			cal.setTimeInMillis((long) analyzeCallback.getXMinValue());
			taskOutput.append(String.format("x min:%s\n", dateFormat.format(cal.getTime())));
			cal.setTimeInMillis((long) analyzeCallback.getXMaxValue());
			taskOutput.append(String.format("x max:%s\n", dateFormat.format(cal.getTime())));
			taskOutput.append(String.format("y min:%s\n", analyzeCallback.getYMinValue()));
			cal.setTimeInMillis((long) analyzeCallback.getYMaxXValue());
			taskOutput.append(String.format("y max:%s at x:%s\n", analyzeCallback.getYMaxValue(), dateFormat.format(cal.getTime())));
			taskOutput.append(String.format("y average:%s\n", analyzeCallback.getYValueAverage()));
			for (Entry<Double, Integer> entry : xMapFrequency.entrySet()) {
				int count = entry.getValue();
				analyzeCallback.setFrequency(count, entry.getKey());
			}
			taskOutput.append(String.format("frequency min:%s\n", analyzeCallback.getFrequencyMinValue()));
			cal.setTimeInMillis((long) analyzeCallback.getFrequencyMaxXValue());
			taskOutput.append(String.format("frequency max:%s at x:%s\n", analyzeCallback.getFrequencyMaxValue(), dateFormat.format(cal.getTime())));
			taskOutput.append(String.format("frequency average:%s\n", analyzeCallback.getFrequencyAverage()));
			
			chartPanel.setxMax(analyzeCallback.getXMaxValue());
			chartPanel.setxMin(analyzeCallback.getXMinValue());
			chartPanel.setyMax(analyzeCallback.getYMaxValue());
			chartPanel.setyMin(analyzeCallback.getYMinValue());
			chartPanel.setFrequencyMin(analyzeCallback.getFrequencyMinValue());
			chartPanel.setFrequencyMax(analyzeCallback.getFrequencyMaxValue());
			chartPanel.setyList(yList);
			chartPanel.setxMapFrequency(xMapFrequency);
			chartPanel.setChartConfig(chartConfig);
			chartPanel.repaint();
		}

		/**
		 * 
		 */
		public void pause() {
			this.analyzer.pause();
		}

		/**
		 * 
		 */
		public void unpause() {
			this.analyzer.unpause();
		}

		/**
		 * 
		 */
		public void stop() {
			if (null != this.analyzer) {
				this.analyzer.finish();
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1374625498800015010L;

	private File sourceFile;
	private JTextArea taskOutput;
	private AnalyzeTask analyzeTask;
	private PropertyChangeListener taskListener;
	private JFileChooser fileChooser;
	private ToolBarPanel toolBarPanel;
	private ConfigTabbedPanel configPane;
	private JFrame frame;
	private ChartPanel chartPanel;

	public ControlPanel(JFrame frame, JTextArea taskOutput, ChartPanel chartPanel, PropertyChangeListener taskListener) {
		this.setLayout(new BorderLayout());

		this.frame = frame;
		this.taskListener = taskListener;
		this.fileChooser = new JFileChooser();
		this.analyzeTask = new AnalyzeTask();
		this.analyzeTask.addPropertyChangeListener(this.taskListener);
		this.analyzeTask.execute();

		this.taskOutput = taskOutput;
		this.chartPanel = chartPanel;
		this.toolBarPanel = new ToolBarPanel(this);
		add(this.toolBarPanel, BorderLayout.NORTH);

		this.configPane = new ConfigTabbedPanel(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				// do nothing
			}
		}, new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED || state == ItemEvent.DESELECTED) {
					ControlPanel.this.chartPanel.setChartConfig(getSelectedConfigPanel().createChartConfig());
					ControlPanel.this.chartPanel.repaint();
				}
			}
		});
		this.configPane.setPreferredSize(new Dimension(500, 500));
		add(configPane, BorderLayout.CENTER);
	}

	private AnalyzeConfigPanel<?, ?> getSelectedConfigPanel() {
		return (AnalyzeConfigPanel<?, ?>) this.configPane.getSelectedComponent();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ToolBarPanel.CMD_START.equals(ae.getActionCommand())) {
			try {
				getSelectedConfigPanel().resetConfigOutput();
				if (getSelectedConfigPanel().isPrepared()) {
					wakeupAnalyzing();
					if (isAnalyzingPaused()) {
						unpauseAnalyzing();
					}
					this.toolBarPanel.clickStartButton();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ToolBarPanel.CMD_STOP.equals(ae.getActionCommand())) {
			if (isAnalyzingPaused()) {
				unpauseAnalyzing();
			}
			stopAnalyzing();
			this.toolBarPanel.clickStopButton();
		} else if (ToolBarPanel.CMD_PAUSE.equals(ae.getActionCommand())) {
			pauseAnalyzing();
			this.toolBarPanel.clickPauseButton();
		} else if (ToolBarPanel.CMD_SOURCE_CHOOSE == ae.getActionCommand()) {
			if (choseFile()) {
				this.toolBarPanel.clickStopButton();
			}
		}
	}

	private boolean choseFile() {
		int returnVal = fileChooser.showOpenDialog(ControlPanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			this.sourceFile = file;
			setTitle("Selected");
			try {
				this.getSelectedConfigPanel().notifyFileChange(this.sourceFile);
				this.getSelectedConfigPanel().repaint();
			} catch (IOException e) {
				e.printStackTrace();
				taskOutput.append("init config panel error\n");
				return false;
			}
			return true;
		} else {
			taskOutput.append("open file error\n");
			return false;
		}
	}

	private void setTitle(String title) {
		this.frame.setTitle(title + " - " + this.sourceFile.getAbsolutePath() + " - smallcrab");
	}

	/**
	 * wakeup analyzing, because analyze task is sleeping after init.
	 * 
	 * see AnalyzeTask.sleep()
	 */
	private void wakeupAnalyzing() {
		analyzeTask.wakeup();
	}

	private boolean isAnalyzingPaused() {
		return analyzeTask.isPaused();
	}

	/**
	 * pause analyzing
	 */
	private void pauseAnalyzing() {
		analyzeTask.pause();
		setTitle("Paused");
	}

	private void unpauseAnalyzing() {
		analyzeTask.unpause();
	}

	/**
	 * stop analyzing
	 */
	private void stopAnalyzing() {
		analyzeTask.stop();
	}

}
