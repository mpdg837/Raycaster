package Raycaster.Player.Input;

import Raycaster.Display.Raycaster.RenderedBlocks.DamageSystem.DamageSystem;

public class Interaction {

    public Input input;
    public DamageSystem damage = new DamageSystem();

    public Interaction(Input input){
        this.input = input;
    }

}
