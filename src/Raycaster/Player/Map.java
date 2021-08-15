package Raycaster.Player;

import Raycaster.Player.InteractiveObjects.Barrels;
import Raycaster.Project.Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Map {
    public byte[][] mapa = new byte[128][128];
    public byte[][] textures = new byte[128][128];

    public Point[][] deltaPos = new Point[128][128];

    public byte[][] HP = new byte[128][128];

    public byte[][] ceciling = new byte[128][128];
    public byte[][] floor = new byte[128][128];

    public boolean[][] light = new boolean[128][128];

    public final Game game ;
    public final Barrels barrels;

    public Map(Game game){
        this.game = game;
        barrels = new Barrels(this);

        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                light[x][y] = true;
                deltaPos[x][y] = new Point(32,32);
            }
        }
    }

    public boolean inside(int x,int y){
        return x>=0 && y>=0 && x<128 && y<128;
    }

    public void analyse(){
        final int radiusB = 20;
            Point myPos =new Point((int)game.playerTransform.postion.getX(),(int)game.playerTransform.postion.getY());

            for (int x = myPos.x - radiusB; x < myPos.x+radiusB; x++) {
                for (int y = myPos.y-radiusB; y < myPos.y+radiusB; y++) {

                    switch (mapa[x][y]) {
                        case 20:
                        case 21:

                            game.doors.updateMap(x,y);

                            break;
                        case 22:
                            barrels.updateMap(x,y);
                            break;
                    }
                }
            }


    }
}
