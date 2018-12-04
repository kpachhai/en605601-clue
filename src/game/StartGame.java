package game;

import game.GameItems.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Clue Game - Main User Interface, and Main logic for game. Game structure is
 * manipulated within MainUI
 */
public class StartGame extends JFrame implements MouseListener, ActionListener, MouseMotionListener {

    //Frame Components.
    private Sidemenu hub;
    private BottomPanel bottomPanel;
    private GameBoard board;

    //MainUI Variables.
    private int turn;               //Current turn number.
    private boolean canMove;        //Conditional affecting whether new room is selectable.
    private boolean inDisprove;     //Conditional affecting whether disproval sequence is initiated.
    private boolean inAccuse;       //Conditional affecting whether accusation sequence is initiated.
    private boolean turnToggle;     //Conditional affecting beginning of a new turn.
    private boolean humanTurn;      //Conditional determining whether action is human or AIPlayer.

    private Player[] players;       //All players in the game.
    private AIPlayer[] aiPlayers;         //Of the players, those that are AIPlayer.
    private Card[] envelope;        //Array containing the mystery answer.
    private Card[] accusation;      //Array containing the accusers guess at answer.

    private javax.swing.Timer diceRoll, diceRollStop;   //Timers.

    private CardDeck deck;

    private Random rand = new Random();

