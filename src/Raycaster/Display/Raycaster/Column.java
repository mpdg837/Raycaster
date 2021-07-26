package Raycaster.Display.Raycaster;

import Raycaster.Display.Texture;

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

    boolean makeLight(Raycasting ray){


        final double partX = (raycastPosition.getX() - (int) raycastPosition.getX());
        final double partY = (raycastPosition.getY() - (int) raycastPosition.getY());

        Point checkPos = new Point(0,0);
        int count = 0;

        if(partX <0.02){
            checkPos = new Point((int)objPosition.getX()-1,(int)objPosition.getY());
            count ++;
        }
        if(partX > 0.98){
            checkPos = new Point((int)objPosition.getX()+1,(int)objPosition.getY());
            count ++;
        }
        if(partY <0.02){
            checkPos = new Point((int)objPosition.getX(),(int)objPosition.getY()-1);
            count ++;
        }
        if(partY >0.98){
            checkPos = new Point((int)objPosition.getX(),(int)objPosition.getY()+1);
            count ++;
        }

        if(count<2) {
            ray.darkerMe = false;
            if (!checkPos.equals(new Point(0, 0))) {
                if (checkPos.x >= 0 && checkPos.x < 128) {
                    if (checkPos.y >= 0 && checkPos.y < 128) {
                        ray.darkerMe = !ray.game.mapa.light[(int) checkPos.getX()][(int) checkPos.getY()];
                    }
                }
            }
        }

        return !ray.game.mapa.light[(int) objPosition.getX()][(int) objPosition.getY()] || ray.darkerMe;
    }
    public void setLen(double len){
        this.len = (int) (len*10);
    }
    void makeColumn(int nStep, Raycasting ray, int n, Texture tex, boolean blockColumn){

        if(destroyed){
            tex = ray.game.destroyed;
        }
        final double deltaY = Texture.size/rect.getHeight();
        double yR =0;

        final int minY =(int)rect.getY() - n*(int)rect.getHeight();

        int hei = (int)rect.getHeight();
        int deltaYa = 0;
        if(half){
            deltaYa = (int)rect.getHeight()/2;
        }

        int xR = rect.x/2;
        if(xR<320) {
            if (blockColumn && ray.rayHalfBlocked[xR]) {
                hei /= 2;
            }
        }

        boolean darkMe = makeLight(ray);

        for(int y=minY+deltaYa;y<minY+hei;y++) {


                if (y >= 0 && rect.x >= 0) {
                    if (y < ray.game.render.renderSize.getY() && rect.x < ray.game.render.renderSize.getX()) {
                        if(spriteReduction){

                            double relX = 0 ;
                            double relDelta = (double) 64/(double)hei;


                                for (int x = rect.x - (hei / 2); x < rect.x + hei / 2; x++) {

                                        if (x >= 0 && x < ray.game.render.renderSize.getX()) {

                                            if(x/2<ray.columns.size()) {
                                                Column column = ray.columns.get(x / 2);

                                                if (column.len > this.len) {
                                                    if (relX >= 0 && relX < 64) {
                                                        int color = tex.bufferXYS[(int) yR][(int) relX];

                                                        if (!ray.game.mapa.light[(int) objPosition.getX()][(int) objPosition.getY()]) {
                                                            color = ray.game.floor.bufferXYNL[(int) yR][(int) relX];
                                                        }

                                                        if (color != -16777216 || half || !tex.transparency) {

                                                            ray.foo[y][x] = color;
                                                            ray.foo[y][x] = color;
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    relX += relDelta;
                                }

                        }else {
                            if (darker) {
                                int color = tex.bufferXYS[(int) yR][index];
                                if (color != -16777216 || half || !tex.transparency) {


                                    if( darkMe ){
                                        color = tex.bufferXYNL[(int) yR][(int) index];
                                    }

                                    ray.foo[y][rect.x] = color;
                                    ray.foo[y][rect.x + 1] = color;
                                }
                            } else {
                                int color = tex.bufferXY[(int) yR][index];
                                if (color != -16777216 || half || !tex.transparency) {


                                    if( darkMe ){
                                        color = tex.bufferXYNL[(int) yR][(int) index];
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
