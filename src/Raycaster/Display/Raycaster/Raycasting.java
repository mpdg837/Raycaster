package Raycaster.Display.Raycaster;

import Raycaster.Display.Raycaster.RenderedBlocks.Box;
import Raycaster.Project.Game;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.awt.image.*;
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

    private int[][] mapa;

    public Point2D analysePos;

    public BufferedImage bufferImg;

    public Raycasting(Game game){

        this.game = game;
        bufferImg = new BufferedImage(game.render.renderSize.x,game.render.renderSize.y, BufferedImage.TYPE_INT_RGB);
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

            int[][] foo = new int[game.render.renderSize.y][game.render.renderSize.x];


            myAngle = game.playerTransform.rotation;
            myPos = game.playerTransform.postion;

            Point lastPoint= new Point(0,0);

            int nStep = 0;
            for(double angle = myAngle - angleDelta;angle<myAngle+angleDelta;angle +=angleStep){
                tempCos = Math.cos(angle);
                tempSin = Math.sin(angle);

                for(double len=0;len<maxLen;len +=0.01){
                    analysePos = new Point2D.Double(myPos.getX()+len * tempCos, myPos.getY()+len * tempSin);

                    if(inside()) {
                        if (mapa[(int) analysePos.getX()][(int) analysePos.getY()] == 1) {
                            Box box = new Box(this);

                            box.drawBox(nStep,len,angle,g);
                            len = maxLen;
                        }else{



                            Point punkta = new Point(nStep, game.render.renderSize.y / 2);

                            double height = ((Raycasting.maxLen - len));

                            double zet = Math.cos(Math.abs(myAngle - angle)) * len;
                            int wallHeight = (int) (18 * height / zet);

                            punkta = new Point((int) punkta.x, (int) (punkta.y + wallHeight / 2));



                            if ( punkta.x < 320) {
                                if ( punkta.y < 240) {
                                    if(punkta !=lastPoint) {
                                        if (foo[punkta.y][punkta.x] == 0){

                                            double partX = (analysePos.getX() - (int) analysePos.getX());
                                            double partY = (analysePos.getY() - (int) analysePos.getY());

                                            int texX = (int) (partX * 64);
                                            int texY = (int) (partY * 64);

                                            if(game.texture != null) {
                                                int color = game.texture.textureMain.getRGB(texX,texY);

                                                foo[punkta.y][punkta.x] = color;
                                                foo[punkta.y - wallHeight][punkta.x] = color;

                                            }
                                        }
                                    }
                                }

                            }
                            lastPoint = punkta;
                        }
                    }else{
                        len = maxLen;
                    }
                }
                nStep++;

            }



            BufferedImage image = array_rasterToBuffer(foo);
            g.drawImage(image,0,0,null);

        }catch (ConcurrentModificationException ignore){}



    }



    public BufferedImage array_rasterToBuffer(int[][] img) {
        final int width = game.render.renderSize.x;
        final int height = game.render.renderSize.y;

        // The bands are "packed" for TYPE_INT_RGB Raster,
        // so we need only one array component per pixel
        int[] pixels = new int[width * height];

        int n = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // "Pack" RGB values to native TYPE_INT_RGB format
                // (NOTE: Do not use Math.abs on these values, and without alpha there won't be negative values)
                pixels[n] = img[y][x];
                n++;
            }
        }



        // NOTE: getRaster rather than getData for "live" view
        WritableRaster rast = bufferImg.getRaster();

        // NOTE: setDataElements rather than setPixels to avoid conversion
        // This requires pixels to be in "native" packed RGB format (as above)
        rast.setDataElements(0, 0, width, height, pixels);

        // No need for setData as we were already working on the live data
        // thus saving at least two expensive array copies

        return bufferImg;
    }


}
