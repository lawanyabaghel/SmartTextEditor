package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class NearbyWords implements SpellingSuggest {
	private static final int THRESHOLD = 100; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) {
		this.dict = dict;
	}

	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   substitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}
        public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}
	
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		for (int index = 0; index <= s.length(); index++) {
			for (int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuffer sb = new StringBuffer(s);
				sb.insert(index, (char)charCode);
				
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}

	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		for(int index = 0; index<s.length(); index++) {
			StringBuffer sb = new StringBuffer(s);
			sb.deleteCharAt(index);
			
			if(!currentList.contains(sb.toString()) && 
					(!wordsOnly||dict.isWord(sb.toString())) &&
					!s.equals(sb.toString())) {
				currentList.add(sb.toString());
			}
		}
	}

	@Override
	public List<String> suggestions(String word, int numSuggestions) {
		List<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  
		List<String> retList = new LinkedList<String>();   // words to return
		 
		queue.add(word);
		visited.add(word);
		int count=0;
		
		while (!queue.isEmpty() && count<numSuggestions) {
			String curr= queue.remove(0);
			List<String> distOne = distanceOne(curr, false);
			for(String n:  distOne){
				if(!visited.contains(n)){
					visited.add(n);
					queue.add(n);
					if(dict.isWord(n)){
						retList.add(n);
						++count;
					}
				}
				
				if(visited.size() == THRESHOLD && retList.size() > 0){
					break;
				}
				
			}
			
		}
		return retList;
	}	

   public static void main(String[] args) {
	   String word = "i";
	   Dictionary d = new DictionaryHashSet();
	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
	   NearbyWords w = new NearbyWords(d);
	   List<String> l = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");

	   word = "tailo";
	   List<String> suggest = w.suggestions(word, 8);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);
	   
   }

}
