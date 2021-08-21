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

        final long start = System.nanoTime();


            game.update();
            game.input.resetKey();





        final long stop = System.nanoTime();

        final int delta = (int) ((double) (stop - start) / (double) 1000000);
        int timeOut = (Render.deltaTime*3/4) - delta;
        if (timeOut < 0) {
            timeOut = 0;
        }

        game.render.saveRaycaster.tim2.schedule(new GameTask(game), timeOut);
    }
}
