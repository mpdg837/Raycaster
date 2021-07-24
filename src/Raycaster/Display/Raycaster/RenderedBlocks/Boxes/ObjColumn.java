package Raycaster.Display.Raycaster.RenderedBlocks.Boxes;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ObjColumn {

    final Raycasting ray;
    public ObjColumn(Raycasting ray){
        this.ray = ray;
    }

    public boolean drawBox(Point punkt, double len, ArrayList<Column> columns){


        final double height =((Raycasting.maxLen - len));


        boolean finish = false;
        if (height > 0) {

            // Wybór tekstury





            if( ray.partX >=0.375 && ray.partX <= 0.625 && ray.partY >=0.375 && ray.partY <= 0.625) {

                boolean cien = false;


                int indexTex;
                if (ray.posY == 24 || ray.posY == 23 || ray.posY == 40 || ray.posY == 39) {
                    cien = true;
                    indexTex = ray.posX;
                } else {
                    indexTex = ray.posY;

                }

                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

                final Column column = new Column();


                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.half = false;
                column.setLen(len);
                columns.add(column);
                finish = true;
            }
        }

        return finish;
    }

}
