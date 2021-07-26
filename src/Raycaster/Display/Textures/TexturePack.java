package Raycaster.Display.Textures;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TexturePack {

    public Texture[] textures;

    private boolean isTranspareced(BufferedImage tex){

        if(tex.getWidth()>0 && tex.getHeight()>0) {
            int pixel = tex.getRGB(0, 0);

            return pixel == Texture.black;
        }else{
            return false;
        }


    }

    public TexturePack(BufferedImage textures){

        this.textures = new Texture[256];

        int n=0;
        for(int x=0;x<16;x++){
            for(int y=0;y<16;y++){

                Point pos = new Point(-x*64,-y*64);

                BufferedImage tex = new BufferedImage(64,64,BufferedImage.TYPE_3BYTE_BGR);
                Graphics g = tex.getGraphics();

                g.drawImage(textures,pos.x,pos.y,null);

                this.textures[n] = new Texture(tex,isTranspareced(tex));

                n++;
            }
        }

    }
}
