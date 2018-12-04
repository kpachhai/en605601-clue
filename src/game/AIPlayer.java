package game;

import game.GameItems.*;
import java.util.*;

/**
 * AI Class is Artifical Intelligence Engine for competitors. Utilizes
 * LinkedList data structures.
 */
public class AIPlayer extends Player {

    private AIPlayerMemory weapon;	//AI Memory of all weapons.
    private AIPlayerMemory person;	//AI Memory of all suspects.
    private AIPlayerMemory room;		//AI Memory of all locations.

    private Random rand = new Random();

    AIPlayer(int num, ArrayList<Card> h, GamePiece p) {

        //Pass to Player.
        super(num, h, p);

        //Instantiate Memories.
        weapon = new AIPlayerMemory();
        person = new AIPlayerMemory();
        room = new AIPlayerMemory();

        //Populate Memories with Card enum.
        for (Card card : Card.values()) {
            switch (card.getType()) {
                case 1:
                    weapon.addToFront(card, 1);
                    break;
                case 2:
                    room.addToFront(card, 1);
                    break;
                default:
                    person.addToFront(card, 1);
                    break;
            }
        }

        //Remove AIPlayer's hand from memory.
        removeStartingCards();
    }

    /**
     * Required Get Methods.
     *
     * @return
     */
    public Card getPersonGuess() {
        return person.getAICardGuess();
    }

    public Card getWeaponGuess() {
        return weapon.getAICardGuess();
    }

    /**
     * setDestination randomly assigns new destination to AIPlayer based on
     * AIPlayer's next location guess.
     */
    public void setDestination() {

        Card temp = room.getAICardGuess();
        Rooms currentRoom = getMovement().getLocation();
        Rooms randomRoom;

        ArrayList<Rooms> canMoveToRooms;

        do {
            randomRoom = Rooms.values()[rand.nextInt(9)];
            canMoveToRooms = new ArrayList<Rooms>();
            if (null != currentRoom) {
                switch (currentRoom) {
                    case STUDY:
                        canMoveToRooms.add(Rooms.HALL);
                        canMoveToRooms.add(Rooms.LIBRARY);
                        canMoveToRooms.add(Rooms.KITCHEN);
                        break;
                    case LIBRARY:
                        canMoveToRooms.add(Rooms.STUDY);
                        canMoveToRooms.add(Rooms.BILLIARD);
                        canMoveToRooms.add(Rooms.CONSERVATORY);
                        break;
                    case CONSERVATORY:
                        canMoveToRooms.add(Rooms.LIBRARY);
                        canMoveToRooms.add(Rooms.BALLROOM);
                        canMoveToRooms.add(Rooms.LOUNGE);
                        break;
                    case BALLROOM:
                        canMoveToRooms.add(Rooms.CONSERVATORY);
                        canMoveToRooms.add(Rooms.BILLIARD);
                        canMoveToRooms.add(Rooms.KITCHEN);
                        break;
                    case KITCHEN:
                        canMoveToRooms.add(Rooms.DININGROOM);
                        canMoveToRooms.add(Rooms.BALLROOM);
                        canMoveToRooms.add(Rooms.STUDY);
                        break;
                    case DININGROOM:
                        canMoveToRooms.add(Rooms.LOUNGE);
                        canMoveToRooms.add(Rooms.BILLIARD);
                        canMoveToRooms.add(Rooms.KITCHEN);
                        break;
                    case LOUNGE:
                        canMoveToRooms.add(Rooms.HALL);
                        canMoveToRooms.add(Rooms.DININGROOM);
                        canMoveToRooms.add(Rooms.CONSERVATORY);
                        break;
                    case HALL:
                        canMoveToRooms.add(Rooms.STUDY);
                        canMoveToRooms.add(Rooms.BILLIARD);
                        canMoveToRooms.add(Rooms.LOUNGE);
                        break;
                    case BILLIARD:
                        canMoveToRooms.add(Rooms.LIBRARY);
                        canMoveToRooms.add(Rooms.HALL);
                        canMoveToRooms.add(Rooms.DININGROOM);
                        canMoveToRooms.add(Rooms.BALLROOM);
                        break;
                    default:
                        break;
                }
            }
        } while (randomRoom == currentRoom || !canMoveToRooms.contains(randomRoom));

        getMovement().setDestination(randomRoom);
    }

