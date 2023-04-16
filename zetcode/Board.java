package zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int RAND_POS = 29;
    private final int ALL_DOTS = 90;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board(){
        initBoard();
    }

    public void initBoard(){
        addKeyListener(new TAdapter()); // Add a KeyAdapter for listening to key events
        setBackground(Color.black); // Set the background color of the panel to black
        setFocusable(true); // Set the panel as focusable to receive keyboard input

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT)); // Set the preferred size of the panel
        loadImages(); // Load the images for the game
        initGame(); // Initialize the game
    }

    private void loadImages(){
        // Load images for the game from files
        ImageIcon iid = new ImageIcon("zetcode/resources/dot.png");
        ball = iid.getImage();
        ImageIcon iia = new ImageIcon("zetcode/resources/apple.png");
        apple = iia.getImage();
        ImageIcon iih = new ImageIcon("zetcode/resources/head.png");
        head = iih.getImage();
    }

    private void initGame() {
        dots = 3; // Initialize the number of dots

        // Set initial positions for the dots
        for (int z=0; z<dots; z++){
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple(); // Locate the initial position of the apple

        timer = new Timer(DELAY, this); // Create a timer for game updates
        timer.start(); // Start the timer
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        doDrawing(g); // Draw the game objects
    }

    private void doDrawing(Graphics g){
        if(inGame){
            // If the game is still in progress, draw the apple and dots
            g.drawImage(apple, apple_x, apple_y, this);
            for(int z=0; z<dots; z++){
                if(z==0){
                    g.drawImage(head, x[z], y[z], this); // Draw the head of the snake
                }else{
                    g.drawImage(ball, x[z], y[z], this); // Draw the body segments of the snake
                }
            }
            Toolkit.getDefaultToolkit().sync(); // Sync the graphics state
        }else{
            gameOver(g); // If the game is over, draw the game over message
        }
    }

    private void gameOver(Graphics g){
        String msg = "Game over";
        Font small = new Font("Helvetica", Font.BOLD, 14); // Create a small font for the game over message
        FontMetrics metr = getFontMetrics(small);
    
        g.setColor(Color.white); // Set the text color to white
        g.setFont(small); // Set the font for the game over message
    
        // Draw the game over message at the center of the panel
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple(){
        // Check if the snake has eaten the apple
        if(x[0] == apple_x && y[0] == apple_y){
            dots++; // Increase the length of the snake
            locateApple(); // Locate a new position for the apple
        }
    }
    
    private void move(){
        // Move the snake by updating the positions of its body segments
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
    
        // Update the position of the head based on the direction of movement
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    
    private void checkCollision(){
        // Check for collision with the borders of the panel
        if(y[0] >= B_HEIGHT || y[0] < 0 || x[0] >= B_WIDTH || x[0] < 0){
            inGame = false; // If collision occurs, set inGame flag to false
        }
    
        // Check for collision with the body of the snake
        for(int z=1; z<dots; z++){
            if(x[z] == x[0] && y[z] == y[0]){
                inGame = false; // If collision occurs, set inGame flag to false
            }
        }
    
        if(!inGame){
            timer.stop(); // Stop the game update timer if game over
        }
    }
    
    private void locateApple(){
        // Generate random position for the apple within the panel
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));
    
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple(); // Check for apple eaten
            checkCollision(); // Check for collision
            move(); // Move the snake
        }
    
        repaint(); // Repaint the panel to update the game state
    }
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if((key == KeyEvent.VK_LEFT) && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_RIGHT) && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_UP) && (!downDirection)){
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if((key == KeyEvent.VK_DOWN) && (!upDirection)){
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}

