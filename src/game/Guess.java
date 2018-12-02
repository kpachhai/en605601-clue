package game;

import java.awt.event.*;
import java.util.*;
import game.GameItems.*;
import javax.swing.*;

/** Guess Window used for making Assumptions and Accusatons.  */
public class Guess extends JFrame implements ActionListener{

	//Components for JFrame.
	private final JComboBox personBox, weaponBox;
	private final JButton button;

	private Card[] guess;	//Container for inputs chosen by player.

	/** Constructor.  */
	public Guess(String title, ArrayList<Card> hand) {

		//Create Components.
		guess = new Card[2];
		JPanel panel = new JPanel();
		personBox = new JComboBox();
		weaponBox = new JComboBox();
		button = new JButton("Guess");

		//Add components to panel.
		panel.add(personBox);
		panel.add(weaponBox);
		panel.add(button);

		//Add guessable cards to panel combo boxes.
		//NOTE: Does not add cards in hand, to avoid user guessing cards that they hold.
		for (Card card: Card.values()) {
			if (card.getType()==1 && !hand.contains(card))
				weaponBox.addItem(card.getName());
			if (card.getType()==3 && !hand.contains(card))
				personBox.addItem(card.getName());
		}

		//Set the initial return values of guess, in the event user does not select.
		for (Card card: Card.values()) {
			if(weaponBox.getItemAt(0).equals(card.getName()))
				guess[1] = card;
			if(personBox.getItemAt(0).equals(card.getName()))
				guess[0] = card;
		}

		//Create Frame.
		add(panel);
		setTitle(title);
		setSize(280, 100);
		setLocation(200, 300);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(false);
		setResizable(false);

		//Add ActionListeners for combo boxes.
		personBox.addActionListener(this);
		weaponBox.addActionListener(this);
	}

	/** Get Methods for guesses and box button.  */
	public Card getSuspectGuess() { return guess[0]; }
	public Card getWeaponGuess()  { return guess[1]; }
	public JButton getButton()    { return button; }

	/** Action Listener assigns values to guess based off of user input.  */
	public void actionPerformed(ActionEvent e){

		for (Card card: Card.values()) {

			//Suspect Guess Selected.
			if (card.getName().equals(personBox.getSelectedItem()))
				guess[0] = card;

			//Weapon Guess Selected.
			else if (card.getName().equals(weaponBox.getSelectedItem()))
				guess[1] = card;
		}
	}
}
