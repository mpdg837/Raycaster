package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.util.ArrayList;

public class Triangle {

    final Raycasting ray;
    public Triangle(Raycasting ray){
        this.ray = ray;
    }

    public boolean drawBox(Point punkt, double len, ArrayList<Column> columns,int type){


        final double height =((Raycasting.maxLen - len));


        boolean finish = false;
        if (height > 0) {

            // Wybór tekstury
            final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


            boolean decyzja = false;

            switch (type) {
                case 0: decyzja = partX > partY;
                    break;
                case 1: decyzja = partX < partY;
                    break;
                case 2: decyzja = partY > 1-partX;
                    break;
                case 3: decyzja = partY < 1-partX;
                    break;
            }
            if( decyzja) {

                boolean cien = false;

                final int posX = (int) (partX * 64);
                final int posY = (int) (partY * 64);

                int indexTex=0;

                switch (type){
                    case 0:

                        if (posY == 0 || posX == 0) {
                            cien = true;
                            indexTex = posX;
                        } else {
                            indexTex = posY;

                        }
                        break;

                    case 1:
                        if (posY == 63 || posX == 63) {
                            cien = true;
                            indexTex = posX;
                        } else {
                            indexTex = posY;

                        }
                        break;
                    case 2:
                        if (posY == 63 || posX == 0) {
                            cien = true;
                            indexTex = posX;
                        } else {
                            indexTex = posY;

                        }
                        break;
                    case 3:
                        if (posY == 0 || posX == 63) {
                            cien = true;
                            indexTex = posX;
                        } else {
                            indexTex = posY;

                        }
                        break;
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
