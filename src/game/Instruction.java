package game;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author kiran
 */
public class Instruction extends JFrame {
    private JLabel label;
    private JScrollPane jScrollPane;

    /** Constructor.  */
    public Instruction() {
        prepareGUI();
    }
    
    private void prepareGUI(){
        /* Put the content for the instructions here */
        String content = "<html>\n" +
                "<div style=\"margin: 5px; border: 5px double white;\">"+
                "\t<h1>Instructions</h1>\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "\t<h3>Overview</h3>\n" +
                "\t\tThis game is a simplified version of the popular board game, Clue. The main simplification is in the navigation of the game board.<br>There are nine rooms, six weapons and six people same as in the board game.\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "\t<h3>How to Start</h3>\n" +
                "\t\tTo start the game, simply hit the “Start” button on the main menu. Then, you can choose a character to play with and then you'll be<br>directed to a new window. After that, you can start playing.\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "\t<h3>Game Setup</h3>\n" +
                "\t<ul>\n" +
                "\t\t<li>The rooms are laid out in a 3x3 grid with a hallway separating each pair of adjacent rooms. </li>\n" +
                "\t\t<li>The game consists of 9 rooms, 6 suspect weapons and 6 people .</li>\n" +
                "\t\t<li>Every player will choose one character piece.</li>\n" +
                "\t\t<li>1 random room, 1 random weapon and 1 random person are chosen beforehand and put away. The point of the game is<br>to guess what’s in this hidden envelope and win.</li>\n" +
                "\t\t<li>Each hallway only holds one person. If someone is currently in a hallway, you may not move there.</li>\n" +
                "\t\t<li>Miss Scarlet always goes first. Play continues clockwise. If no player controls Miss Scarlet, choose another suspect to go first.</li>\n" +
                "\t\t<li>When you enter a room, make a suggestion to help solve the murder.</li>\n" +
                "\t</ul>\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "\t<h3>Playing the Game</h3>\n" +
                "\t<ul>\n" +
                "\t\t<li>When it is your turn and you’re in a room, you can move through one of the doors to the hallway(if it is not blocked),<br>take a secret passage to a diagonally opposite room(if there is one) and make a suggestion or if you were moved to the room<br>by another player making a suggestion, you can stay in that room and make a suggestion.</li>\n" +
                "\t\t<li>When it is your turn and you’re in a hallway, move to one of the two rooms accessible from that hallway and make a suggestion.</li>\n" +
                "\t\t<li>If all of the exits are blocked(i.e., there are people in all of the hallways) and you are not in one of the corner rooms<br>(with a secret passage), and you weren’t moved to the room by another player making a suggestion, you lose your turn<br>(except for maybe making an accusation).</li>\n" +
                "\t\t<li>Your first move must be to the hallway that is adjacent to your home square. The inactive characters stay in their home squares<br>until they are moved to a room by someone making a suggestion.</li>\n" +
                "\t\t<li>Whenever a suggestion is made, the room must be the room the one making the suggestion is currently in. The suspect in the<br>suggestion is moved to the room in the suggestion.</li>\n" +
                "\t\t<li>You may make an accusation at any time during your turn.</li>\n" +
                "\t\t<li>You don’t need to show weapons on the board, since they really don’t do anything (but this would be a nice feature). But you<br>should show where each of the characters is.</li>\n" +
                "\t\t<li>You can only suggest that the murder occurred in the room you presently occupy.</li>\n" +
                "\t\t<li>You may only make a suggestion upon entering a room.</li>\n" +
                "\t\t<li>There is no limit to the number of weapons and suspects that can be in a single room.</li>\n" +
                "\t</ul>\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "\t<h3>Suggesting a Suspect</h3>\n" +
                "\t<ul>\n" +
                "\t\t<li>Players who end their movement in a room can make a suggestion to help eliminate suspect possibilities by guessing the<br>murderer, murder weapon and murder room.</li>\n" +
                "\t\t<li>The player to the guesser’s left must disprove the suggestion by showing them one card from their hand that matches it If that<br>player can’t do so, the player to their left must also disprove the suggestion by showing one card from their hand. This<br>responsibility passes clockwise until someone shows the guesser a card, or until all players have passed.</li>\n" +
                "\t</ul>\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "\t<h3>Accusing and Winning</h3>\n" +
                "\t<ul>\n" +
                "\t\t<li>When you think you’ve solved the mystery, you can make an accusation.</li>\n" +
                "\t\t<li>Unlike suggestions, you don’t have to be occupying a room to make an accusation that the crime occured in there.</li>\n" +
                "\t\t<li>When you make your accusation and you’re correct, you win the game. If you are incorrect, you cannot win the game and can<br>only disprove other player’s suggestions from that point on.</li>\n" +
                "\t\t<li>If all players except one make an incorrect accusation, the last player standing wins.</li>\n" +
                "\t</ul>\n" +
                "\t<br>\n" +
                "\t<br>\n" +
                "</div>" +
                "</html>";

        label = new JLabel(content);

        label.setForeground(Color.BLACK);
        jScrollPane = new JScrollPane(label);

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.getViewport().setBackground(Color.WHITE);

        add(jScrollPane, BorderLayout.CENTER);
        setSize(810, 810);
        setLocation(300, 100);
        setTitle("Instructions for Clue-Less");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
}
