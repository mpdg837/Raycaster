package Raycaster.Display.Raycaster.RenderTools;

import Raycaster.Display.Raycaster.Raycasting;

import java.awt.*;

public class Light {

    public static boolean makeLight(Raycasting ray, Column column){


        final double partX = (column.raycastPosition.getX() - (int) column.raycastPosition.getX());
        final double partY = (column.raycastPosition.getY() - (int) column.raycastPosition.getY());

        Point checkPos = new Point(0,0);
        int count = 0;

        if(partX <0.02){
            checkPos = new Point((int)column.objPosition.getX()-1,(int)column.objPosition.getY());
            count ++;
        }
        if(partX > 0.98){
            checkPos = new Point((int)column.objPosition.getX()+1,(int)column.objPosition.getY());
            count ++;
        }
        if(partY <0.02){
            checkPos = new Point((int)column.objPosition.getX(),(int)column.objPosition.getY()-1);
            count ++;
        }
        if(partY >0.98){
            checkPos = new Point((int)column.objPosition.getX(),(int)column.objPosition.getY()+1);
            count ++;
        }

        if(count<2) {
            ray.darkerMe = false;
            if (!checkPos.equals(new Point(0, 0))) {
                if (checkPos.x >= 0 && checkPos.x < 128) {
                    if (checkPos.y >= 0 && checkPos.y < 128) {
                        ray.darkerMe = !ray.game.mapa.light[(int) checkPos.getX()][(int) checkPos.getY()];
                    }
                }
            }
        }

        return !ray.game.mapa.light[(int) column.objPosition.getX()][(int) column.objPosition.getY()] || ray.darkerMe;
    }

}