    /**
     * removeStartingCards removes AIPlayer's hand from AIPlayer Memory.
     */
    private void removeStartingCards() {
        ArrayList<Card> a = getHand();
        for (int i = 0; i < a.size(); i++) {
            switch (a.get(i).getType()) {
                case 1:
                    weapon.remove(a.get(i));
                    break;
                case 2:
                    room.remove(a.get(i));
                    break;
                case 3:
                    person.remove(a.get(i));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * addWeight adjusts weight of given Cards by 0.8.
     *
     * @param guess Cards in other player's assumption, to be re-weighted.
     */
    public void addWeight(ArrayList<Card> guess) {

        for (int i = 0; i < guess.size(); i++) {

            Card card = guess.get(i);

            //Adjust weight for card, given type.
            if (card.getType() == 1 && weapon.contains(card)) {
                weapon.getNodeOfCard(card).setWeight(weapon.getNodeOfCard(card).getWeight() + 0.8);
            } else if (card.getType() == 2 && room.contains(card)) {
                room.getNodeOfCard(card).setWeight(room.getNodeOfCard(card).getWeight() + 0.8);
            } else if (card.getType() == 3 && person.contains(card)) {
                person.getNodeOfCard(card).setWeight(person.getNodeOfCard(card).getWeight() + 0.8);
            }
        }
    }

    /**
     * findEquivalent takes a room card and returns the equivalent Room eNum.
     *
     * @param card Card to find room equivalent of.
     * @return Rooms eNum equal to given card.
     */
    private Rooms findEquivalent(Card card) {

        for (Rooms rooms : Rooms.values()) {
            if (card.getName().equals(rooms.getName())) {
                return rooms;
            }
        }

        return null;
    }

    /**
     * getNextAction determines AIPlayer next move, based off of current
     * position
     *
     * @param turnStart If it is the beginning of AIPlayer turn.
     * @return Integer representing next action: 1: Top of turn. Move AIPlayer.
     * 2: AIPlayer is ready to make an Accusation. 3: AIPlayer to make
     * Assumption.
     */
    public int getNextAction(boolean turnStart) {
        if (!getMovement().isInARoom() && turnStart) {
            return 1;
        } else if (getMovement().isInARoom() && turnStart) {
            setDestination();
            return 1;
        } else if (shouldMakeAccusation()) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * shouldMakeAccusation() checks if AIPlayer has validity in making an
     * accusation, ending the game.
     *
     * @return True if all Memories contain one item.
     */
    private boolean shouldMakeAccusation() {
        return weapon.size() == 1 && room.size() == 1 && person.size() == 1
                && (findEquivalent(room.getFrontData()) == getMovement().getLocation());
    }

    /**
     * removeCard removes given Card from Memory.
     *
     * @param card Card to be removed.
     */
    public void removeCard(Card card) {
        if (card.getType() == 1 && weapon.contains(card)) {
            weapon.remove(card);
        } else if (card.getType() == 2 && room.contains(card)) {
            room.remove(card);
        } else if (card.getType() == 3 && person.contains(card)) {
            person.remove(card);
        }
    }

    @Override
    public String toString() {
        return getName() + ", " + getPlayerNum() + "\nweapons: " + weapon
                + "\nrooms: " + room + "\nperson: " + person;
    }

    public void makeIrrefutable(ArrayList<Card> guess, int turnNum) {

        if (turnNum == getPlayerNum() && room.contains(getMovement().getEquivalentRoom())) {
            for (Card card : Card.values()) {
                if (!guess.contains(card)) {
                    removeCard(card);
                }
            }
        } else {
            for (Card card : Card.values()) {
                if (!guess.contains(card) && card.getType() != 2) {
                    removeCard(card);
                }
            }
        }

        System.out.println(getName() + ": " + weapon + "\n\t" + room + "\n\t" + person);
    }
}
