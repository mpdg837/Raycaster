package Raycaster.Project;

import Raycaster.Display.Raycaster.SkyBox;
import Raycaster.Display.Raycaster.Texture;
import Raycaster.Display.Render;
import Raycaster.Player.Input.Input;
import Raycaster.Player.Input.Interaction;
import Raycaster.Player.Collision;
import Raycaster.Player.Map;
import Raycaster.Player.Transform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class Game extends Interaction {


    public Render render;

    public double zoom;
    public int deltaY;
    public double angle;

    public Transform playerTransform;
    public Map mapa;

    public final Texture texture;
    public final Texture sprite;

    public final Texture floor;
    public final Texture ceiling;
    public final SkyBox sky;


    public Game(Input input) throws IOException{
        super(input);

        this.mapa = new Map();

        playerTransform = new Transform();


            texture = new Texture(ImageIO.read(new File("texture.png")),false);
            floor = new Texture(ImageIO.read(new File("floor.png")),false);
            ceiling = new Texture(ImageIO.read(new File("floor.png")),false);
            sprite = new Texture(ImageIO.read(new File("sprite.png")),true);
            sky = new SkyBox(ImageIO.read(new File("skybox.jpg")));
    }

    public void start() {


        for(int x=0;x<16;x++){
            for(int y=0;y<10;y++){
                mapa.floor[60+x][60+y] = 1;
            }
        }

        for(int x=0;x<16;x++){
            for(int y=0;y<10;y++){
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
            mapa.mapa[60+x][70] = 1;
            mapa.mapa[60][60+x] = 1;
            mapa.mapa[75][60+x] = 1;
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

    }

    int timer = 0;

    void walking(){
        Point2D lastPos = playerTransform.postion;

        double speed = 0.4;

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

    void cameraRot(){
        Point pos = input.getMousePos();

        if (pos.x > Input.centerMousePos.x) {
            pos.x = Input.centerMousePos.x;
        }
        if (pos.y > Input.centerMousePos.y) {
            pos.y = Input.centerMousePos.y;
        }

        double delta = (double)pos.x / (double) Input.centerMousePos.x * 0.15;
        double deltay = (double)pos.y / (double) Input.centerMousePos.y * 0.15;

        if (pos.x > 0) {
            playerTransform.rotate(0, delta);
        } else if (pos.x < 0) {
            playerTransform.rotate(0, delta);
        }

        if (pos.y > 0) {
            if(angle<1.7){
                angle+= deltay;
            }
        } else if (pos.y < 0) {
            if(angle>-1.7){
                angle+= deltay;
            }
        }

        deltaY = (int)(600 * Math.sin(angle));


        if(timer ==3) {
            input.centerMouse();
            timer = 0;
        }
        timer++;


    }

    public void update() {

        walking();
        cameraRot();

        if(input.getKey(KeyEvent.VK_E)){
            if(zoom<3) {
                zoom += 0.15;

            }
        }else {
            if(zoom>1) {
                zoom -= 0.2;

            }
        }

    }

}
