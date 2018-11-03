/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author kiran
 */
public class StartGame extends JFrame implements MouseListener, ActionListener, MouseMotionListener {
    
    private JFrame mainFrame;
    
    private GameBoard board;
    private Sidemenu sidemenu;
    private BottomPanel bottomPanel;
    
    private Player[] players;    
    private Card[] envelope;
    private Card[] accusation;
    
    private CardDeck cardDeck;
    
    public StartGame(GamePiece mainPlayer) {
        // Initialize main window
        mainFrame = new JFrame("Clue-Less with a Twist");
        mainFrame.setSize(850, 810);
        mainFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 1));
        mainFrame.setBackground(Color.BLACK);
        
        cardDeck = new CardDeck();
        players = new Player[2];
        accusation = new Card[3];
        
        envelope = cardDeck.fillEnvelope();
              
        // Create Players
        players[0] = new Player(0, cardDeck.dealHand(), mainPlayer);
        players[1] = new Player(0, cardDeck.dealHand(), GamePiece.PEACOCK);
       
        // Inititalize UI components
        sidemenu = new Sidemenu(players[0]);
        board = new GameBoard(players);
        bottomPanel = new BottomPanel(players[0]);
        
        // Add Components
        mainFrame.add(board);
        mainFrame.add(sidemenu);
        mainFrame.add(bottomPanel);
        
        // Set JFrame conditionals
        mainFrame.setLocation(300, 100);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
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
