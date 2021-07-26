package Raycaster.Player;

import java.util.Arrays;

public class Map {
    public byte[][] mapa = new byte[128][128];
    public byte[][] HP = new byte[128][128];

    public byte[][] ceciling = new byte[128][128];
    public byte[][] floor = new byte[128][128];

    public boolean[][] light = new boolean[128][128];

    public Map(){
        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                light[x][y] = true;
            }
        }
    }
}
