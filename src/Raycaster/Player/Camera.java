package Raycaster.Player;

import Raycaster.Player.Input.Input;
import Raycaster.Project.Game;

import java.awt.*;

public class Camera {
    public double zoom;
    public int deltaY;
    public double angle;

    int timer = 0;

    public void cameraRot(Game game){
        Point pos = game.input.getMousePos();

        if (pos.x > Input.centerMousePos.x) {
            pos.x = Input.centerMousePos.x;
        }
        if (pos.y > Input.centerMousePos.y) {
            pos.y = Input.centerMousePos.y;
        }

        double delta = (double)pos.x / (double) Input.centerMousePos.x * 0.15;
        double deltay = (double)pos.y / (double) Input.centerMousePos.y * 0.15;

        if (pos.x > 0) {
            game.playerTransform.rotate(0, delta);
        } else if (pos.x < 0) {
            game.playerTransform.rotate(0, delta);
        }

        if (pos.y > 0) {
            if(angle<1.7){
                angle+= deltay;
            }
        } else if (pos.y < 0) {
            if(angle>-1.7){
                angle+= deltay;
            }
        }

        deltaY = (int)(600 * Math.sin(angle));


        if( timer ==3) {
            game.input.centerMouse();
            timer = 0;
        }
        timer++;


    }


}
