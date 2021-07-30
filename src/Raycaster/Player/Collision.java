package Raycaster.Player;

import java.awt.geom.Point2D;

public class Collision {
    Map mapa;
    public Collision(Map mapa){
        this.mapa = mapa;
    }

    public boolean collide(Point2D position) {

        if (mapa.mapa[(int) position.getX()][(int) position.getY()] > 0) {
            boolean decyzja;
            int type = mapa.mapa[(int) position.getX()][(int) position.getY()];

            final double partX = (position.getX() - (int) position.getX());
            final double partY = (position.getY() - (int) position.getY());

            switch (type) {
                case 20:
                case 21:
                case 4:
                case 5:
                    decyzja = mapa.deltaPos[(int) position.getX()][(int) position.getY()].x<32;
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
            }

            return decyzja;
        } else {
            return false;
        }
    }
}
