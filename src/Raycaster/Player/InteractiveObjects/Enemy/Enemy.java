package Raycaster.Player.InteractiveObjects.Enemy;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.geom.Point2D;

public class Enemy {

    final private Game game;
    private final byte[][] n = new byte[128][128];
    final int range = 15;

    boolean[][] blocked;
    Point center;

    public Enemy(Game game){
        this.game = game;
        center = new Point(0,0);
    }

    private boolean inside(int x, int y){
        return x >= 0 && x< 128 && y >= 0 && y< 128;
    }
    public void update(){
        game.render.saveRaycaster.sprites.hurt = false;
        game.timk++;


        hurted = false;

        if(game.timk>3) {


                for (int x = center.x - range; x < center.x + range; x++) {
                    for (int y = center.y - range; y < center.y + range; y++) {

                        if (inside(x, y)) {

                            if (game.mapa.mapa[x][y] == 23 && !blocked[x][y]) {
                                updateEnemy(new Point(x, y), blocked);
                            }

                        }

                    }
                }


            game.timk = 0;
        }else if(game.timk==1){
            blocked = new boolean[128][128];
            center = new Point((int) game.playerTransform.postion.getX(), (int) game.playerTransform.postion.getY());
        }


    }

    Point move(Point enPos,boolean[][] blocked){

        final Point nPos = new Point(enPos.x, enPos.y);

        final Point myPos = new Point((int) game.playerTransform.postion.getX(), (int) game.playerTransform.postion.getY());
        final Point2D delta = new Point2D.Double((double) (myPos.x - enPos.x) / (double) 64, (myPos.y - enPos.y) / (double) 64);


        final Point last = new Point(game.mapa.deltaPos[enPos.x][enPos.y].x, game.mapa.deltaPos[enPos.x][enPos.y].y);
        final Point stepDelta = new Point((int) (delta.getX() * 120), (int) (delta.getY() * 120));



        if (last.x + stepDelta.x >= 0 && last.x + stepDelta.x < 63) {
            last.x += stepDelta.x;
        } else {

            if (last.x + stepDelta.x < 0) {
                if (game.mapa.mapa[enPos.x - 1][enPos.y] == 0) {

                    if (myPos.x != enPos.x - 1 || myPos.y != enPos.y) {
                        nPos.x--;
                        last.x = 63 + stepDelta.x;
                    }
                }

            } else {
                if (game.mapa.mapa[enPos.x + 1][enPos.y] == 0) {
                    if (myPos.x != enPos.x + 1 || myPos.y != enPos.y) {
                        nPos.x++;
                        last.x = stepDelta.x;
                    }
                }

            }

        }

        if (last.y + stepDelta.y >= 0 && last.y + stepDelta.y < 63) {
            last.y += stepDelta.y;
        } else {

            if (last.y + stepDelta.y < 0) {
                if (game.mapa.mapa[nPos.x][nPos.y - 1] == 0) {

                    if (myPos.x != nPos.x || myPos.y != nPos.y - 1) {
                        nPos.y--;
                        last.y = 63 + stepDelta.y;
                    }
                }

            } else {
                if (game.mapa.mapa[nPos.x][nPos.y + 1] == 0) {
                    if (myPos.x != nPos.x || myPos.y != nPos.y + 1) {
                        nPos.y++;
                        last.y = stepDelta.y;
                    }
                }

            }

        }


        final byte hp = game.mapa.HP[enPos.x][enPos.y];

        game.mapa.HP[enPos.x][enPos.y] = 0;
        game.mapa.mapa[enPos.x][enPos.y] = 0;

        game.mapa.deltaPos[nPos.x][nPos.y] = last;
        game.mapa.mapa[nPos.x][nPos.y] = 23;
        blocked[nPos.x][nPos.y] = true;
        game.mapa.HP[nPos.x][nPos.y] += hp;

        return nPos;
    }

    boolean hurted = false;

    public void updateEnemy(Point enPos,boolean[][] blocked){
        byte myN = n[enPos.x][enPos.y];
        n[enPos.x][enPos.y]=0;

        Point nPos = new Point(enPos.x, enPos.y);

        if(game.mapa.HP[enPos.x][enPos.y]<1) {



            boolean hurt = true;

            if (!game.enemyPoint.contains(enPos)) {

                    hurt = false;


            }else {
                if (!hurted) {
                    game.render.saveRaycaster.sprites.hurt = true;
                    hurted = true;
                }
            }

            nPos = move(enPos,blocked);

            if (hurt) {

                game.player.HP -=2;
                if (myN > 3) {
                    myN = 0;

                } else {
                    myN ++;
                }

                if(myN == 3){
                    game.sound.playSound("enemyShoot.wav");
                }

                game.mapa.textures[nPos.x][nPos.y] = (int) (3 + myN  * 16);
            } else {


                if (myN  > 1) {
                    myN  = 0;

                } else {
                    myN ++;
                }

                game.mapa.textures[nPos.x][nPos.y] = (int) (3 + myN  * 16);
            }

        }else {
            if( game.mapa.HP[nPos.x][nPos.y]!=10) {
                if (myN > 7) {
                    myN = 8;

                    game.mapa.mapa[nPos.x][nPos.y] = 25;
                    game.mapa.HP[nPos.x][nPos.y] = 0;
                    game.mapa.textures[nPos.x][nPos.y] = (int) (3 + myN * 16);
                } else {
                    myN++;
                }

                if (myN == 6) {
                    game.sound.playSound("die.wav");
                }

                game.mapa.textures[enPos.x][enPos.y] = (int) (3 + myN * 16);
            }
        }

        n[nPos.x][nPos.y]=myN;


    }
}
