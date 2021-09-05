package Raycaster.Project.Gun;

import java.awt.*;
import java.awt.geom.Point2D;

public class GunCollision {

    final Gun gun;

    public GunCollision(Gun gun){
        this.gun = gun;
    }
    public boolean checkPosForSprite(double cx, double cy, Point zaoPos) {
        final Point pos = gun.game.mapa.deltaPos[(int) cx][(int) cy];

        final int radius = 24;

        if (zaoPos.x <= pos.x + radius && zaoPos.x >= pos.x - radius) {
            if (zaoPos.y <= pos.y + radius && zaoPos.y >= pos.y - radius) {
                return true;
            }
        }

        return false;
    }
    public double analyseEnemy(double cx,double cy,Point zaoPos,double len){


        if(checkPosForSprite(cx,cy,zaoPos)){

            if (gun.game.mapa.HP[(int) cx][(int) cy] < 2) {

                gun.game.render.saveRaycaster.sprites.gunRender.blockMe = true;


                gun.posDetected = new Point((int) cx, (int) cy);

                gun.memLen = len;
                len = 30;
            }


        }


        return len;
    }


    public double detectLen(){

        final double deltaLen = 0.01;

        final double deltaX = deltaLen * gun.game.getCos(gun.game.playerTransform.rotation);
        final double deltaY = deltaLen * gun.game.getSin(gun.game.playerTransform.rotation);

        double cx = gun.game.playerTransform.postion.getX();
        double cy = gun.game.playerTransform.postion.getY();

        gun.posDetected = new Point((int)cx,(int)cy);
        Point lastPoint = new Point(0,0);

        gun.game.render.saveRaycaster.sprites.gunRender.blockMe = false;
        gun.memLen = 0;

        for(double len=0;len<30;len+=deltaLen){

            if(!gun.game.render.saveRaycaster.sprites.gunRender.blockMe ) {
                cx += deltaX;
                cy += deltaY;
                if (cx >= 0 && cx < 128) {
                    if (cy >= 0 && cy < 128) {
                        Point zao = new Point((int) (cx * (double) 32), (int) (cy * (double)32));
                        if (!zao.equals(lastPoint)) {

                            switch (gun.game.mapa.mapa[(int) cx][(int) cy]) {
                                case 0:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                    break;
                                case 2:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                case 18:
                                case 19:
                                    if (gun.game.camera.deltaY > 0) {
                                        if (gun.game.coll.collide(new Point2D.Double(cx, cy), false)) {

                                            gun.memLen = len;
                                            len = 30;
                                            gun.game.render.saveRaycaster.sprites.gunRender.blockMe = false;
                                            gun.posDetected = new Point((int) cx, (int) cy);

                                        }
                                    }
                                    break;

                                case 20:
                                case 21:
                                    if (gun.game.mapa.deltaPos[(int) cx][(int) cy].x < 32) {
                                        gun.memLen = 30;
                                        len = 30;
                                        gun.game.render.saveRaycaster.sprites.gunRender.blockMe = true;
                                    }

                                    break;
                                default:


                                    boolean decyzja = true;
                                    switch (gun.game.mapa.mapa[(int) cx][(int) cy]) {
                                        case 23:
                                            len = analyeCloseEnemy(cx,cy,len);
                                            decyzja = false;
                                            break;
                                        case 4:
                                        case 5:
                                        case 6:
                                        case 11:
                                        case 22:
                                            if (gun.game.mapa.HP[(int) cx][(int) cy] > 2) decyzja = false;
                                            else {
                                                final Point zaoPos = new Point((int) ((cx - (double) (int) cx) * (double) 64), (int) ((cy - (double) (int) cy) * (double) 64));

                                                if(checkPosForSprite(cx,cy,zaoPos)) {

                                                    gun.memLen = len;
                                                    len = 30;
                                                }
                                            }
                                            break;

                                        default:
                                            decyzja = gun.game.coll.collide(new Point2D.Double(cx, cy), false);
                                            break;
                                    }


                                    if (decyzja) {
                                        gun.memLen = len ;
                                        len = 30;

                                        gun.posDetected = new Point((int) cx, (int) cy);
                                    }
                                    break;

                            }

                        }

                        lastPoint = zao;
                    }
                }
            }
        }

        return gun.memLen;
    }

    private double analyeCloseEnemy(double cx,double cy,double len){


        if(gun.game.mapa.mapa[(int)cx][(int)cy]==23) {
            final Point zaoPos = new Point((int) ((cx - (double) (int) cx) * (double) 64), (int) ((cy - (double) (int) cy) * (double) 64));

            len = analyseEnemy(cx, cy, zaoPos, len);
        }


        return len;
    }

}
