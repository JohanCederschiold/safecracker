package codegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIcode extends JFrame {
	

	
//	Panels
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
//	Components
	private JTextArea feedback;
	private JTextField myGuess;
	private JButton guess;
	private JButton newGame;
	
	
//	Look
	private Font font = new Font("Arial Black", Font.PLAIN, 18 );
	
//	Game
	CodeBreaker code;
	
	
	
	
	public GUIcode () {
		
		setLayout(new BorderLayout());
		code = new CodeBreaker();
		code.createNewCode();
		
//		Add panels
		add(centerPanel = new JPanel(), BorderLayout.CENTER);
		add(bottomPanel = new JPanel(), BorderLayout.SOUTH);
		centerPanel.setPreferredSize(new Dimension(1200 ,400));
		
//		Add components
		centerPanel.add(feedback = new JTextArea(30,60));
		feedback.setFont(font);
		bottomPanel.add(myGuess = new JTextField(5));
		myGuess.setFont(font);
		bottomPanel.add(guess = new JButton ("Guess"));
		bottomPanel.add(newGame = new JButton("New game"));
		
//		Add listeners 
		guess.addActionListener(e -> checkGuess());
		newGame.addActionListener(e -> newGame());

		
//		Finalize
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
	}
	
	public void checkGuess () {
		
		int userGuess = 0;
		
		try {
			userGuess = Integer.parseInt(myGuess.getText());
			String feedbackOnGuess = myGuess.getText() + " " + code.betterCheckNumbers(userGuess);
			feedback.append(feedbackOnGuess);
			if (feedbackOnGuess.trim().equals("You are right")) {
				guess.setEnabled(false);
			}
			
		} catch (NumberFormatException nfe) {
			feedback.append("You entered an incorrect format\n");
		}
		
	}
	
	public void newGame () {
		
		feedback.setText("");
		code.createNewCode();
		guess.setEnabled(true);
		
		
	}
	


}
