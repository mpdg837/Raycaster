package Raycaster.Player.InteractiveObjects;

import Raycaster.Project.Game;

import java.awt.event.ItemListener;
import java.awt.geom.Point2D;

public class Items {

    public Game game;
    public Items(Game game){
        this.game = game;
    }
    public void useItems(Point2D position,int type){
        int partXMy = (int) ((position.getX() - (double) ((int) position.getX())) * 64);
        int partYMy = (int) ((position.getY() - (double) ((int) position.getY())) * 64);

        if (partXMy > 8 && partXMy < 56 && partYMy > 8 && partYMy < 56) {
            game.mapa.mapa[(int) position.getX()][(int) position.getY()] = 0;
            switch (type){
                case 24:
                    game.sound.playSound("healthpickup.wav");

                    if(game.player.HP<125) {
                        game.player.HP += 25;
                    }
                    break;
                case 25:
                    game.sound.playSound("weaponpickup.wav");
                    game.gun.addItem();

                    break;
                case 26:
                    game.sound.playSound("healthpickup.wav");
                    break;
            }

        }
    }
}
