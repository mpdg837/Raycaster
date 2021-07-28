package Raycaster.Display.Raycaster.RenderedBlocks.Boxes.Half;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

public class HalfBox {

    final Raycasting ray;
    public HalfBox(Raycasting ray){
        this.ray = ray;
    }



    public void drawBox(int nStep,Point punkt, double len) {


            final double height = ((Raycasting.maxLen - len));



            if (height > 0) {

                // Wybór tekstury


                boolean cien = false;


                int indexTex;
                if (ray.posY == 0 || ray.posY == 63) {
                    cien = true;
                    indexTex = ray.posX;
                } else {
                    indexTex = ray.posY;
                }


                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (ray.renderHeightConstant* height / zet);

                final Column column = new Column();

                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - (wallHeight >> 1), 1, wallHeight);
                column.half = true;
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.setLen(len);

                if (ray.posX == 0 || ray.posX == 63 || ray.posY == 0 || ray.posY == 63) {
                    if (nStep < 640) {
                        ray.sprites[column.len].add(column);
                        ray.rayHalfBlocked[nStep>>1] = true;
                    }
                }
            }


    }
}
