package Raycaster.Display.Raycaster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Column {
    public Rectangle rect;
    public int index;
    public boolean darker;
    public boolean half;

    public int len;
    public boolean spriteReduction = false;

    public void setLen(double len){
        this.len = (int) (len*2);
    }
    void makeColumn(int nStep,Raycasting ray,int n,Texture tex,boolean blockColumn){
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


        for(int y=minY+deltaYa;y<minY+hei;y++) {



                if (y >= 0 && rect.x >= 0) {
                    if (y < ray.game.render.renderSize.getY() && rect.x < ray.game.render.renderSize.getX()) {
                        if(spriteReduction){

                            double relX = 0 ;
                            double relDelta = (double) 64/(double)hei;


                                for (int x = rect.x - (hei / 2); x < rect.x + hei / 2; x++) {

                                        if (x >= 0 && x < ray.game.render.renderSize.getX()) {

                                            Column column = ray.columns.get(x/2);

                                            if(column.len>this.len) {
                                                if (relX >= 0 && relX < 64) {
                                                    final int color = tex.bufferXYS[(int) yR][(int) relX];
                                                    if (color != -16777216 || half) {
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
                                final int color = tex.bufferXYS[(int) yR][index];
                                if (color != -16777216 || half) {
                                    ray.foo[y][rect.x] = color;
                                    ray.foo[y][rect.x + 1] = color;
                                }
                            } else {
                                int color = tex.bufferXY[(int) yR][index];
                                if (color != -16777216 || half) {

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
