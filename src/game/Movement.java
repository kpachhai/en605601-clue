package game;

import game.GameItems.*;

import java.awt.*;
import javax.swing.*;

/**
 * Movement Class contains methods for translating player pieces around the
 * board.
 */
public class Movement {

    private Rooms destination;		//Current destination of piece.
    private Rooms location;			//Current location of piece.
    private int stepsToLocation;	//Distance to destination.
    private int playerNum;			//Used for left-right translation to avoid piece overlap.
    private JLabel gamePiece;		//Player's game token.

    /**
     * Constructor.
     *
     * @param start Initial location.
     * @param gamePiece Player's token.
     * @param playerNum Player's turn #.
     */
    public Movement(Rooms start, JLabel gamePiece, int playerNum) {

        this.gamePiece = gamePiece;
        destination = null;
        location = start;
        stepsToLocation = 0;
        this.playerNum = playerNum;
    }

    /**
     * Get Methods.
     *
     * @return
     */
    public Rooms getDestination() {
        return destination;
    }

    public Rooms getLocation() {
        return location;
    }

    public int getXPos() {
        return location.getXPos() + 12 * playerNum;
    }

    public int getYPos() {
        return location.getYPos();
    }

    /**
     * Set Methods.
     *
     * @param destination
     */
    public void setDestination(Rooms destination) {
        this.destination = destination;
    }

    public void setLocation(Rooms location) {
        this.location = location;
    }

    /**
     * setDistance determines the distance between location and destination and
     * assigns values 4 or 1 for secret passageways.
     */
    public void setDistance() {
        if (secretPassage()) {
            stepsToLocation = 1;
        } else {
            stepsToLocation = 4;
        }
    }

    /**
     * gamePieceMove relocates gamePiece based on # of steps passed into method.
     *
     * @param movement # of steps for gamePiece to move.
     */
    public void gamePieceMove(int movement) {

        //If piece starts in a room, find distance to destination.
        if (isInARoom()) {
            setDistance();
        }

        //If piece makes it to destination, move game piece to destination
        //Toggle hasMoved so turn can continue.
        if (movement >= stepsToLocation) {

            gamePiece.setLocation((destination.getPosition().x + 12 * playerNum), destination.getPosition().y);
            location = destination;
            destination = null;
            stepsToLocation = 0;
        } //If piece doesn't make it to destination.
        else {
            //If piece started turn in a room, relocate to a passageway.
            if (isInARoom()) {
                Point hallPoint = nearestHall().getPosition();
                gamePiece.setLocation((hallPoint.x + 12 * playerNum), hallPoint.y);
            }

            //Readjust location and # of steps.
            location = null;
            stepsToLocation -= movement;
        }
    }

    /**
     * secretPassage determines if location is a room containing a secret
     * passage.
     *
     * @return
     */
    public boolean secretPassage() {
        return (location == Rooms.STUDY && destination == Rooms.KITCHEN)
                || (location == Rooms.KITCHEN && destination == Rooms.STUDY)
                || (location == Rooms.CONSERVATORY && destination == Rooms.LOUNGE)
                || (location == Rooms.LOUNGE && destination == Rooms.CONSERVATORY);
    }

    /**
     * nearestHall finds nearest passageway location by taking the closest point
     * between the sum of the distance to location and distance to destination.
     *
     * @return
     */
    public Passages nearestHall() {

        //Set first passageway as closest point.
        Passages nearest = Passages.PASSAGE_STUDY_LIBRARY;

        //Calculate distance between the three points.
        double nearestValue = location.getPosition().distance(nearest.getPosition())
                + destination.getPosition().distance(nearest.getPosition());

        //Check all passageways for closest.
        for (Passages pass : Passages.values()) {
            double nextValue = location.getPosition().distance(pass.getPosition())
                    + destination.getPosition().distance(pass.getPosition());

            if (nextValue < nearestValue) {
                nearest = pass;
                nearestValue = nextValue;
            }
        }
        return nearest;
    }

    /**
     * Check if gamePiece is located in a room.
     *
     * @return
     */
    public boolean isInARoom() {
        return location != null;
    }

    /**
     * getEquivalentRoom finds matching Card value to given Rooms eNum.
     *
     * @return
     */
    public Card getEquivalentRoom() {
        for (Card card : Card.values()) {
            if (card.getName().equals(location.getName())) {
                return card;
            }
        }

        return null;
    }

}
