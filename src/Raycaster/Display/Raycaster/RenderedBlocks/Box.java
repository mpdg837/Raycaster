package Raycaster.Display.Raycaster.RenderedBlocks;

import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;

public class Box {

    Raycasting ray;
    public Box(Raycasting ray){
        this.ray = ray;
    }

    public void drawBox(int nStep, double len, double angle, Graphics2D g){
        Point punkt = new Point(nStep, ray.game.render.renderSize.y/2);


        double height =((Raycasting.maxLen - len));
        int indexTex = 0;


        if (height > 0) {

            // Wybór tekstury
            double partX = (ray.analysePos.getX() - (int) ray.analysePos.getX());
            double partY = (ray.analysePos.getY() - (int) ray.analysePos.getY());


            boolean cien = partY<0.01 || partY>0.99;

            if(partY<ray.deltaTex || partY>1-ray.deltaTex) {

                indexTex = (int) (partX * 64);
            }else{
                indexTex = (int) (partY * 64);
            }

            // Wyznaczenie tekstury

            double zet = Math.cos(Math.abs(ray.myAngle - angle)) * len;
            int wallHeight = (int)(18 * height/zet);

            if(cien){
                g.drawImage(ray.game.texture.getColumnDarker(indexTex), punkt.x, punkt.y - wallHeight / 2, 1, wallHeight, null);

            }else {
                g.drawImage(ray.game.texture.getColumn(indexTex), punkt.x, punkt.y - wallHeight / 2, 1, wallHeight, null);

            }
        }


    }
}
