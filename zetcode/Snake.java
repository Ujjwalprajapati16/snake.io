package zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() {
        initUI(); // Constructor that initializes the game window
    }

    private void initUI() {
        add(new Board()); // Adds a Board instance to the JFrame, which presumably contains game logic and graphics

        setResizable(false); // Disables window resizing
        pack(); // Packs the window to its preferred size based on the contents

        setTitle("Snake"); // Sets the title of the window to "Snake"
        setLocationRelativeTo(null); // Centers the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sets the default close operation to exit the application when the window is closed
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake(); // Creates a new instance of the Snake class, which creates the game window
            ex.setVisible(true); // Sets the window to be visible
        });
    }
}

