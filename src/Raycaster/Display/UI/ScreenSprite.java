package Raycaster.Display.UI;

import Raycaster.Display.Render;
import Raycaster.Raycaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenSprite {

    public BufferedImage gun;
    public Image scaled;
    public ScreenSprite(){

        try{
            gun = ImageIO.read(new File("gun.png"));
            scaled = gun.getScaledInstance(196,196,Image.SCALE_FAST);
        }catch (IOException err){
            gun = new BufferedImage(96,96,BufferedImage.TYPE_4BYTE_ABGR);
        }

    }

    public void draw(Graphics g,long timeStart){

        g.drawImage(scaled, Raycaster.resolution.x/2 -98,Raycaster.resolution.y-196,null);

        final long timeEnd = System.nanoTime();
        final int deltaRenderTime = (int)(timeEnd - timeStart)/1000000;

        String key= deltaRenderTime + "ms";

       System.out.println(key);

    }
}
