package Raycaster;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Render;
import Raycaster.Player.GameTask;
import Raycaster.Player.Input.Input;
import Raycaster.Project.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;

public class Raycaster extends Frame {


    public Raycasting rayMaker;
    public final BufferedImage buffer;
    public final Container panel;
    public final static Point resolution= new Point(640,480);

    public final Input input = new Input();
    public java.util.Timer tim = new Timer();

    public Timer tim1 = new Timer();
    public Timer tim2 = new Timer();
    public Game game;
    private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[]{
            new DisplayMode(640, 480, 32, 0),
            new DisplayMode(640, 480, 16, 0),
            new DisplayMode(640, 480, 8, 0)};

    public Raycaster(boolean windowed) {

        super("Raycaster Engine");

        this.setLayout(new BorderLayout());
        this.addKeyListener(input);
        this.addMouseListener(input);

        this.setIgnoreRepaint(true);
        panel = new Container();

        if(!windowed) {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.setUndecorated(true);
        }else{
            this.setSize(1024,768);
        }
        panel.setPreferredSize(new Dimension(resolution.x, resolution.y));

        if(!windowed) {
            this.setSize(this.getMaximumSize());
        }

        this.add(panel, BorderLayout.CENTER);

        this.addWindowListener(new WindowME());

        EventQueue.invokeLater(() -> {
            this.setVisible(true);
        });
        buffer = new BufferedImage(resolution.x, resolution.y, BufferedImage.TYPE_3BYTE_BGR);

        try {
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

        }catch (IOException ignore){

            System.exit(0);
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
