package uk.ac.cam.bz267.oop.tick5;
public class ArrayWorld extends World implements Cloneable{

    private boolean[][] mWorld;
    private boolean[] mDeadRow;
    public ArrayWorld(Pattern pat) throws PatternNotFound, PatternFormatException{
      super(pat);
      mDeadRow = new boolean[getWidth()];
      mWorld = new boolean[getHeight()][getWidth()];
      getPattern().initialise(this);
      pointtoDeadRow();
    }
    public ArrayWorld(ArrayWorld w){
      super(w);
      mDeadRow = new boolean[getWidth()];
      mWorld = new boolean[w.getHeight()][w.getWidth()];
      for (int i=0; i<mWorld.length; i++){
        for(int j=0; j<mWorld[i].length; j++){
          mWorld[i][j] = w.mWorld[i][j];
        }
      }
      pointtoDeadRow();
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
       ArrayWorld foo = (ArrayWorld) super.clone();

        // Deep clone member fields here
        foo.mWorld = new boolean[getHeight()][getWidth()];
        for (int i=0; i< mWorld.length; i++){
          for (int j=0; j<mWorld[i].length; j++){
            foo.mWorld[i][j] = mWorld[i][j];
          }
        }
        foo.pointtoDeadRow();
        return foo;
    }

    public ArrayWorld(String serial)  throws PatternFormatException{
      super(serial);
      mWorld = new boolean[getHeight()][getWidth()];
      getPattern().initialise(this);
    }

    // TODO: fill in the inherited formerly-abstract methods
    public void nextGenerationImpl() {
  			 boolean[][] nextGeneration = new boolean[getHeight()][getWidth()];
  			 for (int y = 0; y < getHeight(); ++y) {
  					 nextGeneration[y] = new boolean[getWidth()];
  					 for (int x = 0; x < getWidth(); ++x) {
  							 boolean nextCell = computeCell(x, y);
  							 nextGeneration[y][x]=nextCell;


  					 }
  			 }
  			 mWorld = nextGeneration;

  	 }
     public void pointtoDeadRow(){
       for (int i=0; i < mWorld.length; i++){
         int count = 0;
         for (int j =0; j<mWorld[i].length; j++){
           if (mWorld[i][j] == false){
             count++;
           }
         }
         if (count == mWorld[i].length){
           mWorld[i] = mDeadRow;
         }
         }
       }


     public void setCell(int col, int row, boolean newval) {

     	mWorld [row][col] = newval;
     }
     public boolean getCell(int col, int row) {
   	   if (row < 0 || row > getHeight()-1) return false;
   	   if (col < 0 || col > getWidth()-1) return false;

   	   return mWorld[row][col];
   	}

}
