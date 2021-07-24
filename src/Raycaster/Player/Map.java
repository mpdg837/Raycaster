package Raycaster.Player;

import java.util.Arrays;

public class Map {
    public int[][] mapa = new int[128][128];

    public int[][] ceciling = new int[128][128];
    public int[][] floor = new int[128][128];

    public boolean[][] light = new boolean[128][128];

    public Map(){
        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                light[x][y] = true;
            }
        }
    }
}
