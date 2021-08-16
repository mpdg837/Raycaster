package Raycaster.Display.Raycaster.RenderTools;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Textures.Texture;

import java.awt.*;
import java.awt.geom.Point2D;

public class Column {
    public Rectangle rect;
    public int index;
    public boolean darker;
    public boolean half;
    public Point objPosition;
    public Point2D raycastPosition;

    public int len;
    public boolean spriteReduction = false;

    public boolean destroyed;


    public void setLen(double len){
        this.len = (int) (len*10);
    }
    void makeColumn(int nStep, Raycasting ray, int n, Texture tex, boolean blockColumn){

        if(destroyed){
            tex = ray.game.destroyed;
        }
        final double deltaY = Texture.size/rect.getHeight();
        double yR =0;

        final int minY =(int)rect.getY() ;

        int hei = (int)rect.getHeight();
        int deltaYa = 0;
        if(half){
            deltaYa = (int)rect.getHeight() >> 1;
            yR = 32;
        }

        int xR = rect.x >> 1;
        if(xR<320) {
            if (blockColumn && ray.rayHalfBlocked[xR]) {
                hei = hei >> 1;
            }
        }

        boolean darkMe = Light.makeLight(ray,this);



        // Reduktor

        int min=minY+deltaYa;
        int max=minY+hei;

        if(min<0){
            yR = -deltaY*min;
            min = 0;

        }
        if(max>ray.game.render.renderSize.getY()){
            max = (int)ray.game.render.renderSize.getY();
        }

        for(int y=min;y<max;y++) {


                if ( rect.x >= 0) {
                    if (y < ray.game.render.renderSize.getY() && rect.x < ray.game.render.renderSize.getX()) {



                        if(spriteReduction){

                            double relX = 0 ;
                            final double relDelta = (double) 64/(double)hei;

                            final int hhei = (hei >> 1);

                                for (int x = rect.x - hhei; x < rect.x + hhei; x++) {

                                        if (x >= 0 && x < ray.game.render.renderSize.getX()) {

                                            int colLen = this.len+1;
                                            if((x>>1)<ray.columns.size()) {

                                                colLen = ray.columns.get(x >> 1).len;
                                            }
                                                if (colLen > this.len) {
                                                    if (relX >= 0 && relX < 64) {
                                                        int color = tex.bufferXYS[(int) yR][(int) relX];

                                                        if (!ray.game.mapa.light[(int) objPosition.getX()][(int) objPosition.getY()]) {
                                                            color = tex.bufferXYNL[(int) yR][(int) relX];
                                                        }

                                                        if (color != Texture.black || half || !tex.transparency) {

                                                            ray.foo[y][x] = color;
                                                            ray.foo[y][x] = color;
                                                        }
                                                    }

                                                }
                                        }

                                    relX += relDelta;
                                }

                        }else {
                            if (darker) {
                                int color = tex.bufferXYS[(int) yR][index];
                                if (color != Texture.black|| half || !tex.transparency) {


                                    if( darkMe ){
                                        color = tex.bufferXYNL[(int) yR][ index];
                                    }

                                    ray.foo[y][rect.x] = color;
                                    ray.foo[y][rect.x + 1] = color;
                                }
                            } else {
                                int color = tex.bufferXY[(int) yR][index];
                                if (color != Texture.black || half || !tex.transparency) {


                                    if( darkMe ){
                                        color = tex.bufferXYNL[(int) yR][ index];
                                    }

                                    ray.foo[y][rect.x + 1] = color;
                                    ray.foo[y][rect.x] = color;
                                }
                            }
                        }
                    }
                }

            yR += deltaY;
        }


    }



    public void render(int nStep,Raycasting ray,Texture tex,boolean blockColumn){
        makeColumn(nStep,ray,0,tex,blockColumn);

    }
}
