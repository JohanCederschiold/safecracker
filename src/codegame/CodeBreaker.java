package codegame;

import java.util.Random;

public class CodeBreaker {
	
	
	private int currentCode;
	private char [] numbers;
	
	
//	Constructor
	public CodeBreaker () {
		
	}
	
//	Creates a new code and "translates" to a chararray.
	public void createNewCode () {
		
		genenerateCode();
		numbers = codeToString(currentCode);
		
	}
	
	
//	Generates a new five digit code for instance variable
	private void genenerateCode() {
		
		Random random = new Random ();
		currentCode = random.nextInt(899)+100;

	}
	
	
//	Converts parameter to charArray.
	private char[] codeToString(int convertThis) {
		
		String currentCodeString = Integer.toString(convertThis);
		return currentCodeString.toCharArray();
		
	}

	
//	Return the int code
	public int getCurrentCode () {
		return currentCode;
	}
	
//	Returns a chararray with the numbers. 
	public char [] getCurrentCodeString () {
		return numbers;
	}
	
	
//	Return the quantity of numbers that are in the correct place.
	public int checkNoInPlace (int number) {
			
		char [] nos = codeToString(number); //Convert number
		if (nos.length != numbers.length) {
			return -1; //The userguess is not in the same span as the number. 
		}
		int correctNos = 0;
		
		for (int i = 0; i < nos.length ; i++ ) {
			if (nos[i] == numbers[i]) {
				correctNos++;
			}
		}
		
		return correctNos;

	}
	
//	Checks if there are numbers that are correct but in the wrong place. 
	public boolean checkNoInWrongPlace(int number) {
		
		char [] nos = codeToString(number);
		
		for (int i = 0; i < nos.length ; i++ ) {
			for (int j = 0; j < nos.length ; j++ ) {
				if (i != j && nos[i] == numbers[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public String betterCheckNumbers (int number) {
		
		String answer ="";
		
	
		int noCorrect = checkNoInPlace(number);
		
//		Check if its a win
		if (noCorrect == numbers.length) {
			return "You are right\n";
//		Check if any numbers are in the correct place. 
		} else if (noCorrect > 0 ) {
			answer += String.format("You have %d numbers correct. ", noCorrect);
//		Check that the number is not too long or too short
		} else if (noCorrect == -1) {
			return "The number is not the same number of numbers\n";
//		Else no numbers are correct. 
		} else {
			answer += "No numbers are in the correct place. ";

		}
		
//		Check if the user has entered one of the correct numbers in the wrong place. 
		if (checkNoInWrongPlace(number)) {
			answer += "You have correct numbers in the wrong places.";
		}
		
//		Concatenate new line to the answer. 
		answer += "\n";
		
		return answer;

	}

}
