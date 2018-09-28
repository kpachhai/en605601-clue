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

    /**
     * Constructor.
     */
    public Instruction() {

        /**
         * Insert label into a scroll pane
         */
        String content = "<html>\n"
                + "This page is reserved for instructions"
                + "</html>";

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
