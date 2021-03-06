package Raycaster.Display.Raycaster.RenderedBlocks.Sprites;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

public class SpriteXY {
    final Raycasting ray;
    public SpriteXY(Raycasting ray){
        this.ray = ray;
    }



    public void drawBox(int height,int wallHeight,int nStep, Point punkt, double len) {


        if (height > 0) {

            // Wybór tekstury



            if (ray.posX==32 || ray.posY == 32){

                int indexTex;
                if (ray.posX==32){
                    indexTex = ray.posY;
                }else{
                    indexTex = ray.posX;;
                }


                // Wyznaczenie tekstury

                final Column column = new Column();

                column.darker = true;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - (wallHeight >> 1), 1, wallHeight);
                column.half = false;
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.setLen(len);

                if (nStep < 640) {
                    ray.sprites[column.len].add(column);
                }
            }
        }

    }
}
