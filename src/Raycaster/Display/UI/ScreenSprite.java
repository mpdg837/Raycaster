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





    public int bulletSize;
    public ScreenSprite(){

        gunRender =  new GunRender(this);
        try{

            final BufferedImage useItA = ImageIO.read(new File("use.png"));
            useIt = useItA.getScaledInstance(32,32,Image.SCALE_FAST);
        }catch (IOException ignore){

        }

    }



    public void draw(Graphics g,long timeStart){

       gunRender.moveGun(g);



        final long timeEnd = System.nanoTime();
        final int deltaRenderTime = (int)(timeEnd - timeStart)/1000000;

        String key= deltaRenderTime + "ms";

       System.out.println(key);

    }
}
