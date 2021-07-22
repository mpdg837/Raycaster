package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Box {

    Raycasting ray;
    public Box(Raycasting ray){
        this.ray = ray;
    }

    public void drawBox(Point punkt, double len, double angle, ArrayList<Column> columns, int[][] foo){


        double height =((Raycasting.maxLen - len));
        int indexTex = 0;


        if (height > 0) {

            // Wybór tekstury
            double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


            boolean cien = partY<0.01 || partY>0.99;

            int posX = (int) (partX * 64);
            int posY = (int) (partY * 64);

            if( posY ==0 || posY == 63) {

                indexTex = posX;
            }else{
                indexTex = posY;
            }

            // Wyznaczenie tekstury

            double zet = ray.tempCosB * len;
            int wallHeight = (int)(Raycasting.renderHeightConstant * height/zet);

            Column column = new Column();

            column.darker = cien;
            column.index = indexTex;
            column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);

            columns.add(column);

        }


    }
}
