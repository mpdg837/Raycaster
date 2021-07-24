package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Box {

    final Raycasting ray;
    public Box(Raycasting ray){
        this.ray = ray;
    }

    public void drawBox(Point punkt, double len, ArrayList<Column> columns, int[][] foo){


        final double height =((Raycasting.maxLen - len));



        if (height > 0) {

            // Wybór tekstury
            final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());

            int indexTex;
            boolean cien = false;

            final int posX = (int) (partX * 64);
            final int posY = (int) (partY * 64);

            if( posY ==0 || posY == 63) {
                cien = true;
                indexTex = posX;
            }else{
                indexTex = posY;
            }

            // Wyznaczenie tekstury

            final double zet = ray.tempCosB * len;
            final int wallHeight = (int)(Raycasting.renderHeightConstant * height/zet);

            final Column column = new Column();

            column.darker = cien;
            column.index = indexTex;
            column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
            column.half = false;
            column.setLen(len);
            columns.add(column);

        }


    }
}
