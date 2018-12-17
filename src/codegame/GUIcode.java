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
	private JPanel feedbackPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
//	Components
	private JLabel userScoreAndTimeRemaining;
	private JTextArea feedback;
	private JButton instructions;
	private JButton guess;
	private JButton newGame;
	private JCheckBox timerOnOrOff;
	private Numberpanel [] nopanel;
	
//	Look
	private Font font = new Font("Arial Black", Font.PLAIN, 16 );
	
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
		add(feedbackPanel = new JPanel(), BorderLayout.EAST);
		add(centerPanel = new JPanel(), BorderLayout.CENTER);
		add(bottomPanel = new JPanel(), BorderLayout.SOUTH);
		feedbackPanel.setPreferredSize(new Dimension(500,400));
		
//		Add components
		topPanel.add(userScoreAndTimeRemaining = new JLabel());
		userScoreAndTimeRemaining.setFont(font);
		upDateScore();
		topPanel.add(timerOnOrOff = new JCheckBox("Timer"));
		timerOnOrOff.setFont(font);
		feedbackPanel.add(feedback = new JTextArea(15,28));
		feedback.setFont(font);
		feedback.setLineWrap(true);feedback.setWrapStyleWord(true); //Linebreak & only whole words
		bottomPanel.add(instructions = new JButton("Instructions"));
		bottomPanel.add(guess = new JButton ("Guess"));
		bottomPanel.add(newGame = new JButton("New game"));
		
//		Numberpanels;
		nopanel = new Numberpanel[code.getSafecodeLength()];
		for (int i = 0 ; i < code.getSafecodeLength() ; i++) {
			centerPanel.add(nopanel[i] = new Numberpanel());
		}
	
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
		
		
	}
	
	public void checkGuess () {
		
		noGuesses--; 
		questionTime = standardTime; 	//Resets the timer
		upDateScore();					//Update score

		
//		Sends to codebreaker
		feedback.append(code.betterCheckNumbers(getUserCombination()));
		
//		Checks if it's a win
		if (code.isWin()) {
			guess.setEnabled(false);
			tim.stop();
		}
		
//		Check if the attempts are up.
		isLost();
		
		

		
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
//		Gets called by ActionListener timer. 
		questionTime--;
		if (questionTime < 1)  {
			noGuesses--;
			questionTime = standardTime;
		}
		upDateScore();
		isLost();
	}
	
//	If toggles the timer on and off.
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
	
	public void isLost() {
//		The user has run out of attempts. 
		if (noGuesses <= 0 && !code.isWin()) {
			feedback.append("---The alarm was triggered---");
			System.out.println(code.getCurrentCode());
			guess.setEnabled(false); 	//Activate guess button
			tim.stop();					//Stop the timer
			Siren siren = new Siren();	//Launch "alarm".
			siren.thread.start();

		}
	}
	
	public char [] getUserCombination () {
		
		String userCombination ="";
		
		for (int i = 0 ; i < code.getSafecodeLength() ; i++) {
			userCombination += nopanel[i].getCurrentNo();
			feedback.append(Integer.toString(nopanel[i].getCurrentNo())); //Prints out the combination for feedback to user
		}
		
		feedback.append("   "); //Puts a space behind the figures
		
		
		
		return userCombination.toCharArray();
		
	}
	


}
