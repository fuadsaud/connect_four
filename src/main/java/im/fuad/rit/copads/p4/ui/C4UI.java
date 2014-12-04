package im.fuad.rit.copads.p4.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.IOException;

import im.fuad.rit.copads.p4.C4Client;
import im.fuad.rit.copads.p4.C4BoardIntf;
import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4ModelListener;

/**
 * Class C4UI provides the user interface for the network game of Connect Four.
 *
 * @author  Alan Kaminsky
 * @version 13-Oct-2014
 */
public class C4UI implements C4ModelListener {
    // Hidden data members.

    private C4BoardIntf c4board;

    private JFrame frame;
    private C4Panel boardPanel;
    private JTextField message;
    private JButton newGameButton;

    private C4ViewListener viewListener;

    private Integer myNumber;
    private String myName;
    private String opponentsName;

    // Exported constructors.

    /**
     * Construct a new Connect Four UI.
     *
     * @param  board  Connect Four board.
     * @param  name   Player's name.
     */
    public C4UI(C4BoardIntf board, String name) {
        this.myName = name;
        this.c4board = board;

        // Set up window.
        this.frame = new JFrame ("Connect Four -- " + name);

        Container pane = this.frame.getContentPane();
        JPanel p1 = new JPanel();
        pane.add(p1);

        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and add widgets.
        this.boardPanel = new C4Panel(this.c4board);
        this.boardPanel.setAlignmentX(0.5f);
        p1.add(this.boardPanel);
        p1.add(Box.createVerticalStrut(10));

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.setAlignmentX(0.5f);
        p1.add(p2);

        this.message = new JTextField(20);
        this.message.setAlignmentY(0.5f);
        this.message.setEditable(false);
        this.message.setText("Waiting for partner");
        p2.add(this.message);
        p2.add(Box.createHorizontalStrut(10));

        this.newGameButton = new JButton("New Game");
        this.newGameButton.setAlignmentY(0.5f);
        this.newGameButton.setEnabled(false);
        p2.add(this.newGameButton);

        // Clicking the Connect Four panel informs the view listener.
        this.boardPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = boardPanel.clickToColumn(e);

                addMarker(myNumber, c);
            }
        });

        // Clicking the New Game button informs the view listener.
        this.newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                clear();
            }
        });

        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                leave();
            }
        });

        // Closing the window exits the client.
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Display window.
        this.frame.pack();
        this.frame.setVisible(true);
    }

    /**
     * Sets this client's player number.
     *
     * @param playerNumber the player's number (1 or 2).
     */
    public void number(Integer playerNumber) {
        this.myNumber = playerNumber;
    }

    /**
     * Stores the registered player name and if it's the opponent who's registering, start the
     * game.
     *
     * @param playerNumber the player's number (1 or 2).
     * @param playerName the player's name.
     */
    public void name(Integer playerNumber, String playerName) {
        if (this.myNumber != playerNumber) {
            this.opponentsName = playerName;
            newGameButton.setEnabled(true);
            repaint();
        }
    }

    /**
     * Initiates a new turn in the game for the given player. Updates the status message
     * accordingly.
     *
     * @param playerNumber the player's number (1 or 2).
     */
    public void turn(Integer playerNumber) {
        if (playerNumber == 0)
            setMessage("Game over");
        else if (this.myNumber == playerNumber)
            setMessage("Your turn");
        else
            setMessage(this.opponentsName + "'s turn");
    }

    /**
     * Repaint the board when a marker is added to it.
     *
     * @param playerNumber the number of the player who's making the move.
     * @param row the row in which the marker is being added.
     * @param col the column in which the marker is being added.
     */
    public void markerAdded(Integer playerNumber, Integer row,  Integer col) { repaint(); }

    /**
     * Repaint the board when the it is cleared.
     */
    public void cleared() { repaint(); }

    /**
     * Terminate when the session is over.
     */
    public void left() { terminate(); }

    /**
     * Sets this object's view listener.
     *
     * @param listener the view listener to be registered.
     */
    public void setViewListener(C4ViewListener listener) {
        this.viewListener = listener;
    }

    /**
     * Tells the view listener this client has added a marker in the given column.
     *
     * @param playerNumber this player's number.
     * @param column the column in which the marker is being added.
     */
    private void addMarker(Integer playerNumber, Integer column) {
        try {
            this.viewListener.addMarker(playerNumber, column);
        } catch(IOException e) {
            terminate();
        }
    }

    /**
     * Tells the view listener this client is clearing the board.
     */
    public void clear() {
        try {
            this.viewListener.clear();
        } catch(IOException e) {
            terminate();
        }
    }

    /**
     * Tells the view listener this client is leaving the session.
     */
    private void leave() {
        try {
            this.viewListener.leave();
        } catch(IOException e) {
            terminate();
        }
    }

    /**
     * Repaints the board on the screen.
     */
    private void repaint() { this.boardPanel.repaint(); }

    /**
     * Sets the status message.
     *
     * @param message the new status message.
     */
    private void setMessage(String message) { this.message.setText(message); }

    /**
     * Asks for the termination of this client
     */
    private void terminate() { C4Client.terminate(); }
}
