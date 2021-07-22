package Raycaster.Display.Raycaster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Column {
    public Rectangle rect;
    public int index;
    public boolean darker;
    public boolean half;

    void makeColumn(Raycasting ray,int n,Texture tex){
        double deltaY = Texture.size/rect.getHeight();
        double yR =0;

        int minY =(int)rect.getY() - n*(int)rect.getHeight();

        int deltaYa = 0;
        if(half){
            deltaYa = (int)rect.getHeight()/2;
        }
        for(int y=minY+deltaYa;y<minY+(int)rect.getHeight();y++) {



            if (y >= 0 && rect.x >= 0) {
                if (y < ray.game.render.renderSize.getY() && rect.x < ray.game.render.renderSize.getX()) {
                    if(darker){
                        int color = tex.bufferXYS[(int)yR][index];
                        if(color != getIntFromColor(0,0,0)) {
                            ray.foo[y][rect.x] = color;
                            ray.foo[y][rect.x+1] = color;
                        }
                    }else {
                        int color = tex.bufferXY[(int)yR][index];
                        if(color != getIntFromColor(0,0,0)) {
                            ray.foo[y][rect.x+1] = color;
                            ray.foo[y][rect.x] = color;
                        }
                    }
                }
            }
            yR += deltaY;
        }
    }

    public int getIntFromColor(float Red, float Green, float Blue){
        int R = Math.round(255 * Red);
        int G = Math.round(255 * Green);
        int B = Math.round(255 * Blue);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }

    public void render(Raycasting ray,Texture tex){
        makeColumn(ray,0,tex);

    }
}
