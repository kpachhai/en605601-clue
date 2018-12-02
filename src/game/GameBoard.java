package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** GameBoard Class represents graphical display of game board. */
public class GameBoard extends JPanel implements ActionListener{

    //Default GameBoard Image.
    private final ImageIcon BOARD_IMAGE = new ImageIcon("images/Board Resized.jpeg");

    //Panel components.
    private JLabel board;

    private ArrayList<JLabel> gamePiece;

    private JLabel easterEgg;
    private javax.swing.Timer eggStart, eggMove, eggFlip, eggStop, eggEnd;
    private boolean eggIsRunning;

    /** Constructor.  */
    public GameBoard(Player[] players){

        gamePiece = new ArrayList<JLabel>();

        //Initialize Layered Pane & Set Dimension.
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(BOARD_IMAGE.getIconWidth(), BOARD_IMAGE.getIconHeight()));

        //Initialize GameBoard label, set default GameBoard image, and add to Pane at depth 5.
        board = new JLabel();
        board.setIcon(BOARD_IMAGE);
        layeredPane.add(board, new Integer(5));

        //Initialize Game Piece label, set default image, and add to Pane at depth 10.
        for(int i=0; i<players.length; i++) {
            gamePiece.add(players[i].getGamePiece());
            layeredPane.add(players[i].getGamePiece(), new Integer(10));
        }

        easterEgg = new JLabel();
        easterEgg.setIcon(new ImageIcon("images/flip1.png"));
        layeredPane.add(easterEgg, new Integer(20));
        easterEgg.setVisible(false);
        eggIsRunning = false;

        //Add LayeredPane to Inherited JPanel.
        add(layeredPane, JPanel.CENTER_ALIGNMENT);
        setBackground(Color.BLACK);

        //Set bounds of labels.
        for(int i=0; i<players.length; i++) {
            gamePiece.get(i).setBounds(players[i].getMovement().getXPos(),
                    players[i].getMovement().getYPos(),
                    gamePiece.get(i).getIcon().getIconWidth(),
                    gamePiece.get(i).getIcon().getIconHeight());
        }

        easterEgg.setBounds(580,125,easterEgg.getIcon().getIconWidth(), easterEgg.getIcon().getIconHeight());
        board.setBounds(0, 0, BOARD_IMAGE.getIconWidth(), BOARD_IMAGE.getIconHeight());

        eggStart = new javax.swing.Timer(100, this);
        eggMove = new javax.swing.Timer(2000, this);
        eggFlip = new javax.swing.Timer(500, this);
        eggFlip.setRepeats(false);
        eggStop = new javax.swing.Timer(1500, this);
        eggStop.setRepeats(false);
        eggEnd = new javax.swing.Timer(1500, this);
        eggEnd.setRepeats(false);
    }

    /** Get gamePiece Point Method.  */
    public Point getGamePiecePoint(int index) {
        return new Point(gamePiece.get(index).getX(), gamePiece.get(index).getY());
    }

    /** Set boardIcon method.  */
    public void setBoardIcon(ImageIcon image) { board.setIcon(image); }

    /** Resets boardIcon.  */
    public void resetBoardIcon() { board.setIcon(BOARD_IMAGE); }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == eggStart){
            easterEgg.setLocation(easterEgg.getX()-5,easterEgg.getY());
        }
        if(e.getSource() == eggStop){
            eggStart.stop();
        }
        if(e.getSource() == eggMove && easterEgg.getX() != 370){
            eggStart.start();
            eggStop.start();
        }
        else if(e.getSource() == eggMove && easterEgg.getX() == 370){
            eggFlip.start();
            eggMove.stop();
        }
        if(e.getSource() == eggFlip){
            easterEgg.setIcon(new ImageIcon("images/flip2.png"));
            eggEnd.start();
        }
        if(e.getSource() == eggEnd){
            easterEgg.setLocation(580, 125);
            easterEgg.setVisible(false);
            easterEgg.setIcon(new ImageIcon("images/flip1.png"));
            eggIsRunning = false;
        }
    }

    public void startEgg(){
        if(!eggIsRunning) {
            eggIsRunning = true;
            easterEgg.setVisible(true);
            eggStart.start();
            eggStop.start();
            eggMove.start();
        }
    }
}
