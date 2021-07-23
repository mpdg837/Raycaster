package Raycaster;

import Raycaster.Display.Render;
import Raycaster.Player.Input.Input;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;

public class Raycaster extends Frame {

    public final BufferedImage buffer;
    public final Panel panel;
    public final static Point resolution= new Point(640,480);

    public final Input input = new Input();

    private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[]{
            new DisplayMode(640, 480, 32, 0),
            new DisplayMode(640, 480, 16, 0),
            new DisplayMode(640, 480, 8, 0)};

    public Raycaster(){

        super("Window");

        this.setLayout(new BorderLayout());
        this.addKeyListener(input);
        this.addMouseListener(input);

        panel = new Panel();
        panel.setPreferredSize(new Dimension(resolution.x,resolution.y));

        this.setSize(resolution.x,resolution.y);
        this.add(panel,BorderLayout.CENTER);

        this.addWindowListener(new WindowME());

        EventQueue.invokeLater(()->{this.setVisible(true);});
        buffer = new BufferedImage(resolution.x,resolution.y,BufferedImage.TYPE_3BYTE_BGR);

        java.util.Timer tim = new Timer();
        tim.schedule(new Render(false,this,null),Render.deltaTime);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();

        chooseBestDisplayMode(device);

    }

    private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (DisplayMode bestDisplayMode : BEST_DISPLAY_MODES) {
            final DisplayMode[] modes = device.getDisplayModes();
            for (DisplayMode mode : modes) {
                if (mode.getWidth() == bestDisplayMode.getWidth()
                        && mode.getHeight() == bestDisplayMode.getHeight()
                        && mode.getBitDepth() == bestDisplayMode.getBitDepth()) {
                    return bestDisplayMode;
                }
            }
        }
        return null;
    }

    public static void chooseBestDisplayMode(GraphicsDevice device) {
        final DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }

    public static void main(String[] args){
        Raycaster wsw = new Raycaster();
    }
}
