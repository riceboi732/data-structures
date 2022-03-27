
/**
 * Creates a new flashcard with the given dueDate, text for the front
 * of the card (front), and text for the back of the card (back).
 * dueDate must be in the format YYYY-MM-DDTHH:MM. For example,
 * 2020-05-04T13:03 represents 1:03PM on May 4, 2020. It's
 * okay if this method crashes if the date format is incorrect.
 * In the format above, the time may or may not include milliseconds. 
 * The parse method in LocalDateTime can deal with this situation
 *  without any changes to your code.
 */
import java.time.*;
import java.util.*;
public Flashcard(String dueDate, String front, String back);
public class Flashcard implements Comparable<Flashcard>{ //must implement to be able to be stored in queue
private LocalDateTime dueDate;
private String frontText;
private String backText;

private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//formats date for falshcards
 public Flashcard(String dueDate, String front, String back) {
//creates flashcards 
        this.dueDate = LocalDateTime.parse(dueDate, formatter);
        this.frontText = front;
        this.backText = back;
    }

public String getFrontText() {
        return this.frontText; //returns the text on the back
    } 

public String getBackText() {
        return this.backText; //returns the text on the back
    }

public LocalDateTime getDueDate(){
        return this.dueDate;
    }
}