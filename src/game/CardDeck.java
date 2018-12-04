package game;

import java.util.*;
import game.GameItems.*;

/**
 * Deck class emulates a deck of cards. It fills the game envelope and deals a
 * set number of cards to the 3 players.
 */
public class CardDeck {

    private ArrayList<Card> weapons;    //Container for all weapon cards.
    private ArrayList<Card> rooms;      //Container for all room cards.
    private ArrayList<Card> suspects;   //Container for all suspect cards.

    private Random rand = new Random();

    /**
     * Constructor
     */
    public CardDeck() {

        //Instantiate ArrayLists.
        weapons = new ArrayList<>();
        rooms = new ArrayList<>();
        suspects = new ArrayList<>();

        //add cards to appropriate container.
        for (Card card : Card.values()) {
            switch (card.getType()) {
                case 1:
                    weapons.add(card);
                    break;
                case 2:
                    rooms.add(card);
                    break;
                case 3:
                    suspects.add(card);
                    break;
                default:
                    break;
            }
        }

        //Randomize order in each container.
        Collections.shuffle(weapons);
        Collections.shuffle(rooms);
        Collections.shuffle(suspects);
    }

    /**
     * Get a Card of type Weapon.
     */
    private Card drawWeapon() {

        try {
            Card drawn = weapons.get(0);    //Shallow copy of Card.
            weapons.remove(0);              //Remove Card from container.
            return drawn;                   //Return Card.
        } //Catch if ArrayList is Empty (all cards have been drawn).
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a Card of type Location.
     */
    private Card drawRoom() {

        try {
            Card drawn = rooms.get(0);      //Shallow copy of Card.
            rooms.remove(0);                //Remove Card from container.
            return drawn;                   //Return Card.
        } //Catch if ArrayList is Empty (all cards have been drawn).
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a Card of type Suspect.
     */
    private Card drawSuspects() {

        try {
            Card drawn = suspects.get(0);   //Shallow copy of Card.
            suspects.remove(0);             //Remove Card from container.
            return drawn;                   //Return Card.
        } //Catch is ArrayList is Empty (all cards have been drawn).
        catch (Exception e) {
            return null;
        }
    }

    /**
     * drawCard Method returns a random type of card.
     *
     * @return Drawn card, randomized between all three ArrayLists.
     */
    private Card drawCard() {

        //Set container for return and ne
        Card toReturn = null;

        while (toReturn == null && !allEmpty()) {
            int i = rand.nextInt(3) + 1;

            switch (i) {
                case 1:
                    if (!weapons.isEmpty()) {
                        toReturn = drawWeapon();
                    }
                    break;
                case 2:
                    if (!rooms.isEmpty()) {
                        toReturn = drawRoom();
                    }
                    break;

                case 3:
                    if (!suspects.isEmpty()) {
                        toReturn = drawSuspects();
                    }
                    break;
            }
        }
        return toReturn;
    }

    /**
     * fillEnvelope draws one of each type card from deck and places it in
     * envelope.
     *
     * @return Three cards, one of each type.
     */
    public Card[] fillEnvelope() {
        Card[] envelope = new Card[3];

        envelope[0] = drawWeapon();
        envelope[1] = drawRoom();
        envelope[2] = drawSuspects();

        return envelope;
    }

    /**
     * dealHand deals 6 cards to a player.
     *
     * @return
     */
    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            hand.add(drawCard());
        }

        return hand;
    }

    /**
     * allEmpty checks if all cards have been drawn.
     *
     * @return
     */
    public boolean allEmpty() {
        return (weapons.isEmpty() && rooms.isEmpty() && suspects.isEmpty());
    }
}
