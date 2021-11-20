package spelling;

import java.util.List;

public interface WordPath {

	/** Return a path from word1 to word2 through dictionary words with
	 *  the restriction that each step in the path can involve only a
	 *  single character mutation  
	 */
	public List<String> findPath(String word1, String word2);
}
