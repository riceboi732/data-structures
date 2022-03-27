import java.util.Scanner;

public class GuessingGame {
	// Instance variable storing the number the user is guessing
	private int number;

	// Static variable storing the default number to have the user guess.
	// Why have a default number?
	// (a) For debugging - you know exactly what number you should be guessing
	// and (b) For practice understanding the difference between static and
	// non-static.
	private static int defaultNumber = 27;

	/**
	 * Constructs a new GuessingGame. To start game, you must call startNewGame().
	 * Otherwise, the number to guess will always use the same default value to
	 * guess.
	 */
	public GuessingGame() {
    number = defaultNumber;
	}

	/**
	 * Returns the number the user is supposed to guess.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Returns the default number for guessing (if player tries to play without
	 * starting a new game).
	 */
	public static int getDefaultNumber() {
		return defaultNumber;
	}

	/**
	 * Changes the default number for guessing. This number is used if the player
	 * tries to play without calling startNewGame().
	 * 
	 * @param defaultNumber
	 */
	public static void changeDefaultNumber(int defaultNumber) {
		GuessingGame.defaultNumber = defaultNumber;
	}

	/**
	 * Starts a new guessing game by randomly selecting an integer between 1 and 100
	 * for the player to guess.
	 */
	public void startNewGame() {
		number = (int) Math.ceil(Math.random() * 100);
	}

	public void playGame() {
		System.out.println("The computer has a number. Please enter a number between 1-100: ");
		//System.out.println(number); *USed to check if code running correctly . 
		Scanner bleh = new Scanner(System.in);
		int guess = bleh.nextInt();
		while (guess != number) {
			while (guess > 100) {
				System.out.println("Please enter a number between 0-100");
				System.out.println("Please enter a valid number");
				guess = bleh.nextInt();
			}
			while (guess < 0) {
				System.out.println("Please enter a number between 0-100");
				System.out.println("Please enter a valid number");
				guess = bleh.nextInt();
			}
			if (guess > number) {
				System.out.println("Your guess is too large, please guess again");
				System.out.println("Please enter another guess: ");
				guess = bleh.nextInt();
			}
			if (guess < number) {
				System.out.println("Your guess is too small, please guess again");
				System.out.println("Please enter another guess: ");
				guess = bleh.nextInt();
			}
		}
		if (guess == number) {
			System.out.println("Good job! You guessed the number!");
		}
	}

	/**
	 * GuessingGame main constructs a new game and has the player play a single game
	 * in which she must guess the computer's chosen number (from 1-100).
	 * 
	 * @param args the command line arguments aren't used for this class
	 */
	public static void main(String[] args) {
		GuessingGame deh = new GuessingGame();
    deh.startNewGame();
		deh.playGame();
	}
}
