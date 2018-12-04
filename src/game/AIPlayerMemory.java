package game;

import java.util.Random;
import java.util.ArrayList;
import game.GameItems.*;

/**
 * AIPlayerMemory utilizes LinkedList structure to contain and modify potential
 * guesses.
 */
public class AIPlayerMemory {

    private MemoryNode front;		//Beginning pointer of LinkedList.
    private int count;				//Length of List.
    private double avgWeight;		//Average Value of all weights in Memory.

    private Random r = new Random();

    /**
     * Constructor.
     */
    public AIPlayerMemory() {
        front = null;
        count = 0;
        avgWeight = 1.0;
    }

    /**
     * Get Size.
     */
    public int size() {
        return count;
    }

    /**
     * getNodeOfCard finds node of given card.
     *
     * @param c Card to find.
     * @return MemoryNode of given Card.
     */
    public MemoryNode getNodeOfCard(Card c) {

        MemoryNode curr = front;
        while (curr != null && curr.getCard() != c) {
            curr = curr.getNext();
        }
        return curr;
    }

    /**
     * removeFront removes first node in LinkedList.
     */
    public void removeFront() {

        if (front == null) {
            System.out.println("Empty list");
        } else {
            front = front.getNext();
            count--;
        }
    }

    /**
     * Removes the node of given Card.
     *
     * @param c Card to be found and removed.
     */
    public void remove(Card c) {

        if (count == 1 || c == front.getCard()) {
            removeFront();
        } else {

            MemoryNode curr = front;

            try {
                while (curr.getNext().getCard() != c) {
                    curr = curr.getNext();
                }
            } catch (NullPointerException e) {
                System.out.println("No Card " + c.getName());
            }

            if (curr.getNext().getNext() != null) {
                curr.setNext(curr.getNext().getNext());
                count--;
            } else {
                curr.setNext(null);
                count--;
            }
        }
    }

    /**
     * setAVgWeight calculates average weight of all nodes, used to determine
     * choice.
     */
    public void setAvgWeight() {
        MemoryNode curr = front;
        double temp = 0.0;
        while (curr != null) {
            temp += curr.getWeight();
            curr = curr.getNext();
        }
        avgWeight = temp / count;
    }

    /**
     * getAICardGuess determines card choice by utilizing average weight and
     * randomness.
     *
     * @return Determined card.
     */
    public Card getAICardGuess() {

        MemoryNode curr = front;

        //Calculate Average Weight.
        setAvgWeight();

        //Return null if empty.
        switch (count) {
            case 0:
                return null;

            //Return card if only one card.
            case 1:
                return curr.getCard();

            //Determine card choice.
            default:
                //checks if their are any weights above the average
                if (noWeightsAboveAvg()) {
                    return getRandomNode().getCard();
                } else {
                    //Create arraylist to store potential choices.
                    ArrayList<Card> a = new ArrayList<>();

                    //Add all cards weighted above average.
                    while (curr != null) {
                        if (curr.getWeight() > avgWeight) {
                            a.add(curr.getCard());
                        }
                        curr = curr.getNext();
                    }
                    //Return a random card from selection.
                    return a.get(r.nextInt(a.size()));
                }
        }
    }

    //checks if their are any weights above the average
    public boolean noWeightsAboveAvg() {
        MemoryNode curr = front;
        while (curr != null) {
            if (curr.getWeight() > avgWeight) {
                return false;
            }
            curr = curr.getNext();
        }
        return true;
    }

    /**
     * getRandomNode returns a random node from Memory.
     *
     * @return Randomly chosen MemoryNode.
     */
    public MemoryNode getRandomNode() {
        MemoryNode curr = front;
        int x = r.nextInt(count);
        while (x != 0) {
            curr = curr.getNext();
            x--;
        }
        return curr;
    }

    /**
     * addToFront creates new node and adds it to front of List.
     *
     * @param c Card in node.
     * @param w Initial weight.
     */
    public void addToFront(Card c, int w) {
        front = new MemoryNode(c, w, front);
        count++;
    }

    /**
     * contains checks if given Card exists within memory.
     *
     * @param card
     * @return
     */
    public boolean contains(Card card) {
        MemoryNode curr = front;
        while (curr != null) {
            if (curr.getCard() == card) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    public String toString() {
        MemoryNode curr = front;
        String temp = "";
        while (curr != null) {
            temp += curr;
            curr = curr.getNext();
        }
        return temp;
    }

    public Card getFrontData() {
        return front.getCard();
    }
}
