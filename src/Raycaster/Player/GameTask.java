package Raycaster.Player;

import Raycaster.Display.Render;
import Raycaster.Project.Game;

import java.util.TimerTask;

public class GameTask extends TimerTask {

    private Game game;
    public GameTask(Game game){
        this.game = game;
    }
    @Override
    public void run() {

        game.update();
        game.input.resetKey();

        game.render.saveRaycaster.tim2.schedule(new GameTask(game), Render.deltaTime*3/4);
    }
}
