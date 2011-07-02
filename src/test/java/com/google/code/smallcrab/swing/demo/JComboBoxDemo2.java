package com.google.code.smallcrab.swing.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JComboBoxDemo2 {

    public JComboBoxDemo2(){

        JComboBox box = new JComboBox();
        box.addItem("One");
        box.addItem("Two");
        box.addItem("Three");
        box.addItem("Four");

        box.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                System.out.println(e.getItem() + " " + e.getStateChange() );
            }
        });

        JFrame frame = new JFrame();
        frame.getContentPane().add(box);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String [] args) {
    	JComboBoxDemo2 tester = new JComboBoxDemo2();
    }
}
