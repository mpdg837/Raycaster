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

    private void drawBigPixel(int colorA, Point punkta){
        ray.foo[punkta.y][punkta.x] = colorA;
        ray.foo[punkta.y][punkta.x + 1] = colorA;
        ray.foo[punkta.y+1][punkta.x] = colorA;
        ray.foo[punkta.y+1][punkta.x + 1] = colorA;
    }
    public void floor(Point punkta, double len){
        final double height = ((Raycasting.maxLen - len));

        final double zet = ray.tempCosB * len;
        final int wallHeight = (int) (ray.renderHeightConstant * height / zet);

        punkta = new Point((int) punkta.x, (int) (punkta.y + wallHeight / 2) );




        if (punkta.x < ray.game.render.renderSize.getX()) {
            if (punkta.y < ray.game.render.renderSize.getY()) {

                Point point= new Point(0,0);
                if (!punkta.equals(lastPoint)) {
                    if (ray.foo[punkta.y][punkta.x] == 0) {

                        final double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
                        final double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());

                        final int texX = (int) (partX * Texture.size);
                        final int texY = (int) (partY * Texture.size);

                        point = new Point(texX,texY);

                        if(!point.equals(lastPoint)) {
                            if (ray.game.texture != null) {
                                if (punkta.x + 4 < ray.game.render.renderSize.x && punkta.y + 4 < ray.game.render.renderSize.y) {
                                    if (ray.game.mapa.floor[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()] > 0) {

                                        final Texture myTex = ray.game.texture.textures[ray.game.mapa.floor[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]];
                                        int colorA = myTex.bufferXY[texY][texX];

                                        if (colorA != 0) {

                                            if (!ray.game.mapa.light[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]) {

                                                colorA = myTex.bufferXYNL[texY][texX];
                                            }


                                                drawBigPixel(colorA, new Point(punkta.x, punkta.y ));
                                                drawBigPixel(colorA, new Point(punkta.x + 2, punkta.y ));
                                                drawBigPixel(colorA, new Point(punkta.x, punkta.y  + 2));
                                                drawBigPixel(colorA, new Point(punkta.x + 2, punkta.y + 2));



                                        }
                                    }
                                    if (ray.game.mapa.ceciling[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()] > 0) {
                                        final Texture myTex = ray.game.texture.textures[ray.game.mapa.ceciling[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]];
                                        int colorB = myTex.bufferXYS[texY][texX];


                                        if (colorB != 0) {

                                            if(!ray.game.mapa.light[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()]) {
                                                colorB = myTex.bufferXYNL[texY][texX];
                                            }


                                                drawBigPixel(colorB, new Point(punkta.x, punkta.y - wallHeight));
                                                drawBigPixel(colorB, new Point(punkta.x + 2, punkta.y - wallHeight));
                                                drawBigPixel(colorB, new Point(punkta.x, punkta.y - wallHeight + 2));
                                                drawBigPixel(colorB, new Point(punkta.x + 2, punkta.y - wallHeight + 2));

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
