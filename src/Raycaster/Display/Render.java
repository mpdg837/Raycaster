package Raycaster.Display;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Raycaster;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;
import Raycaster.Project.Game;

public class Render extends TimerTask {

    private static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static int deltaTime =10;

    public final Point renderSize;

    private final Graphics2D drawInside;

    public final ScreenSprite sprites;
    public final Raycaster saveRaycaster;
    private boolean start;
    private final Game game;


    public Render(boolean start, Raycaster canvas, Game game){

        renderSize = canvas.resolution;
        sprites = new ScreenSprite(this);

        drawInside = (Graphics2D) canvas.panel.getGraphics();

        saveRaycaster = canvas;

        this.start = start;
        if(game == null) {
            this.game = new Game(this,canvas.input);
            device.setFullScreenWindow(saveRaycaster);
        }else{
            this.game = game;
        }
    }

    public void init(){
        boolean ok = true;
        while(ok){
            if(game != null){
                game.start();
                ok=false;
            }

        }

        BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_4BYTE_ABGR);
        Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "null");

        saveRaycaster.setCursor(cur);
        start = true;
    }
    @Override
    public void run() {
        try{
            if( drawInside !=null) {
                Raycasting rayMaker = new Raycasting(game);
                rayMaker.draw();

                saveRaycaster.requestFocus();

                if (!start) {
                    init();
                } else {
                    game.update();
                }

                game.input.resetKey();

                sprites.draw(rayMaker.bufferImg.getGraphics());

                drawInside.drawImage(rayMaker.bufferImg,0,0,saveRaycaster.panel.getWidth(),saveRaycaster.panel.getHeight(),saveRaycaster);


            }
        }catch (ConcurrentModificationException ignore){}

        Timer tim = new Timer();
        tim.schedule(new Render(start,saveRaycaster,game),Render.deltaTime);

    }
}
