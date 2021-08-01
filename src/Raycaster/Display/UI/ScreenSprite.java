package Raycaster.Display.UI;

import Raycaster.Raycaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenSprite {

    public Image[] scaled = new Image[4];
    public Image[] zscaled = new Image[4];
    public Image[] rscaled = new Image[4];

    public Image useIt;
    public boolean canUse;

    public int moveGun;
    public double zoom = 1;

    public int shootAnimate = 0;
    public int reloadAnimate = 0;
    public ScreenSprite(){

        try{
            for(int n=0;n<scaled.length;n++) {
                final BufferedImage gun = ImageIO.read(new File("gun"+n+".png"));
                scaled[n] = gun.getScaledInstance(196, 196, Image.SCALE_FAST);

                final BufferedImage zgun = ImageIO.read(new File("zgun"+n+".png"));
                zscaled[n] = zgun.getScaledInstance(196, 196, Image.SCALE_FAST);

                final BufferedImage rgun = ImageIO.read(new File("rgun"+n+".png"));
                rscaled[n] = rgun.getScaledInstance(196, 196, Image.SCALE_FAST);
            }
            final BufferedImage useItA = ImageIO.read(new File("use.png"));
            useIt = useItA.getScaledInstance(32,32,Image.SCALE_FAST);
        }catch (IOException ignore){

        }

    }

    int timReload = 0;

    private void moveGun(Graphics g) {
        int posX = 0;
        int posY = 0;

        if (moveGun < 32) {
            posX = moveGun;
            posY = posX;
        } else if (moveGun < 64) {
            posX = 64 - moveGun;
            posY = posX;
        } else if (moveGun < 98) {
            posX = 64 - moveGun;
            posY = -posX;
        } else if (moveGun < 128) {
            posX = moveGun - 128;
            posY = -posX;
        } else {
            moveGun = 0;
        }

        final int half = (int) (90 * zoom);
        final int full = (int) (zoom * 196);
        final int zoomAdd = (int) ((zoom - 1) * 30);

        if (canUse) {
            g.drawImage(useIt, Raycaster.resolution.x / 2 - 16, Raycaster.resolution.y / 4 - 16, null);
        }


        Image actScaled;
        if(reloadAnimate>0){
            actScaled = rscaled[reloadAnimate].getScaledInstance(full, full, Image.SCALE_FAST);
        }else
        if(zoom>1){
            actScaled = zscaled[shootAnimate].getScaledInstance(full, full, Image.SCALE_FAST);
        }else{
            actScaled = scaled[shootAnimate].getScaledInstance(full, full, Image.SCALE_FAST);
        }

        if(reloadAnimate>0){
            timReload++;

            if(timReload==3) {
                timReload = 0;
                reloadAnimate++;

                if (reloadAnimate >= scaled.length) {
                    reloadAnimate = 0;
                }
            }
        }else
        if(shootAnimate>0){
            shootAnimate++;

            if(shootAnimate>=scaled.length){
                shootAnimate = 0;
            }
        }

        if (zoom > 1) {
            g.drawImage(actScaled, Raycaster.resolution.x / 2 - half + posX, Raycaster.resolution.y - 196 + posY - zoomAdd, null);
        }else{
            g.drawImage(actScaled, Raycaster.resolution.x / 2 - half +100+ posX, Raycaster.resolution.y - 196 + posY - zoomAdd, null);
        }
    }

    public void draw(Graphics g,long timeStart){

       moveGun(g);

        final long timeEnd = System.nanoTime();
        final int deltaRenderTime = (int)(timeEnd - timeStart)/1000000;

        String key= deltaRenderTime + "ms";

       System.out.println(key);

    }
}
