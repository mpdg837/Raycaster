package Raycaster.Display.Raycaster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Column {
    public Rectangle rect;
    public int index;
    public boolean darker;

    void makeColumn(Raycasting ray,int n){
        double deltaY = Texture.size/rect.getHeight();
        double yR =0;

        int minY =(int)rect.getY() - n*(int)rect.getHeight();

        for(int y=minY;y<minY+(int)rect.getHeight();y++) {



            if (y >= 0 && rect.x >= 0) {
                if (y < ray.game.render.renderSize.getY() && rect.x < ray.game.render.renderSize.getX()) {
                    if(darker){
                        int color = ray.game.texture.bufferXYS[(int)yR][index];
                        ray.foo[y][rect.x] = color;
                    }else {
                        int color = ray.game.texture.bufferXY[(int)yR][index];
                        ray.foo[y][rect.x] = color;
                    }
                }
            }
            yR += deltaY;
        }
    }
    public void render(Raycasting ray){
        makeColumn(ray,0);

    }
}
