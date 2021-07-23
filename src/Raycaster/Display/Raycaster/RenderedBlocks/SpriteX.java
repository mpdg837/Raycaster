package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.util.ArrayList;

public class SpriteX {
    final Raycasting ray;
    public SpriteX(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int nStep, Point punkt, double len) {

        boolean end = false;
        final double height = ((Raycasting.maxLen - len));
        int indexTex;


        if (height > 0) {

            // Wybór tekstury
            final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


            boolean cien = true;

            final int posX = (int) (partX * 64);
            final int posY = (int) (partY * 64);

            if (posY==32){
                indexTex = posX;



                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                final Column column = new Column();

                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.half = false;

                end = true;

                if (nStep < 640) {
                    ray.sprites.get((int) (nStep / 2)).add(column);
                }
            }
        }

        return end;
    }
}
