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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author goodm9679
 */
public class DrawingExample extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    //Title of the window
    String title = "My Game";

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;


    // YOUR GAME VARIABLES WOULD GO HERE
    int pacmanx = 90;
    int rotation = 0;
    int charge = 180;
    boolean leftButton = false;
    boolean rightButton = false;
    boolean upButton = false;
    boolean downButton = false;
    // GAME VARIABLES END HERE   

    
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public DrawingExample(){
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
        
        int[] xpoints = {550, 600, 750};
        int[] ypoints = {175, 50, 130};
        // GAME DRAWING GOES HERE
        g.setColor(Color.WHITE);
        g.fillOval(pacmanx, 500, 70, 70);
        g.setColor(Color.MAGENTA);
        g.fillArc(pacmanx, 500, 70, 70, (rotation*-1)-charge/2, charge);
        g.setColor(Color.BLACK);
        g.drawOval(pacmanx, 500, 70, 70);
        if(charge>360){
            g.setColor(Color.MAGENTA);
        }else{
        g.setColor(Color.WHITE);
        }
        g.fillOval(pacmanx + 10, 510, 50, 50);
        g.setColor(Color.BLACK);
        g.drawOval(pacmanx + 10, 510, 50, 50);

        // GAME DRAWING ENDS HERE
    }


    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void  preSetup(){
       // Any of your pre setup before the loop starts should go here

       
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
            //make pacman move
            if(leftButton){
               pacmanx = pacmanx-2;
            }
            if(rightButton){
                pacmanx = pacmanx+2;
            }
            if(upButton){
                charge = charge +2;
            }
            if(downButton){
                charge = charge - 2;
            }
            rotation = pacmanx;
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

    

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e){
            
        }
        
        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e){
            
        }
        
        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e){
            
        }
    }
    
    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter{
        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                leftButton = true;
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightButton = true;
            }
            if (key == KeyEvent.VK_UP) {
                upButton = true;
            }
            if (key == KeyEvent.VK_DOWN) {
                downButton = true;
            }
        }
        
        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                leftButton = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightButton = false;
            }
            if (key == KeyEvent.VK_UP) {
                upButton = false;
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
        DrawingExample game = new DrawingExample();
                
        // starts the game loop
        game.run();
    }
}

