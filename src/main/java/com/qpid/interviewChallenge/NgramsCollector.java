package com.qpid.interviewChallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

/**
 * Collects data on the length of second words in SIZE-2 NGRAMS, which are
 * sentence fragments of two consecutive words.
 * 
 * For example, "Sam I am" contains two size-2 ngrams: "Sam I" and "I am."
 * 
 * Ngrams can be loaded by an NgramsCollector individually, using
 * loadNgram(firstWord, secondWord), or from a URL using
 * loadNgramsFromTextAtUrl(url). The method
 * getAvergeLengthOfNextWords(firstWord) returns the average length of all the
 * words that followed "firstWord" in an ngram.
 * 
 * In the above example, getAvergeLengthOfNextWords("I") would return 2, because
 * the only word after "I" was "am," which has length 2.
 * 
 * @author kward1
 *
 */
public class NgramsCollector {

	private static final int MAX_LINES_READ_FROM_URL = 1000;

	private List<String> urlTextSources = new ArrayList<String>();

	Map<String, int[]> nextWordsCounter;
	private static final int INDEX_FIRST_WORD_COUNT = 0;
	private static final int INDEX_NEXT_WORD_LENGTHS_SUM = 1;

	public NgramsCollector() {
		nextWordsCounter = new HashMap<String, int[]>();
	}

	/**
	 * Cleans text up in preparation for parsing the text into ngrams. Removes html
	 * tags and clutter, converts the text to lowercase, replaces non-letter and
	 * non-dash characters with spaces, and consolidates whitespace.
	 * 
	 * PLEASE DO NOT WRITE UNIT TESTS FOR THIS STATIC METHOD; IT IS TOO BORING.
	 * 
	 * @param originalText
	 * @return
	 */
	public static String cleanUrlText(String originalText) {
		// Remove html clutter
		String text = Jsoup.parse(originalText).text();

		// Changes all characters to lowercase
		text = text.toLowerCase();

		// Replace all characters that are not letters, dashes, or spaces with a space
		text = text.replaceAll("[^a-z- ]", " ");

		// Remove singleton 's' and '-', particularly after removing digits from '1960s'
		text = text.replaceAll("(\\s[s]\\s)|(\\s[s]$)", " ").replaceAll("(\\s[-]\\s)|(\\s[-]$)", " ");

		// Replace all whitespace with a single space
		text = text.replaceAll("\\s+", " ");

		// Removes any leading or trailing whitespace
		text = text.trim();

		return text;
	}

