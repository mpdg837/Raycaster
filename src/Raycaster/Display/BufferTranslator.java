package Raycaster.Display;

import Raycaster.Project.Game;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class BufferTranslator {
    public static BufferedImage array_rasterToBuffer(int[][] img, Game game, BufferedImage bufferImg) {
        final int width = game.render.renderSize.x;
        final int height = game.render.renderSize.y;

        int[] pixels = new int[width * height];

        int n = 0;


        double scale = (double) height/(double)(height+game.camera.deltaY);
        int deltaM = game.camera.deltaY;

        if(game.camera.deltaY<0){
            deltaM = 0;
            scale = (double) height/(double)(height-game.camera.deltaY);
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int ny = (int)((y+deltaM)*scale);

                if(ny>=0 && ny<height) {
                    pixels[n] = img[ny][x];
                }
                n++;
            }
        }

        pixels = game.sky.addSkybox(pixels);

        final WritableRaster rast = bufferImg.getRaster();

        rast.setDataElements(0, 0, width, height, pixels);

        return bufferImg;
    }


}
