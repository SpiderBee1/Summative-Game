package kickout.combat;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author goodm9679
 */
public class KickoutCombat extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    //Title of the window
    String title = "Kickout Combat";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    //STARTING
    int StartLives = 3;
    int StartHealth = 200;
    //PLAYER 1
    Rectangle p1 = new Rectangle(30, 30, 30, 30);
    boolean p1Left = false;
    boolean p1Right = false;
    boolean p1Up = false;
    boolean p1Down = false;
    boolean p1Jump = false;
    boolean p1Kick = false;
    boolean p1OnGround = false;
    int p1xSpeed = 0;
    int p1ySpeed = 0;
    int p1Lives = StartLives;
    int p1Health = StartHealth;
    //PLAYER 2
    boolean p2Left = false;
    boolean p2Right = false;
    boolean p2Up = false;
    boolean p2Down = false;
    boolean p2Jump = false;
    boolean p2Kick = false;
    boolean p2OnGround = false;
    int p2xSpeed = 0;
    int p2ySpeed = 0;
    int p2Lives = StartLives;
    int p2Health = StartHealth;
    //BALL
    Rectangle ball = new Rectangle(30, 30, 30, 30);
    //this is the ball's trail
    int trail = 30;
    int[] prevX = new int[trail];
    int[] prevY = new int[trail];

    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public KickoutCombat() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();

        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        g.setColor(Color.red);
        g.fillRect(64, 64, 64, 64);

        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here

        //setup for trail
        for (int i = 0; i < prevX.length; i++) {
            prevX[i] = -100;
            prevY[i] = -100;
        }
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 


            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    public BufferedImage loadImage(String name) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down

        @Override
        public void mousePressed(MouseEvent e) {
        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {
        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            //PLAYER 1
            if (key == KeyEvent.VK_Q) {
                p1Left = true;
            }
            if (key == KeyEvent.VK_Z) {
                p1Right = true;
            }
            if (key == KeyEvent.VK_S) {
                p1Up = true;
            }
            if (key == KeyEvent.VK_A) {
                p1Down = true;
            }
            if (key == KeyEvent.VK_C) {
                p1Jump = true;
            }
            if (key == KeyEvent.VK_V) {
                p1Kick = true;
            }
            //PLAYER 2
            if (key == KeyEvent.VK_J) {
                p2Left = true;
            }
            if (key == KeyEvent.VK_L) {
                p2Right = true;
            }
            if (key == KeyEvent.VK_I) {
                p2Up = true;
            }
            if (key == KeyEvent.VK_K) {
                p2Down = true;
            }
            if (key == KeyEvent.VK_SEMICOLON) {
                p2Jump = true;
            }
            if (key == (int)'[') {
                p2Kick = true;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            
            //PLAYER 1
            if (key == KeyEvent.VK_Q) {
                p1Left = false;
            }
            if (key == KeyEvent.VK_Z) {
                p1Right = false;
            }
            if (key == KeyEvent.VK_S) {
                p1Up = false;
            }
            if (key == KeyEvent.VK_A) {
                p1Down = false;
            }
            if (key == KeyEvent.VK_C) {
                p1Jump = false;
            }
            if (key == KeyEvent.VK_V) {
                p1Kick = false;
            }
            //PLAYER 2
            if (key == KeyEvent.VK_J) {
                p2Left = false;
            }
            if (key == KeyEvent.VK_L) {
                p2Right = false;
            }
            if (key == KeyEvent.VK_I) {
                p2Up = false;
            }
            if (key == KeyEvent.VK_K) {
                p2Down = false;
            }
            if (key == KeyEvent.VK_SEMICOLON) {
                p2Jump = false;
            }
            if (key == (int)'[') {
                p2Kick = false;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        KickoutCombat game = new KickoutCombat();

        // starts the game loop
        game.run();
    }
}
