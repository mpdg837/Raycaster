package Raycaster.Player;

import Raycaster.Player.Input.Input;
import Raycaster.Project.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Camera {

    public boolean pause;

    public double zoom;
    public int deltaY;
    public double angle;

    int timer = 0;

    public Camera(){
        pause = false;
    }
    public void cameraRot(Game game){

        if(!game.pause) {
            if (game.player.HP > 0) {
                if (!pause) {
                    Point pos = game.input.getMousePos();

                    if (pos.x > Input.centerMousePos.x) {
                        pos.x = Input.centerMousePos.x;
                    }
                    if (pos.y > Input.centerMousePos.y) {
                        pos.y = Input.centerMousePos.y;
                    }

                    double delta = (double) pos.x / (double) Input.centerMousePos.x * 0.15;
                    double deltay = (double) pos.y / (double) Input.centerMousePos.y * 0.15;

                    if (pos.x > 0) {
                        game.playerTransform.rotate(0, delta);
                    } else if (pos.x < 0) {
                        game.playerTransform.rotate(0, delta);
                    }

                    if (pos.y > 0) {
                        if (angle < 1.7) {
                            angle += deltay;
                        }
                    } else if (pos.y < 0) {
                        if (angle > -1.7) {
                            angle += deltay;
                        }
                    }

                    deltaY = (int) (600 * Math.sin(angle));
                }

                if (timer == 3 && !pause) {
                    game.input.centerMouse();

                }
                if (timer == 3) {
                    timer = 0;
                }


                timer++;

            } else {
                deltaY += 15;


                if (deltaY > 600) {
                    deltaY = 600;

                }
            }
        }

        if (game.input.getKeyDown(KeyEvent.VK_ESCAPE)) {
            pause = !pause;
            game.pause = pause;
            game.render.saveRaycaster.sprites.mask.pause = pause;

            if (pause) {

                game.render.saveRaycaster.setCursor(Cursor.DEFAULT_CURSOR);
            } else {
                BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR);
                Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "null");

                game.render.saveRaycaster.setCursor(cur);
            }
        }

    }


}
