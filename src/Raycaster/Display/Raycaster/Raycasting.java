package Raycaster.Display.Raycaster;

import Raycaster.Display.Raycaster.RenderedBlocks.*;
import Raycaster.Project.Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Raycasting {

    public Game game;

    public static double renderHeightConstant = 30;
    public double myAngle = Math.toRadians(90);
    private Point2D myPos = new Point2D.Double(60,50);

    private static double angleDelta = Math.toRadians(15);
    private static double angleStep = Math.toRadians(0.1875/2);
    public static double maxLen = 48;


    private double tempCos;
    private double tempSin;
    public double tempCosB;

    private int[][] mapa;

    public Point2D analysePos;

    public BufferedImage bufferImg;

    private Box box;
    private Floor floor;
    private ObjColumn ocolumn;
    private HalfBox hbox;
    private SpriteX spriteX;
    private SpriteY spriteY;

    public ArrayList<Column> columns;
    public ArrayList<SpriteQueue> sprites;

    public final int half;
    public int[][] foo;

    public Raycasting(Game game){

        this.game = game;
        bufferImg = new BufferedImage(game.render.renderSize.x,game.render.renderSize.y, BufferedImage.TYPE_INT_RGB);

        columns = new ArrayList<Column>();
        sprites = new ArrayList<SpriteQueue>();

        floor = new Floor(this);
        box = new Box(this);
        hbox = new HalfBox(this);
        ocolumn = new ObjColumn(this);
        spriteX = new SpriteX(this);
        spriteY = new SpriteY(this);

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


    public void draw(){
        try{
            loadMap();

            columns.clear();
            sprites.clear();

            for(int n=0;n<game.render.renderSize.x;n++){
                sprites.add(new SpriteQueue());
            }



            myAngle = game.playerTransform.rotation;
            myPos = game.playerTransform.postion;

            Point lastPoint= new Point(0,0);

            int nStep = 0;

            Point lastPointOfMap = new Point(0,0);

            boolean floorOpen = false;

            for(double angle = myAngle - angleDelta;angle<myAngle+angleDelta;angle +=angleStep){


                tempCos = Math.cos(angle);
                tempSin = Math.sin(angle);
                tempCosB =  Math.cos(Math.abs(myAngle - angle));

                Point largeLastPointAnalyse = new Point();

                for(double len=0;len<maxLen;len +=0.01){
                    analysePos = new Point2D.Double(myPos.getX()+len * tempCos, myPos.getY()+len * tempSin);

                    Point zaokraglij = new Point((int)(analysePos.getX()*16),(int)(analysePos.getY()*16));
                    Point largePointAnalyse = new Point((int) analysePos.getX(),(int) analysePos.getY());

                    if(zaokraglij != lastPointOfMap) {

                        Point punkta = new Point(nStep, half);

                        if (inside()) {
                            switch (mapa[(int) analysePos.getX()][(int) analysePos.getY()]){
                                case 1:
                                    box.drawBox(punkta, len, angle, columns, foo);
                                    len = maxLen;
                                    break;
                                case 2:

                                    if(largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        hbox.drawBox(nStep, punkta, len, angle, columns, foo);
                                        largeLastPointAnalyse = largePointAnalyse;
                                    }

                                    if (len < 30) {
                                        floor.floor(punkta, len, angle, lastPoint);
                                        lastPoint = punkta;
                                    }

                                    break;
                                case 3:
                                    if(ocolumn.drawBox(punkta, len, angle, columns, foo)) len = maxLen;
                                    else{
                                        if (len < 30) {
                                            floor.floor(punkta, len, angle, lastPoint);
                                            lastPoint = punkta;
                                        }
                                    }
                                    break;
                                case 4:
                                case 5:

                                    if(largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        boolean ok;

                                        if(mapa[(int) analysePos.getX()][(int) analysePos.getY()] == 4){
                                            ok = spriteX.drawBox(nStep, punkta, len, angle, columns, foo);
                                        }else{
                                            ok = spriteY.drawBox(nStep, punkta, len, angle, columns, foo);
                                        }

                                        if(ok){
                                            largeLastPointAnalyse = largePointAnalyse;
                                        }
                                    }

                                    if (len < 30) {
                                        floor.floor(punkta, len, angle, lastPoint);
                                        lastPoint = punkta;
                                    }

                                    break;
                                default:
                                    if (len < 30) {
                                        floor.floor(punkta, len, angle, lastPoint);
                                        lastPoint = punkta;
                                    }
                                    break;

                            }

                        } else {
                            len = maxLen;
                        }


                    }

                    lastPointOfMap = zaokraglij;

                }
                nStep+=2;

            }

            int n=0;
            boolean renderuj = true;

            for(Column col : columns) {

                col.render(this,game.texture);

                    SpriteQueue queue = sprites.get(n);

                    for(int k=queue.getSize()-1;k>=0;k--){
                        Column columnS = queue.get(k);

                        columnS.render(this,game.sprite);
                    }

                renderuj = !renderuj;
                n++;

            }


            array_rasterToBuffer(foo);


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
