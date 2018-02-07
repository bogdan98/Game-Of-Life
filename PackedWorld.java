package uk.ac.cam.bz267.oop.tick5;

public class PackedWorld extends World implements Cloneable{
private long mWorld = 0L;
public PackedWorld(Pattern pat) throws PatternNotFound, PatternFormatException{
  super(pat);
  pat.initialise(this);
}
@Override
public Object clone() throws CloneNotSupportedException{
   PackedWorld foo;

        foo = (PackedWorld) super.clone();
    // Deep clone member fields here
    return foo;
}
public PackedWorld(PackedWorld w){
  super(w);
  mWorld = w.mWorld;
}
public PackedWorld(String serial) throws PatternFormatException, PatternNotFound{
  super(serial);
  if (getWidth()*getHeight()>64)
  throw new PatternFormatException("Too big!");
getPattern().initialise(this);

}
public void setCell( int col, int row, boolean value){



  if (value) {
           // TODO: complete this using bitwise operators
           // update the value "packed" with the bit at "position" set to 1
  		 mWorld |= (1L<<col+row*getWidth());
        }
        else {
           // TODO: complete this using bitwise operators
           // update the value "packed" with the bit a "position" set to 0
  		 mWorld = ~((~mWorld) | (1L << col+row*getWidth()));
        }

}
public boolean getCell(int col, int row)  {
  int width = getWidth();
  if(col<0 || col>=width || row<0 || row>=getHeight()) { return false;}

  long check = 1;
if ((mWorld & (1L<<col+row*getWidth())) != 0){
   return (check == 1);

}
    else {
      return (check==0);
    }
  }

public void nextGenerationImpl() {
  long world1=0L;
	long k;
 for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {
         if (computeCell(j, i) == true){
		 k=1L;
		 }
		 else {
			 k=0L;
		 }
		 world1=world1+(k << getWidth()*i + j);
      }

   }
   mWorld = world1;

}

}
