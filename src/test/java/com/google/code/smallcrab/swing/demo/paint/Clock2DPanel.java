package com.google.code.smallcrab.swing.demo.paint;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Calendar;
import javax.swing.*;

public class Clock2DPanel extends JPanel implements ActionListener
{
    // Create a shape for the face of the clock
    protected static Ellipse2D face = new Ellipse2D.Float(3, 3, 94, 94);

    // Create a path that represents a tick mark
    protected static GeneralPath tick = new GeneralPath();
    static
    {
        tick.moveTo(100, 100);
        tick.moveTo(49, 0);
        tick.lineTo(51, 0);
        tick.lineTo(51, 6);
        tick.lineTo(49, 6);
        tick.lineTo(49, 0);
    }

    // Create a cool hour hand
    protected static GeneralPath hourHand = new GeneralPath();
    static
    {
        hourHand.moveTo(50, 15);
        hourHand.lineTo(53, 50);
        hourHand.lineTo(50, 53);
        hourHand.lineTo(47, 50);
        hourHand.lineTo(50, 15);
    }

    // Create a cool minute hand
    protected static GeneralPath minuteHand = new GeneralPath();
    static
    {
        minuteHand.moveTo(50, 2);
        minuteHand.lineTo(53, 50);
        minuteHand.lineTo(50, 58);
        minuteHand.lineTo(47, 50);
        minuteHand.lineTo(50, 2);
    }

// And a cool second hand
    protected static GeneralPath secondHand = new GeneralPath();
    static
    {
        secondHand.moveTo(49, 5);
        secondHand.lineTo(51, 5);
        secondHand.lineTo(51, 62);
        secondHand.lineTo(49, 62);
        secondHand.lineTo(49, 5);
    }

// Create some colors for the pieces of the clock
    protected static Color faceColor = new Color(220, 220, 220);
    protected static Color hourColor = Color.red.darker();
    protected static Color minuteColor = Color.blue.darker();
    protected static Color secondColor = new Color(180, 180, 0);
    protected static Color pinColor = Color.gray.brighter();

    // Create circles for the pivot and center pin
    protected Ellipse2D pivot = new Ellipse2D.Float(47, 47, 6, 6);
    protected Ellipse2D centerPin = new Ellipse2D.Float(49, 49, 2, 2);

    
    // Create three transforms that center around the pivot point
    protected AffineTransform hourTransform =
                    AffineTransform.getRotateInstance(0, 50, 50);
    protected AffineTransform minuteTransform =
                    AffineTransform.getRotateInstance(0, 50, 50);
    protected AffineTransform secondTransform =
                    AffineTransform.getRotateInstance(0, 50, 50);

    // Create a timer that fires once a second and a Calendar
    // instance for getting the time values
    protected Timer timer = new Timer(1000, this);
    protected Calendar calendar = Calendar.getInstance();

    // Constructor - hardcode a preferred size of 100x100
    public Clock2DPanel()
    {
        setPreferredSize(new Dimension(100, 100));
    }

// Invoked when panel is added to a container
    public void addNotify()
    {
// Call the superclass and start the timer
        super.addNotify();
        timer.start();
    }

// Invoked when panel is removed from a container
    public void removeNotify()
    {
// Call the superclass and stop the timer
        timer.stop();
        super.removeNotify();
    }

    //
    public void actionPerformed(ActionEvent event)
    {
        // Update the calendar's time
        this.calendar.setTime(new java.util.Date());
        
        // Extract the hours minutes and seconds
        int hours = this.calendar.get(Calendar.HOUR);
        int minutes = this.calendar.get(Calendar.MINUTE);
        int seconds = this.calendar.get(Calendar.SECOND);
        
        // Using a little trigonometry, set the transforms to rotate
        // each hand into the proper position.  Center the rotation
        // around the pivot point (50, 50) instead of the origin
        hourTransform.setToRotation(((double) hours) *
                                            (Math.PI / 6.0), 50, 50);
        minuteTransform.setToRotation(((double) minutes) *
                                            (Math.PI / 30.0), 50, 50);
        secondTransform.setToRotation(((double) seconds) *
                                            (Math.PI / 30.0), 50, 50);
        
        // Force the component to repaint ASAP
        repaint();
    }

    // This is an alternative to creating a UI delegate.  Since JPanel's
    // paint() method only paints the border and backgound, we can just
    // override the paint method of the component to do the graphics.
    public void paint(Graphics g)
    {
        // Call the superclass first to paint the border (if one is assigned)
        super.paint(g);
        
        // Get the graphics context and turn on anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the paint for the clock face and fill it in
        g2.setPaint(faceColor);
        g2.fill(face);

// Set the paint to black and draw the clock's outline
        g2.setPaint(Color.black);
        g2.draw(face);
        
        // Fill in the 12 ticks around the face of the clock
        for (double p = 0.0; p < 12.0; p += 1.0)
        {
            // This is probably terribly inefficient and should be
            // done statically or in the constructor - draw the
            // tick as a transformed shape that is rotated.
            g2.fill(tick.createTransformedShape(
                AffineTransform.getRotateInstance((Math.PI / 6.0)  * p,
                        50, 50)));
        }

        // Set the paint and draw the hour hand.  It is lowest in the
        // 'z-order' so will appear underneath the other hands.  Notice
        // how each hand is transformed by a different <AffineTransform>.
        g2.setPaint(hourColor);
        g2.fill(hourHand.createTransformedShape(hourTransform));

        // Set the paint and draw the minute hand, the second hand,
        // the pivot and the center pin
        g2.setPaint(minuteColor);
        g2.fill(minuteHand.createTransformedShape(minuteTransform));
        g2.setPaint(secondColor);
        g2.fill(secondHand.createTransformedShape(secondTransform));
        g2.fill(pivot);
        g2.setPaint(pinColor);
        g2.fill(centerPin);
    }

// A little test frame to show off our fancy clock
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setLocation(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Clock2DPanel());
        frame.pack();
        frame.show();
    }
}
