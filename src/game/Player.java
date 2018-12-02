package game;

import java.util.*;
import javax.swing.*;

import game.GameItems.*;

/** Player Class represents a game player. Can be human or AI.  */
public class Player {

	private ArrayList<Card> hand;	//Player's hand of cards.
	private Card playerIcon;		//Card representation of player.
	private Movement movement;		//Player's movements.
	private int playerNum;			//Player's turn #.
	private String name;			//Player's character name.
	private JLabel gamePiece;		//Matching Game Piece.

	/** Constructor.
	 * @param num Turn #.
	 * @param h Hand of Cards.
	 * @param gamePiece Player's token.  */
	public Player(int num, ArrayList<Card> h, GamePiece gamePiece) {

		playerIcon = gamePiece.getCard();
		this.gamePiece = new JLabel();
		this.gamePiece.setIcon(gamePiece.getImage());
		hand = h;
		playerNum = num;
		name = playerIcon.getName();
		movement = new Movement(gamePiece.getRooms(), this.gamePiece, playerNum);
	}

	/** Get Methods.  */
	public String getName() 			{ return name; }
	public ArrayList<Card> getHand() 	{ return hand; }
	public Movement getMovement() 		{ return movement; }
	public int getPlayerNum() 			{ return playerNum;}
	public JLabel getGamePiece() 		{ return gamePiece; }
	public Card getPlayerIcon() 		{ return playerIcon; }

	/** disproved checks Player's hand for cards to disprove an assumption.
	 * @param a Other player's assumption to be disproved.
	 * @return Card that can disprove, or null if can't disprove.  */
	public Card disproved(ArrayList<Card> a) {
		for (int i=0; i<a.size(); i++) {
			if (hand.contains(a.get(i)))
				return a.get(i);
		}
		return null;
	}
}
