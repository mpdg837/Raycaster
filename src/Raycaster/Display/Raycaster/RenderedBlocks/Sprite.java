package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;

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
            final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());



                if(partX > 0.49 && partX < 0.51 && partY > 0.49 && partY < 0.51 ) {



                    // Wyznaczenie tekstury

                    final double zet = ray.tempCosB * len;
                    final int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                    final Column column = new Column();


                        column.darker = false;
                        column.index = 32;
                        column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                        column.half = false;

                        column.spriteReduction = true;

                        column.setLen(len);
                            ray.sprites.get(column.len).add(column);




                    end = true;


                }

        }

        return end;
    }
}
