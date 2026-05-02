package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {
	private List<String> choices;
	private Scanner input;
	
	public InputReader() {
        //Hitta lämpligt ställe att closea scanner!
		input = new Scanner(System.in);
	}
	
	public int getUserChoice(List<String> choices) {
        this.choices = choices;
		//Kan vara farligt att använda true som condition, men det finns exit criteria i form av return statement
		while(true) {
			try {
				int inputValue = input.nextInt();
				input.nextLine();
				if(inputValue <= 0 || inputValue > choices.size()) {
					throw new IllegalArgumentException();
				}
				return inputValue;
			}
			catch (IllegalArgumentException e) {
				System.out.println("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! ILLEGALARGUMENT");
			}
			catch (InputMismatchException e) {
				System.out.println("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! INPUTMISMATCH");
                input.nextLine();
			}
		}
	}

	
}