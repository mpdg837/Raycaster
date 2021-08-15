package Raycaster;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Render;
import Raycaster.Display.UI.ScreenSprite;
import Raycaster.Player.GameTask;
import Raycaster.Player.Input.Input;
import Raycaster.Project.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class Raycaster extends Frame {


    public Raycasting rayMaker;
    public BufferedImage buffer;
    public final ScreenSprite sprites;

    public final static Point resolution= new Point(640,480);

    public final Input input = new Input();
    public java.util.Timer tim = new Timer();

    public Timer tim1 = new Timer();
    public Timer tim2 = new Timer();
    public Game game;

    public Raycaster(boolean windowed) {

        super("Raycaster Engine");





        sprites = new ScreenSprite();

        this.setLayout(new BorderLayout());
        this.addKeyListener(input);
        this.addMouseListener(input);

        this.setIgnoreRepaint(true);


        if(!windowed) {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.setUndecorated(true);

        }else{
            this.setResizable(false);
            this.setSize(640,480);
        }



        if(!windowed) {
            this.setSize(this.getMaximumSize());
        }


        this.addWindowListener(new WindowME());

        this.setBackground(Color.BLACK);
        try {

            BufferedImage bufa = ImageIO.read(new File("intro.png"));

        EventQueue.invokeLater(() -> {
            this.setVisible(true);
            this.getGraphics().drawImage(bufa,0,0,this.getWidth(),this.getHeight(),null);
        });


        buffer = new BufferedImage(resolution.x, resolution.y, BufferedImage.TYPE_INT_RGB);


            game = new Game(input);

            rayMaker = new Raycasting(game);



            Raycaster it = this;
            Thread the = new Thread(() -> {
                tim1.schedule(new Render(false, it, null), Render.deltaTime);
            });
            the.start();

            Thread ethe = new Thread(() -> {
                tim2.schedule(new GameTask(game), Render.deltaTime);
            });
            ethe.start();

            if(!windowed) {
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice device = env.getDefaultScreenDevice();

                device.setFullScreenWindow(this);

                chooseBestDisplayMode(device);
                this.getGraphics().drawImage(bufa,0,0,this.getWidth(),this.getHeight(),null);
            }

        }catch (IOException ignore){

            System.exit(0);
        }


    }

    private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (DisplayMode bestDisplayMode : device.getDisplayModes()) {



                if ( bestDisplayMode.getWidth() ==640){
                    System.out.println(bestDisplayMode.toString());
                    return bestDisplayMode;

                }


        }
        return null;
    }

    public void chooseBestDisplayMode(GraphicsDevice device) {
        final DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }else{
            this.setResizable(false);
            this.setSize(640,480);
            this.setUndecorated(false);
        }
    }


    public static void main(String[] args){

        boolean windowed = false;
        if(args.length==1){
            if(args[0].equals("-w")){
                windowed=true;
            }
        }
        new Raycaster(windowed);
    }
}
