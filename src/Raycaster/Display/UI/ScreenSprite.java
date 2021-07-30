package Raycaster.Display.UI;

import Raycaster.Display.Render;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenSprite {

    private final Render ren;
    public BufferedImage gun;

    public ScreenSprite(Render ren){
        this.ren = ren;
        try{
            gun = ImageIO.read(new File("gun.png"));
        }catch (IOException err){
            gun = new BufferedImage(96,96,BufferedImage.TYPE_4BYTE_ABGR);
        }

    }

    public void draw(Graphics g,long timeStart){
        Image scaled = gun.getScaledInstance(196,196,Image.SCALE_FAST);
        g.drawImage(scaled,ren.renderSize.x/2-98,ren.renderSize.y-196,null);

        final long timeEnd = System.nanoTime();
        final int deltaRenderTime = (int)(timeEnd - timeStart)/1000000;

        String key= deltaRenderTime + "ms";

       System.out.println(key);

    }
}
