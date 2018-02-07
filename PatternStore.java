package uk.ac.cam.bz267.oop.tick5;
import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
  private List<Pattern> mPatterns = new LinkedList<>();
private Map<String,List<Pattern>> mMapAuths = new HashMap<>();
private Map<String,Pattern> mMapName = new HashMap<>();
public List<Pattern> getPatternsNameSorted() {
   // TODO: Get a list of all patterns sorted by name
   List<Pattern> copy  = new LinkedList<Pattern>(mPatterns);
   Collections.sort(copy);
   return copy;
}

public List<Pattern> getPatternsAuthorSorted() {
   // TODO: Get a list of all patterns sorted by author then name
    List<Pattern> copy  = new LinkedList<Pattern>(mPatterns);
   Collections.sort(copy, new Comparator<Pattern>() {
   public int compare(Pattern p1, Pattern p2) {
       int i = (p1.getAuthor()).compareTo(p2.getAuthor());
       if (i==0){
         return (p1.compareTo(p2));
       }
       else return i;
   }
 });
 return copy;
}

public List<Pattern> getPatternsByAuthor(String author)  throws PatternNotFound{
  if ( !mMapAuths.containsKey(author)) {
    throw new PatternNotFound(author + "not found");
  }

   // TODO:  return a list of patterns from a particular author sorted by name
   //Map<String, List<Pattern>> copy = new HashMap<String, List<Pattern>>(mMapAuths);
   List<Pattern> listpat = new LinkedList<Pattern>(mMapAuths.get(author));
   Collections.sort(listpat);
   return listpat;

}

public Pattern getPatternByName(String name)  throws PatternNotFound{
   // TODO: Get a particular pattern by name
   //Map<String,Pattern> copy = new HashMap<String,Pattern>(mMapName);
   if (!mMapName.containsKey(name)){
     throw new PatternNotFound(name + "not found");
   }
   Pattern newPat = mMapName.get(name);
   return newPat;
}

public List<String> getPatternAuthors() {
   // TODO: Get a sorted list of all pattern authors in the store
   List<String> lst = new LinkedList<String>(mMapAuths.keySet());
   Collections.sort(lst);
   return lst;

}

public List<String> getPatternNames() {
   // TODO: Get a list of all pattern names in the store,
   // sorted by name
   List<String> lst = new LinkedList<String>(mMapName.keySet());
   Collections.sort(lst);
   return lst;
}

   public PatternStore(String source) throws IOException {
       if (source.startsWith("http://")) {
          loadFromURL(source);
       }
       else {
          loadFromDisk(source);
    }
   }

   public PatternStore(Reader source) throws IOException {
      load(source);
   }

   private void load(Reader r) throws IOException {
      // TODO: read each line from the reader and print it to the screen
      BufferedReader b = new BufferedReader(r);
      // Rest as above
      String line = b.readLine();
      while ( line != null) {
        // Do whatever you need to do with line
        Pattern p = null;
        try{p = new Pattern(line);}
        catch(PatternFormatException e){
          throw new IOException(e.getMessage());
        }
        mPatterns.add(p);
        mMapName.put(p.getName(), p);
        List<Pattern> patlist = mMapAuths.get(p.getAuthor());
        // if list does not exist create it
    if(patlist == null) {
         patlist = new ArrayList<Pattern>();
         patlist.add(p);
         mMapAuths.put(p.getAuthor(), patlist);
    } else {
        // add if item is not already in list
        if(!patlist.contains(p)) patlist.add(p);
    }

        line=b.readLine();

      }
   }


   private void loadFromURL(String url) throws IOException {
    // TODO: Create a Reader for the URL and then call load on it
    URL destination = new URL(url);
URLConnection conn = destination.openConnection();
Reader r = new java.io.InputStreamReader(conn.getInputStream());
load(r);
   }

   private void loadFromDisk(String filename) throws IOException {
    // TODO: Create a Reader for the file and then call load on it
    Reader r = new FileReader(filename);
    load(r);
}



}
