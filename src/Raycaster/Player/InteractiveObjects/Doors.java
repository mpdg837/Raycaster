package Raycaster.Player.InteractiveObjects;

import Raycaster.Project.Game;

import java.awt.*;

public class Doors {

    public final int[][] tim = new int[128][128];
    private final Game game;

    public boolean canUse;

    public Doors(Game game){
        this.game = game;
    }

    private boolean canOpenIt(Point pos){
        switch (game.mapa.mapa[pos.x][pos.y]){
            case 20:
            case 21:

                if(tim[pos.x][pos.y]==0){
                    return true;
                }

                break;
        }
        return false;
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

    public boolean checkOpen(){
        Point myPos = new Point((int)this.game.playerTransform.postion.getX(),(int)this.game.playerTransform.postion.getY());
        if(myPos.x>0 && myPos.x<127){
            if(myPos.y>0 && myPos.y<127){
                return canOpenIt(new Point(myPos.x+1, myPos.y)) | canOpenIt(new Point(myPos.x-1, myPos.y)) | canOpenIt(new Point(myPos.x, myPos.y+1))|canOpenIt(new Point(myPos.x, myPos.y-1));
            }
        }
        return false;
    }


    public void update(){

        canUse = checkOpen();

    }
}
