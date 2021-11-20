package document;

import java.util.List;
public class EfficientDocument extends Document {

	private int numWords;  
	private int numSentences;  
	private int numSyllables;  
	
	public EfficientDocument(String text){
		super(text);
		processText();
	}
	
	
	private boolean isWord(String tok){
		return !(tok.indexOf("!") >=0 || tok.indexOf(".") >=0 || tok.indexOf("?")>=0);
	}
	
	
	private void processText()
	{
		// Call getTokens on the text to preserve separate strings that are 
		// either words or sentence-ending punctuation.
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
	 */
	public int getNumSentences() {
		return this.numSentences;
	}

	@Override
	public int getNumWords() {
	    return this.numWords;
	}

	@Override
	public int getNumSyllables() {
        return this.numSyllables;
	}
	
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
