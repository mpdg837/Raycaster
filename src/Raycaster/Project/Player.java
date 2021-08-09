package Raycaster.Project;

import Raycaster.Display.Render;
import Raycaster.Player.Collision;
import Raycaster.Player.Transform;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class Player {

    private Game game;
    private int timWalk = 0;
    public int HP = 100;

    public double lastDeltaTime = 0;

    public long start = 0;
    public long stop = 0;
    public Player(Game game){
        this.game = game;
    }

    public void walking(){
        stop = System.nanoTime();

        lastDeltaTime = (double) ((double)(stop - start)/(double)1000000);


        Point2D lastPos = game.playerTransform.postion;

        double speed = lastDeltaTime * 0.02;

        game.render.saveRaycaster.sprites.gunRender.moveGun+=3;
        game.render.saveRaycaster.sprites.gunRender.zoom = game.camera.zoom;

        if(game.render.saveRaycaster.sprites.gunRender.zoom <1){
            game.render.saveRaycaster.sprites.gunRender.zoom=1;
        }

        boolean walk = false;
        if (game.input.getKey(KeyEvent.VK_W)) {
            walk = true;
            game.playerTransform.translate(Transform.getUp(),speed);
        }else if (game.input.getKey(KeyEvent.VK_S)) {
            walk = true;
            game.playerTransform.translate(Transform.getDown(),speed);
        }else if (game.input.getKey(KeyEvent.VK_A)) {
            walk = true;
            game.playerTransform.translate(Transform.getLeft(),speed);
        }else if (game.input.getKey(KeyEvent.VK_D)) {
            walk = true;
            game.playerTransform.translate(Transform.getRight(),speed);
        }else{
            game.render.saveRaycaster.sprites.gunRender.moveGun-=3;

            if(game.render.saveRaycaster.sprites.gunRender.moveGun<= 0 || game.render.saveRaycaster.sprites.gunRender.moveGun== 66){

            }else{
                game.render.saveRaycaster.sprites.gunRender.moveGun +=3;

            }
        }

        if(walk){
            if(timWalk==0) {
                game.sound.playSound("footstepsound.wav");
            }

            timWalk ++;

            if(timWalk>10){
                timWalk = 0;
            }

        }else{
            timWalk = 0;
        }


        if(game.coll.collide(game.playerTransform.postion)){
            game.playerTransform.postion =lastPos;
        }


        start = System.nanoTime();

    }
}
