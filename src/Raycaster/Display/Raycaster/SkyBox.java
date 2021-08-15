package Raycaster.Display.Raycaster;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SkyBox {

    public final int[][] bufferXY;
    public Game game;

    public SkyBox(BufferedImage img, Game game){

        final BufferedImage textureMain;
        Image texMainScaled = img.getScaledInstance(320,240,Image.SCALE_FAST);
        textureMain = new BufferedImage(320,240,BufferedImage.TYPE_3BYTE_BGR);

        Graphics grphxa = textureMain.getGraphics();
        grphxa.drawImage(texMainScaled,0,0,null);

        this.game = game;

        bufferXY = new int[240][320];


        for(int x=0;x<320;x++){
            for(int y=0;y<240;y++){
                int color = textureMain.getRGB(x,y);
                bufferXY[y][x] = color;
            }
        }

    }

    public int[] addSkybox(int[] foo){



        for(int x=0;x<318;x++){
           for(int y=5;y<235;y++){

               int index = (y*2)*640 + x*2;
                if(foo[index] == 0) {

                    foo[(y*2)*640 + x*2] = bufferXY[y][x];
                    foo[(y*2+1)*640 + x*2] = bufferXY[y][x];
                    foo[(y*2)*640 + x*2+1] = bufferXY[y][x];
                    foo[(y*2+1)*640 + x*2+1] = bufferXY[y][x];
                }
           }
       }

       return foo;
    }


}
