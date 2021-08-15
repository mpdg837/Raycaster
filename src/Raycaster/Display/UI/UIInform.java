package Raycaster.Display.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIInform {

    private Image mask;
    private ScreenSprite sprite;
    public UIInform(ScreenSprite sprite){
        this.sprite = sprite;

        try{
            BufferedImage maskRead = ImageIO.read(new File("mask.png"));
            mask = maskRead.getScaledInstance(640,48,Image.SCALE_FAST);
        }catch (IOException ignore){}

    }
    public void renderUI(Graphics g){
        g.drawImage(mask,0,432,null);

        g.setFont(new Font("Arial",Font.BOLD,44));
        g.setColor(Color.RED);

        if(sprite.HP>0) g.drawString(sprite.HP+"%",50,470);
        else g.drawString("0%",50,470);

        g.drawString(sprite.infoAmmo,550,470);
    }
}
