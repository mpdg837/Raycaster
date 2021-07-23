package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.util.ArrayList;

public class Sprite {
    Raycasting ray;
    public Sprite(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int nStep, Point punkt, double len, double angle, ArrayList<Column> columns, int[][] foo) {

        boolean end = false;
        double height = ((Raycasting.maxLen - len));
        int indexTex = 0;


        if (height > 0) {

            // Wybór tekstury
            double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


            boolean cien = false;

            int posX = (int) (partX * 64);
            int posY = (int) (partY * 64);



                if(posX ==  32 || posY == 32) {
                    indexTex = posX;


                    // Wyznaczenie tekstury

                    double zet = ray.tempCosB * len;
                    int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                    Column column = new Column();

                    Point lpunkt = new Point(punkt.x,punkt.y);

                        column.darker = cien;
                        column.index = indexTex;
                        column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                        column.half = false;


                            ray.sprites.get(nStep/2).add(column);




                    end = true;


                }

        }

        return end;
    }
}
