package game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;

/**
 *
 * @author kiran
 */
public final class GameItems {

    /**
     * eNum Card represents all cards present in the game.
     */
    public enum Card {
        //Weapons
        KNIFE("Knife", 1), ROPE("Rope", 1), CANDLESTICK("Candlestick", 1),
        REVOLVER("Revolver", 1), PIPE("Lead Pipe", 1), WRENCH("Wrench", 1),
        //Locations
        KITCHEN("Kitchen", 2), BALLROOM("Ballroom", 2), CONSERVATORY("Conservatory", 2),
        BILLIARD("Billiard Room", 2), LIBRARY("Library", 2), STUDY("Study", 2),
        HALL("Hall", 2), LOUNGE("Lounge", 2), DININGROOM("Dining Room", 2),
        //Suspects
        SCARLET("Miss Scarlet", 3), GREEN("Mr Green", 3), PLUM("Prof Plum", 3),
        PEACOCK("Mrs Peacock", 3), MUSTARD("Col Mustard", 3), WHITE("Mrs White", 3);

        private String name;        //Card's name.
        private ImageIcon image;    //Image on Card.
        private int type;           //Whether card is 1)Weapon, 2)Location, 3)Suspect.

        /**
         * Constructor
         */
        Card(String name, int type) {
            //Set variables.
            this.name = name;
            this.type = type;
            //Directory of image is based on card type.
            switch (type) {
                case 1:     //Weapon
                    image = new ImageIcon("images/weapons/" + name + ".jpg");
                    break;
                case 2:     //Location
                    image = new ImageIcon("images/locations/" + name + ".jpg");
                    break;
                case 3:     //Suspect
                    image = new ImageIcon("images/suspects/" + name + ".jpg");
                    break;
            }
        }

        /**
         * Get Methods for Card values
         */
        public String getName() {
            return name;
        }

        public ImageIcon getImage() {
            return image;
        }

        public int getType() {
            return type;
        }
    }

    /**
     * eNum GamePiece represents all usable game pieces in clue.
     */
    public enum GamePiece {
        //Game Pieces.
        GREEN(Rooms.BILLIARD, Card.GREEN, "Green Piece.png"),
        PLUM(Rooms.STUDY, Card.PLUM, "Plum Piece.png"),
        MUSTARD(Rooms.LOUNGE, Card.MUSTARD, "Mustard Piece.png"),
        SCARLET(Rooms.BALLROOM, Card.SCARLET, "Scarlet Piece.png"),
        PEACOCK(Rooms.CONSERVATORY, Card.PEACOCK, "Peacock Piece.png"),
        WHITE(Rooms.KITCHEN, Card.WHITE, "White Piece.png");

        private Rooms rooms;        //Starting locations.
        private Card card;          //Card equivalent.
        private ImageIcon image;    //Character Image.

        /* Constructor */
        GamePiece(Rooms rooms, Card card, String directory) {
            this.rooms = rooms;
            this.card = card;
            image = new ImageIcon("images/" + directory);
        }

        /* Get methods */
        public ImageIcon getImage() {
            return image;
        }

        public Card getCard() {
            return card;
        }

        public Rooms getRooms() {
            return rooms;
        }
    }

    /**
     * eNum Passages represents all intermediary positions on the board.
     */
    public enum Passages {

        //Hall Areas.
        HALL_A(167, 127), HALL_B(234, 168), HALL_C(166, 295), HALL_D(234, 360),
        HALL_E(144, 402), HALL_F(368, 189), HALL_G(368, 359);

        private Point position;     //Location of Hall tile.

        /**
         * Constructor
         */
        Passages(int xPos, int yPos) {
            position = new Point(xPos, yPos);
        }

        /**
         * Get Methods
         */
        public Point getPosition() {
            return position;
        }
    }

    /**
     * eNum Rooms represents all selectable locations on the board.
     */
    public enum Rooms {
        //Rooms
        STUDY(new Area(new Rectangle(40, 50, 160, 90)), 55, 75, "Study"),
        LIBRARY(new Area(new Rectangle(40, 180, 160, 100)), 55, 215, "Library"),
        BILLIARD(new Area(new Rectangle(40, 300, 140, 105)), 55, 340, "Billiard Room"),
        CONSERVATORY(new Area(new Rectangle(40, 455, 140, 110)), 55, 490, "Conservatory"),
        HALL(new Area(new Rectangle(250, 60, 130, 135)), 255, 125, "Hall"),
        BALLROOM(new Area(new Rectangle(230, 415, 175, 125)), 255, 470, "Ballroom"),
        LOUNGE(new Area(new Rectangle(430, 50, 155, 125)), 460, 105, "Lounge"),
        DININGROOM(new Area(new Rectangle(405, 240, 185, 145)), 460, 300, "Dining Room"),
        KITCHEN(new Area(new Rectangle(450, 435, 140, 140)), 460, 490, "Kitchen");

        private Area boundaryBox;   //Mapped boundary for room.
        private int xPos;           //X-Position of Game Piece origin in room.
        private int yPos;           //Y-Position of Game Piece origin in room.
        private Point position;     //Position of Game Piece given as point.
        private String name;        //String name of room, primarily for file IO.

        /**
         * Constructor.
         */
        Rooms(Area boundaryBox, int xPos, int yPos, String name) {
            this.boundaryBox = boundaryBox;
            this.xPos = xPos;
            this.yPos = yPos;
            position = new Point(xPos, yPos);
            this.name = name;
        }

        /**
         * Get Methods
         */
        public Area getBoundaryBox() {
            return boundaryBox;
        }
        public int getXPos() {
            return xPos;
        }
        public int getYPos() {
            return yPos;
        }
        public Point getPosition() {
            return position;
        }
        public String getName() {
            return name;
        }
    }
}
