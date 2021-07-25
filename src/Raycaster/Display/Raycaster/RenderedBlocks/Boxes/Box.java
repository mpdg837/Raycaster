package Raycaster.Display.Raycaster.RenderedBlocks.Boxes;

import Raycaster.Display.Raycaster.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

import java.util.ArrayList;

public class Box {

    final Raycasting ray;
    public Box(Raycasting ray){
        this.ray = ray;
    }

    public boolean pseudoLos(int punkt){

        return (-1)*(punkt-5)*(punkt-15)*(punkt-2*20)*(punkt-30)*(punkt-45) >0;
    }
    public boolean drawBox(Point punkt, double len, ArrayList<Column> columns, int[][] foo){

        boolean ok = false;
        final double height =((Raycasting.maxLen - len));

        final int hp = ray.game.mapa.HP[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()];

        if (height > 0) {


            boolean destroy = false;
            boolean decyzja = true;

            boolean in = (ray.posY >= hp && ray.posY <= 64 - hp && ray.posX >= hp && ray.posX <= 64 - hp);
            if(hp>0) {
                if (ray.posY <= hp || ray.posY >= 64-hp) {
                    decyzja = (pseudoLos(ray.posX)) || in;
                    destroy = in;
                } else {
                    decyzja = (pseudoLos(ray.posY)) || in;
                    destroy = in;
                }
            }

            if(decyzja) {
                // Wybór tekstury

                int indexTex;
                boolean cien = false;

                    if (ray.posY == 0 || ray.posY == 63 || ray.posY == hp || ray.posY == 64 - hp) {
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

                column.destroyed = destroy;

                column.setLen(len);
                columns.add(column);
                ok = true;
            }
        }

        return ok;
    }
}
