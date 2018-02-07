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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
public class GUILife extends JFrame{
  private World mWorld;
  private PatternStore mStore;
  private GamePanel mGamePanel = new GamePanel();
  private java.util.Timer mTimer;
  private boolean mPlaying;
  private JButton mPlayButton;
  private ArrayList<World> mCachedWorlds = new ArrayList<World> ();
  public GUILife(PatternStore ps) throws PatternNotFound, PatternFormatException{
    super("Game of Life");
    mStore=ps;

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1024,768);

    add(createPatternsPanel(),BorderLayout.WEST);
    add(createControlPanel(),BorderLayout.SOUTH);
    add(createGamePanel(),BorderLayout.CENTER);

}
private void addBorder(JComponent component, String title) {
    Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    Border tb = BorderFactory.createTitledBorder(etch,title);
    component.setBorder(tb);
}

  private JPanel createGamePanel() {
    GridLayout gly = new GridLayout(1,1);
    mGamePanel.setLayout(gly);
  addBorder(mGamePanel,"Game Panel");
  return mGamePanel;
}

private JPanel createPatternsPanel() {
    JPanel patt = new JPanel();
    addBorder(patt,"Patterns");
    // TODO
    GridLayout pattLY = new GridLayout(1,1);
    DefaultListModel<Pattern> demolist = new DefaultListModel<>();
    for (Pattern p : mStore.getPatternsNameSorted()) {
            demolist.addElement(p);
    }
    JList<Pattern> jlist = new JList<>(demolist);
    jlist.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (mPlaying) {
mTimer.cancel();
mPlaying=false;
mPlayButton.setText("Play");
}
        else{
        JList<Pattern> list = (JList<Pattern>) e.getSource();
        Pattern p = list.getSelectedValue();
        mCachedWorlds = new ArrayList<World>();
        try{
        if (p.getHeight()*p.getWidth() <= 64){
          mWorld = new PackedWorld(p);
        }
        else{mWorld = new ArrayWorld(p);}
      }
      catch(PatternNotFound f){}
catch (PatternFormatException f){};
mCachedWorlds.add(mWorld);
mGamePanel.display(mWorld);}
    }
  });
    JScrollPane scrollpanel = new JScrollPane(jlist);
    patt.setLayout(pattLY);
    patt.add(scrollpanel);
    return patt;
}


private JPanel createControlPanel() {
    JPanel ctrl =  new JPanel();
    addBorder(ctrl,"Controls");
    // TODO
    GridLayout ctrlLY = new GridLayout(1, 3);
    JButton back = new JButton("<Back");
    back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (mPlaying) {
    mTimer.cancel();
    mPlaying=false;
    mPlayButton.setText("Play");
}
else{moveBack();}

            }
        });
    mPlayButton = new JButton("Play");
    mPlayButton.addActionListener(e->runOrPause());
    JButton forward = new JButton("Forward>");
    forward.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (mPlaying) {
    mTimer.cancel();
    mPlaying=false;
    mPlayButton.setText("Play");
}
else{moveForward();}

            }
        });

    ctrl.setLayout(ctrlLY);
    ctrl.add(back);
    ctrl.add(mPlayButton);
    ctrl.add(forward);

    return ctrl;


}
private void runOrPause() {
    if (mPlaying) {
        mTimer.cancel();
        mPlaying=false;
        mPlayButton.setText("Play");
    }
    else {
        mPlaying=true;
        mPlayButton.setText("Stop");
        mTimer = new Timer(true);
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveForward();
            }
        }, 0, 500);
    }
}

private void moveBack(){

  if (mWorld.getGenerationCount() == 0){
    //print();
    mGamePanel.display(mWorld);

  }
  else {
    mWorld = mCachedWorlds.get(mWorld.getGenerationCount()-1);
    //print();
    mGamePanel.display(mWorld);

  }
}
private void moveForward(){

  if (mWorld.getGenerationCount() == mCachedWorlds.size()-1 ){
          mWorld = copyWorld(true);
          mWorld.nextGeneration();

          mCachedWorlds.add(mWorld);
          //print();
          mGamePanel.display(mWorld);

  }
  else {
    mWorld = mCachedWorlds.get(mWorld.getGenerationCount()+1 );
    mGamePanel.display(mWorld);

  }
}
private World copyWorld(boolean useCloning) {
// TODO later
if (useCloning == false){
 if (mWorld instanceof ArrayWorld){return new ArrayWorld((ArrayWorld)mWorld);}
 else {
   if (mWorld instanceof PackedWorld){return new PackedWorld((PackedWorld)mWorld);}
   else {return null;}
 }
}
else {
try{return (World) mWorld.clone();}
catch(CloneNotSupportedException e){e.printStackTrace();
System.exit(0);};
}
return null;
}
public static void main(String[] args) throws IOException, PatternNotFound, PatternFormatException{
  PatternStore patts = new PatternStore(args[0]);
  GUILife gui = new GUILife(patts);
  gui.setVisible(true);

}

}
