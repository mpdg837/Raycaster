package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;

public class HalfBox {

    final Raycasting ray;
    public HalfBox(Raycasting ray){
        this.ray = ray;
    }



    public void drawBox(int nStep,Point punkt, double len) {


            final double height = ((Raycasting.maxLen - len));



            if (height > 0) {

                // Wybór tekstury
                final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
                final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


                boolean cien = false;

                final int posX = (int) (partX * 64);
                final int posY = (int) (partY * 64);

                int indexTex;
                if (posY == 0 || posY == 63) {
                    cien = true;
                    indexTex = posX;
                } else {
                    indexTex = posY;
                }


                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                final Column column = new Column();

                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.half = true;
                if (posX == 0 || posX == 63 || posY == 0 || posY == 63) {
                    if (nStep < 640) {
                        ray.sprites.get( (nStep / 2)).add(column);
                    }
                }
            }


    }
}