	/**
	 * Converts a word to lowercase. Throws an exception if the word contains bad
	 * characters that would be removed by the cleanUrlText() method.
	 * 
	 * PLEASE DO NOT WRITE UNIT TESTS FOR THIS STATIC METHOD; IT IS TOO BORING.
	 * 
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static String formatWord(String word) throws Exception {
		String lowercaseWord = word.toLowerCase();

		if (!lowercaseWord.equals(cleanUrlText(lowercaseWord))) {
			throw new Exception("ERROR: \"" + word + "\" is not a valid word format, or contains invalid characters.");
		}

		return lowercaseWord;
	}

	/**
	 * Returns true if a connection can be established to testUrl.
	 * 
	 * @param testUrl
	 * @return
	 */
	public boolean confirmUrlIsValid(String testUrl) {
		try {
			URL url = new URL(testUrl);
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (MalformedURLException e) {
			// the URL is not in a valid form
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// the connection couldn't be established
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Reads text from a source URL, 10 lines at a time. Cleans the text chunks and
	 * then feeds it to the ngram parsing method.
	 * 
	 * @param urlTextSource
	 */
	public void loadNgramsFromTextAtUrl(String urlTextSource) {
		if (!confirmUrlIsValid(urlTextSource)) {
			System.out.println("Ingoring invalid URL " + urlTextSource + ".");
			return;
		}

		urlTextSources.add(urlTextSource);

		BufferedReader br = null;
		try {
			URL url = new URL(urlTextSource);
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			int countLines = 0;

			List<String> collectTenLines = new ArrayList<String>();
			while ((line = br.readLine()) != null && countLines < MAX_LINES_READ_FROM_URL) {
				if (line.length() > 0) {
					collectTenLines.add(line);
					countLines++;

					if (collectTenLines.size() >= 10) {
						String tenLineChunk = String.join(" ", collectTenLines);
						if (tenLineChunk.length() > 0) {
							loadNgramsFromText(tenLineChunk);
						}

						collectTenLines.clear();
					}
				}
			}

		} catch (MalformedURLException e) {
			System.out.println("Malformed URL Error: " + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("I/O Error Error: " + e.getStackTrace());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cleans text, splits it into words, and records each consecutive pair of words
	 * as a size-2 ngram.
	 * 
	 * @param cleanText
	 */
	public void loadNgramsFromText(String text) {
		String cleanText = cleanUrlText(text);

		if (!cleanText.contains(" ")) {
			return;
		}

		String[] words = cleanText.split("\\s+");

		for (int w = 0; w < words.length - 1; w++) {
			String firstWord = words[w];
			String secondWord = words[w + 1];

			loadNgram(firstWord, secondWord);
		}
	}

	/**
	 * Adds a size-2 ngram to the metadata record. Increments the count for the
	 * firstWord, and adds the length of the secondWord to the running sum of
	 * next-word lengths.
	 * 
	 * @param firstWord
	 * @param secondWord
	 */
	public void loadNgram(String firstWord, String secondWord) {
		if (!nextWordsCounter.containsKey(firstWord)) {
			nextWordsCounter.put(firstWord, new int[2]);
		}

		nextWordsCounter.get(firstWord)[INDEX_FIRST_WORD_COUNT]++;
		nextWordsCounter.get(firstWord)[INDEX_NEXT_WORD_LENGTHS_SUM] += secondWord.length();
	}

	/**
	 * Returns the count of ngrams that have been recorded.
	 * 
	 * @return
	 */
	public int getTotalNgramsCount() {
		int totalWords = 0;

		for (int[] secondWordStats : nextWordsCounter.values()) {
			totalWords += secondWordStats[INDEX_FIRST_WORD_COUNT];
		}

		return totalWords;
	}

	/**
	 * Returns the average length of words that follow the parameter "firstWord" in
	 * all of the ngrams that have been collected.
	 * 
	 * @param word
	 * @return
	 */
	public double getAvergeLengthOfNextWords(String firstWord) {
		String formattedWord;
		try {
			formattedWord = formatWord(firstWord);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		if (!nextWordsCounter.containsKey(formattedWord)) {
			return 0;
		}

		return nextWordsCounter.get(formattedWord)[INDEX_NEXT_WORD_LENGTHS_SUM]
				/ (double) nextWordsCounter.get(formattedWord)[INDEX_FIRST_WORD_COUNT];
	}

	/**
	 * Returns the average length of second-words over all recorded ngrams.
	 * 
	 * @return
	 */
	public double getAverageNextWordLength() {
		int wordCount = nextWordsCounter.values().stream().mapToInt(wordStats -> wordStats[INDEX_FIRST_WORD_COUNT])
				.sum();
		int wordLengthsSum = nextWordsCounter.values().stream()
				.mapToInt(wordStats -> wordStats[INDEX_NEXT_WORD_LENGTHS_SUM]).sum();

		if (wordCount == 0) {
			return 0;
		}

		return wordLengthsSum / (double) wordCount;
	}

	/**
	 * Returns the first-word with the longest average next-words.
	 * 
	 * @return
	 */
	public String getWordWithLongestAverageNextWord() {
		String bestWord = null;

		for (String checkWord : nextWordsCounter.keySet()) {
			if (bestWord == null || getAvergeLengthOfNextWords(bestWord) < getAvergeLengthOfNextWords(checkWord)) {
				bestWord = checkWord;
			}
		}

		return bestWord;
	}

	public List<String> getUrlTextSources() {
		return urlTextSources;
	}

}
