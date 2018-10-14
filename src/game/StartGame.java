package game;

import game.GameItems.Card;
import game.GameItems.GamePiece;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author kiran
 */
public class StartGame extends JFrame implements MouseListener, ActionListener, MouseMotionListener{
    
    //Frame Components.
    private Sidemenu hub;
    private ConsoleLogic bottomPanel;
    private GameBoard board;
    
        //MainUI Variables.
    private int turn;               //Current turn number.
    private boolean canMove;        //Conditional affecting whether new room is selectable.
    private boolean inDisprove;     //Conditional affecting whether disproval sequence is initiated.
    private boolean inAccuse;       //Conditional affecting whether accusation sequence is initiated.
    private boolean turnToggle;     //Conditional affecting beginning of a new turn.
    private boolean ownTurn;      //Conditional determining whether action is human or AI.

    private Player[] players;       //All players in the game.
    private Opponent[] opponents;   //Of the players, those that are opponents.
    private Card[] envelope;        //Array containing the mystery answer.
    private Card[] accusation;      //Array containing the accusers guess at answer.

    private CardDeck deck;

    private Random rand = new Random();
    
    public StartGame(ArrayList<GamePiece> selection) {
        
        //Set initial variable states.
        canMove = true;
        inDisprove = false;
        inAccuse = false;
        ownTurn = true;
        turnToggle = false;
        turn = 0;

        //Instantiate classes and structures.
        deck = new CardDeck();
        players = new Player[3];
        opponents = new Opponent[2];
        accusation = new Card[3];

        //Main Panel.
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 1));
        setBackground(Color.BLACK);

        //Fill envelope with cards.
        envelope = deck.fillEnvelope();

        //Create AI and Players.
        opponents[0] = new Opponent(1, deck.dealHand(), selection.get(1));
        opponents[1] = new Opponent(2, deck.dealHand(), selection.get(2));
        players[0] = new Player(0, deck.dealHand(), selection.get(0));
        players[1] = opponents[0];
        players[2] = opponents[1];

        //Initialize UI Components.
        hub = new Sidemenu(players[0]);
        board = new GameBoard(players);
        bottomPanel = new ConsoleLogic(players[0]);

        //Add Components.
        add(board);
        add(hub);
        add(bottomPanel);

        //Set JFrame conditionals.
        setSize(810, 810);
        setName("Clue");
        setVisible(true);

        //Add Mouse Listeners.
        addMouseListener(this);
        addMouseMotionListener(this);

        //Add Action Listeners.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
