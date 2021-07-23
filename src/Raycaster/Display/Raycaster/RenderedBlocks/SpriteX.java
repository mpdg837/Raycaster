package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;

public class SpriteX {
    final Raycasting ray;
    public SpriteX(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int nStep, Point punkt, double len) {

        boolean end = false;
        final double height = ((Raycasting.maxLen - len));



        if (height > 0) {

            // Wybór tekstury

            final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());
            final int posY = (int) (partY * 64);

            if (posY==32){

                final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
                final int posX = (int) (partX * 64);

                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                final Column column = new Column();

                column.darker = true;
                column.index = posX;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.half = false;

                end = true;

                if (nStep < 640) {
                    ray.sprites.get((nStep / 2)).add(column);
                }
            }
        }

        return end;
    }
}
