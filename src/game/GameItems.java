package game;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author KP
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
         *
         * @return
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
     * eNum Steps represents each side of a 6-sided die.
     */
    public enum Steps {

        Steps(1, "Steps.jpg");

        private int side;           //Numerical value of dice side
        private ImageIcon image;    //Image of dice side.

        /**
         * Constructor
         */
        Steps(int side, String directory) {
            this.side = side;
            image = new ImageIcon("images/" + directory);
        }

        /**
         * Get Methods
         *
         * @return
         */
        public ImageIcon getImage() {
            return image;
        }

        public int getSide() {
            return side;
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
        PASSAGE_STUDY_LIBRARY(78, 150), PASSAGE_LIBRARY_CONSERVATORY(78, 365), PASSAGE_CONSERVATORY_BALLROOM(185, 478),
        PASSAGE_BALLROOM_KITCHEN(382, 478), PASSAGE_KITCHEN_DININGROOM(492, 365), PASSAGE_DININGROOM_LOUNGE(492, 150),
        PASSAGE_LOUNGE_HALL(382, 50), PASSAGE_HALL_STUDY(185, 50), PASSAGE_BILLIARD_HALL(280, 150),
        PASSAGE_BILLIARD_LIBRARY(185, 248), PASSAGE_BILLIARD_BALLROOM(280, 365), PASSAGE_BILLIARD_DININGROOM(382, 248);

        private Point position;     //Location of Hall tile.

        /**
         * Constructor
         */
        Passages(int xPos, int yPos) {
            position = new Point(xPos, yPos);
        }

        /**
         * Get Methods
         *
         * @return
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
        STUDY(new Area(new Rectangle(43, 24, 150, 107)), 45, 45, "Study"),
        LIBRARY(new Area(new Rectangle(43, 219, 150, 107)), 45, 268, "Library"),
        CONSERVATORY(new Area(new Rectangle(43, 438, 150, 107)), 48, 460, "Conservatory"),
        HALL(new Area(new Rectangle(245, 23, 150, 107)), 250, 45, "Hall"),
        BILLIARD(new Area(new Rectangle(250, 218, 150, 107)), 255, 273, "Billiard Room"),
        BALLROOM(new Area(new Rectangle(245, 439, 150, 107)), 255, 446, "Ballroom"),
        LOUNGE(new Area(new Rectangle(446, 19, 150, 107)), 445, 40, "Lounge"),
        DININGROOM(new Area(new Rectangle(450, 217, 150, 107)), 455, 273, "Dining Room"),
        KITCHEN(new Area(new Rectangle(450, 435, 150, 107)), 455, 465, "Kitchen");

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
