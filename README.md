QPID QA DEPARTMENT INTERVIEW EXAM

Time: 45 minutes

Resources: The Internet


This project is intended to gauge the interviewee's technical skills, and their familiarity with writing unit tests and system tests.

The project contains the class NgramsCollector.java, which collects data on the length of second-words in SIZE-2 NGRAMS, which are sentence fragments of two consecutive words.
		For example, "Sam I am" contains two size-2 ngrams: "Sam I" and "I am."

Ngrams can be loaded individually, using loadNgram(firstWord, secondWord), or from a URL using loadNgramsFromTextAtUrl("url").
The method getAvergeLengthOfNextWords("firstWord") returns the average length of the words that followed "firstWord" in an ngram.
		In the above example, getAvergeLengthOfNextWords("I") would return 2, because the only word to follow "I" was "am," which has length 2.



Instructions:

1. Run the application main method in NgramsRunner.java. This shows a simple example of the usage of an NgramsCollector object. You are welcome to change this file to help your understanding of that class.

2. Compile the application and run the JUnit tests with "mvn clean package" on the command line. At first, there should be two successful tests and two failures.

3. Write at least one new Unit test in the file NgramsCollectorTest.java. Each should test one method in NgramsCollector, with multiple examples and edge cases. Please include comments where they would be helpful.

4. Write an end-to-end "system test" which collects a large amount of text from two Wikipedia pages and then makes test assertions. For this quiz, we can assume that Wikipedia is stable.
