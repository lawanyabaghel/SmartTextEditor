package document;

import java.util.List;

public class BasicDocument extends Document 
{
	public BasicDocument(String text)
	{
		super(text);
	}
	
	@Override
	public int getNumWords()
	{
		List<String> tokens = getTokens("[a-zA-Z]+");
		return tokens.size();
	}
	
	@Override
	public int getNumSentences()
	{ 
		List<String> tokens = getTokens("[^.!?]+");
		return tokens.size();
	}
	
	/**
	 * Get the total number of syllables in the document (the stored text). 
	 * To count the number of syllables in a word, it uses the following rules:
	 *       Each contiguous sequence of one or more vowels is a syllable, 
	 *       with the following exception: a lone "e" at the end of a word 
	 *       is not considered a syllable unless the word has no other syllables. 
	 */
	@Override
	public int getNumSyllables()
	{
		List<String> tokens = getTokens("[aeiouyAEIOUY]+");
		List<String> loneEs = getTokens("[^aeiouyAEIOUY]+[eE]\\b");
		List<String> singleEs = getTokens("\\b[^aeiouyAEIOUY]*[eE]\\b");
		
		
		return tokens.size() - (loneEs.size() - singleEs.size());
	}
	
	public static void main(String[] args)
	{
		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		testCase(new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new BasicDocument("Segue"), 2, 1, 1);
		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
	}
	
}
