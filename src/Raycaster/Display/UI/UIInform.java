package Raycaster.Display.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIInform {

    private Image mask;
    private ScreenSprite sprite;
    public boolean pause;

    public Image scaledMenu;

    public int timStart = 0;

    public UIInform(ScreenSprite sprite){
        this.sprite = sprite;

        try{

            BufferedImage readMenu = ImageIO.read(new File("menu.png"));
            scaledMenu = readMenu.getScaledInstance(640,480,Image.SCALE_FAST);

            BufferedImage maskRead = ImageIO.read(new File("mask.png"));
            mask = maskRead.getScaledInstance(640,48,Image.SCALE_FAST);
        }catch (IOException ignore){}

    }
    public void renderUI(Graphics g) {
        g.drawImage(mask, 0, 432, null);

        g.setFont(new Font("Arial", Font.BOLD, 44));
        g.setColor(Color.RED);

        if (sprite.HP > 0) g.drawString(sprite.HP + "%", 50, 470);
        else g.drawString("0%", 50, 470);

        g.drawString(sprite.infoAmmo, 550, 470);

        if (pause) {
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.WHITE);

            g.drawImage(scaledMenu,0,0,null);
            g.drawString("Pause", 50, 50);


        }

        if(timStart<30){
            g.setColor(new Color(0,0,0,(30-timStart)*8));
            g.fillRect(0,0,640,480);
        }
    }
}
