package Raycaster.Display.Raycaster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Column {
    public Rectangle rect;
    public int index;
    public boolean darker;
    public boolean half;

    void makeColumn(Raycasting ray,int n,Texture tex){
        final double deltaY = Texture.size/rect.getHeight();
        double yR =0;

        final int minY =(int)rect.getY() - n*(int)rect.getHeight();

        int deltaYa = 0;
        if(half){
            deltaYa = (int)rect.getHeight()/2;
        }
        for(int y=minY+deltaYa;y<minY+(int)rect.getHeight();y++) {



            if (y >= 0 && rect.x >= 0) {
                if (y < ray.game.render.renderSize.getY() && rect.x < ray.game.render.renderSize.getX()) {
                    if(darker){
                        final int color = tex.bufferXYS[(int)yR][index];
                        if(color != -16777216|| half) {
                            ray.foo[y][rect.x] = color;
                            ray.foo[y][rect.x+1] = color;
                        }
                    }else {
                        int color = tex.bufferXY[(int)yR][index];
                        if(color != -16777216 || half) {

                            ray.foo[y][rect.x+1] = color;
                            ray.foo[y][rect.x] = color;
                        }
                    }
                }
            }
            yR += deltaY;
        }
    }



    public void render(Raycasting ray,Texture tex){
        makeColumn(ray,0,tex);

    }
}
