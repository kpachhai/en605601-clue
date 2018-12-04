package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import game.GameItems.*;
import java.util.*;

public class SelectCharacter extends JFrame implements ActionListener {

    private ArrayList<GamePiece> characters;
    private ArrayList<JButton> characterButtons;
    private ArrayList<GamePiece> selection;
    private int playerSelectNum;
    private StartGame mainWindow;

    public SelectCharacter() {

        JPanel displayBox = new JPanel(new GridLayout(2, 3, 5, 5));

        characters = new ArrayList<>(6);
        characterButtons = new ArrayList<>(6);
        selection = new ArrayList<>(3);
        playerSelectNum = 0;

        for (GamePiece gamePiece : GamePiece.values()) {
            JButton option = new JButton();
            option.setIcon(gamePiece.getCard().getImage());
            option.addActionListener(this);
            characterButtons.add(option);
            characters.add(gamePiece);
            displayBox.add(option);
        }

        add(displayBox);

        setSize(380, 400);
        setTitle("Select Your Character");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocation(520, 200);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerSelectNum++;

        for (int i = 0; i < characterButtons.size(); i++) {
            if (e.getSource() == characterButtons.get(i)) {
                characterButtons.get(i).setEnabled(false);
                selection.add(characters.get(i));
                setTitle("Select Opponent #" + playerSelectNum);
            }
        }

        if (playerSelectNum == 3) {
            mainWindow = new StartGame(selection);
            dispose();
        }
    }
}
