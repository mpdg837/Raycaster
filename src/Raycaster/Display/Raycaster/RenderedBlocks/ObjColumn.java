package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.util.ArrayList;

public class ObjColumn {

    final Raycasting ray;
    public ObjColumn(Raycasting ray){
        this.ray = ray;
    }

    public boolean drawBox(Point punkt, double len, ArrayList<Column> columns){


        final double height =((Raycasting.maxLen - len));


        boolean finish = false;
        if (height > 0) {

            // Wybór tekstury
            final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());




            if( partX >=0.375 && partX <= 0.625 && partY >=0.375 && partY <= 0.625) {

                boolean cien = false;

                final int posX = (int) (partX * 64);
                final int posY = (int) (partY * 64);

                int indexTex;
                if (posY == 24 || posY == 23 || posY == 40 || posY == 39) {
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

                column.half = false;
                column.setLen(len);
                columns.add(column);
                finish = true;
            }
        }

        return finish;
    }

}
