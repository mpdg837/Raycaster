package Raycaster.Player.Input;

import Raycaster.Display.Raycaster.RenderedBlocks.Boxes.DamageSystem.DamageSystem;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Interaction {

    public Input input;
    public DamageSystem damage = new DamageSystem();

    public Interaction(Input input){
        this.input = input;
    }

}
