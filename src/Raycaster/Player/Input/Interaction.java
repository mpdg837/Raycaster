package Raycaster.Player.Input;

import Raycaster.Display.Raycaster.DamageSystem.DamageSystem;
import Raycaster.Raycaster;

public class Interaction {



    public Input input;
    public DamageSystem damage = new DamageSystem();

    public Interaction(Input input){
        this.input = input;
    }

}
