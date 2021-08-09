package Raycaster.Project;

import Raycaster.Player.Collision;
import Raycaster.Player.Transform;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class Player {

    private Game game;

    public int HP = 100;

    public Player(Game game){
        this.game = game;
    }

    public void walking(){
        Point2D lastPos = game.playerTransform.postion;

        double speed = 0.5;

        game.render.saveRaycaster.sprites.gunRender.moveGun+=3;
        game.render.saveRaycaster.sprites.gunRender.zoom = game.camera.zoom;

        if(game.render.saveRaycaster.sprites.gunRender.zoom <1){
            game.render.saveRaycaster.sprites.gunRender.zoom=1;
        }

        if (game.input.getKey(KeyEvent.VK_W)) {

            game.playerTransform.translate(Transform.getUp(),speed);
        }else if (game.input.getKey(KeyEvent.VK_S)) {

            game.playerTransform.translate(Transform.getDown(),speed);
        }else if (game.input.getKey(KeyEvent.VK_A)) {

            game.playerTransform.translate(Transform.getLeft(),speed);
        }else if (game.input.getKey(KeyEvent.VK_D)) {

            game.playerTransform.translate(Transform.getRight(),speed);
        }else{
            game.render.saveRaycaster.sprites.gunRender.moveGun-=3;

            if(game.render.saveRaycaster.sprites.gunRender.moveGun<= 0 || game.render.saveRaycaster.sprites.gunRender.moveGun== 66){

            }else{
                game.render.saveRaycaster.sprites.gunRender.moveGun +=3;

            }
        }


        if(game.coll.collide(game.playerTransform.postion)){
            game.playerTransform.postion =lastPos;
        }
    }
}
