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

    private static final double angleDelta = Math.toRadians(15);
    public static final double angleStep = Math.toRadians(0.1875/2);
    public static double maxLen = 48;


    public double tempCosB;

    private int[][] mapa;

    public Point2D analysePos;

    public BufferedImage bufferImg;

    private final Box box;
    private final Floor floor;
    private final ObjColumn ocolumn;
    private final HalfBox hbox;
    private final SpriteX spriteX;
    private final SpriteY spriteY;
    private final Sprite sprite;
    private final Triangle tria;

    public ArrayList<Column> columns;
    public ArrayList<SpriteQueue> sprites;

    public final int half;
    public int[][] foo;

    public boolean[] rayHalfBlocked ;

    public Point2D myPos;

    public Raycasting(Game game){

        this.game = game;
        bufferImg = new BufferedImage(game.render.renderSize.x,game.render.renderSize.y, BufferedImage.TYPE_INT_RGB);

        columns = new ArrayList<>();

        sprites = new ArrayList<>();
        for(int n=0;n<maxLen*10;n++){
            sprites.add(new SpriteQueue());
        }

        floor = new Floor(this);
        box = new Box(this);
        hbox = new HalfBox(this);
        ocolumn = new ObjColumn(this);
        spriteX = new SpriteX(this);
        spriteY = new SpriteY(this);
        sprite = new Sprite(this);
        tria = new Triangle(this);

        rayHalfBlocked = new boolean[320];

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


            for (SpriteQueue spriteQueue : sprites) {
                spriteQueue.clear();
            }

            rayHalfBlocked = new boolean[320];
            byte[][] renderedSprites = new byte[128][128];

            myAngle = game.playerTransform.rotation;
            myPos = game.playerTransform.postion;


            int nStep = 0;

            Point lastPointOfMap = new Point(0,0);
            boolean floorRay = false;

            for(double angle = myAngle - angleDelta;angle<myAngle+angleDelta;angle +=angleStep){

                floorRay = !floorRay;

                final double tempCos = Math.cos(angle);
                final double tempSin = Math.sin(angle);
                tempCosB =  Math.cos(Math.abs(myAngle - angle));

                Point largeLastPointAnalyse = new Point();

                for(double len=0;len<maxLen;len +=0.015){


                    analysePos = new Point2D.Double(myPos.getX()+len * tempCos, myPos.getY()+len * tempSin);

                    final Point zaokraglij = new Point((int)(analysePos.getX()*64),(int)(analysePos.getY()*64));
                    final Point largePointAnalyse = new Point((int) analysePos.getX(),(int) analysePos.getY());

                    if(!zaokraglij.equals(lastPointOfMap)) {

                        final Point punkta = new Point(nStep, half);

                        if (inside()) {
                            switch (mapa[(int) analysePos.getX()][(int) analysePos.getY()]){
                                case 1:
                                    box.drawBox(punkta, len,columns,foo);
                                    len = maxLen;
                                    break;
                                case 2:

                                    if(largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        hbox.drawBox(nStep, punkta, len);
                                        largeLastPointAnalyse = largePointAnalyse;
                                    }

                                    if (len < 30 && floorRay) {
                                        floor.floor(punkta, len);

                                    }

                                    break;
                                case 3:

                                    if(ocolumn.drawBox(punkta, len, columns)) len = maxLen;
                                    else{
                                        if (len < 30 && floorRay) {
                                            floor.floor(punkta, len);

                                        }
                                    }
                                    break;
                                case 4:
                                case 5:


                                    if(largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        boolean ok=false;

                                        switch (mapa[(int) analysePos.getX()][(int) analysePos.getY()]){
                                            case 4:
                                                ok = spriteX.drawBox(nStep, punkta, len);
                                                break;
                                            case 5:
                                                ok = spriteY.drawBox(nStep, punkta, len);
                                                break;

                                        }

                                        if(ok){
                                            largeLastPointAnalyse = largePointAnalyse;
                                        }
                                    }

                                    if (len < 30  && floorRay) {
                                        floor.floor(punkta, len);

                                    }

                                    break;
                                case 6:


                                    if(renderedSprites[(int) analysePos.getX()][(int) analysePos.getY()] == 0) {
                                        boolean ok =sprite.drawBox(nStep, punkta, len,angle);

                                        if(ok) {
                                            renderedSprites[(int) analysePos.getX()][(int) analysePos.getY()] = 1;
                                        }
                                    }

                                    if (len < 30  && floorRay) {
                                        floor.floor(punkta, len);

                                    }

                                    break;
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                    int type = mapa[(int) analysePos.getX()][(int) analysePos.getY()] - 7;

                                    if(tria.drawBox(punkta, len, columns,type)) len = maxLen;
                                    else{
                                        if (len < 30 && floorRay) {
                                            floor.floor(punkta, len);

                                        }
                                    }
                                    break;
                                default:
                                    if (len < 30  && floorRay) {
                                        floor.floor(punkta, len);

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

            for(Column col : columns) {

                col.render(n, this, game.texture, true);
                n++;
            }
            for(n=0;n<sprites.size();n++) {
                SpriteQueue queue = sprites.get(sprites.size()-1-n);
                    for(int k=queue.getSize()-1;k>=0;k--) {
                        final Column columnS = queue.get(k);

                        if (columnS.half) {
                            columnS.render(n,this, game.texture,false);
                        } else {
                            columnS.render(n,this, game.sprite,false);
                        }
                    }


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

        final WritableRaster rast = bufferImg.getRaster();

        rast.setDataElements(0, 0, width, height, pixels);

    }


}
