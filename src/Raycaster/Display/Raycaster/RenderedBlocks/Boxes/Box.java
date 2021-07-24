package Raycaster.Display.Raycaster.RenderedBlocks.Boxes;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Box {

    final Raycasting ray;
    public Box(Raycasting ray){
        this.ray = ray;
    }

    public void drawBox(Point punkt, double len, ArrayList<Column> columns, int[][] foo){


        final double height =((Raycasting.maxLen - len));



        if (height > 0) {

            // Wybór tekstury

            int indexTex;
            boolean cien = false;


            if( ray.posY ==0 || ray.posY == 63) {
                cien = true;
                indexTex = ray.posX;
            }else{
                indexTex = ray.posY;
            }

            // Wyznaczenie tekstury

            final double zet = ray.tempCosB * len;
            final int wallHeight = (int)(Raycasting.renderHeightConstant * height/zet);

            final Column column = new Column();

            column.darker = cien;
            column.index = indexTex;
            column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
            column.half = false;
            column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
            column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

            column.setLen(len);
            columns.add(column);

        }


    }
}
