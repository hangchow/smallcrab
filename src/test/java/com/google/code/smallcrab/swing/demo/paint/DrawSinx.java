package com.google.code.smallcrab.swing.demo.paint;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class DrawSinx extends JPanel {

	private static final long serialVersionUID = -5545955161976746807L;

	public DrawSinx() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int x, y;
		double a;
		// 画正弦曲线
		// Graphics g=getGraphics();
		for (x = 0; x <= 360; x += 9) {
			a = Math.sin(x * Math.PI / 180);
			y = (int) (80 + 40 * a);
			g2.drawString("*", x, y);
		}
	}

	public static void main(String[] args) {
		WindowUtilities.openInJFrame(new DrawSinx(), 380, 400);
	}
}
