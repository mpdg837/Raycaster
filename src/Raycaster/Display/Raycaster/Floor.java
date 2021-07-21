package Raycaster.Display.Raycaster;

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
        int wallHeight = (int) (18 * height / zet);

        punkta = new Point((int) punkta.x, (int) (punkta.y + wallHeight / 2));

        lastPoint = new Point(0,0);

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
                                int colorA = ray.game.floor.bufferXY[texY][texX];
                                int colorB = ray.game.floor.bufferXY[texY][texX];

                                ray.foo[punkta.y][punkta.x] = colorA;
                                ray.foo[punkta.y - wallHeight][punkta.x] = colorB;


                            }
                        }

                        lastPoint = point;
                    }
                }
            }

        }
    }

}
