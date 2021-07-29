package Raycaster.Player.Input;

import Raycaster.Display.Raycaster.DamageSystem.DamageSystem;
import Raycaster.Raycaster;

public class Interaction {

    private final double[] sines = new double[130665];
    private final double[] cosines = new double[130665];

    public final Input input;
    public final DamageSystem damage = new DamageSystem();

    public Interaction(Input input){
        this.input = input;

        int n=0;
        for(double angle = -2*Math.PI;angle<=2*Math.PI+0.5;angle+=0.0001){
            sines[n] = Math.sin(angle);
            cosines[n] = Math.cos(angle);
            n++;
        }

        System.out.print(n);

    }

    public double getSin(double angle){

        return sines[(int)((2*Math.PI + angle)*10000)];
    }

    public double getCos(double angle){
        return cosines[(int)((2*Math.PI + angle)*10000)];
    }

}
