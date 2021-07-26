package Raycaster.Display.Raycaster.RenderedBlocks.Boxes;

import Raycaster.Display.Raycaster.RenderTools.Column;
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



            final byte hp = ray.game.mapa.HP[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()];

            final boolean in = ray.posX >=24+hp && ray.posX <= 40-hp && ray.posY >=24+hp && ray.posY <= 40-hp;
            final boolean nearIn = ray.posX >=24 && ray.posX <= 40 && ray.posY >=24 && ray.posY <= 40;

            boolean decyzja=false;
            boolean destroy=false;

            if(hp>1 && nearIn) {
                if (ray.posY <= hp+24 || ray.posY >= 40-hp) {
                    decyzja = (ray.game.damage.pseudoLos(ray.posX,hp)) || in;
                    destroy = in;
                } else {
                    decyzja = (ray.game.damage.pseudoLos(ray.posY,hp)) || in;
                    destroy = in;
                }
            }else if(nearIn){
                decyzja = true;
                destroy = false;
            }

            if( decyzja ) {

                boolean cien = false;



                int indexTex;
                if(destroy) {
                    if (ray.posY == 24 + hp || ray.posY == 23 + hp || ray.posY == 40 - hp || ray.posY == 39 - hp) {
                        cien = true;
                        indexTex = ray.posX;
                    } else {
                        indexTex = ray.posY;

                    }
                }else{
                    if (ray.posY == 24|| ray.posY == 23 || ray.posY == 40  || ray.posY == 39 ) {
                        cien = true;
                        indexTex = ray.posX;
                    } else {
                        indexTex = ray.posY;

                    }
                }

                // Wyznaczenie tekstury

                final double zet = ray.tempCosB * len;
                final int wallHeight = (int) (ray.renderHeightConstant * height / zet);

                final Column column = new Column();


                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - wallHeight / 2, 1, wallHeight);
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.destroyed = destroy;

                column.half = false;
                column.setLen(len);
                columns.add(column);
                finish = true;
            }
        }

        return finish;
    }

}
