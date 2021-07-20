package Raycaster.Player;

import java.awt.geom.Point2D;

public class Collision {
    Map mapa;
    public Collision(Map mapa){
        this.mapa = mapa;
    }

    public boolean collide(Point2D position){
        return mapa.mapa[(int)position.getX()][(int)position.getY()]>0;
    }
}
