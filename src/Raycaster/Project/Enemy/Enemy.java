package Raycaster.Project.Enemy;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.geom.Point2D;

public class Enemy {

    final private Game game;
    private boolean[][] blocked = new boolean[128][128];

    public Enemy(Game game){
        this.game = game;
    }

    private boolean inside(int x, int y){
        return x >= 0 && x<= 128 && y >= 0 && y<= 128;
    }
    public void update(){
        blocked = new boolean[128][128];

        game.timk++;
        if(game.timk>3) {

            final int range = 10;
            for(int x=(int)game.playerTransform.postion.getX()-range ; x<(int)game.playerTransform.postion.getX()+range;x++){
                for(int y=(int)game.playerTransform.postion.getY()-range ; y<(int)game.playerTransform.postion.getY()+range;y++){
                    if(inside(x,y)){

                        if(game.mapa.mapa[x][y] == 23 && !blocked[x][y]){
                            updateEnemy(new Point(x,y));
                        }

                    }
                }
            }

            game.timk = 0;
        }
        if(game.tim>60) {

            game.render.saveRaycaster.requestFocus();
            game.tim = 0;
        }

    }

    Point move(Point enPos){

        Point nPos = new Point(enPos.x, enPos.y);

        final Point myPos = new Point((int) game.playerTransform.postion.getX(), (int) game.playerTransform.postion.getY());
        final Point2D delta = new Point2D.Double((double) (myPos.x - enPos.x) / (double) 64, (myPos.y - enPos.y) / (double) 64);


        Point last = new Point(game.mapa.deltaPos[enPos.x][enPos.y].x, game.mapa.deltaPos[enPos.x][enPos.y].y);
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

                    if (myPos.x != enPos.x || myPos.y != enPos.y - 1) {
                        nPos.y--;
                        last.y = 63 + stepDelta.y;
                    }
                }

            } else {
                if (game.mapa.mapa[nPos.x][nPos.y + 1] == 0) {
                    if (myPos.x != enPos.x || myPos.y != enPos.y + 1) {
                        nPos.y++;
                        last.y = stepDelta.y;
                    }
                }

            }

        }


        game.mapa.mapa[enPos.x][enPos.y] = 0;
        game.mapa.deltaPos[nPos.x][nPos.y] = last;

        game.mapa.mapa[nPos.x][nPos.y] = 23;
        blocked[nPos.x][nPos.y] = true;

        return nPos;
    }

    public boolean detect(Point enPos,Point myPos){
        Point2D analysePos = new Point2D.Double(enPos.x, enPos.y);
        final Point2D delta = new Point2D.Double((double) (myPos.x - enPos.x) / (double) 64, (myPos.y - enPos.y) / (double) 64);

        boolean hurt = true;

        Point lastZao = myPos;


        for (int n = 0; n < 64; n++) {
            final Point zao = new Point((int) analysePos.getX(), (int) analysePos.getY());
            if (!zao.equals(lastZao)) {

                if (zao.equals(myPos)) {
                    break;
                } else if (game.mapa.mapa[zao.x][zao.y] != 0 && game.mapa.mapa[zao.x][zao.y] != 23) {

                    hurt = false;
                    break;


                }

            }

            analysePos.setLocation(analysePos.getX() + delta.getX(), analysePos.getY() + delta.getY());
            lastZao = zao;
        }

        return hurt;
    }
    public void updateEnemy(Point enPos){

        if(game.mapa.HP[enPos.x][enPos.y]<1) {
            boolean hurt = true;
            byte numer = 0;

            Point nPos = new Point(enPos.x, enPos.y);
            final Point myPos = new Point((int) game.playerTransform.postion.getX(), (int) game.playerTransform.postion.getY());


            if (!game.enemyPoint.contains(enPos)) {

               hurt = detect(enPos,myPos);


            }

            nPos = move(enPos);

            if (hurt) {


                if (game.n > 3) {
                    game.n = 0;

                } else {
                    game.n++;
                }


                game.mapa.textures[nPos.x][nPos.y] = (byte) (3 + game.n * 16);
            } else {


                if (game.n > 1) {
                    game.n = 0;

                } else {
                    game.n++;
                }

                game.mapa.textures[nPos.x][nPos.y] = (byte) (3 + game.n * 16);
            }


        }else{
            if (game.n > 6) {
                game.n = 7;

            } else {
                game.n++;
            }

            game.mapa.textures[enPos.x][enPos.y] = (byte) (3 + game.n * 16);
        }

    }
}
