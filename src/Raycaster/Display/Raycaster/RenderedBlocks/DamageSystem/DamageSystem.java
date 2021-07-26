package Raycaster.Display.Raycaster.RenderedBlocks.DamageSystem;

public class DamageSystem {

    private final boolean[][] destroyModel;

    public DamageSystem(){
        destroyModel = new boolean[64][6];
        for(byte x=0;x<64;x++){
            for(byte y=0;y<6;y++){
                destroyModel[x][y] = generatePseudoLos(x,y);
            }
        }
    }
    public static boolean generatePseudoLos(byte punkt,byte hp){

        switch (hp){
            case 1:
            case 2:
                return (-1)*(punkt-15)*(punkt-60) >0;

            case 3:
                return (-1)*(punkt-15)*(punkt-30)*(punkt-45) >0;

            case 4:
                return (-1)*(punkt-15)*(punkt-40)*(punkt-30)*(punkt-45) >0;

            default:
                return (-1)*(punkt-5)*(punkt-15)*(punkt-2*20)*(punkt-30)*(punkt-45) >0;

        }

    }

    public boolean pseudoLos(int punkt,int hp){
        return destroyModel[punkt][hp];
    }
}
