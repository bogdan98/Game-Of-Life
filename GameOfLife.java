package uk.ac.cam.bz267.oop.tick5;
import java.io.*;
import java.net.*;
import java.util.*;
public class GameOfLife {

    private World mWorld;
    private PatternStore mStore;
    private ArrayList<World> mCachedWorlds = new ArrayList<World> ();
    public GameOfLife(PatternStore xy){
      mStore = xy;
    }

    public void play() throws java.io.IOException, PatternFormatException, PatternNotFound {

        String response="";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please select a pattern to play (l to list:");
        while (!response.equals("q")) {
                response = in.readLine();
                System.out.println(response);
                if (response.equals("f")) {
                        if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");

                        else if (mWorld.getGenerationCount() == mCachedWorlds.size()-1 ){
                                mWorld = copyWorld(true);
                                mWorld.nextGeneration();

                                mCachedWorlds.add(mWorld);
                                print();
                        }
                        else {
                          mWorld = mCachedWorlds.get(mWorld.getGenerationCount()+1 );
                          print();
                        }
                }
                else if (response.equals("b")){
                  if (mWorld.getGenerationCount() == 0){
                    print();
                  }
else {
                    mWorld = mCachedWorlds.get(mWorld.getGenerationCount()-1);
                    print();
                  }
                }

                else if (response.equals("l")) {
                        List<Pattern> names = mStore.getPatternsNameSorted();
                        int i=0;
                        for (Pattern p : names) {
                                System.out.println(i+" "+p.getName()+"  ("+p.getAuthor()+")");
                                i++;
                        }
                }
                else if (response.startsWith("p")) {
                   List<Pattern> names = mStore.getPatternsNameSorted();
                   // TODO: Extract the integer after the p in response
                   String[] parts = response.split(" ");
                   int xxx = Integer.parseInt(parts[1]);
                   // TODO: Get the associated pattern
                   Pattern Pat = names.get(xxx);

                   // TODO: Initialise mWorld using PackedWorld or ArrayWorld based
                   //       on pattern world size
                   if (Pat.getHeight()*Pat.getWidth()<=64){
                     mWorld = new PackedWorld(Pat);



                   }
                   else{mWorld = new ArrayWorld(Pat);


                     }
                     mCachedWorlds.add(mWorld);
                   print();
                }

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



    public static void main(String args[]){

        if (args.length!=1) {
                System.out.println("Usage: java GameOfLife <path/url to store>");
                return;
        }

        try {
                PatternStore ps = new PatternStore(args[0]);
                GameOfLife gol = new GameOfLife(ps);
                gol.play();
        }
        catch (IOException ioe) {
                System.out.println("Failed to load pattern store");
        }
        catch(PatternFormatException e){
          System.out.println(e.getMessage());
        }
        catch(PatternNotFound e){
          System.out.println(e.getMessage());

        }


    }

    public void print() {
        // TODO
        System.out.println("-"+ mWorld.getGenerationCount());
        for (int row = 0; row < mWorld.getHeight(); row++) {
           for (int col = 0; col < mWorld.getWidth(); col++) {
              System.out.print(mWorld.getCell(col, row) ? "#" : "_");
           }
           System.out.println();
        }
    }
  }

    /*public static void main(String[] args) throws java.io.IOException {
            World w=null;

            // TODO: initialise w as an ArrayWorld or a PackedWorld
            // based on the command line input
            String s1 = new String("--packed");
            String s2 = new String("--array");
            try {
              if (args[0].equals(s1)){
                    w = new PackedWorld(args[1]);}
              else if(args[0].equals(s2)) {
                w = new ArrayWorld(args[1]);}

            else {
              w = new ArrayWorld(args[0]);
            }}catch(PatternFormatException e) {
              System.out.println(e.getMessage());
              System.exit(0);
            }


            GameOfLife gol = new GameOfLife(w);
            gol.play();
    }
  }*/
