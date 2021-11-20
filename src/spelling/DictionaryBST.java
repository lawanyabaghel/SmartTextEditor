package spelling;

import java.util.TreeSet;

public class DictionaryBST implements Dictionary 
{
   private TreeSet<String> dict; 
   public DictionaryBST() {
	   dict = new TreeSet<String>(); 
   }
	
    
    public boolean addWord(String word) {
    	// TODO: Implement this method
    	if (!dict.contains(word.toLowerCase())) {
    		 dict.add(word.toLowerCase());
    		 return true;
    	}
        return false;
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        return dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
    	return (!s.isEmpty()) && dict.contains(s.toLowerCase());
    }

}
