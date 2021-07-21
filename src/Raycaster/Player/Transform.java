package Raycaster.Player;

import java.awt.geom.Point2D;

public class Transform {
    public double rotation = 0;
    public Point2D postion = new Point2D.Double(64,64);

    private double deltaR = Math.PI/2;

    public void translate(int kieruek,double len){
        if(kieruek == getUp()) {
            postion = new Point2D.Double(postion.getX() + len * Math.cos(rotation), postion.getY() + len * Math.sin(rotation));
        }else if(kieruek == getDown()){
            postion = new Point2D.Double(postion.getX() - len * Math.cos(rotation), postion.getY() - len * Math.sin(rotation));
        }else if(kieruek == getLeft()){
            postion = new Point2D.Double(postion.getX() + (len/2) * Math.cos(rotation-deltaR), postion.getY() + (len/2) * Math.sin(rotation-deltaR));
        }else if(kieruek == getRight()){
            postion = new Point2D.Double(postion.getX() + (len/2) * Math.cos(rotation+deltaR), postion.getY() + (len/2)* Math.sin(rotation+deltaR));
        }
    }

    public void rotate(int kieruek,double len){
        if(kieruek == getUp()) {
            rotation +=len;
        }else if(kieruek == getDown()){
            rotation -=len;
        }
    }

    public static int getUp(){return 0;}
    public static int getDown(){return 1;}
    public static int getLeft(){return 2;}
    public static int getRight(){return 3;}
}
