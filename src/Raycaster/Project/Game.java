package Raycaster.Project;

import Raycaster.Display.Raycaster.SkyBox;
import Raycaster.Display.Textures.Texture;
import Raycaster.Display.Render;
import Raycaster.Display.Textures.TexturePack;
import Raycaster.Player.Camera;
import Raycaster.Player.Input.Input;
import Raycaster.Player.Input.Interaction;
import Raycaster.Player.Collision;
import Raycaster.Player.Map;
import Raycaster.Player.Transform;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class Game extends Interaction {


    public Render render;



    public Transform playerTransform;
    public Map mapa;

    public final TexturePack texture;
    public final TexturePack sprite;

    public final Texture floor;
    public final Texture ceiling;
    public final SkyBox sky;

    public final Texture destroyed;

    public final Camera camera = new Camera();

    public Game(Input input) throws IOException{
        super(input);

        this.mapa = new Map();

        playerTransform = new Transform();

            destroyed = new Texture(ImageIO.read(new File("destroyed.png")),false);
            texture = new TexturePack(ImageIO.read(new File("texture.png")));
            floor = new Texture(ImageIO.read(new File("floor.png")),false);
            ceiling = new Texture(ImageIO.read(new File("floor.png")),false);
            sprite = new TexturePack(ImageIO.read(new File("sprite.png")));
            sky = new SkyBox(ImageIO.read(new File("skybox.jpg")));
    }

    public void start() {


        for(int x=0;x<16;x++){
            for(int y=0;y<16;y++){
                mapa.floor[60+x][60+y] = 1;
            }
        }

        for(int x=0;x<16;x++){
            for(int y=0;y<16;y++){
                mapa.ceciling[60+x][60+y] = 1;
            }
        }

        for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                mapa.ceciling[62+x][63+y] = 0;
            }
        }


        for(int x=0;x<16;x++) {
            mapa.mapa[60+x][60] = 1;
            mapa.mapa[60+x][75] = 1;
            mapa.mapa[60][60+x] = 1;
            mapa.mapa[75][60+x] = 1;
            mapa.HP[75][60+x]=0;
        }

        mapa.mapa[65][65] = 1;
        mapa.mapa[62][62] =2;
        mapa.mapa[64][62] =3;
        mapa.mapa[66][62] =4;
        mapa.mapa[68][62] =5;

        mapa.mapa[66][68] =6;
        mapa.mapa[67][68] =6;
        mapa.mapa[68][68] =6;


        mapa.mapa[69][66] =7;
        mapa.mapa[69][65] =9;
        mapa.mapa[70][66] =10;
        mapa.mapa[70][65] =8;
        mapa.mapa[71][64] =11;

        mapa.mapa[61][69] =12;
        mapa.mapa[61][67] =13;
        mapa.mapa[61][65] =14;
        mapa.mapa[61][63] =15;

        mapa.mapa[74][69] =16;
        mapa.mapa[74][67] =17;
        mapa.mapa[74][65] =18;
        mapa.mapa[74][63] =19;

        for(int x=61;x<70;x++) {
            for (int y = 61; y < 63; y++) {
                mapa.light[x][y] = false;
            }
        }

        mapa.HP[65][65]=0;

    }


    void walking(){
        Point2D lastPos = playerTransform.postion;

        double speed = 0.45;

        if (input.getKey(KeyEvent.VK_W)) {
            playerTransform.translate(Transform.getUp(),speed);
        }else if (input.getKey(KeyEvent.VK_S)) {
            playerTransform.translate(Transform.getDown(),speed);
        }else if (input.getKey(KeyEvent.VK_A)) {
            playerTransform.translate(Transform.getLeft(),speed);
        }else if (input.getKey(KeyEvent.VK_D)) {
            playerTransform.translate(Transform.getRight(),speed);
        }

        Collision coll = new Collision(mapa);
        if(coll.collide(playerTransform.postion)){
            playerTransform.postion =lastPos;
        }
    }



    public void update() {

        walking();
        camera.cameraRot(this);

        if (input.getKey(KeyEvent.VK_E)) {
            if (camera.zoom < 3) {
                camera.zoom += 0.15;

            }
        } else {
            if (camera.zoom > 1) {
                camera.zoom -= 0.2;

            }
        }

        tim ++;

        if(tim>60) {
            tim = 0;
            if (mapa.HP[65][65] >= 5) {
                mapa.HP[65][65] = 0;
                mapa.HP[64][62] = 0;
            } else {
                mapa.HP[65][65]++;
                mapa.HP[64][62]++;
            }
        }
    }

    int tim = 0;

}
