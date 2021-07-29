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

    public boolean drawBox(Point punkt, double len, ArrayList<Column> columns,int type){




        boolean finish = false;
        if (ray.height > 0) {

            // Wybór tekstury



            boolean decyzja = false;

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
                column.rect = new Rectangle(punkt.x, punkt.y - (ray.wallHeight >> 1), 1, ray.wallHeight);
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
