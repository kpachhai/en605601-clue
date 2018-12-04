package game;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import game.GameItems.*;

/** UIConsole Panel houses the visual of a Player's hand, the assumptions made, and console messages.  */
public class BottomPanel extends JTabbedPane implements ChangeListener {

    //Panel Components.
    private JLabel console;
    private JPanel cardsTab, consoleTab;
    private JPanel consoleArea, assumptionArea;
    private JButton enterButton;
    private JLabel suspectAssumption, weaponAssumption, roomAssumption;

    private JButton[] hand; //Container for buttons of player's cards.

    private final ImageIcon BACK_IMAGE = new ImageIcon("images/cardback.jpg");
    private final ImageIcon PAPER = new ImageIcon("images/Paper.jpg");
    private final ImageIcon CARD_TAB_ICON = new ImageIcon("images/CardIcon Small.png");
    private final ImageIcon CONSOLE_TAB_ICON = new ImageIcon("images/SpeechIcon Small.png");

    private Player player;

    private Card weaponAssumptionCard;
    private Card suspectAssumptionCard;
    private Card roomAssumptionCard;

    /** Constructor.
     * @param player User's character.  */
    public BottomPanel(Player player){


        this.player = player;

        setTabPlacement(LEFT);

        //Construct console.
        console = new JLabel();
        console.setIcon(PAPER);
        console.setHorizontalTextPosition(JLabel.CENTER);
        console.setVerticalTextPosition(JLabel.CENTER);

        //Construct enterButton.
        enterButton = new JButton("Enter");

        //Add console and enterButton to consoleArea.
        consoleArea = new JPanel(new BorderLayout());
        consoleArea.add(console, BorderLayout.CENTER);
        consoleArea.add(enterButton, BorderLayout.SOUTH);

        //Create assumptionArea and both tabs.
        assumptionArea = new JPanel(new FlowLayout(10,1,1));
        cardsTab = new JPanel(new FlowLayout(10,1,1));
        consoleTab = new JPanel(new GridLayout(1,2,1,1));

        //Create and add suspectAssumption.
        suspectAssumption = new JLabel();
        suspectAssumption.setIcon(BACK_IMAGE);
        assumptionArea.add(suspectAssumption);

        //Create and add weaponAssumption.
        weaponAssumption = new JLabel();
        weaponAssumption.setIcon(BACK_IMAGE);
        assumptionArea.add(weaponAssumption);

        //Create and add roomAssumption.
        roomAssumption = new JLabel();
        roomAssumption.setIcon(player.getMovement().getEquivalentRoom().getImage());
        assumptionArea.add(roomAssumption);

        //Add areas to consoleTab.
        consoleTab.add(consoleArea);
        consoleTab.add(assumptionArea);

        //Create buttons for hand.
        hand = new JButton[6];
        for( int i=0; i<hand.length; i++){
            hand[i] = new JButton();
            hand[i].setIcon(player.getHand().get(i).getImage());
            cardsTab.add(hand[i]);
            hand[i].setVerticalAlignment(TOP);
        }

        addChangeListener(this);

        //Add tab images.
        addTab("", CARD_TAB_ICON, cardsTab);
        addTab("", CONSOLE_TAB_ICON, consoleTab);
        setBackgroundAt(0, Color.BLUE);
        setBackgroundAt(1, Color.RED);
        setTabLayoutPolicy(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    @Override
    public void stateChanged(ChangeEvent e){
    }

    /** Get Methods.
     * @return  */
    public JButton getEnterButton() {return enterButton; }
    public JButton[] getHand()      { return hand;}

    /** Set Methods.
     * @param assumption */
    public void setWeaponAssumption(Card assumption)  {
        weaponAssumptionCard = assumption;
        weaponAssumption.setIcon(assumption.getImage());
    }

    public void setSuspectAssumption(Card assumption) {
        suspectAssumptionCard = assumption;
        suspectAssumption.setIcon(assumption.getImage());
    }

    public void setRoomAssumption(Card assumption)    {
        roomAssumptionCard = assumption;
        roomAssumption.setIcon(assumption.getImage());
    }

    public Card getWeaponAssumption(){ return weaponAssumptionCard; }
    public Card getSuspectAssumption(){ return suspectAssumptionCard; }
    public Card getRoomAssumption(){ return roomAssumptionCard; }

    /** changeConsoleMessage adds given String to console display and switches tab to console.
     * @param message To be displayed in the console.  */
    public void changeConsoleMessage(String message){
        setSelectedIndex(1);
        console.setText(message);
    }

    /** clearConsoleMessage removes content of console.  */
    public void clearConsoleMessage(){
        console.setText("");
    }

    /** *  messageConfirmed checks whether message has been cleared.Method also re-enables assumption cards after disproval.
     * @return  */
    public boolean messageConfirmed(){
        weaponAssumption.setEnabled(true);
        suspectAssumption.setEnabled(true);
        roomAssumption.setEnabled(true);
        return console.getText().equals("");
    }

    /** resetAssumption returns all assumption cards to the original cardback image.  */
    public void resetAssumption(){
        suspectAssumption.setIcon(BACK_IMAGE);
        weaponAssumption.setIcon(BACK_IMAGE);
        roomAssumption.setIcon(BACK_IMAGE);
    }

    /** highlightDisprovables disables all hand buttons other than valid disprovables.
     * @param guess Cards assumed by outher player to be disproved.  */
    public void highlightDisprovables(ArrayList<Card> guess){
        setSelectedIndex(0);

        for(int i = 0; i<player.getHand().size(); i++){
            Card check = player.getHand().get(i);
            if(!guess.contains(check))
                hand[i].setEnabled(false);
        }
    }

    /** highlightDisproval disables all assumption cards other than the one chosen by other player.
     * @param card Card used to disprove player's assumption.  */
    public void highlightDisproval(Card card){
        switch (card.getType()) {
            case 1:
                suspectAssumption.setEnabled(false);
                roomAssumption.setEnabled(false);
                break;
            case 2:
                weaponAssumption.setEnabled(false);
                suspectAssumption.setEnabled(false);
                break;
            default:
                weaponAssumption.setEnabled(false);
                roomAssumption.setEnabled(false);
                break;
        }
    }

    /** resetButtons re-enables all hand buttons after disproving is completed.  */
    public void resetButtons(){
        for (JButton hand1 : hand) {
            hand1.setEnabled(true);
        }
    }
}
