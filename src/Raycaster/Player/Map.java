package Raycaster.Player;

import Raycaster.Player.InteractiveObjects.Barrels;
import Raycaster.Project.Game;

import java.awt.*;
import java.io.*;
import java.util.Locale;

public class Map {
    public byte[][] mapa = new byte[128][128];
    public int[][] textures = new int[128][128];

    public Point[][] deltaPos = new Point[128][128];

    public byte[][] HP = new byte[128][128];

    public byte[][] ceiling = new byte[128][128];
    public byte[][] floor = new byte[128][128];

    public boolean[][] light = new boolean[128][128];

    public final Game game ;
    public final Barrels barrels;

    public Map(Game game){
        this.game = game;
        barrels = new Barrels(this);

        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                light[x][y] = true;
                deltaPos[x][y] = new Point(32,32);
            }
        }
    }

    public boolean inside(int x,int y){
        return x>=0 && y>=0 && x<128 && y<128;
    }

    public void analyse(){
        final int radiusB = 20;
            Point myPos =new Point((int)game.playerTransform.postion.getX(),(int)game.playerTransform.postion.getY());

            for (int x = myPos.x - radiusB; x < myPos.x+radiusB; x++) {
                for (int y = myPos.y-radiusB; y < myPos.y+radiusB; y++) {

                    if (inside(x, y)) {
                        switch (mapa[x][y]) {
                            case 20:
                            case 21:

                                game.doors.updateMap(x, y);

                                break;
                            case 22:
                                barrels.updateMap(x, y);
                                break;
                        }
                    }
                }
            }


    }

    public void loadDefaultMap(){

        var mapa = this;

        for(int x=0;x<16;x++){
            for(int y=0;y<16;y++){
                floor[60+x][60+y] = 1;
            }
        }

        for(int x=0;x<16;x++){
            for(int y=0;y<16;y++){
                ceiling[60+x][60+y] = 1;
            }
        }

        for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
               ceiling[62+x][63+y] = 0;

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
        mapa.textures[63][68] =3;

        mapa.mapa[65][68] =23;
        mapa.textures[65][68] =3;

        mapa.mapa[68][68] =23;
        mapa.textures[68][68] =3;


        mapa.mapa[69][69] =24;
        mapa.textures[69][69] =5;

        mapa.mapa[70][69] =25;
        mapa.textures[70][69] =4;

        mapa.mapa[72][69] =26;
        mapa.textures[72][69] =6;

        mapa.mapa[62][69] =27;
        mapa.textures[62][69] =7;

        mapa.mapa[60][70] =21;
        mapa.textures[60][70] =8;
        mapa.deltaPos[60][70] = new Point(0,0);

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
    public void readMap(){

        for(int x=0;x<128;x++){
            for(int y=0;y<128;y++){
                floor[x][y] = 1;
                ceiling[x][y] = 1;
                light[x][y] = true;
            }
        }

        try {
            FileReader read = new FileReader(new File("map.txt"));
            LineNumberReader reader = new LineNumberReader(read);
            try {
                String line = reader.readLine();

                int num =0;
                while (line != null) {

                    String analyseLine = line.replaceAll("\\s", "");
                    analyseLine = analyseLine.toUpperCase(Locale.ROOT);

                    StringBuilder builderName = new StringBuilder();
                    StringBuilder builderValue = new StringBuilder();

                    Point positionX = new Point();
                    Point deltaPosX = new Point();

                    byte textureX = 0;
                    byte objectX = 0;
                    byte floorX = 0;
                    byte ceilingX= 0;
                    byte HPX = 0;

                    boolean isLightedX  =false;

                    boolean readValue = false;
                    boolean readName = true;

                    boolean lock = false;
                    for(char chars : analyseLine.toCharArray()){
                        switch (chars+""){
                            case "/":
                                lock = true;
                                break;
                            case "=":
                                readName = false;
                                break;
                            case "'":
                                if(readValue){
                                    readName = true;
                                    try {
                                        switch (builderName.toString()) {
                                            case "OBJECT":
                                                objectX = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "POSX":
                                                positionX.x = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "POSY":
                                                positionX.y = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "DELTAX":
                                                deltaPosX.x = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "DELTAY":
                                                deltaPosX.y = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "TEXTURE":
                                                textureX = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "HP":
                                                HPX = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "FLOOR":
                                                floorX = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "CEILING":
                                                ceilingX = (byte)Integer.parseInt(builderValue.toString());
                                                break;
                                            case "LIGHT":
                                                isLightedX = (byte)Integer.parseInt(builderValue.toString()) == 1;
                                                break;
                                        }

                                        System.out.println(builderName.toString() + " " + builderValue.toString());
                                    }catch (Exception ignore){

                                    }
                                        builderName = new StringBuilder();
                                        builderValue = new StringBuilder();


                                }else{
                                    readName = false;
                                }

                                readValue = !readValue;
                                break;
                            default:
                                if(readValue){
                                    builderValue.append(chars);
                                }else if(readName) {
                                    builderName.append(chars);
                                }
                                break;
                        }
                    }

                    if(!lock){
                        mapa[positionX.x][positionX.y] = objectX;
                        textures[positionX.x][positionX.y] = textureX;
                        deltaPos[positionX.x][positionX.y] = deltaPosX;
                        HP[positionX.x][positionX.y] = HPX;
                        ceiling[positionX.x][positionX.y] = ceilingX;
                        floor[positionX.x][positionX.y] = floorX;
                        light[positionX.x][positionX.y] = isLightedX;
                    }
                    line = reader.readLine();
                    num ++;
                }

                if(num==0){
                    loadDefaultMap();
                }
            }catch (IOException ignore){

            }

        }catch (FileNotFoundException ignore){}
    }
}
