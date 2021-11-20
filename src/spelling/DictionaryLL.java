package spelling;

import java.util.LinkedList;


public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	
	public DictionaryLL() {
		this.dict = new LinkedList<String>();
	}


    // Add this word to the dictionary.   
    public boolean addWord(String word) {
 
    	if (!dict.contains(word.toLowerCase())) {
    		dict.add(word.toLowerCase());
            return true;
    	}
        return false;
    }

    public int size(){
        return dict.size();
    }

    public boolean isWord(String s) {
    	if(dict.contains(s.toLowerCase()) && (!s.isEmpty())) {
    		return true;
    	}
        return false;
    }

    
}
