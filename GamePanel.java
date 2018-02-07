package uk.ac.cam.bz267.oop.tick5;
import javax.swing.JPanel;
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
public class GamePanel extends JPanel{

     private World mWorld = null;
          public int[] distr (int width, int number){
         int rem = width % number;
         int inw = width/number;
         int[] arr = new int[number];
         for (int i=0; i<rem; i++){
           arr[i] = inw + 1;
         }
         for (int j = rem; j<number; j++){
           arr[j] = inw;
         }
         return arr;
       }

       @Override
       protected void paintComponent(java.awt.Graphics g) {
           // Paint the background white
           g.setColor(java.awt.Color.WHITE);
           int width = this.getWidth();
           int height =this.getHeight();
           g.fillRect(0, 0, width, height);

         if (mWorld==null){;}
         else {
           if (height/(mWorld.getHeight()) < width/(mWorld.getWidth())){
             int i=0;
             int j=0;
             int xcoord = 0;
             int ycoord = 0;
             int avgd = height/(mWorld.getHeight());
             int[] dist = distr(height, mWorld.getHeight());
             while (i<mWorld.getHeight()){
               while(j<mWorld.getWidth()){
                 g.setColor(Color.LIGHT_GRAY);
                 g.drawRect(xcoord, ycoord, avgd, dist[i]);
                 g.setColor(Color.BLACK);
                 if (mWorld.getCell(j, i) == true){
                   g.fillRect(xcoord, ycoord, avgd, dist[i]);
                  }
                 xcoord+=avgd;


                 j++;
               }
               ycoord+=dist[i];
               i++;
               j=0;
               xcoord = 0;
             }
           }


           else {
             int i=0;
             int j=0;
             int xcoord = 0;
             int ycoord = 0;
             int avgd = width/(mWorld.getWidth());
             int[] dist = distr(width, mWorld.getWidth());
             while (i<mWorld.getHeight()){
                while(j<mWorld.getWidth()){
                  g.setColor(Color.LIGHT_GRAY);
                  g.drawRect(xcoord, ycoord, dist[j], avgd);
                  g.setColor(Color.BLACK);
                  if (mWorld.getCell(j, i) == true){
                    g.fillRect(xcoord, ycoord, dist[j], avgd);
                  }
                  xcoord+=dist[j];

                  j++;
                }
                ycoord+=avgd;
                i++;
                j=0;
                xcoord = 0;
              }
            }
            g.drawString("Generation:"+String.valueOf(mWorld.getGenerationCount()), 3, height-12);

          }

        }




      public void display(World w) {
          mWorld = w;
          repaint();
      }
  }
