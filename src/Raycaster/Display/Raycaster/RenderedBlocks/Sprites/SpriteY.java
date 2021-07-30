package Raycaster.Display.Raycaster.RenderedBlocks.Sprites;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

public class SpriteY {
    final Raycasting ray;
    public SpriteY(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int height,int wallHeight,int nStep, Point punkt, double len) {

        boolean end = false;



        if (height > 0) {

            // Wybór tekstury

            int delta = ray.game.mapa.deltaPos[(int)ray.analysePos.getX()][(int)ray.analysePos.getY()].y;


            if (ray.posX==32 && ray.posY>delta){


                // Wyznaczenie tekstury


                final Column column = new Column();

                column.darker =false;
                column.index = ray.posY-delta;
                column.rect = new Rectangle(punkt.x, punkt.y - (wallHeight >> 1), 1, wallHeight);
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
