package Raycaster.Display.Raycaster;

import java.awt.*;

public class Floor {

    Raycasting ray;
    public Floor(Raycasting ray){
        this.ray = ray;
    }
    public void floor(Point punkta, double len, double angle, Point lastPoint){
        double height = ((Raycasting.maxLen - len));

        double zet = ray.tempCosB * len;
        int wallHeight = (int) (18 * height / zet);

        punkta = new Point((int) punkta.x, (int) (punkta.y + wallHeight / 2));


        if (punkta.x < 320) {
            if (punkta.y < 240) {
                if (punkta != lastPoint) {
                    if (ray.foo[punkta.y][punkta.x] == 0) {

                        double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
                        double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());

                        int texX = (int) (partX * 64);
                        int texY = (int) (partY * 64);

                        if (ray.game.texture != null) {
                            int color = ray.game.floor.textureMain.getRGB(texX, texY);


                                ray.foo[punkta.y][punkta.x] = color;
                                ray.foo[punkta.y - wallHeight][punkta.x] = color;


                        }
                    }
                }
            }

        }
    }

}
