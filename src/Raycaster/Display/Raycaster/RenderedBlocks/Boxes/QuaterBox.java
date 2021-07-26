package Raycaster.Display.Raycaster.RenderedBlocks.Boxes;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class QuaterBox {
    final Raycasting ray;
    public QuaterBox(Raycasting ray){
        this.ray = ray;
    }

    public boolean drawBox(Point punkt, double len, ArrayList<Column> columns, int[][] foo,int type) {

        boolean end = false;
        final double height = ((Raycasting.maxLen - len));


        if (height > 0) {

            boolean decyzja = false;

            switch (type){
                case 0:
                    decyzja = ray.posX<=32;
                    break;
                case 1:
                    decyzja = ray.posX>=32;
                    break;
                case 2:
                    decyzja = ray.posY<=32;
                    break;
                case 3:
                    decyzja = ray.posY>=32;
                    break;
            }

            if(decyzja) {
                // Wybór tekstury

                int indexTex;
                boolean cien = false;

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
                column.half = false;
                column.objPosition = new Point((int) ray.analysePos.getX(), (int) ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(), ray.analysePos.getY());

                column.setLen(len);
                columns.add(column);
                return true;
            }
        }
        return false;
    }
}
