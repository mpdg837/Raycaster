package Raycaster.Display.Raycaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    public static int size=64;
    public BufferedImage[] texture = new BufferedImage[size];
    public BufferedImage[] textureS = new BufferedImage[size];

    public Texture(BufferedImage img){

            Image texMainScaled = img.getScaledInstance(size,size,Image.SCALE_FAST);

            for(int n=0;n<size;n++){
                texture[n] = new BufferedImage(1,size,BufferedImage.TYPE_3BYTE_BGR);
                Graphics grphx = texture[n].getGraphics();

                grphx.drawImage(texMainScaled,-n,0,null);

                textureS[n] = new BufferedImage(1,size,BufferedImage.TYPE_3BYTE_BGR);
                grphx = textureS[n].getGraphics();
                grphx.setColor(new Color(0,0,0,40));

                grphx.drawImage(texMainScaled,-n,0,null);
                grphx.drawLine(0,0,0,64);
            }
            System.out.println("Done");


    }

    public BufferedImage getColumn(int n){
        return texture[n];
    }

    public BufferedImage getColumnDarker(int n){
        return textureS[n];
    }
}
