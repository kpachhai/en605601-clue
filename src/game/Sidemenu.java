/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author kiran
 */
public class Sidemenu extends JPanel implements ActionListener {
    
    private JButton makeAccusation; //Opens Accusation window.

    public Sidemenu(Player player) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public JButton getMakeAccusation() { return makeAccusation; }

}
