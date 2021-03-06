package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Triangle {

    final Raycasting ray;
    public Triangle(Raycasting ray){
        this.ray = ray;
    }

    public boolean drawBox(int height,int wallHeight,Point punkt, double len, ArrayList<Column> columns,int type){




        boolean finish = false;
        if (height > 0) {

            // Wybór tekstury



            boolean decyzja = false;
            boolean in = false;
            final byte hp = ray.game.mapa.HP[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()];
            switch (type) {
                case 0: decyzja = ray.partX > ray.partY;
                    break;
                case 1: decyzja = ray.partX < ray.partY;
                    break;
                case 2: decyzja = ray.partY > 1-ray.partX;
                    break;
                case 3: decyzja = ray.partY < 1-ray.partX;
                    break;
            }


            boolean destroyed = (ray.game.damage.pseudoLos(ray.posX,hp));

            if(hp<1){
                destroyed = false;
            }

            if( decyzja) {

                boolean cien = false;


                int indexTex=0;

                switch (type){
                    case 0:

                        if (ray.posY == 0 || ray.posX == 0) {
                            if(ray.posY == 0) {
                                cien = true;
                            }

                            indexTex = ray.posX;
                        } else {
                            cien = true;

                            indexTex = ray.posY;

                        }
                        break;

                    case 1:
                        if (ray.posY == 63 || ray.posX == 63) {

                            if(ray.posY == 63) {
                                cien = true;
                            }

                            indexTex = ray.posX;
                        } else {

                            indexTex = ray.posY;

                        }
                        break;
                    case 2:
                        if (ray.posY == 63 || ray.posX == 0) {
                            if(ray.posY ==63) {
                                cien = true;
                            }
                            indexTex = ray.posX;
                        } else {

                            indexTex = ray.posY;

                        }
                        break;
                    case 3:
                        if (ray.posY == 0 || ray.posX == 63) {
                            if(ray.posY == 0) {
                                cien = true;
                            }
                            indexTex = ray.posX;
                        } else {
                            cien = true;
                            indexTex = ray.posY;

                        }
                        break;
                }

                // Wyznaczenie tekstury


                final Column column = new Column();



                column.darker = cien;
                column.index = indexTex;
                column.rect = new Rectangle(punkt.x, punkt.y - (wallHeight >> 1), 1, wallHeight);
                column.objPosition = new Point((int)ray.analysePos.getX(),(int)ray.analysePos.getY());
                column.raycastPosition = new Point2D.Double(ray.analysePos.getX(),ray.analysePos.getY());

                column.destroyed = destroyed;
                column.half = false;
                column.setLen(len);
                columns.add(column);
                finish = true;
            }
        }

        return finish;
    }

}
