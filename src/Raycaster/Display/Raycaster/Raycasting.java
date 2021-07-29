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

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.util.ArrayList;
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
        bufferImg = new BufferedImage(game.render.renderSize.x,game.render.renderSize.y, BufferedImage.TYPE_INT_RGB);

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

        foo = new int[game.render.renderSize.y][game.render.renderSize.x];

        half = game.render.renderSize.y >> 1;
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
        rayHalfBlocked = new boolean[320];
        byte[][] renderedSprites = new byte[128][128];

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

        for(double angle = myAngle - actAngleDelta ;angle<myAngle+actAngleDelta;angle +=actStep){

            floorRay = !floorRay;

            tempCosB =  Math.cos(Math.abs(myAngle - angle));

            Point largeLastPointAnalyse = new Point();


            final double deltaX = deltaLen * Math.cos(angle);
            final double deltaY = deltaLen * Math.sin(angle);

            cx = myPos.getX();
            cy = myPos.getY();

            for(double len=0;len<maxLen-18;len +=deltaLen){

                cx += deltaX;
                cy += deltaY;

                analysePos.setLocation(cx,cy);

                final Point zaokraglij = new Point((int)(analysePos.getX()*64),(int)(analysePos.getY()*64));
                final Point largePointAnalyse = new Point((int) analysePos.getX(),(int) analysePos.getY());

                partX = (analysePos.getX() - (int) analysePos.getX());
                partY = (analysePos.getY() - (int) analysePos.getY());

                posX = (int)(partX*64);
                posY = (int)(partY*64);

                if(!zaokraglij.equals(lastPointOfMap)) {

                    final Point punkta = new Point(nStep, half);

                    if (inside()) {
                        switch (mapa[(int) analysePos.getX()][(int) analysePos.getY()]){
                            case 1:

                                if(box.drawBox(punkta, len,columns,foo)) len = maxLen;
                                else{
                                    if ( floorRay) {
                                        floor.floor(punkta, len);

                                    }
                                }

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

                                if ( floorRay) {
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

                                if ( floorRay) {
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
                                    if ( floorRay) {
                                        floor.floor(punkta, len);

                                    }
                                }
                                break;
                            case 11:
                                sprXY.drawBox(nStep, punkta, len);

                                if ( floorRay) {
                                    floor.floor(punkta, len);

                                }

                                break;
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                                int typeA=0;
                                switch (mapa[(int) analysePos.getX()][(int) analysePos.getY()]){
                                    case 12:
                                        typeA = 0;
                                        break;

                                    case 13:
                                        typeA = 1;
                                        break;
                                    case 14:
                                        typeA = 2;
                                        break;
                                    case 15:
                                        typeA = 3;
                                        break;
                                }

                                if(quFull.drawBox(punkta, len,columns,foo,typeA)) len = maxLen;
                                else{
                                    if ( floorRay) {
                                        floor.floor(punkta, len);

                                    }
                                }

                                break;
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                                int typeB=0;
                                switch (mapa[(int) analysePos.getX()][(int) analysePos.getY()]){
                                    case 16:
                                        typeB = 0;
                                        break;

                                    case 17:
                                        typeB = 1;
                                        break;
                                    case 18:
                                        typeB = 2;
                                        break;
                                    case 19:
                                        typeB = 3;
                                        break;
                                }

                                if(largePointAnalyse.getX() != largeLastPointAnalyse.getX() || largePointAnalyse.getY() != largeLastPointAnalyse.getY()) {
                                    if(quHalf.drawBox(nStep, punkta, len,typeB)) largeLastPointAnalyse = largePointAnalyse;
                                }

                                if (floorRay) {
                                    floor.floor(punkta, len);

                                }


                                break;
                            default:
                                if ( floorRay) {
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

                final Texture myTex = game.texture.textures[game.mapa.textures[(int)col.objPosition.x][(int)col.objPosition.y]];
                col.render(n, this, myTex, true);
                n++;
            }


            for(n=0;n<sprites.length;n++) {
                SpriteQueue queue = sprites[sprites.length-1-n];
                    for(int k=queue.getSize()-1;k>=0;k--) {

                        final Column columnS = queue.get(k);

                        if (columnS.half) {
                            final Texture myTex = game.texture.textures[game.mapa.textures[(int)columnS.objPosition.x][(int)columnS.objPosition.y]];
                            columnS.render(n,this, myTex,false);
                        } else {
                            final Texture myTex = game.sprite.textures[game.mapa.textures[(int)columnS.objPosition.x][(int)columnS.objPosition.y]];
                            columnS.render(n,this, myTex,false);
                        }
                    }


            }

            BufferTranslator.array_rasterToBuffer(foo,game,bufferImg);






    }






}
