package uk.ac.cam.bz267.oop.tick5;

public class Pattern implements Comparable<Pattern>{

    private String mName;
    private String mAuthor;
    private int mWidth;
    private int mHeight;
    private int mStartCol;
    private int mStartRow;
    private String mCells;
    public String toString(){
      return getName()+"  ("+getAuthor()+")";
    }
    @Override
public int compareTo(Pattern o) {
   // TODO
   return getName().compareTo(o.getName());
}

    //TODO: write public 'get' methods for ALL of the fields above;
    //      for instance 'getName' should be written as:
    public String getName() {
       return mName;
    }
    public String getAuthor(){
      return mAuthor;
    }
    public int getWidth(){
      return mWidth;
    }
    public int getHeight(){
      return mHeight;
    }
    public int getStartCol(){
      return mStartCol;
    }
    public int getStartRow(){
      return mStartRow;
    }
    public String getCells(){
      return mCells;
    }

    public Pattern(String format) throws PatternFormatException{
       //TODO: initialise all fields of this class using contents of
       //      'format' to determine the correct values (this code
       //      is similar tothat you used in the new ArrayLife constructor
       if (format.isEmpty()) { throw new PatternFormatException("Please specify a pattern.");}
       String[] parts = format.split(":");
       if (parts.length !=7) { throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found "+parts.length+").");}
       mName = parts[0];
       mAuthor = parts[1];
       try{
       mWidth = Integer.parseInt(parts[2]);
     } catch(NumberFormatException e){
        throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number ('"+parts[2]+"' given).");
     }

       try{
       mHeight = Integer.parseInt(parts[3]);}
       catch(NumberFormatException e){
          throw new PatternFormatException("Invalid pattern format: Could not interpret the height field as a number ('"+parts[3]+"' given).");
       }
       try{
       mStartCol = Integer.parseInt(parts[4]);}
       catch(NumberFormatException e){
          throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number ('"+parts[4]+"' given).");
       }
       try{mStartRow = Integer.parseInt(parts[5]);}
       catch(NumberFormatException e){
           throw new PatternFormatException("Invalid pattern format: Could not interpret the startY field as a number ('"+parts[5]+"' given).");
       }
       mCells = parts[6];
    }

    public void initialise(World world) throws PatternFormatException{
       //TODO: update the values in the 2D array representing the state of
       //      'world' as expressed by the contents of the field 'mCells'.
       String[] code1 = mCells.split(" ");
       char[][] code2 = new char[mHeight][mWidth];
       char[][] code3 = new char[code1.length][];
       for (int i=0; i<code1.length; i++){
         code3[i] = code1[i].toCharArray();
       }
       for (int i = 0; i < mHeight; i++){
         for (int j=0; j<mWidth; j++){
           if ((i<code1.length) && (j<code1[i].length()))
           {
             code2[i][j] = code3[i][j];
           }
           else{
             code2[i][j] = '0';
           }

         }
       }
       for (int i = 0; i < mHeight-mStartRow; i++){
       	for (int j=0; j<mWidth-mStartCol; j++){
       		if (code2[i][j] == '1' || code2[i][j] == '0'){
       			world.setCell( j+mStartCol,i+mStartRow, code2[i][j] == '1' );
       		}
          else
          { throw new PatternFormatException("Invalid pattern format: Malformed pattern '"+mCells+"'."); };
       	}
       }
    }
}
