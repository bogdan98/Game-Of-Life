package uk.ac.cam.bz267.oop.tick5;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.event.*;
public class ButtonDemo extends JFrame  {

    public ButtonDemo() {
        JButton b = new JButton("Click me!");
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button was clicked!");
            }
        });
        add(b);
    }
}
