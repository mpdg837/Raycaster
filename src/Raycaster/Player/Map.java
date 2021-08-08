package Raycaster.Player;

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

    public Map(){
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

    int tim = 0;
    public void analyse(){

        tim++;

        if(tim>10) {
            tim = 0;
            for (int x = 0; x < 128; x++) {
                for (int y = 0; y < 128; y++) {
                    switch (mapa[x][y]) {
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
}
