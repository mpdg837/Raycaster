package Raycaster.Project;

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
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class Game extends Interaction {


    public Render render;

    public Transform playerTransform;
    public Map mapa;

    public Texture texture;
    public Texture sprite;

    public Texture floor;
    public Texture ceiling;

    boolean oka=true;

    public Game(Render render, Input input){
        super(input);
        this.render = render;
        this.mapa = new Map();

        playerTransform = new Transform();
    }

    public void start() {
        try{
            texture = new Texture(ImageIO.read(new File("texture.png")));
            floor = new Texture(ImageIO.read(new File("floor.png")));
            ceiling = new Texture(ImageIO.read(new File("floor.png")));
            sprite = new Texture(ImageIO.read(new File("sprite.png")));
        }catch (IOException ignore){}

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

        for(int x=0;x<16;x++) {
            mapa.mapa[60+x][60] = 1;
            mapa.mapa[60+x][75] = 1;
            mapa.mapa[60][60+x] = 1;
            mapa.mapa[75][60+x] = 1;
        }

        mapa.mapa[65][65] = 1;
        mapa.mapa[62][62] =2;
        mapa.mapa[63][62] =3;
        mapa.mapa[64][62] =4;
        mapa.mapa[65][62] =5;
        mapa.mapa[66][62] =0;
        mapa.mapa[67][62] =7;
        mapa.ceciling[64][64]=1;
    }

    int timer = 0;

    public void update() {


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

        Point pos = input.getMousePos();

            if (pos.x > input.centerMousePos.x) {
                pos.x = input.centerMousePos.x;
            }

            double delta = (double)pos.x / (double) Input.centerMousePos.x * 0.15;

            if (pos.x > 0) {
                playerTransform.rotate(0, delta);
            } else if (pos.x < 0) {
                playerTransform.rotate(0, delta);
            }

        if(timer ==3) {
            input.centerMouse();
            timer = 0;
        }
        timer++;

        Collision coll = new Collision(mapa);
        if(coll.collide(playerTransform.postion)){
            playerTransform.postion =lastPos;
        }
    }

}
