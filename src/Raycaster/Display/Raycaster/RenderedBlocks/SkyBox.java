package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SkyBox {

    public static Point size = new Point(320,240);
    public int[][] bufferXY;

    public BufferedImage textureMain;

    public SkyBox(BufferedImage img){

        Image texMainScaled = img.getScaledInstance(size.x,size.y,Image.SCALE_FAST);
        textureMain = new BufferedImage(size.x,size.y,BufferedImage.TYPE_3BYTE_BGR);

        Graphics grphxa = textureMain.getGraphics();
        grphxa.drawImage(texMainScaled,0,0,null);



        bufferXY = new int[size.y][size.x];



        for(int x=0;x<size.x;x++){
            for(int y=0;y<size.y;y++){

                    int color = textureMain.getRGB(x, y);
                    bufferXY[y][x] = color;

            }
        }



    }

    public int[][] draw(Game game,int[][] foo){

            for(int x=0;x<320;x++){
                for(int y=0;y<240;y++) {
                    if (foo[y][x] == 0) {
                        foo[y][x] = bufferXY[y][x];
                    }
                }
            }


        return foo;
    }
}
