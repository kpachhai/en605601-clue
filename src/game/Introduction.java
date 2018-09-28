package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author kiran
 */
public class Introduction extends JFrame {
    //Frame Components.
    private JFrame mainFrame;
    private JLabel backgroundImage;
    private JLabel titleLabel;
    private JButton startButton;
    private JButton instructionButton;
    private Instruction instructionUI;

    /** Constructor.  */
    public Introduction(){
        prepareGUI();
    }
    
    private void prepareGUI(){
        // Set up the background 
        mainFrame = new JFrame("Clue-Less Game with a Twist");
        mainFrame.setSize(800, 800);
        mainFrame.setLayout(new GridLayout(1,1));
        
        backgroundImage = new JLabel(new ImageIcon("images/background.png"), JLabel.CENTER);
        mainFrame.add(backgroundImage);
        mainFrame.setLocation(300, 100);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        // Put the title and the button on top of background(i.e. to the foreground)
        backgroundImage.setLayout(new BorderLayout());
        titleLabel = new JLabel(new ImageIcon("images/welcome.png"), JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        startButton = new JButton("Start Game");
        instructionButton = new JButton("Instructions");
        
        backgroundImage.add(titleLabel, BorderLayout.CENTER);
        backgroundImage.add(startButton, BorderLayout.PAGE_START);
        backgroundImage.add(instructionButton, BorderLayout.PAGE_END);
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Do Something here
            }
        });
        instructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructionUI = new Instruction();
            }
        });
    }
}
