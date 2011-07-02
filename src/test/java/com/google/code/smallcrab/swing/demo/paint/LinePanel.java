package com.google.code.smallcrab.swing.demo.paint;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** @see http://stackoverflow.com/questions/5797862 */
public class LinePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7267276551468944941L;
	private MouseHandler mouseHandler = new MouseHandler();
    private Point p1 = new Point(100, 100);
    private Point p2 = new Point(540, 380);
    private boolean drawing;

    public LinePanel() {
        this.setPreferredSize(new Dimension(640, 480));
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            drawing = true;
            p1 = e.getPoint();
            p2 = p1;
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            drawing = false;
            p2 = e.getPoint();
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (drawing) {
                p2 = e.getPoint();
                repaint();
            }
        }
    }

    private class ControlPanel extends JPanel {

        private static final int DELTA = 10;

        public ControlPanel() {
            this.add(new JButton(new AbstractAction("<") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    adjust(-DELTA, 0);
                }
            }));
            this.add(new JButton(new AbstractAction("v") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    adjust(0, DELTA);
                }
            }));
            this.add(new JButton(new AbstractAction("^") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    adjust(0, -DELTA);
                }
            }));
            this.add(new JButton(new AbstractAction(">") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    adjust(DELTA, 0);
                }
            }));
        }

        private void adjust(int x, int y) {
            p1.translate(x, y);
            p2.translate(x, y);
            LinePanel.this.repaint();
        }
    }

    private void display() {
        JFrame f = new JFrame("LinePanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.add(new ControlPanel(), BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new LinePanel().display();
            }
        });
    }
}
