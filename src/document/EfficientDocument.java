package document;

import java.util.List;

/** 
 * A class that represents a text document
 * It does one pass through the document to count the number of syllables, words, 
 * and sentences and then stores those values.
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class EfficientDocument extends Document {

	private int numWords;  // The number of words in the document
	private int numSentences;  // The number of sentences in the document
	private int numSyllables;  // The number of syllables in the document
	
	public EfficientDocument(String text)
	{
		super(text);
		processText();
	}
	
	
	private boolean isWord(String tok)
	{
	    // This is a fast way of checking whether a string is a word
		return !(tok.indexOf("!") >=0 || tok.indexOf(".") >=0 || tok.indexOf("?")>=0);
	}
	
	
    /** Passes through the text one time to count the number of words, syllables 
     * and sentences, and set the member variables appropriately.
     * Words, sentences and syllables are defined as described below. 
     */
	private void processText()
	{
		// Call getTokens on the text to preserve separate strings that are 
		// either words or sentence-ending punctuation. Ignore everything
		// That is not a word or a sentence-ending puctuation.
		List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
		
		int length = tokens.size();
		for (String word : tokens){	
			if(isWord(word)) {
				numSyllables += countSyllables(word);
				numWords++;
			}
			else numSentences++;
		}
		if (isWord(tokens.get(length-1))) numSentences++; 
	}

	
	/**
	 * This method does NOT process the whole text each time it is called.  
	 * It returns information already stored in the EfficientDocument object.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences() {
		return this.numSentences;
	}

	
	/**
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords() {
	    return this.numWords;
	}


	/**      
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables() {
        return this.numSyllables;
	}
	
	// Can be used for testing
	public static void main(String[] args)
	{
	    testCase(new EfficientDocument("This is a test.  How many???  "
                + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new EfficientDocument(""), 0, 0, 0);
        testCase(new EfficientDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new EfficientDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2); 
        testCase(new EfficientDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new EfficientDocument("Segue"), 2, 1, 1);
		testCase(new EfficientDocument("Sentence"), 2, 1, 1);
		testCase(new EfficientDocument("Sentences?!"), 3, 1, 1);
		testCase(new EfficientDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
		
	}
	

}
