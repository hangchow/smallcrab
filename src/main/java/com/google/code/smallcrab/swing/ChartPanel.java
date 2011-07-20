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

	private static final Insets borderInsets = new Insets(40, 40, 40, 40);

	private static final DateFormat dateFormat = Format.getShortDateFormat();

	private static final Calendar cal = Format.getCalendar();

	private double xMax;

	private double yMax;

	private double xMin = 0;

	private double yMin = 0;

	private int frequencyMin = 0;

	private int frequencyMax = 0;

	private List<List<Double>> yList;

	private Map<Double, Integer> xMapFrequency;

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public void setFrequencyMin(int frequencyMin) {
		this.frequencyMin = frequencyMin;
	}

	public void setFrequencyMax(int frequencyMax) {
		this.frequencyMax = frequencyMax;
	}

	public void setyList(List<List<Double>> yList) {
		this.yList = yList;
	}

	public void setxMapFrequency(Map<Double, Integer> xMapFrequency) {
		this.xMapFrequency = xMapFrequency;
	}

	public ChartPanel() {
		super(new BorderLayout());
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.white);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (yList != null) {
			drawAxis(g);
			drawXMark(g);
			if (chartConfig != null) {
				if (chartConfig.isDrawFrequency()) {
					drawFrequencyMark(g);
					drawFrequency(g);
				}
				if (chartConfig.isDrawY()) {
					drawYMark(g);
					drawY(g);
				}
			}
		}
	}

	private ChartConfig chartConfig;

	public void setChartConfig(ChartConfig chartConfig) {
		this.chartConfig = chartConfig;
	}

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
	}

	private void drawXMark(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2d.setComposite(ac);
		AffineTransform transform = new AffineTransform();
		int xLength = this.getWidth() - borderInsets.left - borderInsets.right;
		int yLength = this.getHeight() - borderInsets.top - borderInsets.bottom;
		FontMetrics metrics = g.getFontMetrics();
		String yLabel;
		g.setColor(Color.black);
		cal.setTimeInMillis((long) this.xMax);
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) ((this.xMax - this.xMin) / 4 * 3 + this.xMin));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength / 4 * 3 - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) ((this.xMax - this.xMin) / 2 + this.xMin));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength / 2 - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) ((this.xMax - this.xMin) / 4 + this.xMin));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left + xLength / 4 - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		cal.setTimeInMillis((long) (this.xMin));
		yLabel = String.valueOf(dateFormat.format(cal.getTime()));
		transform.setToTranslation(borderInsets.left - metrics.stringWidth(yLabel) / 2, borderInsets.top + yLength + metrics.getHeight());
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
	}

	private void drawYMark(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2d.setComposite(ac);
		AffineTransform transform = new AffineTransform();
		int yLength = this.getHeight() - borderInsets.top - borderInsets.bottom;
		FontMetrics metrics = g.getFontMetrics();
		String yLabel;
		// draw y mark label
		g.setColor(Color.red);
		yLabel = String.valueOf(this.yMax);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.yMax - this.yMin) / 4 * 3 + this.yMin);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength / 4 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.yMax - this.yMin) / 2 + this.yMin);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength / 2 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.yMax - this.yMin) / 4 + this.yMin);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength / 4 * 3 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf(this.yMin);
		transform.setToTranslation(borderInsets.left + metrics.getHeight(), borderInsets.top + yLength - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
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
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2d.setComposite(ac);
		FontMetrics metrics = g.getFontMetrics();
		AffineTransform transform = new AffineTransform();
		String yLabel;
		g.setColor(Color.blue);
		yLabel = String.valueOf(this.frequencyMax);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.frequencyMax - this.frequencyMin) / 4 * 3 + this.frequencyMin);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength / 4 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.frequencyMax - this.frequencyMin) / 2 + this.frequencyMin);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength / 2 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf((this.frequencyMax - this.frequencyMin) / 4 + this.frequencyMin);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength / 4 * 3 - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		yLabel = String.valueOf(this.frequencyMin);
		transform.setToTranslation(borderInsets.left - metrics.getHeight(), borderInsets.top + yLength - metrics.stringWidth(yLabel) / 2);
		transform.rotate(Math.PI / 2);
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
	}

	private void drawFrequency(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(borderInsets.left, this.getHeight() - borderInsets.bottom);
		g2d.setTransform(transform);
		Object[] countArray = xMapFrequency.entrySet().toArray();
		Arrays.sort(countArray, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Object o1, Object o2) {
				return (int) (((Entry<Double, Integer>) o1).getKey() - ((Entry<Double, Integer>) o2).getKey());
			}
		});

		int xAxisLength = this.getWidth() - borderInsets.right - borderInsets.left;
		int yAxisLength = this.getHeight() - borderInsets.bottom - borderInsets.top;
		AlphaComposite acPoint = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		AlphaComposite acLine = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
		g2d.setColor(Color.blue);
		GeneralPath line = new GeneralPath();
		boolean lineStart = true;
		for (Object entry : countArray) {
			@SuppressWarnings("unchecked")
			double x = ((Entry<Double, Integer>) entry).getKey();
			int pointX = (int) ((x - xMin) / (xMax - xMin) * xAxisLength);
			@SuppressWarnings("unchecked")
			int y = ((Entry<Double, Integer>) entry).getValue();
			int pointY = -(int) (1.0 * (y - frequencyMin) / (frequencyMax - frequencyMin) * yAxisLength);
			int[] pointXs = new int[] { pointX, pointX + 1, pointX, pointX - 1 };
			int[] pointYs = new int[] { pointY + 1, pointY, pointY - 1, pointY };
			g2d.setComposite(acPoint);
			g2d.drawPolygon(pointXs, pointYs, 4);
			g2d.fillPolygon(pointXs, pointYs, 4);
			g2d.setComposite(acLine);
			if (this.chartConfig.isDrawFrequencyLine()) {
				if(lineStart) {
					line.moveTo(pointX, pointY);
					lineStart = false;
				} else {
					line.lineTo(pointX, pointY);
				}
			}
			if (this.chartConfig.isDrawFrequencyHistogram()) {
				g2d.drawLine(pointX, 0, pointX, pointY);
			}
		}
		if (this.chartConfig.isDrawFrequencyLine()) {
			g2d.draw(line);
		}

	}

	private void drawY(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(borderInsets.left, this.getHeight() - borderInsets.bottom);
		g2d.setTransform(transform);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f);
		g2d.setColor(Color.red);
		g2d.setComposite(ac);
		int xAxisLength = this.getWidth() - borderInsets.right - borderInsets.left;
		int yAxisLength = this.getHeight() - borderInsets.bottom - borderInsets.top;
		for (List<Double> points : yList) {
			double x = points.get(0);
			int pointX = (int) ((x - xMin) / (xMax - xMin) * xAxisLength);
			for (int i = 1; i < points.size(); i++) {
				double y = points.get(i);
				int pointY = -(int) ((y - yMin) / (yMax - yMin) * yAxisLength);
				int[] pointXs = new int[] { pointX, pointX + 1, pointX, pointX - 1 };
				int[] pointYs = new int[] { pointY + 1, pointY, pointY - 1, pointY };
				g2d.drawPolygon(pointXs, pointYs, 4);
			}
		}
	}

}
