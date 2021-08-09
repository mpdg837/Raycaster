package Raycaster.Display.UI;

import Raycaster.Display.Render;
import Raycaster.Raycaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenSprite {

    public final GunRender gunRender;

    public Image useIt;
    public boolean canUse;

    public Image mask;
    public boolean hurt;



    public int bulletSize;


    public ScreenSprite(){

        gunRender =  new GunRender(this);
        try{

            final BufferedImage useItA = ImageIO.read(new File("use.png"));

            useIt = useItA.getScaledInstance(32,32,Image.SCALE_FAST);

            BufferedImage maskRead = ImageIO.read(new File("mask.png"));
            mask = maskRead.getScaledInstance(640,48,Image.SCALE_FAST);
        }catch (IOException ignore){

        }

    }



    public void draw(Graphics g,long timeStart) {

        gunRender.moveGun(g);

        if (hurt) {
            Color color = new Color(255, 0, 0, 128);
            g.setColor(color);
            g.fillRect(0, 0, Raycaster.resolution.x, Raycaster.resolution.y);
        }

        g.drawImage(mask,0,432,null);
    }
}
