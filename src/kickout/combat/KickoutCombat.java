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
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Line;
import javax.imageio.ImageIO;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author goodm9679
 */
public class KickoutCombat extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 1200;
    static final int HEIGHT = 900;
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
    //0=none, 1=p1, 2=p2
    int lastHit = 1;
    //PLAYER 1
    Rectangle p1 = new Rectangle(130, 520, 70, 250);
    boolean p1Left = false;
    boolean p1Right = false;
    boolean p1Jump = false;
    boolean p1Down = false;
    boolean p1Knee = false;
    boolean p1Kick = false;
    boolean p1OnGround = true;
    boolean p1OnWall = false;
    boolean p1MovePositive = true;
    int p1xSpeed = 0;
    int p1ySpeed = 0;
    int p1CoolDown = 0;
    int p1JumpTime = 0;
    int p1WalkTime = 0;
    int p1CrouchTime = 0;
    int p1Lives = StartLives;
    int p1Health = StartHealth;
    //PLAYER 2
    Rectangle p2 = new Rectangle(1000, 520, 70, 250);
    boolean p2Left = false;
    boolean p2Right = false;
    boolean p2Jump = false;
    boolean p2Down = false;
    boolean p2Knee = false;
    boolean p2Kick = false;
    boolean p2OnGround = false;
    boolean p2OnWall = false;
    boolean p2MovePositive = false;
    int p2xSpeed = 0;
    int p2ySpeed = 0;
    int p2CoolDown = 0;
    int p2JumpTime = 0;
    int p2WalkTime = 0;
    int p2CrouchTime = 0;
    int p2Lives = StartLives;
    int p2Health = StartHealth;
    //BALL
    Rectangle ball = new Rectangle(574, 720, 50, 50);
    boolean ballOnGround = true;
    int charge = 180;
    int rotation = 0;
    int rotationOffset = 0;
    int ballxSpeed = 20;
    //this is the ball's trail
    int trail = 30;
    int[] prevX = new int[trail];
    int[] prevY = new int[trail];
    //BLOCKS
    Rectangle platform = new Rectangle(10, 10, 10, 10);
    //CHARACTER BODY
    Line[] LStand = new Line[11];
    Line[] RStand = new Line[11];
    Line[] LJump = new Line[11];
    Line[] RJump = new Line[11];
    Line[] LKnee = new Line[11];
    Line[] RKnee = new Line[11];
    Line[] LHighKnee = new Line[11];
    Line[] RHighKnee = new Line[11];
    Line[] LKick = new Line[11];
    Line[] RKick = new Line[11];
    Line[] LHighKick = new Line[11];
    Line[] RHighKick = new Line[11];
    Line[] LWall = new Line[11];
    Line[] RWall = new Line[11];
    Line[] LSlide = new Line[11];
    Line[] RSlide = new Line[11];
    Line[] LCrouch = new Line[11];
    Line[] RCrouch = new Line[11];
    Line[] LWalk1 = new Line[11];
    Line[] LWalk2 = new Line[11];
    Line[] LWalk3 = new Line[11];
    Line[] RWalk1 = new Line[11];
    Line[] RWalk2 = new Line[11];
    Line[] RWalk3 = new Line[11];
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

    public void playSong(String name) {
        try {
            FileInputStream fileInputStream = new FileInputStream(name);
            Player player = null;
            try {
                player = new Player(fileInputStream);
            } catch (JavaLayerException ex) {
                Logger.getLogger(KickoutCombat.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Song is playing...");
            player.play();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JavaLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        g.fillRect(10, 30, 70, 250);
        g.setColor(Color.black);
        g.drawRect(30, 30, 50, 50);
        g.drawLine(55, 80, 55, 180);
        //BACKGROUND
        g.drawLine(0, 670, WIDTH, 670);
        //PLAYER EFFECTS
        //PLAYER 1
        g.setColor(Color.MAGENTA);
        g.fillRect(p1.x, p1.y, p1.width, p1.height);
        //draw graphics
        //determine direction
        if(p1xSpeed > 0){
            p1MovePositive = true;
        }else if(p1xSpeed < 0){
            p1MovePositive = false;
        }
        //moving right
        if(p1MovePositive == true){
            if(p1OnGround && p1xSpeed == 0){
                g.setColor(Color.BLACK);
                g.drawRect(p1.x + p1.width - 50, p1.y, 50, 50); 
            }
        }
        //moving left
        if(p1MovePositive == false){
            if(p1OnGround && p1xSpeed == 0){
                g.setColor(Color.BLACK);
                g.drawRect(p1.x, p1.y, 50, 50); 
            }
        }
        
        //PLAYER 2
        g.setColor(Color.YELLOW);
        g.fillRect(p2.x, p2.y, p2.width, p2.height);
        //BALL EFFECTS
        for (int i = 0; i < prevX.length; i++) {
            if (lastHit == 1) {
                Color purpl = new Color(220, 0, 220, (int) (i / 60.0 * 255));
                g.setColor(purpl);
                g.fillOval(prevX[i], prevY[i], 50, 50);
            } else if (lastHit == 2) {
                Color yello = new Color(230, 230, 0, (int) (i / 180.0 * 255));
                g.setColor(yello);
                g.fillOval(prevX[i], prevY[i], 50, 50);
                g.fillOval(prevX[i]+10, prevY[i]+10, 30, 30);
            }
        }
        //BALL
        g.setColor(Color.DARK_GRAY);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
        //this determines the ball's outer color
        if (lastHit == 0) {
            g.setColor(Color.LIGHT_GRAY);
        } else if (lastHit == 1) {
            g.setColor(Color.MAGENTA);
        } else if (lastHit == 2) {
            g.setColor(Color.YELLOW);
        }
        g.fillArc(ball.x, ball.y, ball.width, ball.height, (rotation * -1) - charge / 2, charge);
        g.setColor(Color.BLACK);
        g.drawOval(ball.x, ball.y, ball.width, ball.height);
        if (charge > 360) {
            g.setColor(Color.MAGENTA);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillOval(ball.x + 10, ball.y + 10, 30, 30);
        g.setColor(Color.BLACK);
        g.drawOval(ball.x + 10, ball.y + 10, 30, 30);
        //HUD
        g.setColor(Color.BLACK);
        g.fillRoundRect(490, HEIGHT - 110, 220, 220, 120, 60);
        g.fillRoundRect(490, -100, 220, 220, 120, 60);
        g.fillRect(0, 0, 30, HEIGHT);
        g.fillRect(WIDTH - 30, 0, 30, HEIGHT);
        g.fillRect(0, HEIGHT - 80, WIDTH, 80);
        g.fillRect(0, 0, WIDTH, 80);
        //g.fillArc(700, -20, 1000, 200, 180, 90);
        //g.fillArc(-500, -20, 1000, 200, 270, 90);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(500, HEIGHT - 100, 200, 200, 100, 50);
        g.fillRoundRect(500, -90, 200, 200, 100, 50);
        g.setColor(Color.MAGENTA);
        g.fillRect(0, HEIGHT - 70, 490, 60);
        g.setColor(Color.YELLOW);
        g.fillRect(710, HEIGHT - 70, 490, 60);
        g.fillRect(600, 300, 4, 4);
        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here
        //border positions
        //border[1] = (10, 10, 10, 10);
        //setup for trail
        for (int i = 0; i < prevX.length; i++) {
            prevX[i] = -100;
            prevY[i] = -100;
        }
        //doesn't work
        //playSong("JerryTerry - Dial Me In.mp3");
        
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
            //PLAYER 1 LOGIC STARTS HERE
            if (p1Right && p1xSpeed < 10) {
                p1xSpeed = p1xSpeed + 2;
            }
            if (p1Left && p1xSpeed > -10) {
                p1xSpeed = p1xSpeed - 2;
            }
            //friction
            if (p1OnGround) {
                p1JumpTime = 0;
                if (p1Down) {
                    if (p1xSpeed > 0) {
                        if (p1CrouchTime > 0) {
                            p1CrouchTime = p1CrouchTime - 1;
                        }
                        p1xSpeed = p1CrouchTime;

                    }
                    if (p1xSpeed < 0) {
                        if (p1CrouchTime < 0) {
                            p1CrouchTime = p1CrouchTime + 1;
                        }
                        p1xSpeed = p1CrouchTime;
                    }
                } else {
                    if (p1xSpeed > 0) {
                        p1CrouchTime = p1xSpeed;
                        p1xSpeed = p1xSpeed - 1;
                    }
                    if (p1xSpeed < 0) {
                        p1CrouchTime = p1xSpeed;
                        p1xSpeed = p1xSpeed + 1;
                    }
                }
                if(p1Jump && p1JumpTime == 0 && p1CoolDown == 0){
                    p1ySpeed = p1ySpeed -2;
                    p1OnGround = false;
                }
            }
            if(!p1OnGround){
                if(p1Jump && p1JumpTime < 8){
                    p1ySpeed = p1ySpeed -3;
                    p1JumpTime = p1JumpTime + 1;
                }
                p1ySpeed = p1ySpeed + 1;
            }
            if(p1.y + p1.height > 770){
                p1.y = 520;
                p1ySpeed = 0;
                p1OnGround = true;
            }
            //move player 1 according to speed
            p1.x = p1.x + p1xSpeed;
            p1.y = p1.y + p1ySpeed;
            //PLAYER 1 LOGIC ENDS HERE
            //BALL LOGIC STARTS HERE
            //stops the ball from travelling offscreen to the right
            if (ball.x + ball.width + 30 >= WIDTH) {
                ballxSpeed = ballxSpeed / 2;
                if (!ballOnGround) {
                    ballxSpeed = ballxSpeed + 2;
                }
                if (ballxSpeed > 0) {
                    ballxSpeed = ballxSpeed * (-1);
                }
                rotationOffset = rotationOffset - (ballxSpeed - 5);

                ball.x = WIDTH - ball.width - 31;
            }
            //stops the ball from travelling offscreen to the left
            if (ball.x <= 30) {
                ballxSpeed = ballxSpeed / 2;
                if (!ballOnGround) {
                    ballxSpeed = ballxSpeed - 2;
                }
                if (ballxSpeed < 0) {
                    ballxSpeed = ballxSpeed * (-1);
                }
                rotationOffset = rotationOffset - (ballxSpeed + 5);

                ball.x = 31;
            }
            if (ball.x == WIDTH / 2 - ball.width / 2) {
                if (ballxSpeed == 1 || ballxSpeed == -1) {
                    ballxSpeed = 0;
                }
                //pretty rotation transition
                if (ballxSpeed == 0) {
                    if (rotationOffset > 55) {
                        rotationOffset = rotationOffset - 1;
                    }
                    if (rotationOffset < 55) {
                        rotationOffset = rotationOffset + 1;
                    }
                }
            }

            //turns the ball grey in a 0 speed scenario
            if (ballxSpeed == 0) {
                lastHit = 0;
            }
            //prevents offset number overflow (very unlikely occurence but safer this way)
            if (rotationOffset > 180) {
                rotationOffset = rotationOffset - 180;
            }
            if (rotationOffset < -180) {
                rotationOffset = rotationOffset + 180;
            }
            ball.x = ball.x + ballxSpeed;
            rotation = rotationOffset + ball.x;
            for (int i = 0; i < prevX.length - 1; i++) {
                prevX[i] = prevX[i + 1];
                prevY[i] = prevY[i + 1];
            }
            //shuffle birds
            if (true) {
                prevX[prevX.length - 1] = ball.x;
                prevY[prevY.length - 1] = ball.y;
            } else {
                prevX[prevX.length - 1] = -20;
                prevY[prevY.length - 1] = -20;
            }
            //BALL LOGIC ENDS HERE
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
                p1Jump = true;
                p1JumpTime = p1JumpTime + 1;
            }
            if (key == KeyEvent.VK_A) {
                p1Down = true;
                p1CrouchTime = p1CrouchTime + p1CrouchTime;
            }
            if (key == KeyEvent.VK_C) {
                p1Knee = true;
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
                p2Jump = true;
            }
            if (key == KeyEvent.VK_K) {
                p2Down = true;
            }
            if (key == KeyEvent.VK_SEMICOLON) {
                p2Knee = true;
            }
            if (key == (int) '[') {
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
                p1Jump = false;
                p1JumpTime = 100;
            }
            if (key == KeyEvent.VK_A) {
                p1Down = false;
            }
            if (key == KeyEvent.VK_C) {
                p1Knee = false;
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
                p2Jump = false;
            }
            if (key == KeyEvent.VK_K) {
                p2Down = false;
            }
            if (key == KeyEvent.VK_SEMICOLON) {
                p2Knee = false;
            }
            if (key == (int) '[') {
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
