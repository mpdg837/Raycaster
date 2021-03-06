package Raycaster.Display;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.UI.ScreenSprite;
import Raycaster.Raycaster;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;
import Raycaster.Project.Game;

public class Render extends TimerTask {

    public final static int deltaTime =34;

    public final Point renderSize;

    private final Graphics2D drawInside;

    public final Raycaster saveRaycaster;
    private boolean start;
    private Game game;

    public int deltaRenderTime;

    public Render(boolean start, Raycaster canvas, Game game){

        renderSize = Raycaster.resolution;


        drawInside = (Graphics2D) canvas.getGraphics();

        saveRaycaster = canvas;

        this.start = start;

        if(game == null) {

            this.game = canvas.game;
            this.game.render = this;



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

            if( drawInside !=null) {



                if (!start) {
                    init();
                } else {

                    final long timeStart = System.nanoTime();


                    saveRaycaster.rayMaker.draw();

                    saveRaycaster.sprites.draw(saveRaycaster.rayMaker.bufferImg.getGraphics(),timeStart);

                    drawInside.drawImage(saveRaycaster.rayMaker.bufferImg, 0, 0 , saveRaycaster);

                    final long timeEnd = System.nanoTime();
                    this.deltaRenderTime = (int)(timeEnd - timeStart)/1000000;

                    String key = deltaRenderTime + "ms";

                    System.out.println(key);

                    if(this.deltaRenderTime > deltaTime){
                        this.deltaRenderTime = deltaTime;
                    }



                }




            }


        saveRaycaster.tim1.schedule(new Render(start, saveRaycaster, game), Render.deltaTime - this.deltaRenderTime);

    }
}
