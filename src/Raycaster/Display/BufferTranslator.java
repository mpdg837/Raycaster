package Raycaster.Display;

import Raycaster.Project.Game;
import Raycaster.Raycaster;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class BufferTranslator {
    public static BufferedImage array_rasterToBuffer(int[][] img, Game game, BufferedImage bufferImg) {
        final int width = game.render.renderSize.x;
        final int height = game.render.renderSize.y;

        int n = 0;

        int[] pixels = new int[Raycaster.resolution.x * Raycaster.resolution.y];

        double scale = (double) height/(double)(height+game.camera.deltaY);
        int deltaM = game.camera.deltaY;

        if(game.camera.deltaY<0){
            deltaM = 0;
            scale = (double) height/(double)(height-game.camera.deltaY);
        }
        double yact = deltaM * scale;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {


                if(yact>=0 && yact<height) {
                    pixels[n] = img[(int)yact][x];
                }

                if(x==320 && y==240){
                    game.render.saveRaycaster.sprites.renderedSkyBoxInCenter= (pixels[n]==0) ;
                }
                n++;
            }
            yact += scale;
        }

        pixels = game.sky.addSkybox(pixels);

        final WritableRaster rast = bufferImg.getRaster();

        rast.setDataElements(0, 0, width, height, pixels);

        return bufferImg;
    }


}
