package com.google.code.smallcrab.swing;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import com.google.code.smallcrab.config.chart.ChartConfig;
import com.google.code.smallcrab.protocol.Format;

public class ChartPanel extends JPanel {

	private static final long serialVersionUID = -5545955161976746807L;

	public ChartPanel() {
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 400));
		setBackground(Color.white);
	}

	private double xMaxValue;

	public void setxMaxValue(double xMaxValue) {
		this.xMaxValue = xMaxValue;
	}

	private double yMaxValue;

	public void setyMaxValue(double yMaxValue) {
		this.yMaxValue = yMaxValue;
	}

	private double xMinValue = 0;

	private double yMinValue = 0;

	public void setxMinValue(double xMinValue) {
		this.xMinValue = xMinValue;
	}

	public void setyMinValue(double yMinValue) {
		this.yMinValue = yMinValue;
	}

	private double xMinCount = 0;

	private double xMaxCount = 0;

	public void setxMinCount(int xMinCount) {
		this.xMinCount = xMinCount;
	}

	public void setxMaxCount(int xMaxCount) {
		this.xMaxCount = xMaxCount;
	}

	private Insets borderInsets = new Insets(40, 40, 40, 40);

	public void paintPoint(int x, int y) {

	}

	private List<List<Double>> result;

	private Map<Double, Integer> xCount;

	public void setResult(List<List<Double>> result2) {
		this.result = result2;
	}

	public void setxCount(Map<Double, Integer> xCount) {
		this.xCount = xCount;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (result != null) {
			drawAxis(g);
			if (chartConfig != null && chartConfig.isNeedFrequency()) {
				drawFrequencyMark(g);
				drawFrequencyData(g);
			}
			drawData(g);
		}
	}

	private DateFormat dateFormat = Format.getDateFormat();
	private Calendar cal = Format.getCalendar();

	private ChartConfig chartConfig;

	private void drawAxis(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2d.setComposite(ac);
		int xEnd = this.getWidth() - borderInsets.right;
		int yEnd = this.getHeight() - borderInsets.bottom;
		int xLength = this.getWidth() - borderInsets.left - borderInsets.right;
		int yLength = this.getHeight() - borderInsets.top - borderInsets.bottom;
		// draw axis
		g.setColor(Color.black);
		g.drawLine(borderInsets.left, borderInsets.top, borderInsets.left, yEnd);
		g.drawLine(borderInsets.left, borderInsets.top, borderInsets.left - 3, borderInsets.top + 3);
		g.drawLine(borderInsets.left, borderInsets.top, borderInsets.left + 3, borderInsets.top + 3);
		g.drawLine(borderInsets.left, yEnd, xEnd, yEnd);
		g.drawLine(xEnd, yEnd, xEnd - 3, yEnd + 3);
		g.drawLine(xEnd, yEnd, xEnd - 3, yEnd - 3);
		// draw y mark
		g.drawLine(borderInsets.left + 5, borderInsets.top, borderInsets.left - 5, borderInsets.top);
		g.drawLine(borderInsets.left + 5, borderInsets.top + yLength / 4, borderInsets.left - 5, borderInsets.top + yLength / 4);
		g.drawLine(borderInsets.left + 5, borderInsets.top + yLength / 2, borderInsets.left - 5, borderInsets.top + yLength / 2);
		g.drawLine(borderInsets.left + 5, borderInsets.top + yLength / 4 * 3, borderInsets.left - 5, borderInsets.top + yLength / 4 * 3);
		g.drawLine(borderInsets.left, yEnd, borderInsets.left - 5, yEnd);
		// draw x mark
		g.drawLine(xEnd, yEnd, xEnd, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 8 * 7, yEnd, borderInsets.left + xLength / 8 * 7, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 4 * 3, yEnd, borderInsets.left + xLength / 4 * 3, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 8 * 5, yEnd, borderInsets.left + xLength / 8 * 5, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 2, yEnd, borderInsets.left + xLength / 2, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 8 * 3, yEnd, borderInsets.left + xLength / 8 * 3, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 4, yEnd, borderInsets.left + xLength / 4, yEnd + 5);
		g.drawLine(borderInsets.left + xLength / 8 * 1, yEnd, borderInsets.left + xLength / 8 * 1, yEnd + 5);
		g.drawLine(borderInsets.left, yEnd, borderInsets.left, yEnd + 5);
		// draw axis label
		g.setColor(Color.black);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2d.setComposite(ac);
		String xLabel = "x-axis";
		String yLabel = "y-axis";
		g.setFont(this.getFont());
		FontMetrics metrics = g.getFontMetrics();
		int width = metrics.stringWidth(xLabel);
		g.drawString(xLabel, borderInsets.left + (xLength - width) / 2, yEnd + (borderInsets.bottom + metrics.getHeight()) / 2);
		AffineTransform transform = new AffineTransform();
		width = metrics.stringWidth(yLabel);
		transform.setToTranslation((borderInsets.left - metrics.getHeight()) / 2, borderInsets.top + (yLength - width) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		// draw y mark label
		g.setColor(Color.red);
		yLabel = String.valueOf(this.yMaxValue);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.yMaxValue - this.yMinValue) / 4 * 3 + this.yMinValue);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength / 4 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.yMaxValue - this.yMinValue) / 2 + this.yMinValue);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength / 2 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.yMaxValue - this.yMinValue) / 4 + this.yMinValue);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength / 4 * 3 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf(this.yMinValue);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		// draw x mark label
		g.setColor(Color.black);
		cal.setTimeInMillis((long) this.xMaxValue);
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) ((this.xMaxValue - this.xMinValue) / 4 * 3 + this.xMinValue));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength / 4 * 3 - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) ((this.xMaxValue - this.xMinValue) / 2 + this.xMinValue));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength / 2 - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) ((this.xMaxValue - this.xMinValue) / 4 + this.xMinValue));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength / 4 - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) (this.xMinValue));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
	}

	/**
	 * draw frequncy mark label
	 * 
	 * @param g
	 * @param g2d
	 * @param yLength
	 * @param metrics
	 * @param transform
	 */
	private void drawFrequencyMark(Graphics g) {
		int yLength = this.getHeight() - borderInsets.top - borderInsets.bottom;
		Graphics2D g2d = (Graphics2D) g;
		FontMetrics metrics = g.getFontMetrics();
		AffineTransform transform = new AffineTransform();
		String yLabel;
		g.setColor(Color.blue);
		yLabel = String.valueOf(this.xMaxCount);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.xMaxCount - this.xMinCount) / 4 * 3 + this.xMinCount);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength / 4 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.xMaxCount - this.xMinCount) / 2 + this.xMinCount);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength / 2 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.xMaxCount - this.xMinCount) / 4 + this.xMinCount);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength / 4 * 3 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf(this.xMinCount);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
	}

	private void drawFrequencyData(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(borderInsets.left, this.getHeight() - borderInsets.bottom);
		g2d.setTransform(transform);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2d.setComposite(ac);
		Object[] countArray = xCount.entrySet().toArray();
		Arrays.sort(countArray, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Object o1, Object o2) {
				return (int) (((Entry<Double, Integer>) o1).getKey() - ((Entry<Double, Integer>) o2).getKey());
			}
		});
		GeneralPath line = new GeneralPath();
		int xAxisLength = this.getWidth() - borderInsets.right - borderInsets.left;
		int yAxisLength = this.getHeight() - borderInsets.bottom - borderInsets.top;
		boolean isLineStart = true;
		for (Object entry : countArray) {
			@SuppressWarnings("unchecked")
			double x = ((Entry<Double, Integer>) entry).getKey();
			int pointX = (int) ((x - xMinValue) / (xMaxValue - xMinValue) * xAxisLength);
			@SuppressWarnings("unchecked")
			int y = ((Entry<Double, Integer>) entry).getValue();
			int pointY = -(int) ((y - xMinCount) / (xMaxCount - xMinCount) * yAxisLength);
			int[] pointXs = new int[] { pointX, pointX + 1, pointX, pointX - 1 };
			int[] pointYs = new int[] { pointY + 1, pointY, pointY - 1, pointY };
			g2d.setColor(Color.blue);
			g2d.drawPolygon(pointXs, pointYs, 4);
			g2d.fillPolygon(pointXs, pointYs, 4);
			if (isLineStart) {
				line.moveTo(pointX, pointY);
				isLineStart = false;
			} else {
				line.lineTo(pointX, pointY);
			}
		}
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		g2d.setComposite(ac);
		g2d.draw(line);

	}

	private void drawData(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(borderInsets.left, this.getHeight() - borderInsets.bottom);
		g2d.setTransform(transform);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f);
		g2d.setColor(Color.red);
		g2d.setComposite(ac);
		int xAxisLength = this.getWidth() - borderInsets.right - borderInsets.left;
		int yAxisLength = this.getHeight() - borderInsets.bottom - borderInsets.top;
		for (List<Double> points : result) {
			double x = points.get(0);
			int pointX = (int) ((x - xMinValue) / (xMaxValue - xMinValue) * xAxisLength);
			for (int i = 1; i < points.size(); i++) {
				double y = points.get(i);
				int pointY = -(int) ((y - yMinValue) / (yMaxValue - yMinValue) * yAxisLength);
				int[] pointXs = new int[] { pointX, pointX + 1, pointX, pointX - 1 };
				int[] pointYs = new int[] { pointY + 1, pointY, pointY - 1, pointY };
				g2d.drawPolygon(pointXs, pointYs, 4);
				g2d.fillPolygon(pointXs, pointYs, 4);
			}
		}
	}

	public void setChartConfig(ChartConfig chartConfig) {
		this.chartConfig = chartConfig;
	}

}
