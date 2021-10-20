package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size=0;
	}
	
	
	/** Insert a word into the trie.
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie. It should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary.
	 *
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
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
	
	/** 
	 * Return the number of words in the dictionary. 
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
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
     * 
     * The list of completions must contain 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and there aren't enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
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
