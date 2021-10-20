package spelling;

import java.util.LinkedList;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	
	public DictionaryLL() {
		this.dict = new LinkedList<String>();
	}


    /** Add this word to the dictionary.   
     */
    public boolean addWord(String word) {
 
    	if (!dict.contains(word.toLowerCase())) {
    		dict.add(word.toLowerCase());
            return true;
    	}
        return false;
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        // TODO: Implement this method
        return dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
    	if(dict.contains(s.toLowerCase()) && (!s.isEmpty())) {
    		return true;
    	}
        return false;
    }

    
}
