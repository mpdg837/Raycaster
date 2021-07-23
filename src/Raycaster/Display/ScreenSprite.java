package Raycaster.Display;

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

    public void draw(Graphics g){
        g.drawImage(gun,ren.renderSize.x/2-98,ren.renderSize.y-196,196,196,null);
    }
}
