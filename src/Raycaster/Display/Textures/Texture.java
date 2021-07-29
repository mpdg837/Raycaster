package Raycaster.Display.Textures;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture {

    public static final int black = -16777216;

    public static final int size=64;

    public final int[][] bufferXY;
    public final int[][] bufferXYS;
    public final int[][] bufferXYNL;
    public boolean transparency = false;

    public Texture(BufferedImage img,boolean transparency){


         BufferedImage textureMain;
         BufferedImage textureMainS;
         BufferedImage textureMainNL;

            this.transparency = transparency;
            Image texMainScaled = img.getScaledInstance(size,size,Image.SCALE_FAST);
            textureMain = new BufferedImage(size,size,BufferedImage.TYPE_3BYTE_BGR);

            Graphics grphxa = textureMain.getGraphics();
            grphxa.drawImage(texMainScaled,0,0,null);

            textureMainS = new BufferedImage(size,size,BufferedImage.TYPE_3BYTE_BGR);

            Graphics grphxb = textureMainS.getGraphics();
            grphxb.drawImage(texMainScaled,0,0,null);

            grphxb.setColor(new Color(0,0,0,64));
            grphxb.fillRect(0,0,Texture.size,Texture.size);

            textureMainNL = new BufferedImage(size,size,BufferedImage.TYPE_3BYTE_BGR);

            grphxb = textureMainNL.getGraphics();
            grphxb.drawImage(texMainScaled,0,0,null);

            grphxb.setColor(new Color(0,0,0,196));
            grphxb.fillRect(0,0,Texture.size,Texture.size);

                bufferXY = new int[size][size];
                bufferXYS = new int[size][size];
                bufferXYNL = new int[size][size];

                for(int x=0;x<size;x++){
                    for(int y=0;y<size;y++){
                        int color = textureMain.getRGB(x,y);
                        bufferXY[y][x] = color;

                        color = textureMainS.getRGB(x,y);
                        bufferXYS[y][x] = color;

                        color = textureMainNL.getRGB(x,y);
                        bufferXYNL[y][x] = color;
                    }
                }



    }

}
