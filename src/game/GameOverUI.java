package game;

import javax.swing.*;

/** Class is a placeholder for Game-Ending screen. */
public class GameOverUI extends JFrame{

    /** Constructor.  */
    public GameOverUI(String display){
        JPanel panel = new JPanel();
        JLabel message = new JLabel("", new ImageIcon(display), JLabel.CENTER);

        panel.add(message);
        add(panel);

        setSize(800,800);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
