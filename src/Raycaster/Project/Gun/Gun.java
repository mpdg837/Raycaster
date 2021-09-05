package Raycaster.Project.Gun;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Gun {

    public final Game game;

    public final int maxAmmo=20;

    public int ammo;
    public int itemsAmmo = 5;

    public Point posDetected;
    double memLen = 0;
    final GunCollision gcoll;

    public Gun(Game game){
        gcoll = new GunCollision(this);
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

            detect = (int)gcoll.detectLen();

            game.render.saveRaycaster.sprites.gunRender.shootAnimate=1;
            game.sound.playSound("shoot.wav");
            ammo--;
        }

    }


}
