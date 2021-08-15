package Raycaster.Player.InteractiveObjects;

import Raycaster.Player.Map;

import java.awt.*;

public class Barrels {

    private Map map;

    public Barrels(Map map){
        this.map = map;
    }
    public void updateMap(int x,int y){
        if (map.HP[x][y] > 0 && map.HP[x][y] < 5) {


            Point myPos =new Point((int)map.game.playerTransform.postion.getX(),(int)map.game.playerTransform.postion.getY());

            // Wybuch beczki

            final int radius = 2;
            for (int xa = x - radius; xa <= x + radius; xa++) {
                for (int ya = y - radius; ya <= y + radius; ya++) {
                    if (map.inside(xa, ya)) {

                        if(map.HP[xa][ya]<5) {
                            map.HP[xa][ya]++;

                            if(map.HP[xa][ya] == 2){
                                map.game.sound.playSound("explode.wav");
                            }
                        }


                        if(myPos.equals(new Point(xa,ya))){
                            if(map.game.player.HP>0) {
                                map.game.player.HP = 0;
                            }else{
                                map.game.player.HP -=10;
                            }
                        }

                    }


                }
            }
        }
    }
}
