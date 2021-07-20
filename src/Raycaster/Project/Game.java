package Raycaster.Project;

import Raycaster.Display.Raycaster.Texture;
import Raycaster.Display.Render;
import Raycaster.Input.Input;
import Raycaster.Input.Interaction;
import Raycaster.Player.Collision;
import Raycaster.Player.Map;
import Raycaster.Player.Transform;
import Raycaster.Raycaster;

import javax.annotation.processing.SupportedSourceVersion;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Interaction {


    public Render render;

    public Transform playerTransform;
    public Map mapa;

    public Texture texture;

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
        }catch (IOException ignore){}

        mapa.mapa[63][70] = 1;
        mapa.mapa[70][70] = 1;

    }

    int timer = 0;

    public void update() {

        try {
            BufferedImage tex = ImageIO.read(new File("gun.png"));
            render.draw.drawImage(tex,112,145,null);
        }catch (IOException ignore){}


        Point2D lastPos = playerTransform.postion;

        if (input.getKey(KeyEvent.VK_W)) {
            playerTransform.translate(Transform.getUp(),0.3);
        }else if (input.getKey(KeyEvent.VK_S)) {
            playerTransform.translate(Transform.getDown(),0.3);
        }else if (input.getKey(KeyEvent.VK_A)) {
            playerTransform.translate(Transform.getLeft(),0.3);
        }else if (input.getKey(KeyEvent.VK_D)) {
            playerTransform.translate(Transform.getRight(),0.3);
        }

        Point pos = input.getMousePos();


            System.out.println(pos.x);
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
