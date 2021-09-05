package Raycaster.Display.Raycaster.RenderTools;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Textures.Texture;

import java.awt.*;

public class Floor {


    final Raycasting ray;
    Point lastPoint;
    public Floor(Raycasting ray){

        this.ray = ray;
        lastPoint = new Point(0,0);
    }

    private static void drawBigPixel(int[][] foo,int colorA, Point punkta){

        for(int x= punkta.x;x< punkta.x+4;x++){
            for(int y= punkta.y;y< punkta.y+4;y++) {

                    foo[y][x] = colorA;

            }
        }

    }


    public void floor(int wallHeight,Point punkta){


        final Point punktax = new Point( punkta.x, (punkta.y + (wallHeight >> 1)) );




        if (punktax.x < ray.game.render.renderSize.getX()) {
            if (punktax.y < ray.game.render.renderSize.getY()) {

                Point point= new Point(0,0);
                if (!punktax.equals(lastPoint)) {
                    if (ray.foo[punktax.y][punktax.x] == 0) {


                        point = new Point(ray.posX,ray.posY);

                        if(!point.equals(lastPoint)) {
                            if (ray.game.texture != null) {
                                if (punktax.x + 4 < ray.game.render.renderSize.x && punktax.y + 4 < ray.game.render.renderSize.y) {
                                    if (ray.game.mapa.floor[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()] > 0) {

                                        final Texture myTex = ray.game.texture.textures[ray.game.mapa.floor[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]];
                                        int colorA = myTex.bufferXY[ray.posY][ray.posX];

                                        if (colorA != 0) {

                                            if (!ray.game.mapa.light[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]) {

                                                colorA = myTex.bufferXYNL[ray.posY][ray.posX];
                                            }


                                                drawBigPixel(ray.foo, colorA, new Point(punktax.x, punktax.y ));


                                        }
                                    }
                                    if (ray.game.mapa.ceiling[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()] > 0) {
                                        final Texture myTex = ray.game.texture.textures[ray.game.mapa.ceiling[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]];
                                        int colorB = myTex.bufferXYS[ray.posY][ray.posX];


                                        if (colorB != 0) {

                                            if(!ray.game.mapa.light[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]) {
                                                colorB = myTex.bufferXYNL[ray.posY][ray.posX];
                                            }


                                                drawBigPixel(ray.foo,colorB, new Point(punktax.x, punktax.y - wallHeight));

                                        }
                                    }
                                }
                            }
                        }


                    }
                }
                lastPoint = point;
            }

        }
    }

}
