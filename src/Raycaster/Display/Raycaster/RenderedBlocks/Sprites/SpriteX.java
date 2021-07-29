package Raycaster.Display.Raycaster.RenderedBlocks.Sprites;

import Raycaster.Display.Raycaster.RenderTools.Column;
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


        if (ray.height > 0) {

            // Wybór tekstury



            if (ray.posY==32){



                // Wyznaczenie tekstury


                final Column column = new Column();

                column.darker = true;
                column.index = ray.posX;
                column.rect = new Rectangle(punkt.x, punkt.y - (ray.wallHeight >> 1), 1, ray.wallHeight);
                column.half = false;
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.setLen(len);
                end = true;

                if (nStep < 640) {
                    ray.sprites[column.len].add(column);
                }
            }
        }

        return end;
    }
}
