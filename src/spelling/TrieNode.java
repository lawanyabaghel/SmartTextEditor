package spelling;

import java.util.HashMap;
import java.util.Set;
class TrieNode {
	private HashMap<Character, TrieNode> children; 
	private String text;  
	private boolean isWord;
	
	public TrieNode(){
		children = new HashMap<Character, TrieNode>();
		text = "";
		isWord = false;
	}
	
	public TrieNode(String text)
	{
		this();
		this.text = text;
	}
	
	
	public TrieNode getChild(Character c)
	{
		return children.get(c);
	}
	
	/* Inserts this character at this node.
	 * Returns the newly created node, if c wasn't already
	 * in the trie.  If it was, it does not modify the trie
	 * and returns null.
	 */
	public TrieNode insert(Character c)
	{
		if (children.containsKey(c)) {
			return null;
		}
		
		TrieNode next = new TrieNode(text + c.toString());
		children.put(c, next);
		return next;
	}
    public String getText()
	{
		return text;
	}
	
	public void setEndsWord(boolean b)
	{
		isWord = b;
	}
	
	public boolean endsWord()
	{
		return isWord;
	}
	
	public Set<Character> getValidNextCharacters()
	{
		return children.keySet();
	}

}

