package Raycaster.Player.InteractiveObjects;

import Raycaster.Project.Game;

import java.awt.*;

public class Doors {

    private final int[][] tim = new int[128][128];
    private final Game game;

    public Doors(Game game){
        this.game = game;
    }

    private void openIt(Point pos){
        switch (game.mapa.mapa[pos.x][pos.y]){
            case 20:
            case 21:

                if(tim[pos.x][pos.y]==0){
                    tim[pos.x][pos.y]=1;
                }

                break;
        }
    }
    public void open(){
        Point myPos = new Point((int)this.game.playerTransform.postion.getX(),(int)this.game.playerTransform.postion.getY());
        if(myPos.x>0 && myPos.x<127){
            if(myPos.y>0 && myPos.y<127){
                openIt(new Point(myPos.x+1, myPos.y));
                openIt(new Point(myPos.x-1, myPos.y));
                openIt(new Point(myPos.x, myPos.y+1));
                openIt(new Point(myPos.x, myPos.y-1));
            }
        }
    }

    public void update(){
        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                if(tim[x][y]>0){
                    tim[x][y]+=2;

                    int pos;

                    if(tim[x][y]<64){
                        pos = tim[x][y];
                    }else if(tim[x][y]<2*64+1){
                        pos = 64;
                    }else{
                        pos = 64 - (tim[x][y] - (2*64+1));
                    }
                    game.mapa.deltaPos[x][y].setLocation(pos,pos);

                    if(tim[x][y]>191){
                        tim[x][y]=0;
                    }
                }
            }
        }
    }
}
