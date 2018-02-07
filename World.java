package uk.ac.cam.bz267.oop.tick5;

public abstract class World {
  private int mGeneration ;
  private Pattern mPattern;
  public World(World w){
    mGeneration = w.mGeneration;
    mPattern = w.mPattern;
  }
  @Override
  public Object clone() throws CloneNotSupportedException{
  return super.clone();
  }
  public World(Pattern pattern) throws PatternNotFound{
mGeneration = 0;
mPattern = pattern;
  }

  public World(String pattern) throws PatternFormatException{
    mGeneration = 0;
  mPattern = new Pattern(pattern);

  }
  public abstract boolean getCell(int c, int r);
  public abstract void setCell(int c, int r, boolean p);
  public void nextGeneration(){
    nextGenerationImpl();
        mGeneration++;
  };
  public int getWidth(){
return mPattern.getWidth();
  }
  public int getHeight(){
return mPattern.getHeight();
  }
  public int getGenerationCount(){
return mGeneration;
  }
  protected void incrementGenerationCount(){
mGeneration++;
  }
  protected Pattern getPattern(){
    return mPattern;
  }
  protected abstract void nextGenerationImpl();
  protected int countNeighbours(int c, int r){
    int k=0;
    int i;
    int j;

    for (i=c-1; i<c+2; i++) {
    for (j=r-1; j<r+2; j++) {
    if ((getCell(i, j) == true) ) {

    k=k+1;
    }

    }

    }
    if ((getCell(c, r) == true) ) {
    return (k-1);
    }
    else {
    	return (k);
    }
  };
  protected boolean computeCell(int c, int r){

       // liveCell is true if the cell at position (col,row) in world is live
       boolean liveCell = getCell(c, r);

       // neighbours is the number of live neighbours to cell (col,row)
       int neighbours = countNeighbours(c, r);

       // we will return this value at the end of the method to indicate whether
       // cell (col,row) should be live in the next generation
       boolean nextCell = false;

       //A live cell with less than two neighbours dies (underpopulation)
       if ((liveCell==true) && (neighbours < 2)) {
          nextCell = false;
       }
       else {
       if ((liveCell==true) && (neighbours < 4) && (neighbours >1)) {
       nextCell = true;
       }
       else {
       if ((liveCell==true) && (neighbours >3)) {

       nextCell = false;
       }
       else {
       if ((liveCell==false) && (neighbours==3)) {
       nextCell = true;
       }
       }
       }
       }



       return nextCell;
  };
}
