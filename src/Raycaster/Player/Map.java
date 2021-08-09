package Raycaster.Player;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Map {
    public byte[][] mapa = new byte[128][128];
    public byte[][] textures = new byte[128][128];

    public Point[][] deltaPos = new Point[128][128];
    public Point2D[][] deltaPosE = new Point[128][128];

    public byte[][] HP = new byte[128][128];

    public byte[][] ceciling = new byte[128][128];
    public byte[][] floor = new byte[128][128];

    public boolean[][] light = new boolean[128][128];

    Game game ;
    public Map(Game game){
        this.game = game;
        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                light[x][y] = true;
                deltaPos[x][y] = new Point(32,32);
            }
        }
    }

    private boolean inside(int x,int y){
        return x>=0 && y>=0 && x<128 && y<128;
    }

    public void analyse(){
        final int radiusB = 10;
            Point myPos =new Point((int)game.playerTransform.postion.getX(),(int)game.playerTransform.postion.getY());

            for (int x = myPos.x - radiusB; x < myPos.x+radiusB; x++) {
                for (int y = myPos.y-radiusB; y < myPos.y+radiusB; y++) {

                    switch (mapa[x][y]) {
                        case 20:
                        case 21:

                            // Drzwi
                            if (game.doors.tim[x][y] > 0) {
                                game.doors.tim[x][y] += 2;

                                int pos;

                                if (game.doors.tim[x][y] < 64) {
                                    pos = game.doors.tim[x][y];
                                } else if (game.doors.tim[x][y] < 2 * 64 + 1) {
                                    pos = 64;
                                } else {
                                    pos = 64 - (game.doors.tim[x][y] - (2 * 64 + 1));
                                }

                                if (game.doors.tim[x][y] == 5) {
                                    game.sound.playSound("dooropensound.wav");
                                }else if (game.doors.tim[x][y] == 131) {
                                    game.sound.playSound("doorclosesound.wav");
                                }

                                game.mapa.deltaPos[x][y].setLocation(pos, pos);

                                if (game.doors.tim[x][y] > 191) {
                                    game.doors.tim[x][y] = 0;
                                }
                            }

                            break;
                        case 22:
                            if (HP[x][y] > 0 && HP[x][y] < 5) {


                                // Wybuch beczki

                                final int radius = 1;
                                for (int xa = x - radius; xa < x + radius; xa++) {
                                    for (int ya = y - radius; ya < y + radius; ya++) {
                                        if (inside(xa, ya)) {

                                            if(HP[xa][ya]<5) {
                                                HP[xa][ya]++;
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }
            }


    }
}
