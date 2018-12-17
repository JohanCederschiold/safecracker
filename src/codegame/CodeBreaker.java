package codegame;

import java.util.Random;

public class CodeBreaker {
	
//	Intance variables
	private char [] numbers;
	private final int safecodeLenght = 3;
	private boolean win = false;
	
	
//	Constructor
	public CodeBreaker () {
		
	}

	
	public void createNewCode ()  {
		
//		Resets the win boolean to false to start new round.
		win = false;
		
		Random random = new Random();
		String numberAsString = "";
		
//		Concatenates the no to the string. 
		for (int i = 0 ; i < safecodeLenght ; i++ ) {
			numberAsString += random.nextInt(10);
		}
		
//		Transforms the string to chararray.
		numbers = numberAsString.toCharArray();
		
	}


//	Return the quantity of numbers that are in the correct place.
	public int checkNoInPlace (char [] nos) {
			
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
	public boolean checkNoInWrongPlace(char [] nos) {
		
		for (int i = 0; i < nos.length ; i++ ) {
			for (int j = 0; j < nos.length ; j++ ) {
				if (i != j && nos[i] == numbers[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
//	This method uses checknoinplace() and checknoinwrongplace() to return a result as string. 
	public String betterCheckNumbers (char [] nos) {
		
		String answer ="";
		
	
		int noCorrect = checkNoInPlace(nos);
		
//		Check if its a win
		if (noCorrect == numbers.length) {
			win = true;
			return "Safedoor opens";
//		Check if any numbers are in the correct place. 
		} else if (noCorrect > 0 ) {
			answer += String.format("Correct: %d. ", noCorrect);
		}
		
//		Check if the user has entered one of the correct numbers in the wrong place. 
		if (checkNoInWrongPlace(nos)) {
			answer += "Some numbers in wrong place";
		}
		
//		Concatenate new line to the answer. 
		answer += "\n";
		
		return answer;

	}
	
	public boolean isWin () {
		return win;
	}
	
//	Return the char array code
	public char [] getCurrentCode () {
		return numbers;
	}
	
	public int getSafecodeLength () {
		return safecodeLenght;
	}
	
 	


}
