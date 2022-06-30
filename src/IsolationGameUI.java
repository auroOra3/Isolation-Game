import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class IsolationGameUI extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{""}, new IsolationGameUI());
    }

    @Override
    public void draw()
    {
        background(0);
        switch (currentState)
        {
            case MENU -> initScreen();
            case GAME -> gamePlayScreen();
            case GAMEOVER -> gameOverScreen();
        }
    }

    public void initScreen()
    {

    }

    public void gamePlayScreen()
    {

    }

    public void gameOverScreen()
    {

    }
}

enum GameState {
    MENU,
    GAME,
    GAMEOVER;
}

