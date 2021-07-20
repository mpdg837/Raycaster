package Raycaster.Display.Raycaster;

import Raycaster.Project.Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;

public class Raycasting {

    private Game game;

    private double myAngle = Math.toRadians(90);
    private Point2D myPos = new Point2D.Double(60,50);

    private static double angleDelta = Math.toRadians(15);
    private static double angleStep = Math.toRadians(0.1875/2);

    private double deltaTex = 0.05;

    private static double maxLen = 48;


    private double tempCos;
    private double tempSin;

    private int[][] mapa;

    private Point2D analysePos;
    public Raycasting(Game game){
        this.game = game;
    }

    public void loadMap(){
        this.mapa = game.mapa.mapa;

    }

    public boolean inside(){

        if(analysePos.getX() >= 0 && analysePos.getX() < 128){
            return analysePos.getY() >= 0 && analysePos.getY() < 128;
        }else{
            return false;
        }
    }
    public void draw(Graphics2D g){
        try{
            loadMap();

            myAngle = game.playerTransform.rotation;
            myPos = game.playerTransform.postion;

            int nStep = 0;
            for(double angle = myAngle - angleDelta;angle<myAngle+angleDelta;angle +=angleStep){
                tempCos = Math.cos(angle);
                tempSin = Math.sin(angle);

                for(double len=0;len<maxLen;len +=0.01){
                    analysePos = new Point2D.Double(myPos.getX()+len * tempCos, myPos.getY()+len * tempSin);

                    if(inside()) {
                        if (mapa[(int) analysePos.getX()][(int) analysePos.getY()] == 1) {
                            Point punkt = new Point(nStep, this.game.render.renderSize.y/2);

                            double height =((maxLen - len));
                            int indexTex = 0;


                            if (height > 0) {

                                // Wybór tekstury
                                double partX = (analysePos.getX() - (int) analysePos.getX());
                                double partY = (analysePos.getY() - (int) analysePos.getY());


                                boolean cien = partY<0.01 || partY>0.99;

                                if(partY<deltaTex || partY>1-deltaTex) {

                                    indexTex = (int) (partX * 64);
                                }else{
                                    indexTex = (int) (partY * 64);
                                }

                                // Wyznaczenie tekstury

                                double zet = Math.cos(Math.abs(myAngle - angle)) * len;
                                int wallHeight = (int)(18 * height/zet);

                                if(cien){
                                    g.drawImage(game.texture.getColumnDarker(indexTex), punkt.x, punkt.y - wallHeight / 2, 1, wallHeight, null);
                                }else {
                                    g.drawImage(game.texture.getColumn(indexTex), punkt.x, punkt.y - wallHeight / 2, 1, wallHeight, null);
                                }
                            }

                            len = maxLen;
                        }
                    }else{
                        len = maxLen;
                    }
                }
                nStep++;

            }
        }catch (ConcurrentModificationException ignore){}


    }
}
