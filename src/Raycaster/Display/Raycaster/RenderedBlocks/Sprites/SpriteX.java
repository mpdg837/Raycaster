package Raycaster.Display.Raycaster.RenderedBlocks.Sprites;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

public class SpriteX {
    final Raycasting ray;
    public SpriteX(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int nStep, Point punkt, double len) {

        boolean end = false;
        final double height = ((Raycasting.maxLen - len));



        if (height > 0) {

            // Wybór tekstury



            if (ray.posY==32){



                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                final Column column = new Column();

                column.darker = true;
                column.index = ray.posX;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.half = false;
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.setLen(len);
                end = true;

                if (nStep < 640) {
                    ray.sprites.get(column.len).add(column);
                }
            }
        }

        return end;
    }
}
