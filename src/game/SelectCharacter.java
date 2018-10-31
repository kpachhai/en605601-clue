package game;

import game.GameItems.GamePiece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author kiran
 */
public class SelectCharacter extends JFrame implements ActionListener {

    private JFrame mainFrame;
    private JPanel displayBox;
    private StartGame startGame;

    private ArrayList<GamePiece> characters;
    private ArrayList<JButton> characterButtons;

    public SelectCharacter() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Select Your Character");
        mainFrame.setSize(810, 810);
        mainFrame.setLayout(new GridLayout(1, 1));

        displayBox = new JPanel(new GridLayout(2, 3, 5, 5));

        characters = new ArrayList<>(6);
        characterButtons = new ArrayList<>(6);

        for (GamePiece gamePiece : GamePiece.values()) {
            JButton option = new JButton();
            option.setIcon(gamePiece.getCard().getImage());
            option.addActionListener(this);
            characterButtons.add(option);
            characters.add(gamePiece);
            displayBox.add(option);
        }

        mainFrame.add(displayBox);
        mainFrame.setLocation(300, 100);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setAlwaysOnTop(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < characterButtons.size(); i++) {
            if (e.getSource() == characterButtons.get(i)) {
                startGame = new StartGame(characters.get(i));
                mainFrame.dispose();
            }
        }
    }
}
