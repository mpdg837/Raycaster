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
    public boolean renderedSkyBoxInCenter;

    public Image useIt;
    public boolean canUse;

    public UIInform mask;
    public boolean hurt;


    public int HP;
    public String infoAmmo;

    public int bulletSize;


    public ScreenSprite(){

        gunRender =  new GunRender(this);
        mask = new UIInform(this);

        try{

            final BufferedImage useItA = ImageIO.read(new File("use.png"));

            useIt = useItA.getScaledInstance(32,32,Image.SCALE_FAST);



        }catch (IOException ignore){

        }

    }


    int timDead = 0;

    public void draw(Graphics g,long timeStart) {

        gunRender.moveGun(g);

        if(HP<0){
            timDead ++;

            int intense = 0;

            if(timDead*10 < 128){
                intense = (timDead*10);
            }else{
                intense = 128;
            }

            final Color color = new Color(255, 0, 0, intense);
            g.setColor(color);
            g.fillRect(0, 0, Raycaster.resolution.x, Raycaster.resolution.y);
        }
        if (hurt) {
            final Color color = new Color(255, 0, 0, 128);
            g.setColor(color);
            g.fillRect(0, 0, Raycaster.resolution.x, Raycaster.resolution.y-48);

        }


       mask.renderUI(g);


    }
}
