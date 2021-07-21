package Raycaster.Display.Raycaster;

import Raycaster.Display.Raycaster.RenderedBlocks.Box;
import Raycaster.Project.Game;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class Raycasting {

    public Game game;

    public double myAngle = Math.toRadians(90);
    private Point2D myPos = new Point2D.Double(60,50);

    private static double angleDelta = Math.toRadians(15);
    private static double angleStep = Math.toRadians(0.1875/2);

    public double deltaTex = 0.05;

    public static double maxLen = 48;


    private double tempCos;
    private double tempSin;
    public double tempCosB;

    private int[][] mapa;

    public Point2D analysePos;

    public BufferedImage bufferImg;
    private Graphics grphx;

    private Box box;
    private Floor floor;

    ArrayList<Column> columns;

    public final int half;
    public int[][] foo;

    public Raycasting(Game game){

        this.game = game;
        bufferImg = new BufferedImage(game.render.renderSize.x,game.render.renderSize.y, BufferedImage.TYPE_INT_RGB);
        columns = new ArrayList<Column>();

        grphx = bufferImg.getGraphics();
        floor = new Floor(this);
        box = new Box(this);
        foo = new int[game.render.renderSize.y][game.render.renderSize.x];

        half = game.render.renderSize.y / 2;
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
            columns.clear();

            myAngle = game.playerTransform.rotation;
            myPos = game.playerTransform.postion;

            Point lastPoint= new Point(0,0);

            int nStep = 0;

            Point lastPointOfMap = new Point(0,0);



            for(double angle = myAngle - angleDelta;angle<myAngle+angleDelta;angle +=angleStep){
                tempCos = Math.cos(angle);
                tempSin = Math.sin(angle);
                tempCosB =  Math.cos(Math.abs(myAngle - angle));

                for(double len=0;len<maxLen;len +=0.01){
                    analysePos = new Point2D.Double(myPos.getX()+len * tempCos, myPos.getY()+len * tempSin);

                    Point zaokraglij = new Point((int)(analysePos.getX()*16),(int)(analysePos.getY()*16));

                    if(zaokraglij != lastPointOfMap) {
                        if (inside()) {
                            if (mapa[(int) analysePos.getX()][(int) analysePos.getY()] == 1) {


                                box.drawBox(nStep, len, angle, columns, foo);
                                len = maxLen;
                            } else {

                                if (len < 30) {

                                    Point punkta = new Point(nStep, half);

                                    floor.floor(punkta, len, angle, lastPoint);
                                    lastPoint = punkta;
                                }
                            }
                        } else {
                            len = maxLen;
                        }
                    }

                    lastPointOfMap = zaokraglij;

                }
                nStep++;

            }


            array_rasterToBuffer(foo);


            for(Column col : columns) {
                if (col.darker) {
                    grphx.drawImage(game.texture.getColumnDarker(col.index),(int)col.rect.getX(),(int)col.rect.getY(),(int)col.rect.getWidth(),(int)col.rect.getHeight(),null);
                }else{
                    grphx.drawImage(game.texture.getColumn(col.index),(int)col.rect.getX(),(int)col.rect.getY(),(int)col.rect.getWidth(),(int)col.rect.getHeight(),null);
                }
            }

            g.drawImage(bufferImg,0,0,null);

        }catch (ConcurrentModificationException ignore){}



    }



    public void array_rasterToBuffer(int[][] img) {
        final int width = game.render.renderSize.x;
        final int height = game.render.renderSize.y;

        int[] pixels = new int[width * height];

        int n = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                pixels[n] = img[y][x];
                n++;
            }
        }

        WritableRaster rast = bufferImg.getRaster();

        rast.setDataElements(0, 0, width, height, pixels);

    }


}
