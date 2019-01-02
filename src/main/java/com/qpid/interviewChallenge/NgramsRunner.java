package com.qpid.interviewChallenge;

import java.text.DecimalFormat;

public class NgramsRunner {

	private static final DecimalFormat df1 = new DecimalFormat("0.#");

	private static final String URL_TEXT_SOURCE = "https://en.wikipedia.org/wiki/X-Men";

	public static void main(String[] args) {

		System.out.println("\n\nPART 1: Generate NgramsCollector with text parsed from the URL [" + URL_TEXT_SOURCE + "].");
		NgramsCollector ngramsCollector = new NgramsCollector();
		ngramsCollector.loadNgramsFromTextAtUrl(URL_TEXT_SOURCE);

		System.out.println("\n\nPART 2: Get the average next-word length.");
		double averageWordLength = ngramsCollector.getAverageNextWordLength();

		System.out.println("\n\nPART 3: Find the word with the longest average next word.");
		String longestFirstWord = ngramsCollector.getWordWithLongestAverageNextWord();
		double longestAveNextWordLength = ngramsCollector.getAvergeLengthOfNextWords(longestFirstWord);

		System.out.println("\n\nPART 4: Print the results.");
		System.out.println("Analysis of size-2 ngrams of the text at the URL [" + URL_TEXT_SOURCE + "]:");
		System.out.println("  - The average word length in the sample text is " + df1.format(averageWordLength) + ".");
		System.out.println("  - The longest average next-word length, after \"" + longestFirstWord + "\", is "
				+ df1.format(longestAveNextWordLength));

		System.out.println("\n\nHere are results for some more words.");
		for (String checkWord : new String[] { "the", "a", "had", "professor" }) {
			double averageNextWordLength = ngramsCollector.getAvergeLengthOfNextWords(checkWord);
			System.out.println("  - Length of words after \"" + checkWord + "\": " + df1.format(averageNextWordLength));
		}
	}

}
