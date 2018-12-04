package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author kiran
 */
public class Introduction {

    //Frame Components.
    private JFrame mainFrame;
    private JLabel backgroundImage;
    private JLabel titleLabel;
    private JButton startButton;
    private JButton instructionButton;
    private Instruction instructionUI;
    private SelectCharacter selectCharacterUI;

    /* Constructor.  */
    public Introduction() {
        prepareGUI();
    }

    private void prepareGUI() {
        // Set up the background 
        mainFrame = new JFrame("Clue-Less Game with a Twist");
        mainFrame.setSize(810, 810);
        mainFrame.setLayout(new GridLayout(1, 1));

        // Put the title and the button on top of background(i.e. to the foreground)
        backgroundImage = new JLabel(new ImageIcon("images/background.png"), JLabel.CENTER);
        backgroundImage.setLayout(new BorderLayout());
        titleLabel = new JLabel(new ImageIcon("images/welcome.png"), JLabel.CENTER);
        startButton = new JButton("Start Game");
        instructionButton = new JButton("Instructions");

        backgroundImage.add(titleLabel, BorderLayout.CENTER);
        backgroundImage.add(startButton, BorderLayout.PAGE_START);
        backgroundImage.add(instructionButton, BorderLayout.PAGE_END);

        startButton.addActionListener((ActionEvent e) -> {
            selectCharacterUI = new SelectCharacter();
            mainFrame.dispose();
        });
        instructionButton.addActionListener((ActionEvent e) -> {
            instructionUI = new Instruction();
        });

        mainFrame.add(backgroundImage);
        mainFrame.setLocation(300, 100);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
    }
}
