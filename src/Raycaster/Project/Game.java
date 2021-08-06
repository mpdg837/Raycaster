package Raycaster.Project;

import Raycaster.Display.Raycaster.Raycasting;
import Raycaster.Display.Raycaster.SkyBox;
import Raycaster.Display.Textures.Texture;
import Raycaster.Display.Render;
import Raycaster.Display.Textures.TexturePack;
import Raycaster.Player.Camera;
import Raycaster.Player.Input.Input;
import Raycaster.Player.Input.Interaction;
import Raycaster.Player.Collision;
import Raycaster.Player.InteractiveObjects.Doors;
import Raycaster.Player.Map;
import Raycaster.Player.Transform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends Interaction {


    public Render render;



    public Transform playerTransform;
    public Map mapa;

    public final TexturePack texture;
    public final TexturePack[] sprite;

    public final Texture floor;
    public final Texture ceiling;
    public final SkyBox sky;

    public final Texture destroyed;

    public final Camera camera = new Camera();

    public final Doors doors;
    public final Player player;
    public final Gun gun;
    public Collision coll;


    public ArrayList<Point> enemyPoint = new ArrayList<>();

    public Game(Input input) throws IOException{
        super(input);

        this.mapa = new Map();
        doors = new Doors(this);

        playerTransform = new Transform();

            destroyed = new Texture(ImageIO.read(new File("destroyed.png")),false);
            texture = new TexturePack(ImageIO.read(new File("texture.png")));
            floor = new Texture(ImageIO.read(new File("floor.png")),false);
            ceiling = new Texture(ImageIO.read(new File("floor.png")),false);

            sprite = new TexturePack[3];
            for(int n=0;n<sprite.length;n++) {
                sprite[n] =new TexturePack(ImageIO.read(new File("sprite"+n+".png")));
            }
            sky = new SkyBox(ImageIO.read(new File("skybox.jpg")));
        player = new Player(this);

        gun = new Gun(this);
        coll = new Collision(mapa);
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
                mapa.ceciling[62+x][63+y] = 3;

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
        mapa.mapa[66][62] =20;
        mapa.textures[66][62] =1;

        mapa.mapa[68][62] =5;

        mapa.mapa[66][68] =6;
        mapa.mapa[68][68] =6;


        mapa.mapa[61][68] =22;
        mapa.textures[61][68] =2;

        mapa.mapa[63][68] =23;
        mapa.textures[63][68] =3+16;


        mapa.mapa[69][66] =7;
        mapa.mapa[69][65] =9;
        mapa.mapa[70][66] =10;
        mapa.mapa[70][65] =8;


        mapa.mapa[71][64] =11;

        mapa.mapa[61][69] =12;
        mapa.mapa[61][67] =13;
        mapa.mapa[61][65] =14;
        mapa.mapa[61][63] =15;

        mapa.textures[61][69] =4;
        mapa.textures[61][67] =4;
        mapa.textures[61][65] =4;
        mapa.textures[61][63] =4;

        mapa.mapa[74][69] =16;
        mapa.mapa[74][67] =17;
        mapa.mapa[74][65] =18;
        mapa.mapa[74][63] =19;

        mapa.textures[74][69] =4;
        mapa.textures[74][67] =4;
        mapa.textures[74][65] =4;
        mapa.textures[74][63] =4;
        for(int x=61;x<70;x++) {
            for (int y = 61; y < 63; y++) {
                mapa.light[x][y] = false;
            }
        }

        mapa.HP[65][65]=0;

        mapa.textures[69][66] =2;
        mapa.textures[69][65] =2;
        mapa.textures[70][66] =2;
        mapa.textures[70][65] =2;
    }




    public void doors(){
        if (input.getKey(KeyEvent.VK_O)) {
            doors.open();
        }

        doors.update();

        render.saveRaycaster.sprites.canUse = doors.canUse;
    }


    public void update() {

        player.walking();
        camera.cameraRot(this);

        doors();
        gun.useGun();

        mapa.analyse();


        final Point enPos = new Point(63,68);

        boolean hurt = true;
        byte numer = 0;

        if(!enemyPoint.contains(enPos)) {

            final Point myPos = new Point((int)playerTransform.postion.getX(),(int)playerTransform.postion.getY());

            final Point2D delta = new Point2D.Double((double) (myPos.x - enPos.x) / (double) 64, (myPos.y - enPos.y) / (double) 64);
            Point2D analysePos = new Point2D.Double(enPos.x, enPos.y);

            Point lastZao = myPos;



            for (int n = 0; n < 64; n++) {
                final Point zao = new Point((int) analysePos.getX(), (int) analysePos.getY());
                if (!zao.equals(lastZao)) {

                    if (zao.equals(myPos)) {
                        break;
                    } else if (mapa.mapa[zao.x][zao.y] != 0 && mapa.mapa[zao.x][zao.y] != 23) {

                        hurt = false;
                        numer = mapa.mapa[zao.x][zao.y];
                        break;


                    }

                }

                analysePos.setLocation(analysePos.getX() + delta.getX(), analysePos.getY() + delta.getY());
                lastZao = zao;
            }

        }

        timk++;
        if(timk>3) {
            if (hurt) {


                if (n > 3) {
                    n = 0;

                } else {
                    n++;
                }




                mapa.textures[63][68] = (byte) (3 + n * 16);
            } else {

                if (n > 1) {
                    n = 0;

                } else {
                    n++;
                }

                System.out.println("COLL " + numer);
                mapa.textures[63][68] = (byte) (3 + n * 16);
            }
            timk = 0;
        }
        if(tim>60) {

            render.saveRaycaster.requestFocus();
            tim = 0;
        }
    }

    int timk = 0;
    int n=0;
    int tim = 0;

}
