package game;

import game.GameItems.*;

/** MemoryNode is node of LinkedList AIMemory.  */
public class MemoryNode {

	MemoryNode next;	//Next node in list.
	double weight;		//Determinant of chosen card in AI card selection.
	Card card;			//Represents a weapon, suspect, or location.

	/** Constructor.  */
	MemoryNode(Card c, int w, MemoryNode mn) {
		next=mn;
		weight = w;
		card = c;
	}

	/** Get Methods.  */
	public MemoryNode getNext() { return next; }
	public double getWeight() 	{ return weight; }
	public Card getCard() 		{ return card; }

	/** Set Methods.  */
	public void setNext(MemoryNode next) { this.next = next; }
	public void setWeight(double weight) { this.weight = weight; }
	public void setCard(Card card) 		 { this.card = card; }

	public String toString(){
		return card+", " +weight+"-->";
	}

}
