/**
 * 
 */
package com.google.code.smallcrab.swing.iis;

import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.config.chart.ChartConfig;
import com.google.code.smallcrab.config.matcher.iis.IisLogLineMatcher;
import com.google.code.smallcrab.config.viewer.iis.IisLogLineViewer;
import com.google.code.smallcrab.reducer.FileLineAnalyzer;
import com.google.code.smallcrab.swing.AnalyzeConfigPanel;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class IisPanel extends AnalyzeConfigPanel<IisLogLineViewer, IisLogLineMatcher> {

	private static final long serialVersionUID = -4148324874328833373L;

	public IisPanel() {
		super(new GridBagLayout());
		setName("iis");
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
	protected List<IisLogLineViewer> prepareViewers() throws ConfigException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#createFileLineAnalyzer()
	 */
	@Override
	public FileLineAnalyzer createFileLineAnalyzer() {
		return null;
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
	protected List<IisLogLineMatcher> prepareMatchers() throws ConfigException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepared()
	 */
	@Override
	public boolean isPrepared() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnalyzeAppend() {
		return false;
	}

	@Override
	public boolean isAnalyzeCount() {
		return true;
	}

}
