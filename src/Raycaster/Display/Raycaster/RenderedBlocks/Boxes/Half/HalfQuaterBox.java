package Raycaster.Display.Raycaster.RenderedBlocks.Boxes.Half;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

public class HalfQuaterBox {

    final Raycasting ray;
    public HalfQuaterBox(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int nStep, Point punkt, double len,int type) {

        boolean end = false;
        final double height = ((Raycasting.maxLen - len));



        if (height > 0) {

            // Wybór tekstury
            boolean decyzja = false;

            switch (type) {
                case 0:
                    decyzja = ray.posX <= 32;
                    break;
                case 1:
                    decyzja = ray.posX >= 32;
                    break;
                case 2:
                    decyzja = ray.posY <= 32;
                    break;
                case 3:
                    decyzja = ray.posY >= 32;
                    break;
            }

            if (decyzja) {
                boolean cien = false;


                int indexTex;
                if (ray.posY == 0 || ray.posY == 63 || ray.posY == 32) {
                    cien = true;
                    indexTex = ray.posX;
                } else {
                    indexTex = ray.posY;
                }


                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (ray.renderHeightConstant * height / zet);

                final Column column = new Column();

                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.half = true;
                column.objPosition = new Point((int) ray.analysePos.getX(), (int) ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(), ray.analysePos.getY());

                column.setLen(len);

                if (ray.posX == 0 || ray.posX == 32 || ray.posX == 63 || ray.posY == 0 || ray.posY == 32 || ray.posY == 63) {
                    if (nStep < 640) {
                        ray.sprites.get(column.len).add(column);
                        ray.rayHalfBlocked[nStep / 2] = true;
                    }
                    end = true;
                }


            }
        }

        return  end;
    }

}
