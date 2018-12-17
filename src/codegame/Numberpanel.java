package codegame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Numberpanel extends JPanel {
	
	private JButton up;
	private JButton down;
	private JLabel number;
//	private String filePath = "C:\\Users\\ceder\\Documents\\Yrgo filer\\Programfiler\\CodeGame\\";
	private int currentno;
	
	
	
	
	public Numberpanel () {
		
		setLayout(new BorderLayout());
		
//		Add components
		add(up = new JButton ("UP"), BorderLayout.NORTH);
		add(number = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(makeFileName()))), BorderLayout.CENTER);
		add(down = new JButton ("DOWN"), BorderLayout.SOUTH);
		
//		Starting number
		currentno = 0;
		
//		Add Listeners
		up.addActionListener(e -> pushUp());
		down.addActionListener(e -> pushDown());
		
		
		
	}
	
	public String makeFileName () { 
		return currentno + ".jpg";
		
	}
	
	public void pushDown () {
		
		if (currentno == 0 ) {
			currentno = 9;
		} else {
			currentno--;	
		}
		
		
		getNewImage();
	}
	
	public void pushUp () {
		
		if (currentno == 9 ) {
			currentno = 0;
		} else {
			currentno++;	
		}
		
		
		getNewImage();
	}
	
	
	
	public void getNewImage() {

		Click click = new Click();
		click.thread.start();
		number.setIcon(new ImageIcon(getClass().getClassLoader().getResource(makeFileName())));
		
	}
	
	public int getCurrentNo() {
		return currentno;
	}
	
	

}
