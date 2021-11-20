package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size=0;
	}
	
	
	/*
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie. 
	 */
	public boolean addWord(String word)
	{
		TrieNode node=root;
	    for (Character c : word.toLowerCase().toCharArray()) {
	    	if (node.getValidNextCharacters().contains(c)) {
				node = node.getChild(c);
			} else {
				node = node.insert(c);
			}
	    }
	    if (!node.endsWord()) {
			node.setEndsWord(true);
			size++;
			return true;
		}
	    return false;
	}
	
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, */
	@Override
	public boolean isWord(String s) 
	{
		TrieNode node=root;
		for (Character ch:s.toLowerCase().toCharArray()) {
			TrieNode child = node.getChild(ch);
			if (child!=null) node=child;
			else return false;
		}
		
		return node.endsWord();
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. 
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // Find the stem in the trie.  
    	 // Once the stem is found, perform a breadth first search to generate completions
    	 TrieNode node = root;

  		for (Character c : prefix.toLowerCase().toCharArray()) {
  			TrieNode child = node.getChild(c);
  			if (child != null) {
  				node = child;
  			} else {
  				return Collections.<String> emptyList();
  			}
  		}

  		List<String> completions = new LinkedList<>();
  		Queue<TrieNode> queue = new LinkedList<>();
  		queue.offer(node);

  		while (!queue.isEmpty() && numCompletions > 0) {
  			TrieNode t = queue.poll();

  			if (t.endsWord()) {
  				completions.add(t.getText());
  				--numCompletions;
  			}

  			for (Character c : t.getValidNextCharacters()) {
  				queue.offer(t.getChild(c));
  			}

  		}

  		return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}
