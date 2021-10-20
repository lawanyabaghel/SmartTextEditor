package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary 
{
   private TreeSet<String> dict;
	
    // TODO: Implement the dictionary interface using a TreeSet.  
   public DictionaryBST() {
	   dict = new TreeSet<String>(); 
   }
	
    
    /** Add this word to the dictionary.  
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
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
    	//TODO: Implement this method
    	return (!s.isEmpty()) && dict.contains(s.toLowerCase());
    }

}
