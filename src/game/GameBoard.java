/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author kiran
 */
public class GameBoard extends JPanel implements ActionListener {

    //Default GameBoard Image.
    private final ImageIcon BOARD_IMAGE = new ImageIcon("images/Board Resized.jpeg");

    //Panel components.
    private JLabel board;

    private ArrayList<JLabel> gamePiece;

    /**
     * Constructor.
     *
     * @param players
     */
    public GameBoard(Player[] players) {

        gamePiece = new ArrayList<>();

        //Initialize Layered Pane & Set Dimension.
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(BOARD_IMAGE.getIconWidth(), BOARD_IMAGE.getIconHeight()));

        //Initialize GameBoard label, set default GameBoard image, and add to Pane at depth 5.
        board = new JLabel();
        board.setIcon(BOARD_IMAGE);
        layeredPane.add(board, new Integer(5));

        //Initialize Game Piece label, set default image, and add to Pane at depth 10.
        for (Player player : players) {
            gamePiece.add(player.getGamePiece());
            layeredPane.add(player.getGamePiece(), new Integer(10));
        }

        //Add LayeredPane to Inherited JPanel.
        add(layeredPane, JPanel.CENTER_ALIGNMENT);
        setBackground(Color.BLACK);

        //Set bounds of labels.
        for (int i = 0; i < players.length; i++) {
            gamePiece.get(i).setBounds(players[i].getMovement().getXPos(),
                    players[i].getMovement().getYPos(),
                    gamePiece.get(i).getIcon().getIconWidth(),
                    gamePiece.get(i).getIcon().getIconHeight());
        }

        board.setBounds(0, 0, BOARD_IMAGE.getIconWidth(), BOARD_IMAGE.getIconHeight());
    }

    /**
     * Get gamePiece Point Method.
     * @param index
     * @return 
     */
    public Point getGamePiecePoint(int index) {
        return new Point(gamePiece.get(index).getX(), gamePiece.get(index).getY());
    }

    /**
     * Set boardIcon method.
     * @param image
     */
    public void setBoardIcon(ImageIcon image) {
        board.setIcon(image);
    }

    /**
     * Resets boardIcon.
     */
    public void resetBoardIcon() {
        board.setIcon(BOARD_IMAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
