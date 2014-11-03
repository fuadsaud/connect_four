package im.fuad.rit.copads.p3.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.IOException;

import im.fuad.rit.copads.p3.C4Client;
import im.fuad.rit.copads.p3.C4BoardIntf;
import im.fuad.rit.copads.p3.C4ViewListener;
import im.fuad.rit.copads.p3.C4ModelListener;
import im.fuad.rit.copads.p3.Player;

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

    // Exported constructors.

    /**
     * Construct a new Connect Four UI.
     *
     * @param  board  Connect Four board.
     * @param  name   Player's name.
     */
    public C4UI(C4BoardIntf board, String name) {
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

                    addMarker(c);
                }
            });

            // Clicking the New Game button informs the view listener.
            this.newGameButton.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent e) {
                    clear();
                }
            });

            // Closing the window exits the client.
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Display window.
            this.frame.pack();
            this.frame.setVisible(true);
    }

    public void markerAdded(Player player, Integer row,  Integer col) { repaint(); }

    public void cleared() { repaint(); }

    public void gameStarted() { newGameButton.setEnabled(true); repaint(); }

    public void gameOver() { setMessage("Game over"); }

    public void turn(Player player) {
        if (player.isMe())
            setMessage("Your turn");
        else
            setMessage(player.getName() + "'s turn");
    }

    public void setViewListener(C4ViewListener listener) {
        this.viewListener = listener;
    }

    private void addMarker(Integer column) {
        try {
            this.viewListener.addMarker(column);
        } catch(IOException e) {
            terminate();
        }
    }

    private void clear() {
        try {
            this.viewListener.clear();
        } catch(IOException e) {
            terminate();
        }
     }

    private void repaint() { this.boardPanel.repaint(); }

    private void setMessage(String message) {
        this.message.setText(message);
    }

    private void terminate() { C4Client.terminate(); }
}
