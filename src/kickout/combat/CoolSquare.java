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
public class CoolSquare extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 400;
    static final int HEIGHT = 400;
    //Title of the window
    String title = "A Cool Square";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    //start position
    int startX = 85;
    int startY = 320;
    //player shape and location
    Rectangle player = new Rectangle(startX, startY, 30, 30);
    int up = 30;
    //array of  rectangles
    Rectangle[] blocks = new Rectangle[5];
    boolean upButton = false;
    boolean leftButton = false;
    boolean rightButton = false;
    boolean downButton = false;
    boolean canJump = true;
    boolean flip = false;
    boolean onFloor = false;
    int max = 0;
    //defines amount of boost the player can use
    int boost = 40;
    double bar = 0;
    int bar2 = 0;
    int numBirds = 30;
    int[] prevX = new int[numBirds];
    int[] prevY = new int[numBirds];
    BufferedImage lizard = loadImage("lizard.jpg");

    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public CoolSquare() {
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


        //draw blocks
        for (int i = 0; i < blocks.length; i++) {
            g.fillRect(blocks[i].x, blocks[i].y, blocks[i].width, blocks[i].height);
        }

        for (int i = 0; i < prevX.length; i++) {
            Color tail = new Color(255, 122, (int) (i / 30.0 * 100), (int) (i / 30.0 * 255));
            g.setColor(tail);
            g.fillOval(prevX[i] + 10, prevY[i] + 10, 10, 10);
        }
        //draw square
        g.setColor(Color.RED);
        g.fillRect(player.x, player.y, 30, 30);
        //removed image
        //g.drawImage(lizard, player.x, player.y, 30, 30, null);
        g.setColor(Color.GREEN);
        g.fillRect(WIDTH - 30, 10, 20, bar2);


        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here
        for (int i = 0; i < prevX.length; i++) {
            prevX[i] = -100;
            prevY[i] = -100;
        }
        //rectangle array
        blocks[0] = new Rectangle(200, 200, 50, 20);
        blocks[1] = new Rectangle(275, 275, 50, 20);
        blocks[2] = new Rectangle(-30, 350, WIDTH + 60, 20);
        blocks[3] = new Rectangle(125, 125, 75, 20);
        blocks[4] = new Rectangle();
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
            //boost
            if (upButton && max != boost) {
                player.y = player.y - 2;
                max = max + 1;
                //the frame after release wil not fall
                flip = false;
            }
            //jump
            if (onFloor) {
            }


            //fall when relese up
            if (max != boost && upButton == false && onFloor == false) {
                player.y = player.y + 2;
            }

            //fall after boost
            if (max == boost && onFloor == false) {
                player.y = player.y + 2;
            }

            //reset boost
            if (max > 0 && onFloor == true) {
                max = 0;
            }
            //reset floor value
            onFloor = false;
            //move left
            if (leftButton) {
                player.x = player.x - 2;
            }
            //go down
            if (downButton && onFloor == false) {
                player.y = player.y + 1;
            }
            //move right
            if (rightButton) {
                player.x = player.x + 2;
            }
            //loop right to left
            if (player.x > WIDTH) {
                player.x = -30;
            }
            //loop left to right
            if (player.x < -30) {
                player.x = WIDTH;
            }

            for (int i = 0; i < prevX.length - 1; i++) {
                prevX[i] = prevX[i + 1];
                prevY[i] = prevY[i + 1];
            }
            //shuffle birds
            if (upButton && max < boost) {
                prevX[prevX.length - 1] = player.x;
                prevY[prevY.length - 1] = player.y;
            } else {
                prevX[prevX.length - 1] = -20;
                prevY[prevY.length - 1] = -20;
            }
            //check for colisions
            for (int i = 0; i < blocks.length; i++) {
                if (player.intersects(blocks[i])) {
                    int cHeight = Math.min(blocks[i].y + blocks[i].height, player.y + player.height) - Math.max(blocks[i].y, player.y);
                    int cWidth = Math.min(blocks[i].x + blocks[i].width, player.x + player.width) - Math.max(blocks[i].x, player.x);

                    if (cHeight > cWidth) {
                        if (player.x < blocks[i].x) {
                            player.x = player.x - cWidth;
                        } else {
                            player.x = player.x + cWidth;
                        }
                    } else {
                        //above the block
                        if (player.y < blocks[i].y) {
                            player.y = player.y - cHeight;
                            onFloor = true;
                        } else {
                            player.y = player.y + cHeight;
                        }
                    }
                }
            }

            //calculate bar
            bar = ((boost - max) * 100.0 / boost);
            bar2 = (int) Math.ceil(bar);
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
    
    public BufferedImage loadImage(String name){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(name));
        }catch(Exception e){
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

            if (key == KeyEvent.VK_UP) {
                upButton = true;
            }
            if (key == KeyEvent.VK_LEFT) {
                leftButton = true;
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightButton = true;
            }
            if (key == KeyEvent.VK_DOWN) {
                downButton = true;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP) {
                upButton = false;
            }
            if (key == KeyEvent.VK_LEFT) {
                leftButton = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightButton = false;
            }
            if (key == KeyEvent.VK_DOWN) {
                downButton = false;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        CoolSquare game = new CoolSquare();

        // starts the game loop
        game.run();
    }
}
