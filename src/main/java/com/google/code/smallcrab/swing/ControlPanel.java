package com.google.code.smallcrab.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.code.smallcrab.analyze.AnalyzeCallback;
import com.google.code.smallcrab.analyze.FileLineAnalyzer;

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

		private boolean isPaused() {
			boolean isPaused = false;
			if (analyzer != null) {
				isPaused = analyzer.isPaused();
			}
			return isPaused;
		}

		private FileLineAnalyzer analyzer;

		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			while (true) {
				try {
					sleep();
					final long totalLength = sourceFile.length();
					this.analyzer = analyzeConfigPanel.createFileLineAnalyzer();
					setProgress(0);
					outputClear();
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
					stopAnalyze(analyzer, totalLength, result, ac);
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
			taskOutput.append("= Size:" + totalLength + "\n");
		}

		private void stopAnalyze(FileLineAnalyzer ala, final long totalLength, final Map<String, Integer> result, final AnalyzeCallback ac) {
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
			toolBarPanel.clickStopButton();
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
			this.analyzer.finish();
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
	private AnalyzeConfigPanel<?, ?> analyzeConfigPanel;
	private ConfigTabbedPanel configPane;

	public ControlPanel(JTextArea taskOutput, PropertyChangeListener taskListener) {
		this.setLayout(new BorderLayout());

		this.taskListener = taskListener;
		this.fileChooser = new JFileChooser();
		this.analyzeTask = new AnalyzeTask();
		this.analyzeTask.addPropertyChangeListener(this.taskListener);
		this.analyzeTask.execute();

		this.taskOutput = taskOutput;
		this.toolBarPanel = new ToolBarPanel(this);
		add(this.toolBarPanel, BorderLayout.NORTH);

		this.configPane = new ConfigTabbedPanel(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				analyzeConfigPanel = (AnalyzeConfigPanel<?, ?>) sourceTabbedPane.getSelectedComponent();
			}
		});
		this.analyzeConfigPanel = (AnalyzeConfigPanel<?, ?>) this.configPane.getSelectedComponent();

		add(configPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ToolBarPanel.CMD_START.equals(ae.getActionCommand())) {
			try {
				this.analyzeConfigPanel.resetConfigOutput();
				if (this.analyzeConfigPanel.isPrepared()) {
					wakeupAnalyzing();
				}
				if (isAnalyzingPaused()) {
					unpauseAnalyzing();
				}
				this.toolBarPanel.clickStartButton();
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
			choseFile();
			this.toolBarPanel.clickStopButton();
		}

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

	private void choseFile() {
		int returnVal = fileChooser.showOpenDialog(ControlPanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			this.sourceFile = file;
		} else {
			taskOutput.append("open file error\n");
		}
	}

}
