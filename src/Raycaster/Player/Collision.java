package Raycaster.Player;

import Raycaster.Player.InteractiveObjects.Items;
import Raycaster.Project.Game;

import java.awt.geom.Point2D;

public class Collision {
    Map mapa;
    Game game;
    final Items item;

    public Collision(Map mapa,Game game){

        this.mapa = mapa;
        this.game = game;
        this.item = new Items(game);
    }

    public boolean collide(Point2D position,boolean player) {

        if (mapa.mapa[(int) position.getX()][(int) position.getY()] > 0) {
            boolean decyzja;
            int type = mapa.mapa[(int) position.getX()][(int) position.getY()];

            final double partX = (position.getX() - (int) position.getX());
            final double partY = (position.getY() - (int) position.getY());

            switch (type) {
                case 20:
                case 21:
                    decyzja = mapa.deltaPos[(int) position.getX()][(int) position.getY()].x<32;
                    break;
                case 4:
                case 5:
                    if(mapa.HP[(int) position.getX()][(int) position.getY()] <2){
                        decyzja = mapa.deltaPos[(int) position.getX()][(int) position.getY()].x<32;
                    }else{
                        decyzja = false;
                    }

                    break;
                case 6:
                case 11:
                case 22:
                case 23:
                    decyzja =mapa.HP[(int) position.getX()][(int) position.getY()] <2;
                    break;
                case 3:
                    decyzja = partX >=0.375 && partX <= 0.625 && partY >=0.375 && partY <= 0.625;
                    break;
                case 7:
                    decyzja = partX > partY;
                    break;
                case 8:
                    decyzja = partX < partY;
                    break;
                case 9:
                    decyzja = partY > 1 - partX;
                    break;
                case 10:
                    decyzja = partY < 1 - partX;
                    break;
                case 12:
                case 16:
                    decyzja = partX < 0.5;
                    break;
                case 13:
                case 17:
                    decyzja = partX > 0.5;
                    break;
                case 14:
                case 18:
                    decyzja = partY < 0.5;
                    break;
                case 15:
                case 19:
                    decyzja = partY > 0.5;
                    break;

                default:
                    decyzja = true;
                    break;
                case 24:
                case 25:
                case 26:
                    if(player) {
                        item.useItems(position,type);
                    }
                    decyzja = false;

                    break;
            }

            return decyzja;
        } else {
            return false;
        }
    }
}
