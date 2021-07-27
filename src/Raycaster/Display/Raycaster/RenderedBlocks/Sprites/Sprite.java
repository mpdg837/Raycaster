package Raycaster.Display.Raycaster.RenderedBlocks.Sprites;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

public class Sprite {
    final Raycasting ray;
    public Sprite(Raycasting ray){
        this.ray = ray;
    }



    public boolean drawBox(int nStep, Point punkt, double len,double angle) {

        boolean end = false;
        final double height = ((Raycasting.maxLen - len));




        if (height > 0) {

            // Wybór tekstury



                if(ray.partX > 0.45 && ray.partX < 0.55 && ray.partY > 0.45 && ray.partY < 0.55 ) {



                    // Wyznaczenie tekstury

                    final double zet = ray.tempCosB * len;
                    final int wallHeight = (int) (ray.renderHeightConstant* height / zet);

                    final Column column = new Column();


                        column.darker = false;
                        column.index = 32;
                        column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                        column.half = false;
                        column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                        column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                        column.spriteReduction = true;

                        column.setLen(len);
                            ray.sprites[column.len].add(column);




                    end = true;


                }

        }

        return end;
    }
}
