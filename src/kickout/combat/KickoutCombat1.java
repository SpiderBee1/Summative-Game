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
import java.awt.Point;
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
import javax.imageio.ImageIO;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Created by Matthew Goodman Music by JerryTerry sounds from soundbible, used
 * under license. Inspired by lethal league this version is unfinished, but
 * demonstrates my ability to code adequately
 */
public class KickoutCombat1 extends JComponent {

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
    //pause function
    //not utilised in this early version
    boolean RunGame = false;
    /*
    [All levels should have a dark color scheme to contrast the brighter ball and players]
    level 1: dead end(bust some heads)
    level 2: outer space(psychografik)
    level 3: graveyard(spooky scary)
     */
    int level = 1;
    int StartLives = 3;
    int StartHealth = 200;
    //0=none, 1=p1, 2=p2
    int lastHit = 0;
    //PLAYER 1
    //player one comments apply to player 2 as well
    Rectangle p1 = new Rectangle(130, 570, 70, 200);
    boolean p1Left = false;
    boolean p1Right = false;
    boolean p1Jump = false;
    boolean p1Down = false;
    boolean p1Knee = false;
    boolean p1Kick = false;
    boolean p1OnGround = true;
    boolean p1CanJump = false;
    boolean p1OnLeftWall = false;
    boolean p1OnRightWall = false;
    boolean p1MovePositive = true;
    boolean p1CanPlat = false;
    boolean p1CanPlat2 = false;
    int p1xSpeed = 0;
    int p1ySpeed = 0;
    int p1CoolDown = 0;
    int p1JumpTime = 0;
    int p1WalkTime = 0;
    int p1CrouchTime = 0;
    int p1Lives = StartLives;
    int p1Health = StartHealth;
    Rectangle[] p1Hits = new Rectangle[8];
    //PLAYER 2
    Rectangle p2 = new Rectangle(1000, 570, 70, 200);
    boolean p2Left = false;
    boolean p2Right = false;
    boolean p2Jump = false;
    boolean p2Down = false;
    boolean p2Knee = false;
    boolean p2Kick = false;
    boolean p2OnGround = false;
    boolean p2CanJump = false;
    boolean p2OnLeftWall = false;
    boolean p2OnRightWall = false;
    boolean p2MovePositive = false;
    boolean p2CanPlat = false;
    boolean p2CanPlat2 = false;
    int p2xSpeed = 0;
    int p2ySpeed = 0;
    int p2CoolDown = 0;
    int p2JumpTime = 0;
    int p2WalkTime = 0;
    int p2CrouchTime = 0;
    int p2Lives = StartLives;
    int p2Health = StartHealth;
    Rectangle[] p2Hits = new Rectangle[8];
    //BALL
    Rectangle ball = new Rectangle(575, 500, 50, 50);
    boolean ballOnGround = false;
    boolean ballCanPlat = false;
    int charge = 180;
    int rotation = 0;
    int rotationOffset = 55;
    int ballxSpeed = 0;
    int ballySpeed = -30;
    //this is the ball's trail
    int trail = 30;
    int[] prevX = new int[trail];
    int[] prevY = new int[trail];
    //BLOCKS
    //middle
    Rectangle platform = new Rectangle(380, 470, 440, 20);
    //platform stoppers
    Rectangle platformL = new Rectangle(308, 0, 2, 450);
    Rectangle platformR = new Rectangle(900, 0, 2, 450);
    //top platforms
    Rectangle platformLeft = new Rectangle(0, 250, 248, 20);
    Rectangle platformRight = new Rectangle(952, 250, 248, 20);
    //LIGHTS
    Rectangle topLeftLight = new Rectangle(240, 260, 36, 36);
    Rectangle topRightLight = new Rectangle(926, 260, 36, 36);
    int blink = 0;
    int[] colors = new int[2];
    //CHARACTER BODY
    //very unfinished
    //rectangles hold the same number of integers as a line variable would, but can run on school computers.
    //x1 is x, y1 is y, x2 is width, y2 is height.
    Rectangle[] LStand = new Rectangle[11];
    Rectangle[] RStand = new Rectangle[11];
    Rectangle[] LJump = new Rectangle[11];
    Rectangle[] RJump = new Rectangle[11];
    Rectangle[] LKnee = new Rectangle[11];
    Rectangle[] RKnee = new Rectangle[11];
    Rectangle[] LHighKnee = new Rectangle[11];
    Rectangle[] RHighKnee = new Rectangle[11];
    Rectangle[] LKick = new Rectangle[11];
    Rectangle[] RKick = new Rectangle[11];
    Rectangle[] LHighKick = new Rectangle[11];
    Rectangle[] RHighKick = new Rectangle[11];
    Rectangle[] LWall = new Rectangle[11];
    Rectangle[] RWall = new Rectangle[11];
    Rectangle[] LSlide = new Rectangle[11];
    Rectangle[] RSlide = new Rectangle[11];
    Rectangle[] LCrouch = new Rectangle[11];
    Rectangle[] RCrouch = new Rectangle[11];
    Rectangle[] LWalk1 = new Rectangle[11];
    Rectangle[] LWalk2 = new Rectangle[11];
    Rectangle[] LWalk3 = new Rectangle[11];
    Rectangle[] RWalk1 = new Rectangle[11];
    Rectangle[] RWalk2 = new Rectangle[11];
    Rectangle[] RWalk3 = new Rectangle[11];
    //FONT
    Font normal = new Font("unsteady oversteer", Font.BOLD, 50);
    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)

    public KickoutCombat1() {
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

    //SOUND PLAYER
    //note: change this to a better system in future. needs to be able to stop sound.
    public void playSong(final String name) {
        try {
            FileInputStream fileInputStream = new FileInputStream(name);
            Player player = null;
            try {
                player = new Player(fileInputStream);
            } catch (JavaLayerException ex) {
                Logger.getLogger(KickoutCombat1.class.getName()).log(Level.SEVERE, null, ex);
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
    // g.setfont();
    // g.drawString();
    //this method draws the body for player 1, using lines
    void drawPlayer1(Graphics g) {
        Color LightPurp = new Color(255, 190, 255);
        if (p1xSpeed > 0) {
            p1MovePositive = true;
        } else if (p1xSpeed < 0) {
            p1MovePositive = false;
        }
        //moving right
        if (p1MovePositive == true) {
            //head
            g.setColor(Color.MAGENTA);
            if (p1OnRightWall == true && p1OnGround == false) {
                g.fillRect(p1.x, p1.y, 45, 45);
                g.setColor(LightPurp);
                g.drawRect(p1.x, p1.y, 45, 45);
            } else {
                g.fillRect(p1.x + p1.width - 45, p1.y, 45, 45);
                g.setColor(LightPurp);
                g.drawRect(p1.x + p1.width - 45, p1.y, 45, 45);
            }
            if (true) {
                //draw RStand
                for (int i = 0; i < 11; i++) {
                    g.drawLine(RStand[i].x + p1.x, RStand[i].y + p1.y, RStand[i].width + p1.x, RStand[i].height + p1.y);
                }
            }
        }
        //moving left
        if (p1MovePositive == false) {
            g.setColor(Color.MAGENTA);
            if (p1OnLeftWall == true && p1OnGround == false) {
                g.fillRect(p1.x + p1.width - 45, p1.y, 45, 45);
                g.setColor(LightPurp);
                g.drawRect(p1.x + p1.width - 45, p1.y, 45, 45);
            } else {
                g.fillRect(p1.x, p1.y, 45, 45);
                g.setColor(LightPurp);
                g.drawRect(p1.x, p1.y, 45, 45);
            }
            if (true) {
                for (int i = 0; i < 11; i++) {
                    //draw LStand
                    g.drawLine(LStand[i].x + p1.x, LStand[i].y + p1.y, LStand[i].width + p1.x, LStand[i].height + p1.y);
                }
            }
        }
    }

    //this method draws the body for player 2, using lines
    void drawPlayer2(Graphics g) {
        Color LightYell = new Color(255, 255, 190);
        if (p2xSpeed > 0) {
            p2MovePositive = true;
        } else if (p2xSpeed < 0) {
            p2MovePositive = false;
        }
        //moving right
        if (p2MovePositive == true) {
            //head
            g.setColor(Color.YELLOW);
            if (p2OnRightWall == true && p2OnGround == false) {
                g.fillRect(p2.x, p2.y, 45, 45);
                g.setColor(LightYell);
                g.drawRect(p2.x, p2.y, 45, 45);
            } else {
                g.fillRect(p2.x + p2.width - 45, p2.y, 45, 45);
                g.setColor(LightYell);
                g.drawRect(p2.x + p2.width - 45, p2.y, 45, 45);
            }
            if (true) {
                //draw RStand
                for (int i = 0; i < 11; i++) {
                    g.drawLine(RStand[i].x + p2.x, RStand[i].y + p2.y, RStand[i].width + p2.x, RStand[i].height + p2.y);
                }
            }
        }
        //moving left
        if (p2MovePositive == false) {
            g.setColor(Color.YELLOW);
            if (p2OnLeftWall == true && p2OnGround == false) {
                g.fillRect(p2.x + p2.width - 45, p2.y, 45, 45);
                g.setColor(LightYell);
                g.drawRect(p2.x + p2.width - 45, p2.y, 45, 45);
            } else {
                g.fillRect(p2.x, p2.y, 45, 45);
                g.setColor(LightYell);
                //g.drawRect(p1.x, p1.y, 45, 45);
            }
            if (true) {
                for (int i = 0; i < 11; i++) {
                    //draw LStand
                    g.drawLine(LStand[i].x + p2.x, LStand[i].y + p2.y, LStand[i].width + p2.x, LStand[i].height + p2.y);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g
    ) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        //colors
        Color ClearCyan = new Color(20, 230, 230, 50);
        Color LightCyan = new Color(190, 255, 255);
        Color ClearPurp = new Color(230, 20, 230, 50);
        Color LightPurp = new Color(255, 190, 255);
        Color ClearYell = new Color(230, 230, 20, 50);
        Color LightYell = new Color(255, 255, 190);
        Color Sky = new Color(36, 0, 128);
        Color Grass = new Color(25, 128, 0);
        Color Goal = new Color(163, 163, 163);
        //BACKGROUND
        if (level == 1) {
            g.setColor(Sky);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.GRAY);
            g.fillRect(0, 680, WIDTH, 200);
            g.setColor(Color.BLACK);
            //vertical net
            g.drawLine(450, 490, 450, 750);
            g.drawLine(500, 490, 500, 750);
            g.drawLine(550, 490, 550, 750);
            g.drawLine(600, 490, 600, 750);
            g.drawLine(650, 490, 650, 750);
            g.drawLine(700, 490, 700, 750);
            g.drawLine(750, 490, 750, 750);
            //horizontal net(increasing sag)
            g.drawLine(400, 510, 800, 510);
            g.drawArc(380, platform.y + 70, platform.width, 10, 180, 170);
            g.drawArc(380, platform.y + 100, platform.width, 20, 180, 170);
            g.drawArc(380, platform.y + 130, platform.width, 30, 180, 170);
            g.drawArc(380, platform.y + 160, platform.width, 40, 180, 170);
            g.drawArc(380, platform.y + 190, platform.width, 50, 180, 170);
            g.drawArc(380, platform.y + 220, platform.width, 60, 180, 170);
            g.setColor(Goal);
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
            g.fillRect(platform.x, platform.y + 20, 20, 275);
            g.fillRect(platform.x + platform.width - 20, platform.y + 20, 20, 275);
        }
        //PLATFORM
        g.setColor(Color.black);
        //g.fillRect(platform.x, platform.y, platform.width, platform.height);
        g.fillRect(platformRight.x, platformRight.y, platformRight.width, platformRight.height);
        g.fillRect(platformLeft.x, platformLeft.y, platformLeft.width, platformLeft.height);
        //g.fillRect(platformRight.x, platformRight.y, platformRight.width, platformRight.height);
        //lights
        if (colors[0] == 0) {
            g.setColor(Color.CYAN);
            g.fillOval(topLeftLight.x, topLeftLight.y, topLeftLight.width, topLeftLight.height);
            g.setColor(ClearCyan);
        } else if (colors[0] == 1) {
            g.setColor(Color.MAGENTA);
            g.fillOval(topLeftLight.x, topLeftLight.y, topLeftLight.width, topLeftLight.height);
            g.setColor(ClearPurp);
        } else if (colors[0] == 2) {
            g.setColor(Color.YELLOW);
            g.fillOval(topLeftLight.x, topLeftLight.y, topLeftLight.width, topLeftLight.height);
            g.setColor(ClearYell);
        }
        //flicker left light
        if ((blink < 1 || blink > 7) && (blink < 18 || blink > 28)) {
            g.fillArc(topLeftLight.x - 75, topLeftLight.y - 75, topLeftLight.width + 150, topLeftLight.height + 150, 252, 46);
            g.fillArc(topLeftLight.x - 150, topLeftLight.y - 150, topLeftLight.width + 300, topLeftLight.height + 300, 255, 40);
            g.fillArc(topLeftLight.x - 225, topLeftLight.y - 225, topLeftLight.width + 450, topLeftLight.height + 450, 252, 46);
            g.fillArc(topLeftLight.x - 300, topLeftLight.y - 300, topLeftLight.width + 600, topLeftLight.height + 600, 255, 40);
            g.fillArc(topLeftLight.x - 375, topLeftLight.y - 375, topLeftLight.width + 750, topLeftLight.height + 750, 252, 46);
            g.fillArc(topLeftLight.x - 450, topLeftLight.y - 450, topLeftLight.width + 900, topLeftLight.height + 900, 255, 40);
        }
        g.setColor(Color.BLACK);
        g.fillArc(topLeftLight.x - 10, topLeftLight.y - 10, topLeftLight.width + 20, topLeftLight.height + 20, 0, 180);
        if (colors[1] == 0) {
            g.setColor(Color.CYAN);
            g.fillOval(topRightLight.x, topRightLight.y, topRightLight.width, topRightLight.height);
            g.setColor(ClearCyan);
        } else if (colors[1] == 1) {
            g.setColor(Color.MAGENTA);
            g.fillOval(topRightLight.x, topRightLight.y, topRightLight.width, topRightLight.height);
            g.setColor(ClearPurp);
        } else if (colors[1] == 2) {
            g.setColor(Color.YELLOW);
            g.fillOval(topRightLight.x, topRightLight.y, topRightLight.width, topRightLight.height);
            g.setColor(ClearYell);
        }
        //flicker right light
        if ((blink < 201 || blink > 207) && (blink < 218 || blink > 228)) {
            g.fillArc(topRightLight.x - 75, topRightLight.y - 75, topRightLight.width + 150, topRightLight.height + 150, 242, 46);
            g.fillArc(topRightLight.x - 150, topRightLight.y - 150, topRightLight.width + 300, topRightLight.height + 300, 245, 40);
            g.fillArc(topRightLight.x - 225, topRightLight.y - 225, topRightLight.width + 450, topRightLight.height + 450, 242, 46);
            g.fillArc(topRightLight.x - 300, topRightLight.y - 300, topRightLight.width + 600, topRightLight.height + 600, 245, 40);
            g.fillArc(topRightLight.x - 375, topRightLight.y - 375, topRightLight.width + 750, topRightLight.height + 750, 242, 46);
            g.fillArc(topRightLight.x - 450, topRightLight.y - 450, topRightLight.width + 900, topRightLight.height + 900, 245, 40);
        }
        g.setColor(Color.BLACK);
        g.fillArc(topRightLight.x - 10, topRightLight.y - 10, topRightLight.width + 20, topRightLight.height + 20, 0, 180);
        //PLAYER EFFECTS
        //PLAYER 1
        g.setColor(Color.MAGENTA);
        //g.fillRect(p1.x, p1.y, p1.width, p1.height);
        drawPlayer1(g);

        //PLAYER 2
        g.setColor(Color.YELLOW);
        //g.fillRect(p2.x, p2.y, p2.width, p2.height);
        drawPlayer2(g);
        //BALL EFFECTS
        //ball trail
        for (int i = 0; i < prevX.length; i++) {
            if (lastHit == 1) {
                Color purpl = new Color(230, 0, 230, (int) (i / 180.0 * 255));
                g.setColor(purpl);
                g.fillOval(prevX[i], prevY[i], 50, 50);
                g.fillOval(prevX[i] + 10, prevY[i] + 10, 30, 30);
            } else if (lastHit == 2) {
                Color yello = new Color(230, 230, 0, (int) (i / 180.0 * 255));
                g.setColor(yello);
                g.fillOval(prevX[i], prevY[i], 50, 50);
                g.fillOval(prevX[i] + 10, prevY[i] + 10, 30, 30);
            } else if (lastHit == 0) {
                Color cya = new Color(0, 230, 230, (int) (i / 180.0 * 255));
                g.setColor(cya);
                g.fillOval(prevX[i], prevY[i], 50, 50);
                g.fillOval(prevX[i] + 10, prevY[i] + 10, 30, 30);
            }
        }
        //BALL
        g.setColor(Color.DARK_GRAY);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
        //this determines the ball's outer color
        if (lastHit == 0) {
            g.setColor(Color.CYAN);
        } else if (lastHit == 1) {
            g.setColor(Color.MAGENTA);
        } else if (lastHit == 2) {
            g.setColor(Color.YELLOW);
        }
        g.fillArc(ball.x, ball.y, ball.width, ball.height, (rotation * -1) - charge / 2, charge);
        g.setColor(Color.BLACK);
        g.drawOval(ball.x, ball.y, ball.width, ball.height);
        if (charge > 360) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillOval(ball.x + 10, ball.y + 10, 30, 30);
        g.setColor(Color.BLACK);
        g.drawOval(ball.x + 10, ball.y + 10, 30, 30);
        //inner lines(top left, top right,bottom left, bottom right
        g.setColor(Color.BLACK);
        g.drawLine(ball.x + 15, ball.y + 15, ball.x + 22, ball.y + 22);
        g.drawLine(ball.x + ball.width - 15, ball.y + 15, ball.x + ball.width - 22, ball.y + 22);
        g.drawLine(ball.x + 15, ball.y + ball.height - 15, ball.x + 22, ball.y + ball.height - 22);
        g.drawLine(ball.x + ball.width - 15, ball.y + ball.height - 15, ball.x + ball.width - 22, ball.y + ball.height - 22);
        g.setColor(Color.GREEN);
        g.fillRect(50, 100, 300, 50);
        g.setFont(normal);
        g.setColor(Color.BLACK);
        g.drawString("1234567890", 50, 150);
        //HUD
        g.setColor(Color.BLACK);
        g.fillRoundRect(490, HEIGHT - 110, 220, 220, 120, 60);
        g.fillRect(0, 0, 30, HEIGHT);
        g.fillRect(WIDTH - 30, 0, 30, HEIGHT);
        g.fillRect(0, HEIGHT - 80, WIDTH, 80);
        //g.fillRect(0, 0, WIDTH, 80);
        //g.fillArc(700, -20, 1000, 200, 180, 90);
        //g.fillArc(-500, -20, 1000, 200, 270, 90);
        g.setColor(Color.CYAN);
        g.fillRoundRect(500, HEIGHT - 100, 200, 200, 100, 50);
        g.setColor(Color.MAGENTA);
        g.fillRect(0, HEIGHT - 70, 490, 60);
        g.setColor(Color.YELLOW);
        g.fillRect(710, HEIGHT - 70, 490, 60);
        //debug
        if (p1CoolDown != 0) {
            g.setColor(Color.RED);
            if (p1MovePositive == true) {
                g.fillRect(p1Hits[0].x, p1Hits[0].y, p1Hits[0].width, p1Hits[0].height);
            }
            if (p1MovePositive == false) {
                g.fillRect(p1Hits[1].x, p1Hits[1].y, p1Hits[1].width, p1Hits[1].height);
            }
        }
        //pause
            if(RunGame == false){
                g.setColor(Color.WHITE);
                g.fillRect(500, 250, 80, 200);
                g.fillRect(610, 250, 80, 200);
            }
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
        //Player 1 hitboxes
        //right knee
        p1Hits[0] = new Rectangle(0, 0, 80, 100);
        //left knee
        p1Hits[1] = new Rectangle(0, 0, 80, 100);
        //right kick
        p1Hits[0] = new Rectangle(0, 0, 100, 100);
        //left kick
        p1Hits[1] = new Rectangle(0, 0, 100, 100);

        //Player 2 hitboxes
        //right knee
        p2Hits[0] = new Rectangle(0, 0, 80, 100);
        //left knee
        p2Hits[1] = new Rectangle(0, 0, 80, 100);
        //right kick
        p2Hits[0] = new Rectangle(0, 0, 100, 100);
        //left kick
        p2Hits[1] = new Rectangle(0, 0, 100, 100);
        colors[0] = 0;
        colors[1] = 0;
        //player lines
        /*
        Order of lines
        0: spine
        1: back arm
        2: back forearm
        3: front arm
        4: front forearm
        5: back thigh
        6: back shin
        7: back foot
        8: front thigh
        9: front shin
        10: front foot
        
        the first point is connected to the previous line
         */
        //right standing
        RStand[0] = new Rectangle(48, 45, 48, 120);
        RStand[1] = new Rectangle(48, 45, 35, 85);
        RStand[2] = new Rectangle(35, 85, 35, 130);
        RStand[3] = new Rectangle(48, 45, 54, 95);
        RStand[4] = new Rectangle(54, 95, 70, 136);
        RStand[5] = new Rectangle(48, 120, 44, 165);
        RStand[6] = new Rectangle(44, 165, 30, 200);
        RStand[7] = new Rectangle(30, 200, 36, 200);
        RStand[8] = new Rectangle(48, 120, 70, 160);
        RStand[9] = new Rectangle(70, 160, 62, 200);
        RStand[10] = new Rectangle(62, 200, 68, 200);
        //left standing
        LStand[0] = new Rectangle(22, 45, 22, 120);
        LStand[1] = new Rectangle(22, 45, 35, 85);
        LStand[2] = new Rectangle(35, 85, 35, 130);
        LStand[3] = new Rectangle(22, 45, 16, 95);
        LStand[4] = new Rectangle(16, 95, 0, 136);
        LStand[5] = new Rectangle(22, 120, 26, 165);
        LStand[6] = new Rectangle(26, 165, 40, 200);
        LStand[7] = new Rectangle(40, 200, 34, 200);
        LStand[8] = new Rectangle(22, 120, 0, 160);
        LStand[9] = new Rectangle(0, 160, 8, 200);
        LStand[10] = new Rectangle(8, 200, 2, 200);
        //right knee(not fifnished)
        RKnee[0] = new Rectangle(48, 45, 48, 120);
        RKnee[1] = new Rectangle(48, 45, 35, 85);
        RKnee[2] = new Rectangle(35, 85, 35, 130);
        RKnee[3] = new Rectangle(48, 45, 54, 95);
        RKnee[4] = new Rectangle(54, 95, 70, 136);
        RKnee[5] = new Rectangle(48, 120, 44, 165);
        RKnee[6] = new Rectangle(44, 165, 30, 200);
        RKnee[7] = new Rectangle(30, 200, 36, 200);
        RKnee[8] = new Rectangle(48, 120, 70, 160);
        RKnee[9] = new Rectangle(70, 160, 62, 200);
        RKnee[10] = new Rectangle(62, 200, 68, 200);
        //start music
        new Thread() {
            @Override
            public void run() {
                playSong("JerryTerry - Bust Some Heads.mp3");
            }
        }.start();
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
            if (RunGame) {
                if (p1.intersects(platformL)) {
                    p1OnGround = false;
                }
                if (p1.intersects(platformR)) {
                    p1OnGround = false;
                }
                //reset can platform
                p1CanPlat = false;
                p1CanPlat2 = false;
                //check platform 1
                if (p1.y + p1.height < platform.y + platform.height) {
                    p1CanPlat = true;
                }
                //check platform 2
                if (p1.y + p1.height < platformLeft.y + platformLeft.height) {
                    p1CanPlat2 = true;
                }
                if (p1Right && p1xSpeed < 10 && p1CoolDown <= 3) {
                    //player moves slower in the air
                    if (p1OnGround) {
                        p1xSpeed = p1xSpeed + 2;
                    } else {
                        p1xSpeed = p1xSpeed + 1;
                    }
                }
                if (p1Left && p1xSpeed > -10 && p1CoolDown <= 3) {
                    //player moves slower in the air
                    if (p1OnGround) {
                        p1xSpeed = p1xSpeed - 2;
                    } else {
                        p1xSpeed = p1xSpeed - 1;
                    }
                }
                //friction
                //check if p1 on ground
                if (p1.y + p1.height > 770) {
                    p1.y = 770 - p1.height;
                    p1ySpeed = 0;
                    p1OnGround = true;
                }
                //check if on platform 1
                if (p1CanPlat) {
                    if (p1.intersects(platform)) {
                        p1.y = platform.y - p1.height;
                        p1OnGround = true;
                        p1ySpeed = 0;
                    }
                }
                //check if on platform 2
                if (p1CanPlat2) {
                    if (p1.intersects(platformLeft) || p1.intersects(platformRight)) {
                        p1.y = platformLeft.y - p1.height;
                        p1OnGround = true;
                        p1ySpeed = 0;
                    }
                }
                //move player 1 according to speed
                p1.x = p1.x + p1xSpeed;
                p1.y = p1.y + p1ySpeed;
                //reset can jump
                p1CanJump = false;
                p1OnLeftWall = false;
                //check if on left wall
                if (p1.x < 30) {
                    p1.x = 30;
                    if (p1Jump == false) {
                        p1xSpeed = 0;
                    } else {
                        p1xSpeed = 12;
                    }
                    p1ySpeed = 1;
                    p1CanJump = true;
                    p1JumpTime = 2;
                    p1OnLeftWall = true;
                    //  p1.y = p1.y + 3;
                }
                p1OnRightWall = false;
                //check if on right wall
                if (p1.x + p1.width > WIDTH - 30) {
                    p1.x = WIDTH - 30 - p1.width;
                    if (p1Jump == false) {
                        p1xSpeed = 0;
                    } else {
                        p1xSpeed = -12;
                    }
                    p1ySpeed = 1;
                    p1CanJump = true;
                    p1JumpTime = 2;
                    p1OnRightWall = true;
                }
                if (p1OnGround) {
                    p1CanJump = true;
                    p1OnRightWall = false;
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
                        p1CanPlat = false;
                        if (p1.y < 760 - p1.height) {
                            p1.y = p1.y + 20;
                            p1OnGround = false;
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

                }
                if (!p1OnGround) {
                    //analog jump
                    if (p1Jump && p1JumpTime < 8) {
                        p1ySpeed = p1ySpeed - 3;
                        p1JumpTime = p1JumpTime + 1;
                    }
                    p1ySpeed = p1ySpeed + 1;
                }

                if (p1CanJump) {
                    if (p1CoolDown == 0) {
                        if (p1Jump && p1JumpTime == 0) {
                            p1ySpeed = p1ySpeed - 2;
                            p1OnGround = false;
                            if (p1OnLeftWall) {
                                p1xSpeed = 10;
                                p1ySpeed = -3;
                                //p1Left = false;
                            }
                            if (p1OnRightWall) {
                                p1xSpeed = -10;
                                p1ySpeed = -3;
                                //p1Right = false;
                            }
                        }
                    }
                }
                //move player 1 according to speed
                //p1.x = p1.x + p1xSpeed;
                //p1.y = p1.y + p1ySpeed;
                //update if on left wall

                //PLAYER 1 KICKS
                //right knee
                p1Hits[0] = new Rectangle(p1.x + 40, p1.y + 100, 80, 100);
                //left knee
                p1Hits[1] = new Rectangle(p1.x - 50, p1.y + 100, 80, 100);
                //right kick
                p1Hits[2] = new Rectangle(p1.x + 40, p1.y + 100, 100, 100);
                //left kick
                p1Hits[3] = new Rectangle(p1.x - 50, p1.y + 100, 100, 100);
                if (p1Knee && p1CoolDown == 0 && p1OnLeftWall == false && p1OnRightWall == false) {
                    if (p1MovePositive == true) {
                        if (p1Hits[0].intersects(ball)) {
                            ballxSpeed = 0 + p1xSpeed / 4;
                            ballySpeed = -27;
                            ballOnGround = false;
                            lastHit = 1;
                            new Thread() {
                                @Override
                                public void run() {
                                    playSong("Jab.mp3");
                                }
                            }.start();
                        }
                    }
                    if (p1MovePositive == false) {
                        if (p1Hits[1].intersects(ball)) {
                            ballxSpeed = 0 + p1xSpeed / 4;
                            ballySpeed = -27;
                            ballOnGround = false;
                            lastHit = 1;
                            new Thread() {
                                @Override
                                public void run() {
                                    playSong("Jab.mp3");
                                }
                            }.start();
                        }
                    }
                    p1Knee = false;
                    p1CoolDown = 20;
                }
                if (p1Kick && p1CoolDown == 0 && p1OnLeftWall == false && p1OnRightWall == false) {
                    if (p1MovePositive == true) {
                        if (p1Hits[2].intersects(ball)) {
                            ballxSpeed = 30 + p1xSpeed;
                            if (p1Down == false) {
                                ballySpeed = -20 + p1ySpeed;
                            } else {
                                ballySpeed = 8;
                            }
                            ballOnGround = false;
                            lastHit = 1;
                            new Thread() {
                                @Override
                                public void run() {
                                    playSong("Kick.mp3");
                                }
                            }.start();
                        }
                    }
                    if (p1MovePositive == false) {
                        if (p1Hits[3].intersects(ball)) {
                            ballxSpeed = -30 + p1xSpeed;
                            if (p1Down == false) {
                                ballySpeed = -20 + p1ySpeed;
                            } else {
                                ballySpeed = 8;
                            }
                            ballOnGround = false;
                            lastHit = 1;
                            new Thread() {
                                @Override
                                public void run() {
                                    playSong("Kick.mp3");
                                }
                            }.start();
                        }
                    }
                    p1Kick = false;
                    p1CoolDown = 20;
                }
                //reduce cooldown
                if (p1CoolDown > 0) {
                    p1CoolDown = p1CoolDown - 1;
                }
                //PLAYER 1 LOGIC ENDS HERE
                //PLAYER 2 LOGIC STARTS HERE
                //PLAYER 2 LOGIC ENDS HERE
                //BALL LOGIC STARTS HERE
                //stops the ball from travelling offscreen to the right
                if (ball.intersects(platformL)) {
                    ballOnGround = false;
                }
                if (ball.intersects(platformR)) {
                    ballOnGround = false;
                }
                //reset can platform
                ballCanPlat = false;
                if (ball.y + ball.height < platform.y + platform.height && ballySpeed >= 0) {
                    ballCanPlat = true;
                }
                //check if ball is on ground
                if (ball.y + ball.height > 770) {
                    ball.y = 770 - ball.height;
                    ballySpeed = ballySpeed / 2;
                    ballOnGround = true;
                }
                if (ballCanPlat) {
                    if (ball.intersects(platform)) {
                        ball.y = platform.y - ball.height;
                        ballOnGround = true;
                        ballySpeed = 0;
                    }
                }
                //stops the ball from travelling offscreen to the right
                if (ball.x + ball.width + 30 >= WIDTH) {
                    ballxSpeed = ballxSpeed / 2;
                    if (!ballOnGround) {
                        ballxSpeed = ballxSpeed + 2;
                        ballySpeed = ballySpeed - 4;
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
                        ballySpeed = ballySpeed - 4;
                    }
                    if (ballxSpeed < 0) {
                        ballxSpeed = ballxSpeed * (-1);
                    }
                    rotationOffset = rotationOffset - (ballxSpeed + 5);

                    ball.x = 31;
                }
                if (ball.x == WIDTH / 2 - ball.width / 2 && ballySpeed == 0 && ballOnGround) {
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

                //prevents offset number overflow (very unlikely occurence but safer this way)
                if (rotationOffset > 180) {
                    rotationOffset = rotationOffset - 180;
                }
                if (rotationOffset < -180) {
                    rotationOffset = rotationOffset + 180;
                }
                //constant ball falling
                ballySpeed = ballySpeed + 1;

                ball.x = ball.x + ballxSpeed;
                ball.y = ball.y + ballySpeed;
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

                //LIGHTS
                if (ball.intersects(topLeftLight)) {
                    colors[0] = lastHit;
                    blink = 0;
                }
                if (ball.intersects(topRightLight)) {
                    colors[1] = lastHit;
                    blink = 200;
                }

                if (blink == 400) {
                    blink = 0;
                }
                blink = blink + 1;
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
                p1Kick = true;
            }
            if (key == KeyEvent.VK_V) {
                p1Knee = true;
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
            if (key == KeyEvent.VK_R) {
                if (RunGame) {
                    RunGame = false;
                } else {
                    RunGame = true;
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        KickoutCombat1 game = new KickoutCombat1();

        // starts the game loop
        game.run();
    }
}
