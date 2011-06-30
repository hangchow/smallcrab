package com.google.code.smallcrab.swing.demo.paint;

/*
 * Copyright (c) 2010. 小美版权所有
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Random;

public class AxisFrame extends JFrame {

	public AxisFrame() throws HeadlessException {
		super("坐标图示例");
		this.design();
	}

	AxisCanvas canvas;

	private void design() {
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		canvas = new AxisCanvas();
		content.add(canvas, BorderLayout.CENTER);
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		AxisFrame frame = new AxisFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class AxisCanvas extends JLabel {

	private Insets borderInsets = new Insets(20, 30, 30, 20);
	private String xLabel = "X 轴";
	private String yLabel = "Y 轴";
	private Color lineColor = Color.GREEN;
	private Color dotColor = Color.RED;

	@Override
	public void paint(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(this.getForeground());
		int y = this.getHeight() - borderInsets.bottom;
		g.drawLine(borderInsets.left, borderInsets.top, borderInsets.left, y);
		g.drawLine(borderInsets.left, borderInsets.top, borderInsets.left - 3, borderInsets.top + 5);
		g.drawLine(borderInsets.left, borderInsets.top, borderInsets.left + 3, borderInsets.top + 5);
		int x = this.getWidth() - borderInsets.right;
		g.drawLine(borderInsets.left, y, x, y);
		g.drawLine(x, y, x - 5, y - 3);
		g.drawLine(x, y, x - 5, y + 3);
		g.setFont(this.getFont());
		FontMetrics metrics = g.getFontMetrics();
		int width = metrics.stringWidth(xLabel);
		g.drawString(xLabel, borderInsets.left + (this.getWidth() - borderInsets.left - borderInsets.right - width) / 2, y + (borderInsets.bottom + metrics.getHeight()) / 2);
		AffineTransform transform = new AffineTransform();
		width = metrics.stringWidth(yLabel);
		transform.setToTranslation((borderInsets.left - metrics.getHeight()) / 2, borderInsets.top + (this.getHeight() - borderInsets.top - borderInsets.bottom - width) / 2);
		transform.rotate(Math.PI / 2);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform ot = g2d.getTransform();
		g2d.setTransform(transform);
		g.drawString(yLabel, 0, 0);
		g2d.setTransform(ot);
		float[] xs = new float[20];
		float[] ys = new float[xs.length];
		Random random = new Random();
		for (int i = 0; i < xs.length; i++) {
			xs[i] = random.nextFloat();
			ys[i] = random.nextFloat();
		}
		Arrays.sort(xs);
		drawData(g2d, x, y, xs, ys);
	}

	protected void drawData(Graphics2D g, int x, int y, float[] xs, float[] ys) {
		Stroke os = g.getStroke();
		Stroke stroke = new BasicStroke(2);
		g.setStroke(stroke);
		int x1 = borderInsets.left;
		int x2 = x;
		int y1 = y;
		int y2 = borderInsets.top;
		int width = x2 - x1;
		int height = y2 - y1; // 负的
		int len = xs.length;
		assert (ys.length == len);
		int lastx = 0;
		int lasty = 0;
		for (int i = 0; i < len; i++) {
			x = Math.round(x1 + xs[i] * width);
			y = Math.round(y1 + ys[i] * height);
			if (i > 0) {
				g.setColor(this.getLineColor());
				g.drawLine(x, y, lastx, lasty);
				g.setColor(this.getDotColor());
				g.fillOval(lastx - 3, lasty - 3, 5, 5);
			}
			lastx = x;
			lasty = y;
		}
		g.setColor(this.getDotColor());
		g.fillOval(lastx - 3, lasty - 3, 5, 5);
		g.setStroke(os);
	}

	public Insets getBorderInsets() {
		return borderInsets;
	}

	public void setBorderInsets(Insets borderInsets) {
		this.borderInsets = borderInsets;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getDotColor() {
		return dotColor;
	}

	public void setDotColor(Color dotColor) {
		this.dotColor = dotColor;
	}

}