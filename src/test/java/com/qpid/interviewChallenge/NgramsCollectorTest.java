package com.qpid.interviewChallenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the NgramsCollector class.
 *
 */
public class NgramsCollectorTest {

	private NgramsCollector nramsScraperCollector;

	private static final String EXAMPLE_TEXT_1 = "There are more things in heaven and earth, Horatio, than are dreamt of in your philosophy.";

	/**
	 * Setup method which runs before each test.
	 */
	@Before
	public void setUp() {
		// Reset the example NgramsScraper
		nramsScraperCollector = new NgramsCollector();

		// Load the example NgramsScraper with some test data
		nramsScraperCollector.loadNgramsFromText(EXAMPLE_TEXT_1);
	}

	/**
	 * Confirm that the average length of words after the word "in" was correctly
	 * calculated by the getAvergeLengthOfNextWords() method.
	 * 
	 * The words that follow "in" are: "heaven" (1x) and "your" (1x).
	 * Expected length is ( ["heaven" x 1 = 6] + ["your" x 1 = 4]) / 2 = 5.
	 */
	@Test
	public void getAvergeLengthOfNextWordsTest1() {
		double nextWordLengthAfterIn = nramsScraperCollector.getAvergeLengthOfNextWords("in");
		double nextWordLengthAfterInCalculated = ("heaven".length() + "your".length()) / 2.;
		assertEquals(nextWordLengthAfterInCalculated, nextWordLengthAfterIn, .00001);
	}

	/**
	 * Confirm that the average length of words after the word "and" was correctly
	 * calculated by the getAvergeLengthOfNextWords() method.
	 * 
	 * The words that follow "and" are: "earth" (1x).
	 * Expected length is ["earth" x 1 = 5] / 1 = 5.
	 */
	@Test
	public void getAvergeLengthOfNextWordsTest2() {
		double nextWordLengthAfterAnd = nramsScraperCollector.getAvergeLengthOfNextWords("and");
		double nextWordLengthAfterAndCalculated = "earth".length();
		assertEquals(nextWordLengthAfterAndCalculated, nextWordLengthAfterAnd, .00001);
	}

	/**
	 * TODO: Write at least one more unit test.
	 */
	@Test
	public void unitTest() {
		fail("Not yet implemented");
	}

	/**
	 * TODO: Write at least one system test.
	 */
	@Test
	public void systemTest() {
		fail("Not yet implemented");
	}

}
