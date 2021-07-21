package Raycaster;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Render;
import Raycaster.Input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;

public class Raycaster extends Frame {

    public final BufferedImage buffer;
    public final Panel panel;
    public static Point resolution= new Point(320,240);

    Raycasting rayMaker ;

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

        this.setSize(resolution.x,resolution.y+32);
        this.add(panel,BorderLayout.CENTER);

        this.addWindowListener(new Window());

        EventQueue.invokeLater(()->{this.setVisible(true);});
        buffer = new BufferedImage(resolution.x,resolution.y,BufferedImage.TYPE_3BYTE_BGR);

        java.util.Timer tim = new Timer();
        tim.schedule(new Render(false,this,null),Render.deltaTime);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();

        chooseBestDisplayMode(device);

    }

    private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
                        && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
                        && modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()) {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }

    public static void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }

    public static void main(String[] args){
        Raycaster wsw = new Raycaster();
    }
}
