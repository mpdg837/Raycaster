package Raycaster.Display.Raycaster;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Raycaster.Texture;

import java.awt.*;

public class Floor {

    private Point lastPoint;

    Raycasting ray;
    public Floor(Raycasting ray){
        this.ray = ray;
    }
    public void floor(Point punkta, double len, double angle, Point lastPoint){
        double height = ((Raycasting.maxLen - len));

        double zet = ray.tempCosB * len;
        int wallHeight = (int) (Raycasting.renderHeightConstant * height / zet);

        punkta = new Point((int) punkta.x, (int) (punkta.y + wallHeight / 2));

        lastPoint = new Point(0,0);

        boolean renderFloor = false;
        boolean renderCeiling = false;

        Point lastPos = new Point(0,0);
        Point pos;

        if (punkta.x < ray.game.render.renderSize.getX()) {
            if (punkta.y < ray.game.render.renderSize.getY()) {


                if (punkta != lastPoint) {
                    if (ray.foo[punkta.y][punkta.x] == 0) {

                        double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
                        double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());

                        int texX = (int) (partX * Texture.size);
                        int texY = (int) (partY * Texture.size);

                        Point point = new Point(texX,texY);

                        if(point!=lastPoint) {
                            if (ray.game.texture != null) {
                                if (punkta.x + 1 < ray.game.render.renderSize.x && punkta.y + 1 < ray.game.render.renderSize.y) {
                                    if (ray.game.mapa.floor[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()] > 0) {
                                        int colorA = ray.game.floor.bufferXY[texY][texX];
                                        if (colorA != 0) {

                                            ray.foo[punkta.y][punkta.x] = colorA;
                                            ray.foo[punkta.y][punkta.x + 1] = colorA;
                                            ray.foo[punkta.y+1][punkta.x] = colorA;
                                            ray.foo[punkta.y+1][punkta.x + 1] = colorA;
                                        }
                                    }
                                    if (ray.game.mapa.ceciling[(int) ray.analysePos.getX()][(int) ray.analysePos.getY()] > 0) {
                                        int colorB = ray.game.ceiling.bufferXYS[texY][texX];
                                        if (colorB != 0) {

                                            ray.foo[punkta.y - wallHeight][punkta.x] = colorB;
                                            ray.foo[punkta.y - wallHeight][punkta.x + 1] = colorB;
                                            ray.foo[punkta.y+1 - wallHeight][punkta.x] = colorB;
                                            ray.foo[punkta.y+1 - wallHeight][punkta.x + 1] = colorB;

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
    }

}
