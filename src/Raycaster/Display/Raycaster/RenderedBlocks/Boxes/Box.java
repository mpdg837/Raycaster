package Raycaster.Display.Raycaster.RenderedBlocks.Boxes;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;

import java.util.ArrayList;

public class Box {

    final Raycasting ray;
    public Box(Raycasting ray){
        this.ray = ray;
    }


    public boolean drawBox(int height,int wallHeight,Point punkt, double len, ArrayList<Column> columns, int[][] foo){

        boolean ok = false;

        if (height > 0) {


            boolean destroy = false;
            boolean decyzja = true;
            byte hp = 0;
            final byte rhp = ray.game.mapa.HP[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()];

            if(rhp>1){
                hp = 2;
            }

            boolean in = (ray.posY >= hp && ray.posY <= 64 - hp && ray.posX >= hp && ray.posX <= 64 - hp);
            if(hp>1) {
                if (ray.posY <= hp || ray.posY >= 64-hp) {
                    decyzja = (ray.game.damage.pseudoLos(ray.posX,rhp)) || in;
                    destroy = in;
                } else {
                    decyzja = (ray.game.damage.pseudoLos(ray.posY,rhp)) || in;
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


                final Column column = new Column();

                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - (wallHeight >> 1), 1, wallHeight);
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
