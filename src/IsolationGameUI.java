import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class IsolationGameUI extends PApplet {
    private static Board board;
    private static ArrayList<Piece> pieces;
    protected static int scale, leftCornerX, leftCornerY;
    private static int dimension = 640;
    public boolean canHold, hasLoaded = false;

    PImage lightQueen, darkQueen;
    private GameState currentState = GameState.MENU;

    public void settings() {
        size(dimension, dimension);
        lightQueen = loadImage("light_queen.png");
        darkQueen = loadImage("dark_queen.png");
    }

//    public void setup() {
//        //scale corresponds to pixel scale of each grid space, and offset
//        //is the pixel distance between the edge of the window and the board
//        scale = dimension / 10;
//
//        //Convenient variables for the coordinates of the left corner
//        //of the bottom left corner gridspace, or "a1" of the board
//        leftCornerX = scale;
//        leftCornerY = dimension - 2 * scale;
//
//        for (int i = 0; i < pieces.size(); i++) {
//            pieces.get(i).setImg
//        }
//
//        canHold = true;
//    }


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
