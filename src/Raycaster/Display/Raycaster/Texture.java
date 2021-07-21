package Raycaster.Display.Raycaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    public static int size=64;

    public int[][] bufferXY;
    public int[][] bufferXYS;

    public BufferedImage textureMain;
    public BufferedImage textureMainS;

    public Texture(BufferedImage img){

            Image texMainScaled = img.getScaledInstance(size,size,Image.SCALE_FAST);
            textureMain = new BufferedImage(size,size,BufferedImage.TYPE_3BYTE_BGR);

            Graphics grphxa = textureMain.getGraphics();
            grphxa.drawImage(texMainScaled,0,0,null);

            textureMainS = new BufferedImage(size,size,BufferedImage.TYPE_3BYTE_BGR);

            Graphics grphxb = textureMainS.getGraphics();
            grphxb.drawImage(texMainScaled,0,0,null);

            grphxb.setColor(new Color(0,0,0,64));
            grphxb.fillRect(0,0,Texture.size,Texture.size);

                bufferXY = new int[size][size];
                bufferXYS = new int[size][size];


                for(int x=0;x<size;x++){
                    for(int y=0;y<size;y++){
                        int color = textureMain.getRGB(x,y);
                        bufferXY[y][x] = color;

                        color = textureMainS.getRGB(x,y);
                        bufferXYS[y][x] = color;
                    }
                }



    }

}
