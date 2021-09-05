package Raycaster.Project;

import Raycaster.Display.Raycaster.SkyBox;
import Raycaster.Display.Textures.Texture;
import Raycaster.Display.Render;
import Raycaster.Display.Textures.TexturePack;
import Raycaster.Project.Gun.Gun;
import Raycaster.Sound.MakeSound;
import Raycaster.Player.Camera;
import Raycaster.Player.Input.Input;
import Raycaster.Player.Input.Interaction;
import Raycaster.Player.Collision;
import Raycaster.Player.InteractiveObjects.Doors;
import Raycaster.Player.Map;
import Raycaster.Player.Transform;
import Raycaster.Player.InteractiveObjects.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class Game extends Interaction {


    public Render render;


    public boolean pause;
    public Transform playerTransform;
    public Map mapa;

    public final TexturePack texture;
    public final TexturePack[] sprite;

    public final SkyBox sky;

    public final Texture destroyed;

    public final Camera camera = new Camera();

    public final Doors doors;
    public Player player;
    public Gun gun;
    public Collision coll;

    final Enemy enemy = new Enemy(this);
    final public MakeSound sound = new MakeSound();

    public ArrayList<Point> enemyPoint = new ArrayList<>();

    public int timeStart = 0;

    public Game(Input input) throws IOException{
        super(input);

        this.mapa = new Map(this);
        doors = new Doors(this);

        playerTransform = new Transform();

            destroyed = new Texture(ImageIO.read(new File("destroyed.png")),false);
            texture = new TexturePack(ImageIO.read(new File("texture.png")));

            sprite = new TexturePack[3];
            for(int n=0;n<sprite.length;n++) {
                sprite[n] =new TexturePack(ImageIO.read(new File("sprite"+n+".png")));
            }
            sky = new SkyBox(ImageIO.read(new File("skybox.jpg")),this);
        player = new Player(this);

        gun = new Gun(this);
        coll = new Collision(mapa,this);
    }

    public void start() {
        mapa.readMap();
    }


    public void restart(){
        player.HP = 100;
        this.mapa = new Map(this);
        playerTransform = new Transform();
        player = new Player(this);

        gun = new Gun(this);
        coll = new Collision(mapa,this);

        start();

    }

    public void doors(){
        if (input.getKey(KeyEvent.VK_O)) {
            doors.open();

        }

        doors.update();

        render.saveRaycaster.sprites.canUse = doors.canUse;
    }


    public void update() {

        if(timeStart<60){
            timeStart ++;

            input.centerMouse();

            render.saveRaycaster.sprites.mask.timStart = timeStart;
            render.saveRaycaster.sprites.infoAmmo = "";
        }else
        if(!pause) {
            render.saveRaycaster.sprites.HP = player.HP;

            this.enemy.update();
            gun.useGun();


            player.walking();

            new Thread(this::doors).start();
            new Thread(mapa::analyse).start();

            new Thread(()->{
                if (tim > 60) {
                    if (!sound.isPlaying("soundtrack.wav")) sound.playSound("soundtrack.wav");

                    render.saveRaycaster.requestFocus();
                    tim = 0;
                } else {
                    tim++;
                }
            }).start();



        }

        camera.cameraRot(this);
    }

    public int timk = 0;
    public int n=0;
    public int tim = 0;

}
