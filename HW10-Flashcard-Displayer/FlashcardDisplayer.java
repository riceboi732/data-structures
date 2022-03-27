
/**
 * Creates a flashcard displayer with the flashcards in file.
 * File has one flashcard per line. On each line, the date the flashcard 
 * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a comma, 
 * followed by the text for the front of the flashcard, followed by another comma,
 * followed by the text for the back of the flashcard. You can assume that the 
 * front/back text does not itself contain commas. (I.e., a properly formatted file
 * has exactly 2 commas per line.)
 * In the format above, the time may be omitted, or the time may be more precise
 * (e.g., seconds may be included). The parse method in LocalDateTime can deal
 * with these situations without any changes to your code.
 */
public FlashcardDisplayer(String file);
 
/**
 * Writes out all flashcards to a file so that they can be loaded
 * by the FlashcardDisplayer(String file) constructor. Returns true
 * if the file could be written. The FlashcardDisplayer should still
 * have all of the same flashcards after this method is called as it
 * did before the method was called. However, it may be that flashcards
 * with the same exact next display date and time are removed in a different order.
 */
public boolean saveFlashcards(String outFile);
 
/**
 * Displays any flashcards that are currently due to the user, and 
 * asks them to report whether they got each card correct. If the
 * card was correct, it is added back to the deck of cards with a new
 * due date that is one day later than the current date and time; if
 * the card was incorrect, it is added back to the card with a new due
 * date that is one minute later than that the current date and time.
 */
public void displayFlashcards();