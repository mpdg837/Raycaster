package Raycaster.Display.UI;

import Raycaster.Raycaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GunRender {

    public boolean blockMe;

    private final ScreenSprite sprite;
    private BufferedImage guncenter;

    public Image[] scaled = new Image[4];
    public Image[] zscaled = new Image[4];
    public Image[] rscaled = new Image[4];

    public Image[] rdes = new Image[4];

    public BufferedImage bullet;

    public int moveGun;
    public double zoom = 1;

    public int shootAnimate = 0;
    public int reloadAnimate = 0;

    public GunRender(ScreenSprite sprite){
        this.sprite = sprite;

        try {

            guncenter = ImageIO.read(new File("guncenter.png"));

            for (int n = 0; n < scaled.length; n++) {
                final BufferedImage gun = ImageIO.read(new File("gun" + n + ".png"));
                scaled[n] = gun.getScaledInstance(196, 196, Image.SCALE_FAST);

                final BufferedImage zgun = ImageIO.read(new File("zgun" + n + ".png"));
                zscaled[n] = zgun.getScaledInstance(196, 196, Image.SCALE_FAST);

                final BufferedImage rgun = ImageIO.read(new File("rgun" + n + ".png"));
                rscaled[n] = rgun.getScaledInstance(196, 196, Image.SCALE_FAST);

                final BufferedImage des = ImageIO.read(new File("des" + n + ".png"));
                rdes[n] = des.getScaledInstance(196, 196, Image.SCALE_FAST);
            }

            bullet = ImageIO.read(new File("bullet.png"));
        }catch (IOException ignore){};
    }
    int timReload = 0;

    public void moveGun(Graphics g) {

        g.drawImage(guncenter, 320-guncenter.getWidth()/2,249-guncenter.getHeight()/2, null);

        if(sprite.bulletSize>0 && !blockMe) {

            int xPos = 320 - (sprite.bulletSize >> 1);
            int yPos = 240 - (sprite.bulletSize >> 1);

            if(!sprite.renderedSkyBoxInCenter){
                g.drawImage(rdes[shootAnimate], xPos, yPos, sprite.bulletSize, sprite.bulletSize, null);
            }

        }

        if(shootAnimate>0) {
            int bulletNSize = (4 - shootAnimate) * 10;

            if (zoom > 1) {
                g.drawImage(bullet,320 - (bulletNSize >> 1), 240 - (bulletNSize >> 1) + 3 * bulletNSize, bulletNSize, bulletNSize,null);
            } else {
                g.drawImage(bullet,320 - (bulletNSize >> 1) + 2 * bulletNSize, 240 - (bulletNSize >> 1) + 3 * bulletNSize, bulletNSize, bulletNSize,null);
            }
        }



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

        if(zoom<1){
            zoom = 1;
        }

        final int half = (int) (90 * zoom);
        final int full = (int) (zoom * 196);
        final int zoomAdd = (int) ((zoom - 1) * 30);

        if (sprite.canUse) {

            g.drawImage(sprite.useIt, Raycaster.resolution.x / 2 - 16, Raycaster.resolution.y / 4 - 16, null);

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

        if(sprite.HP>0){
            if (zoom > 1) {
                g.drawImage(actScaled, Raycaster.resolution.x / 2 - half + posX, Raycaster.resolution.y - 196 + posY - zoomAdd, null);
            }else{
                g.drawImage(actScaled, Raycaster.resolution.x / 2 - half +100+ posX, Raycaster.resolution.y - 196 + posY - zoomAdd, null);
            }
        }else{
            g.drawImage(actScaled, Raycaster.resolution.x / 2 - half +100+ posX, Raycaster.resolution.y - 196 + posY - zoomAdd -sprite.HP , null);
        }



    }

}
