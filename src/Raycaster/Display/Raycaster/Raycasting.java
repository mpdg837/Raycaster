package Raycaster.Display.Raycaster;

import Raycaster.Display.BufferTranslator;
import Raycaster.Display.Raycaster.RenderTools.Column;
import Raycaster.Display.Raycaster.RenderTools.Floor;
import Raycaster.Display.Raycaster.RenderTools.SpriteQueue;
import Raycaster.Display.Raycaster.RenderedBlocks.*;
import Raycaster.Display.Raycaster.RenderedBlocks.Boxes.*;
import Raycaster.Display.Raycaster.RenderedBlocks.Boxes.Half.HalfBox;
import Raycaster.Display.Raycaster.RenderedBlocks.Boxes.Half.HalfQuaterBox;
import Raycaster.Display.Raycaster.RenderedBlocks.Sprites.Sprite;
import Raycaster.Display.Raycaster.RenderedBlocks.Sprites.SpriteX;
import Raycaster.Display.Raycaster.RenderedBlocks.Sprites.SpriteXY;
import Raycaster.Display.Raycaster.RenderedBlocks.Sprites.SpriteY;
import Raycaster.Display.Textures.Texture;
import Raycaster.Project.Game;
import Raycaster.Raycaster;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class Raycasting {

    public Game game;

    public double renderHeightConstant = 30;
    public double myAngle = Math.toRadians(90);

    private static final double angleDelta = Math.toRadians(15);
    public static final double angleStep = Math.toRadians(0.09);
    public static final double deltaLen = 0.015;

    public static double maxLen = 48;


    public double tempCosB;

    private byte[][] mapa;

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
    private final SpriteXY sprXY;
    private final QuaterBox quFull;
    private final HalfQuaterBox quHalf;

    public ArrayList<Column> columns;
    public SpriteQueue[] sprites;

    public final int half;
    public int[][] foo;

    public boolean[] rayHalfBlocked ;

    public Point2D myPos;
    public boolean darkerMe;

    public double partX;
    public double partY;
    public int posX;
    public int posY;

    public Raycasting(Game game){

        this.game = game;
        bufferImg = new BufferedImage(Raycaster.resolution.x, Raycaster.resolution.y, BufferedImage.TYPE_INT_RGB);

        columns = new ArrayList<>();

        sprites = new SpriteQueue[(int)(maxLen*10)];
        for(int n=0;n<sprites.length;n++){
            sprites[n] = new SpriteQueue();
        }

        floor = new Floor(this);
        box = new Box(this);
        hbox = new HalfBox(this);
        ocolumn = new ObjColumn(this);
        spriteX = new SpriteX(this);
        spriteY = new SpriteY(this);
        sprite = new Sprite(this);
        tria = new Triangle(this);
        sprXY = new SpriteXY(this);
        quFull = new QuaterBox(this);
        quHalf = new HalfQuaterBox(this);

        rayHalfBlocked = new boolean[320];
        half = Raycaster.resolution.y >> 1;
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

    public void prebake(){

        Arrays.fill(rayHalfBlocked,false);

        byte[][] renderedSprites = new byte[128][128];
        foo = new int[Raycaster.resolution.y][Raycaster.resolution.x];

        myAngle = game.playerTransform.rotation;
        myPos = game.playerTransform.postion;


        int nStep = 0;

        Point lastPointOfMap = new Point(0,0);
        boolean floorRay = false;


        double actAngleDelta = angleDelta;
        double actStep = angleStep;
        renderHeightConstant = 30;

        if (game.camera.zoom>1){
            actAngleDelta = angleDelta/game.camera.zoom;
            actStep = angleStep/game.camera.zoom;
            renderHeightConstant=30*game.camera.zoom;
        }

        double cx = 0;
        double cy = 0;

        analysePos = new Point2D.Double(0,0);

        final double min = myAngle - actAngleDelta;
        final double max = myAngle + actAngleDelta;


        for(double angle =  min ;angle< max;angle +=actStep) {

                floorRay = !floorRay;

                tempCosB = game.getCos(Math.abs(myAngle - angle));

                Point largeLastPointAnalyse = new Point();


                final double deltaX = deltaLen * game.getCos(angle);
                final double deltaY = deltaLen * game.getSin(angle);

                cx = myPos.getX();
                cy = myPos.getY();


                final double deltaZ = tempCosB * deltaLen;
                double height = Raycasting.maxLen;
                double zet = 0;


                for (double len = 0; len < maxLen - 18; len += deltaLen) {

                    cx += deltaX;
                    cy += deltaY;

                    zet +=deltaZ;
                    height -= deltaLen;



                    final Point zaokraglij = new Point((int) (cx * 64), (int) (cy * 64));


                    if (!zaokraglij.equals(lastPointOfMap)) {



                        if (inside() )  {
                            final int wallHeight = (int) (renderHeightConstant * height / zet);
                            final Point largePointAnalyse = new Point((int) cx, (int) cy);

                            partX = (cx - (int) cx);
                            partY = (cy - (int) cy);

                            posX = (int) (partX * 64);
                            posY = (int) (partY * 64);

                            final Point punkta = new Point(nStep, half);

                            analysePos.setLocation(cx, cy);
                            switch (mapa[(int) cx][(int) cy]) {
                                case 1:

                                    if (box.drawBox((int)height,wallHeight,punkta, len, columns, foo)) len = maxLen;
                                    else {
                                        if (floorRay) {
                                            floor.floor(wallHeight,punkta);

                                        }
                                    }

                                    break;
                                case 2:

                                    if (largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        hbox.drawBox((int)height,wallHeight,nStep, punkta, len);
                                        largeLastPointAnalyse = largePointAnalyse;
                                    }

                                    if (len < 30 && floorRay) {
                                        floor.floor(wallHeight,punkta);

                                    }

                                    break;
                                case 3:

                                    if (ocolumn.drawBox((int)height,wallHeight,punkta, len, columns)) len = maxLen;
                                    else {
                                        if (len < 30 && floorRay) {
                                            floor.floor(wallHeight,punkta);

                                        }
                                    }
                                    break;
                                case 4:
                                case 5:
                                case 20:
                                case 21:

                                    if (largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        boolean ok = false;

                                        switch (mapa[(int) cx][(int) cy]) {
                                            case 4:
                                            case 20:// Drzwi
                                                ok = spriteX.drawBox((int)height,wallHeight,nStep, punkta, len);
                                                break;
                                            case 5:
                                            case 21:// Drzwi
                                                ok = spriteY.drawBox((int)height,wallHeight,nStep, punkta, len);
                                                break;

                                        }

                                        if (ok) {
                                            largeLastPointAnalyse = largePointAnalyse;
                                        }
                                    }

                                    if (floorRay) {
                                        floor.floor(wallHeight,punkta);

                                    }

                                    break;
                                case 6:


                                    if (renderedSprites[(int) cx][(int) cy] == 0) {
                                        boolean ok = sprite.drawBox((int)height,wallHeight,nStep, punkta, len, angle);

                                        if (ok) {
                                            renderedSprites[(int) cx][(int) cy]= 1;
                                        }
                                    }

                                    if (floorRay) {
                                        floor.floor(wallHeight,punkta);

                                    }

                                    break;
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                    int type = mapa[(int) cx][(int) cy]- 7;

                                    if (tria.drawBox((int)height,wallHeight,punkta, len, columns, type)) len = maxLen;
                                    else {
                                        if (floorRay) {
                                            floor.floor(wallHeight,punkta);

                                        }
                                    }
                                    break;
                                case 11:
                                    sprXY.drawBox((int)height,wallHeight,nStep, punkta, len);

                                    if (floorRay) {
                                        floor.floor(wallHeight,punkta);

                                    }

                                    break;
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                    int typeA = mapa[(int) cx][(int) cy]-12;


                                    if (quFull.drawBox((int)height,wallHeight,punkta, len, columns, foo, typeA)) len = maxLen;
                                    else {
                                        if (floorRay) {
                                            floor.floor(wallHeight,punkta);

                                        }
                                    }

                                    break;
                                case 16:
                                case 17:
                                case 18:
                                case 19:
                                    int typeB = mapa[(int) cx][(int) cy]-16;

                                    if (largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                        if (quHalf.drawBox((int)height,wallHeight,nStep, punkta, len, typeB))
                                            largeLastPointAnalyse = largePointAnalyse;
                                    }

                                    if (floorRay) {
                                        floor.floor(wallHeight,punkta);

                                    }


                                    break;
                                default:
                                    if (floorRay) {
                                        floor.floor(wallHeight,punkta);

                                    }
                                    break;

                            }

                        } else {
                            len = maxLen;
                        }


                    }



                    lastPointOfMap = zaokraglij;

                }
                nStep += 2;


        }
    }

    public void draw(){

            loadMap();

            columns.clear();


            for (SpriteQueue spriteQueue : sprites) {
                spriteQueue.clear();
            }

            prebake();


            int n=0;

            for(Column col : columns) {

                final Texture myTex = game.texture.textures[game.mapa.textures[col.objPosition.x][col.objPosition.y]];
                col.render(n, this, myTex, true);
                n++;
            }


            for(n=0;n<sprites.length;n++) {
                SpriteQueue queue = sprites[sprites.length-1-n];
                    for(int k=queue.getSize()-1;k>=0;k--) {

                        final Column columnS = queue.get(k);

                        if (columnS.half) {
                            final Texture myTex = game.texture.textures[game.mapa.textures[columnS.objPosition.x][columnS.objPosition.y]];
                            columnS.render(n,this, myTex,false);
                        } else {
                            final Texture myTex = game.sprite.textures[game.mapa.textures[columnS.objPosition.x][columnS.objPosition.y]];
                            columnS.render(n,this, myTex,false);
                        }
                    }


            }

            BufferTranslator.array_rasterToBuffer(foo,game,bufferImg);






    }






}