    /**
     * Constructor.
     *
     * @param selection
     */
    public StartGame(ArrayList<GamePiece> selection) {

        //Set initial variable states.
        canMove = true;
        inDisprove = false;
        inAccuse = false;
        humanTurn = true;
        turnToggle = false;
        turn = 0;

        //Instantiate classes and structures.
        deck = new CardDeck();
        players = new Player[3];
        aiPlayers = new AIPlayer[2];
        accusation = new Card[3];

        //Main Panel.
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 1));
        setBackground(Color.BLACK);

        //Fill envelope with cards.
        envelope = deck.fillEnvelope();

        //Create AIPlayer and Players.
        aiPlayers[0] = new AIPlayer(1, deck.dealHand(), selection.get(1));
        aiPlayers[1] = new AIPlayer(2, deck.dealHand(), selection.get(2));
        players[0] = new Player(0, deck.dealHand(), selection.get(0));
        players[1] = aiPlayers[0];
        players[2] = aiPlayers[1];

        //Initialize UI Components.
        hub = new Sidemenu(players[0]);
        board = new GameBoard(players);
        bottomPanel = new BottomPanel(players[0]);

        //Add Components.
        add(board);
        add(hub);
        add(bottomPanel);

        //Set JFrame conditionals.
        setSize(850, 810);
        setResizable(false);
        setName("Clue");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //Add Mouse Listeners.
        addMouseListener(this);
        addMouseMotionListener(this);

        //Create Timers.
        diceRoll = new javax.swing.Timer(50, this);
        diceRollStop = new javax.swing.Timer(500, this);
        diceRollStop.setRepeats(false);

        //Add Action Listeners.
        hub.getMakeAccusation().addActionListener(this);
        hub.getMakeAssumption().addActionListener(this);
        hub.getEndTurn().addActionListener(this);
        hub.getAssumptionWindow().getButton().addActionListener(this);
        hub.getAccusationWindow().getButton().addActionListener(this);
        bottomPanel.getEnterButton().addActionListener(this);
        for (JButton hand : bottomPanel.getHand()) {
            hand.addActionListener(this);
        }

        System.out.println(envelope[0] + " " + envelope[1] + " " + envelope[2]);
    }

    /**
     * Action Listener block that controls flow of game logic with a series of
     * nested conditionals.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //If console isn't empty (forcing enter button press).
        if (!bottomPanel.messageConfirmed()) {

            //If enter button clicked.
            if (e.getSource() == bottomPanel.getEnterButton()) {

                //Clear console.
                bottomPanel.clearConsoleMessage();

                //If not human, Get AIPlayer next action.
                if (!humanTurn && !inDisprove && !inAccuse) {
                    performAIAction(aiPlayers[turn - 1].getNextAction(turnToggle));
                } //If in Assumption State.
                else if (inDisprove) {
                    displayDisproval();
                } //If in Accusation State.
                else if (inAccuse) {
                    checkAccusation();
                } //If beginning of turn.
                else if (turnToggle) {
                    //If not in room.
                    if (!players[turn].getMovement().isInARoom()) {
                        //Reset assumption area and move player.
                        bottomPanel.resetAssumption();
                        diceRoll.start();
                        diceRollStop.start();
                    } //Else set assumption area to current room.
                    else {
                        bottomPanel.setRoomAssumption(players[turn].getMovement().getEquivalentRoom());
                    }
                } //Else increment turn.
                else {
                    nextTurn();
                }
            }
        } //If triggered by diceRoll timer.
        else if (e.getSource() == diceRoll) {
            int randomImage = rand.nextInt(1);
            hub.setDiceImage(Steps.values()[randomImage].getImage());
        } //If triggered by diceRollStop timer.
        else if (e.getSource() == diceRollStop) {
            displayMovement();
        } //If Human Player's turn to disprove assumption.
        else if (inDisprove && humanTurn) {
            //Detect which card human player is disproving with.
            for (int i = 0; i < bottomPanel.getHand().length; i++) {
                if (e.getSource() == bottomPanel.getHand()[i]) {

                    //Leave assumption state.
                    inDisprove = false;
                    //Remove card from assuming AIPlayer Memory.
                    aiPlayers[turn - 1].removeCard(players[0].getHand().get(i));

                    //Reset console panel and display disproval in console.
                    bottomPanel.resetButtons();
                    bottomPanel.changeConsoleMessage("You disproved " + players[turn].getName() + ".");
                }
            }
        } //If Human Player's turn and not disproving.
        else if (humanTurn) {

            //If human player clicked on Accusation button.
            if (e.getSource() == hub.getMakeAccusation()) //Disable StartGame until Accusation Selection is complete.
            {
                setEnabled(false);
            } //If human player clicked on Assumption button.
            else if (e.getSource() == hub.getMakeAssumption()) //Disable StartGame until Assumption Selection is complete.
            {
                setEnabled(false);
            } //If human player ended turn.
            else if (e.getSource() == hub.getEndTurn()) //Increment turn.
            {
                nextTurn();
            } //If human player finalized accusation with in-window button press.
            else if (e.getSource() == hub.getAccusationWindow().getButton()) //Run Accusation state.
            {
                displayPlayerAccusation();
            } //If human player finalized assumption with in-window button press.
            else if (e.getSource() == hub.getAssumptionWindow().getButton()) //Run Assumption state.
            {
                displayPlayerAssumption();
            }
        }
    }

    /**
     * Mouse Click listener used for setting player movement.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        //If human player's turn to choose movement & not in a hall.
        if (canMove && humanTurn) {
            Rooms clicked = roomClicked(e.getPoint());

            //If point clicked is within a room, start movement timers (dice rolls).
            if (clicked != null && bottomPanel.messageConfirmed()) {
                players[turn].getMovement().setDestination(clicked);
                diceRoll.start();
                diceRollStop.start();
            }
        }
    }

    /**
     * Mouse Movement listener used for displaying intermediary passages.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

        //If human player's turn, player is in room, and all prompts are clear.
        if (humanTurn && canMove && bottomPanel.messageConfirmed()) {
            if (players[turn].getMovement().isInARoom()) {

                //Directory for pathway image.
                String directory = "images/pathways/"
                        + players[turn].getMovement().getLocation().getName() + "/";

                Rooms hoverLocation = roomHovered(e.getPoint());

                //Show board image updated with pathway, or refresh original image.
                if (hoverLocation != null) {
                    board.setBoardIcon(new ImageIcon(directory + hoverLocation.getName() + ".jpeg"));
                } else {
                    board.resetBoardIcon();
                }
            } //Refresh original board image.
            else {
                board.resetBoardIcon();
            }
        }
    }

    /**
     * roomHovered detects which room mouse pointer is located in.
     */
    private Rooms roomHovered(Point point) {
        for (Rooms rooms : Rooms.values()) {
            if (rooms.getBoundaryBox().contains(point)
                    && !rooms.getBoundaryBox().contains(board.getGamePiecePoint(turn))) {
                return rooms;
            }
        }
        return null;
    }

    /**
     * roomClicked detects which room was clicked.
     */
    private Rooms roomClicked(Point point) {
        Rooms currentRoom = players[turn].getMovement().getLocation();
        ArrayList<Rooms> canMoveToRooms = new ArrayList<>();
        if (null != currentRoom) {
            switch (currentRoom) {
                case STUDY:
                    canMoveToRooms.add(Rooms.HALL);
                    canMoveToRooms.add(Rooms.LIBRARY);
                    canMoveToRooms.add(Rooms.KITCHEN);
                    break;
                case LIBRARY:
                    canMoveToRooms.add(Rooms.STUDY);
                    canMoveToRooms.add(Rooms.BILLIARD);
                    canMoveToRooms.add(Rooms.CONSERVATORY);
                    break;
                case CONSERVATORY:
                    canMoveToRooms.add(Rooms.LIBRARY);
                    canMoveToRooms.add(Rooms.BALLROOM);
                    canMoveToRooms.add(Rooms.LOUNGE);
                    break;
                case BALLROOM:
                    canMoveToRooms.add(Rooms.CONSERVATORY);
                    canMoveToRooms.add(Rooms.BILLIARD);
                    canMoveToRooms.add(Rooms.KITCHEN);
                    break;
                case KITCHEN:
                    canMoveToRooms.add(Rooms.DININGROOM);
                    canMoveToRooms.add(Rooms.BALLROOM);
                    canMoveToRooms.add(Rooms.STUDY);
                    break;
                case DININGROOM:
                    canMoveToRooms.add(Rooms.LOUNGE);
                    canMoveToRooms.add(Rooms.BILLIARD);
                    canMoveToRooms.add(Rooms.KITCHEN);
                    break;
                case LOUNGE:
                    canMoveToRooms.add(Rooms.HALL);
                    canMoveToRooms.add(Rooms.DININGROOM);
                    canMoveToRooms.add(Rooms.CONSERVATORY);
                    break;
                case HALL:
                    canMoveToRooms.add(Rooms.STUDY);
                    canMoveToRooms.add(Rooms.BILLIARD);
                    canMoveToRooms.add(Rooms.LOUNGE);
                    break;
                case BILLIARD:
                    canMoveToRooms.add(Rooms.LIBRARY);
                    canMoveToRooms.add(Rooms.HALL);
                    canMoveToRooms.add(Rooms.DININGROOM);
                    canMoveToRooms.add(Rooms.BALLROOM);
                    break;
                default:
                    break;
            }
        }
        for (Rooms rooms : Rooms.values()) {
            if (rooms.getBoundaryBox().contains(point) && rooms != currentRoom && canMoveToRooms.contains(rooms)) {
                return rooms;
            }
        }
        return null;
    }

    /**
     * nextTurn increments the turn count by one, and shows appropriate prompts,
     * depending on if next turn is a human or AIPlayer.
     */
    public void nextTurn() {

        //Disable buttons.
        hub.toggleButtonsEnabled(false);

        //Allow player movement.
        canMove = true;
        turnToggle = true;

        //Increment turn.
        if (turn < 2) {
            turn++;
        } else {
            turn = 0;
        }

        //Change player image display in hub.
        hub.changeTurnIndicator(players[turn].getPlayerIcon());

        //Clear assumptions.
        bottomPanel.resetAssumption();

        //Determine human or AIPlayer turn.
        humanTurn = (turn == 0);

        //Display turn in console.
        bottomPanel.changeConsoleMessage(players[turn].getName() + "'s turn.");
    }

    /**
     * displayPlayerAccusation gets input accusation as done by human.
     */
    public void displayPlayerAccusation() {

        //Toggle state to in accusation.
        inAccuse = true;

        //Set panels to user-provided accusation and populate array with choices.
        bottomPanel.setSuspectAssumption(hub.getAccusationWindow().getSuspectGuess());
        accusation[2] = hub.getAccusationWindow().getSuspectGuess();
        accusation[1] = players[turn].getMovement().getEquivalentRoom();
        bottomPanel.setWeaponAssumption(hub.getAccusationWindow().getWeaponGuess());
        accusation[0] = hub.getAccusationWindow().getWeaponGuess();

        //Display accusation in console.
        bottomPanel.changeConsoleMessage(players[turn].getName() + " has made an accusation!");

        //Disable accusation window and set StartGame to clickable.
        hub.getAccusationWindow().setVisible(false);
        setEnabled(true);
        hub.toggleButtonsEnabled(false);

    }

    /**
     * diplayAIAccusation gets input accusation as done by AIPlayer.
     */
    public void displayAIAccusation() {

        //Toggle state to in accusation.
        inAccuse = true;

        //Get / Display Suspect accusation guess from AIPlayer.
        Card guess = aiPlayers[turn - 1].getPersonGuess();
        bottomPanel.setSuspectAssumption(guess);
        accusation[2] = guess;

        //Get Rooms accusation guess from AIPlayer.
        accusation[1] = players[turn].getMovement().getEquivalentRoom();

        //Det / Display Weapon accusation guess from AIPlayer.
        guess = aiPlayers[turn - 1].getWeaponGuess();
        bottomPanel.setWeaponAssumption(guess);
        accusation[0] = guess;

        //Display accusation in console.
        bottomPanel.changeConsoleMessage(players[turn].getName() + " has made an accusation!");
    }

    /**
     * displayPlayerAssumption gets input assumption as done by human.
     */
    public void displayPlayerAssumption() {

        //Toggle state to in assumption.
        inDisprove = true;

        //Set Assumption Cards in BottomPanel to user's input.
        bottomPanel.setSuspectAssumption(hub.getAssumptionWindow().getSuspectGuess());
        bottomPanel.setWeaponAssumption(hub.getAssumptionWindow().getWeaponGuess());

        //Hide assumption window and re-enable StartGame.
        hub.getAssumptionWindow().setVisible(false);
        setEnabled(true);
        hub.toggleButtonsEnabled(false);

        //Display assumption in console.
        bottomPanel.changeConsoleMessage(players[turn].getName() + " has made an assumption!");
    }

    /**
     * displayAIAssumption gets input assumption as done by AIPlayer.
     */
    public void displayAIAssumption() {

        //Force AIPlayer to only make assumptions when not in assumption state.
        if (!inDisprove) {

            //Toggle assumption state.
            inDisprove = true;

            //Display AIPlayer's assumption.
            bottomPanel.setSuspectAssumption(aiPlayers[turn - 1].getPersonGuess());
            bottomPanel.setWeaponAssumption(aiPlayers[turn - 1].getWeaponGuess());

            //Display assumption in console.
            bottomPanel.changeConsoleMessage(players[turn].getName() + " has made an assumption!");
        }
    }

    /**
     * displayMovement displays the results from a dice Roll for both human and
     * AIPlayer.
     */
    public void displayMovement() {

        //Stop diceRoll timers.
        diceRoll.stop();
        diceRollStop.stop();

        //Create random number between 1-6 and set corresponding dice image in hub.
        int roll = 2;
        ImageIcon rollIcon = Steps.values()[0].getImage();
        hub.setDiceImage(rollIcon);

        //Move this player's game piece according to distance travelled.
        players[turn].getMovement().gamePieceMove(roll);

        //Remove any passageways that may be highlighted on the board.
        board.resetBoardIcon();

        //If this player moves into a room.
        if (players[turn].getMovement().isInARoom()) {

            //Enable buttons if it is a human player's turn.
            hub.toggleButtonsEnabled(humanTurn);

            //Set movement capability to false, disabling mouse listeners.
            canMove = false;

            //Change room assumption to matching room.
            bottomPanel.setRoomAssumption(players[turn].getMovement().getEquivalentRoom());

            //If player is AIPlayer.
            if (!humanTurn) {

                //Modify turnToggle state and display the AIPlayer's movements in console.
                turnToggle = false;
                bottomPanel.changeConsoleMessage(players[turn].getName()
                        + " moved to the " + players[turn].getMovement().getLocation().getName() + ".");
            }
        } //If player ended in hallway, end turn.
        else {
            nextTurn();
        }
    }

    /**
     * displayDisproval checks opponent players ability to disprove and does so.
     * Disprovals are made clockwise to the assuming player. The disproval is
     * displayed if assuming player is human. AI re-weighting is done here,
     * based on assumption.
     */
    public void displayDisproval() {

        //Add guessed cards to an ArrayList.
        ArrayList<Card> guess = new ArrayList<>();
        guess.add(bottomPanel.getSuspectAssumption());
        guess.add(bottomPanel.getWeaponAssumption());
        guess.add(players[turn].getMovement().getEquivalentRoom());

        //Toggle humanTurn to true, for the purpose of button click recognition.
        humanTurn = true;

        //Adjust AIPlayer weights.
        for (int i = 0; i < aiPlayers.length; i++) {
            if (turn - 1 != i) {
                aiPlayers[i].addWeight(guess);
            }
        }

        //Action is dependent on which player has assumed.
        switch (turn) {

            //Human player has assumed:
            case 0:

                //If 1st AIPlayer can disprove.
                if (players[1].disproved(guess) != null) {
                    //Display disproval.
                    inDisprove = false;
                    bottomPanel.highlightDisproval(players[1].disproved(guess));
                    bottomPanel.changeConsoleMessage(players[1].getName() + " disproves you.");
                } //If 2nd  AIPlayer can disprove.
                else if (players[2].disproved(guess) != null) {
                    //Display disproval.
                    inDisprove = false;
                    bottomPanel.highlightDisproval(players[2].disproved(guess));
                    bottomPanel.changeConsoleMessage(players[2].getName() + " disproves you.");
                } //If neither player can disprove.
                else {
                    //Display no disproval in console.
                    bottomPanel.changeConsoleMessage("You were not disproved.");
                    inDisprove = false;
                    aiPlayers[0].makeIrrefutable(guess, 0);
                    aiPlayers[1].makeIrrefutable(guess, 0);
                }
                break;

            //1st AIPlayer has assumed.
            case 1:

                //If 2nd AIPlayer can disprove.
                if (players[2].disproved(guess) != null) {
                    //Remove disproving card from Memory and display that
                    //a disproval has occurred.
                    inDisprove = false;
                    aiPlayers[0].removeCard(players[2].disproved(guess));
                    bottomPanel.changeConsoleMessage(players[1].getName()
                            + " was disproved by " + players[2].getName());
                } //If human can disprove.
                else if (players[0].disproved(guess) != null) //Highlight disproval buttons for user.
                {
                    bottomPanel.highlightDisprovables(guess);
                } //If neither player can disprove.
                else {
                    //Display no disproval in console.
                    inDisprove = false;
                    bottomPanel.changeConsoleMessage(players[1].getName() + " was not disproved.");
                    aiPlayers[0].makeIrrefutable(guess, 1);
                    aiPlayers[1].makeIrrefutable(guess, 1);
                }
                break;

            //2nd AIPlayer has assumed.
            case 2:

                //If human can disprove.
                if (players[0].disproved(guess) != null) //Highlight disproval buttons for user.
                {
                    bottomPanel.highlightDisprovables(guess);
                } //If 1st AIPlayer can disprove.
                else if (players[1].disproved(guess) != null) {
                    //Remove disproving card from Memory and display that
                    //a disproval has occurred.
                    inDisprove = false;
                    aiPlayers[0].removeCard(players[1].disproved(guess));
                    bottomPanel.changeConsoleMessage(players[2].getName()
                            + " was disproved by " + players[1].getName());
                } //If neither player can disprove.
                else {
                    //Display no disproval in console.
                    inDisprove = false;
                    bottomPanel.changeConsoleMessage(players[2].getName() + " was not disproved.");
                    aiPlayers[0].makeIrrefutable(guess, 2);
                    aiPlayers[1].makeIrrefutable(guess, 2);
                }
                break;
        }

        //Modify turnToggle;
        turnToggle = false;
    }

    /**
     * performAIAction enacts AIPlayer's next move based on int provided by
     * AIPlayer class logic.
     *
     * @param actionValue action to be performed: 1: Movement from either room
     * or hallway. 2: Accusation. 3: Assumption.
     */
    private void performAIAction(int actionValue) {
        switch (actionValue) {
            case 1:
                diceRoll.start();
                diceRollStop.start();
                break;
            case 2:
                displayAIAccusation();
                break;
            case 3:
                displayAIAssumption();
                break;
        }
    }

    /**
     * Check an accusation against envelope. If correct display win screen, else
     * display Game Over.
     */
    private void checkAccusation() {

        boolean winCheck = true;

        for (int i = 0; i < envelope.length; i++) {
            if (envelope[i] != accusation[i]) {
                winCheck = false;
            }
        }

        if (winCheck && turn == 0) {
            new GameOverUI("images/win_screen.png");
        } else {
            new GameOverUI("images/lose_screen.png");
        }

        hub.getNoteBookWindow().dispose();
        dispose();
    }

    /**
     * Required for abstract implementations, but not used.
     */
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
}
