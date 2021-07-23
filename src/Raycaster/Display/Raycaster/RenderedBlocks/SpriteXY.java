package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.util.ArrayList;

public class SpriteXY {
    Raycasting ray;
    public SpriteXY(Raycasting ray){
        this.ray = ray;
    }



    public void drawBox(int nStep, Point punkt, double len, double angle, ArrayList<Column> columns, int[][] foo) {

        double height = ((Raycasting.maxLen - len));
        int indexTex = 0;


        if (height > 0) {

            // Wybór tekstury
            double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


            boolean cien = false;

            int posX = (int) (partX * 64);
            int posY = (int) (partY * 64);

            if (posX==32 || posY == 32){
                if (posX==32) {

                    indexTex = posY;
                } else {
                    indexTex = posX;
                }



                // Wyznaczenie tekstury

                double zet = ray.tempCosB * len;
                int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                Column column = new Column();

                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.half = false;

                if (nStep < 640) {
                    ray.sprites.get((int) (nStep / 2)).add(column);
                }
            }
        }

    }
}
