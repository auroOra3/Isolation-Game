import processing.core.PApplet;
import processing.core.PImage;

public class IsolationGameUI extends PApplet
{

    PImage lightQueen, darkQueen;
    private GameState currentState = GameState.MENU;

    @Override
    public void setup() { }

    @Override
    public void settings()
    {
        size(640, 640);
        noStroke();
        lightQueen = loadImage("light_queen.png");
        darkQueen = loadImage("dark_queen.png");
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
