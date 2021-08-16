package Raycaster.Project;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Gun {

    private final Game game;

    public final int maxAmmo=20;

    public int ammo;
    public int itemsAmmo = 5;

    public Point posDetected;

    public Gun(Game game){
        this.game = game;
    }

    int detect;
    public void useGun(){


        game.render.saveRaycaster.sprites.infoAmmo = ammo +"/"+itemsAmmo;

        if(ammo == -1 && itemsAmmo == 0){
            game.render.saveRaycaster.sprites.infoAmmo = "0";
        }
        if(game.render.saveRaycaster.sprites.gunRender.reloadAnimate<=0 && game.player.HP>0) {
            if (game.input.getKeyDown(KeyEvent.VK_R)) {
                if(game.render.saveRaycaster.sprites.gunRender.reloadAnimate<=0) {
                    game.sound.playSound("reload.wav");
                    game.render.saveRaycaster.sprites.gunRender.reloadAnimate = 1;
                    reloadMe();
                }
            }else
            if (game.input.getMouseButtonDown(MouseEvent.BUTTON1)) {

                if(game.render.saveRaycaster.sprites.gunRender.shootAnimate<=0){
                    shoot();

                }
            }

            if( game.render.saveRaycaster.sprites.gunRender.reloadAnimate !=1) {
                if (game.input.getMouseButton(MouseEvent.BUTTON3)) {
                    if (game.camera.zoom < 3) {
                        game.camera.zoom += 0.15;

                    }
                } else {

                    if (game.camera.zoom > 1) {
                        game.camera.zoom -= 0.2;

                    }

                }

                game.tim++;
            }
        }





        if(game.render.saveRaycaster.sprites.gunRender.shootAnimate==1) {



            if( 240 > 240 - ((48-detect)*3) +  Math.abs(game.camera.deltaY) && 240 < 240 + ((48-detect)*3) +  Math.abs(game.camera.deltaY)  ) {

                if (game.mapa.HP[posDetected.x][posDetected.y] < 5 && game.mapa.mapa[posDetected.x][posDetected.y]!=0 ) {

                    game.mapa.HP[posDetected.x][posDetected.y]++;
                }

            }
        }

        if(game.render.saveRaycaster.sprites.gunRender.shootAnimate>0) {


                double wsp = (double) (20 - detect) / (double) 20 * 100;
                game.render.saveRaycaster.sprites.bulletSize = (int) (wsp);

        }else{
            game.render.saveRaycaster.sprites.bulletSize = 0;
        }
    }

    public void addItem(){
        itemsAmmo ++;

    }
    public double detectLen(){

        final double deltaLen = 0.01;

        final double deltaX = deltaLen * game.getCos(game.playerTransform.rotation);
        final double deltaY = deltaLen * game.getSin(game.playerTransform.rotation);

        double cx = game.playerTransform.postion.getX();
        double cy = game.playerTransform.postion.getY();

        posDetected = new Point((int)cx,(int)cy);
        Point lastPoint = new Point(0,0);

        game.render.saveRaycaster.sprites.gunRender.blockMe = false;
        double memLen = 0;

        for(double len=0;len<30;len+=deltaLen){

            if(!game.render.saveRaycaster.sprites.gunRender.blockMe ) {
                cx += deltaX;
                cy += deltaY;
                if (cx >= 0 && cx < 128) {
                    if (cy >= 0 && cy < 128) {
                        Point zao = new Point((int) (cx * (double) 32), (int) (cy * (double)32));
                        if (!zao.equals(lastPoint)) {

                            switch (game.mapa.mapa[(int) cx][(int) cy]) {
                                case 0:
                                case 24:
                                case 25:
                                case 26:
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
                                    if (game.camera.deltaY > 0) {
                                        if (game.coll.collide(new Point2D.Double(cx, cy), false)) {

                                            memLen = len;
                                            len = 30;
                                            game.render.saveRaycaster.sprites.gunRender.blockMe = false;
                                            posDetected = new Point((int) cx, (int) cy);

                                        }
                                    }
                                    break;
                                case 23:

                                    final Point pos = game.mapa.deltaPos[(int) cx][(int) cy];
                                    final Point zaoPos = new Point((int) ((cx - (double) (int) cx) * (double) 64), (int) ((cy - (double) (int) cy) * (double) 64));

                                    final int radius =24;

                                    if (zaoPos.x <= pos.x + radius && zaoPos.x >= pos.x - radius) {
                                        if (zaoPos.y <= pos.y + radius && zaoPos.y >= pos.y - radius) {

                                            if (game.mapa.HP[(int) cx][(int) cy] <= 5) {


                                                game.render.saveRaycaster.sprites.gunRender.blockMe = true;

                                                memLen = len;
                                                len = 30;
                                                posDetected = new Point((int) cx, (int) cy);
                                            }

                                        }
                                    }

                                    break;
                                case 20:
                                case 21:
                                    if (game.mapa.deltaPos[(int) cx][(int) cy].x < 32) {
                                        memLen = 30;
                                        len = 30;
                                        game.render.saveRaycaster.sprites.gunRender.blockMe = true;
                                    }

                                    break;
                                default:


                                        boolean decyzja = true;
                                        switch (game.mapa.mapa[(int) cx][(int) cy]) {

                                            case 4:
                                            case 5:
                                            case 6:
                                            case 11:

                                                if (game.mapa.HP[(int) cx][(int) cy] > 2) decyzja = false;
                                                else {

                                                    memLen = len;
                                                    len = 30;
                                                }
                                                break;

                                            default:
                                                decyzja = game.coll.collide(new Point2D.Double(cx, cy), false);
                                                break;
                                        }


                                        if (decyzja) {
                                            memLen = len;
                                            len = 30;
                                            game.render.saveRaycaster.sprites.gunRender.blockMe = true;
                                            posDetected = new Point((int) cx, (int) cy);
                                        }

                                    break;

                            }

                        }

                        lastPoint = zao;
                    }
                }
            }
        }

        return memLen;
    }

    private void reloadMe(){
        if(itemsAmmo>0) {
            game.render.saveRaycaster.sprites.gunRender.reloadAnimate = 1;

            ammo = maxAmmo;
            itemsAmmo--;
        }
    }
    public void shoot(){





        if(ammo < 1){


            game.sound.playSound("reload.wav");

                reloadMe();

        }else{

            detect = (int)detectLen();
            game.render.saveRaycaster.sprites.gunRender.shootAnimate=1;
            game.sound.playSound("shoot.wav");
            ammo--;
        }

    }


}
