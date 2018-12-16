package codegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GUIcode extends JFrame {
	

	
//	Panels
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
//	Components
	private JLabel userScoreAndTimeRemaining;
	private JTextArea feedback;
	private JTextField myGuess;
	private JButton instructions;
	private JButton guess;
	private JButton newGame;
	private JCheckBox timerOnOrOff;
	
	
//	Look
	private Font font = new Font("Arial Black", Font.PLAIN, 18 );
	
//	Game
	private CodeBreaker code;
	private int noGuesses;
	private Timer tim;
	private int standardTime = 60;
	private int questionTime;

	
	
	public GUIcode () {
		
		setLayout(new BorderLayout());
		code = new CodeBreaker();
		

		
//		Add panels
		add(topPanel = new JPanel(), BorderLayout.NORTH);
		add(centerPanel = new JPanel(), BorderLayout.CENTER);
		add(bottomPanel = new JPanel(), BorderLayout.SOUTH);
		centerPanel.setPreferredSize(new Dimension(1200 ,400));
		
//		Add components
		topPanel.add(userScoreAndTimeRemaining = new JLabel());
		userScoreAndTimeRemaining.setFont(font);
		upDateScore();
		topPanel.add(timerOnOrOff = new JCheckBox("Timer"));
		timerOnOrOff.setFont(font);
		centerPanel.add(feedback = new JTextArea(30,60));
		feedback.setFont(font);
		feedback.setLineWrap(true);feedback.setWrapStyleWord(true); //Linebreak & only whole words
		bottomPanel.add(instructions = new JButton("Instructions"));
		bottomPanel.add(myGuess = new JTextField(5));
		myGuess.setFont(font);
		bottomPanel.add(guess = new JButton ("Guess"));
		bottomPanel.add(newGame = new JButton("New game"));
		
//		Set up new game.
		tim = new Timer(1000, e -> countdown());
		newGame();
		
//		Add listeners 
		guess.addActionListener(e -> checkGuess());
		newGame.addActionListener(e -> newGame());
		timerOnOrOff.addActionListener(e -> toggleTimer());
		instructions.addActionListener(e -> readFile());
		
		

		
//		Finalize
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//		Create soundfile

		
		
	}
	
	public void checkGuess () {
		
		noGuesses--; //Increments the number of guesses regardless of correct syntax or not. 
		questionTime = standardTime;
		upDateScore();
		
//		Get user guess and convert it to char array
		char [] userGuess = myGuess.getText().toCharArray();
		
//		Check if the user actually put something in the textfield. 
		if (userGuess.length < 1) {
			feedback.append("You didn't make a guess!\n");
			return;
		}
		
//		check that its only digits in the users guess
		for (char character : userGuess) {
			if (!Character.isDigit(character)) {
				feedback.append("Sorry - that is not a valid guess!\n");
				return;
			}
		}
		
//		Sends to codebreaker
		feedback.append(myGuess.getText() + " -- " + code.betterCheckNumbers(userGuess));
		
//		Checks if it's a win
		if (code.isWin()) {
			guess.setEnabled(false);
			tim.stop();
		}
		
//		Check if the attempts are up.
		lostGame();
		
		

		
	}
	
	public void newGame () {
		
		/*	Resets the score and the timer (and updates the jlabel).
		 * 	Clears the textarea and generates a new code. 
		 * 	Enables guess button and starts the timer.
		 */ 
		
		noGuesses = 10;
		questionTime = standardTime;
		upDateScore();
		feedback.setText(""); 	//clears the textarea
		code.createNewCode();	//Requests a new code
		guess.setEnabled(true); //Enables the guess button
		if (timerOnOrOff.isSelected()) {
			tim.start();
		}
				
		
	}
	
	public void upDateScore() {
		userScoreAndTimeRemaining.setText(String.format("Remaining attempts: %d     "
				+ "You have %02d seconds to penalty", noGuesses, questionTime));

	}
	
	public void countdown() {
		questionTime--;
		if (questionTime < 1)  {
			noGuesses--;
			questionTime = standardTime;
		}
		upDateScore();
		lostGame();
	}
	
	public void toggleTimer () {
		
		if (timerOnOrOff.isSelected()) {
			tim.start();
			countdown();
		} else {
			tim.stop();
		}
		
	}
	
	public void readFile ()  {
		
		feedback.setText("");
		
		try {
			Scanner filescanner = new Scanner(new File("instructions.txt"), "UTF-8");
			
			while (filescanner.hasNextLine()) {
				feedback.append(filescanner.nextLine() + "\n");
				
			}
			filescanner.close();
			
		} catch (FileNotFoundException e) {
			feedback.append("Can't find file");
		} 

	}
	
	public void lostGame() {
//		The user has run out of attempts. 
		if (noGuesses <= 0 && !code.isWin()) {
			feedback.append("---The alarm was triggered---");
			guess.setEnabled(false);
			tim.stop();
			Siren siren = new Siren();
			siren.thread.start();

		}
	}
	


}
