import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class IsolationGameUI extends PApplet {

    GameState currentState = GameState.STARTSCREEN;
    private GameBoard gameBoard;
    private boolean whiteTurn;
    private int currentFrame = 0;

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{""}, new IsolationGameUI());
    }

    @Override
    public void settings() {
        size(540, 669);
    }

    @Override
    public void setup() {
        ImageRessources.loadRessources(this);
        this.gameBoard = new GameBoard();
    }

    @Override
    public void draw() {
        switch (currentState) {
            case STARTSCREEN -> initScreen();
            case INSTRUCTION -> gameInstructionScreen();
            case GAME -> gamePlayScreen();
            case GAMEOVER -> gameOverScreen();
        }
    }

    public void initScreen() {
        frameRate(9.0f);
        currentFrame = (currentFrame+1) % ImageRessources.numOfFrames;
        int offset = 0;
        for (int x = -100; x < width; x += ImageRessources.menuScreenBackground[0].width) {
            background(ImageRessources.menuScreenBackground[(currentFrame+offset) % ImageRessources.numOfFrames]);
            offset+=2;
        }
        textFont(ImageRessources.bete);
        textAlign(CENTER);
        fill(252, 241, 201);
        textSize(40);
        text("Don´t get stranded", width/2, height-550);
        fill(0, 95, 177);
        textSize(25);
        text("Click to start", width/2, height-60);
        textSize(15);
        text("Game Instructions", width/2, height-30);

    }

    public void gameInstructionScreen() {
        background(ImageRessources.islandScreen);
        image(ImageRessources.treasureMap, 10 , height-550, 200F*2.6F, 166*2.8F);

    }

    public void gamePlayScreen() {
        int xOffset = 90, yOffset = 171;
        background(ImageRessources.islandScreen);
        image(ImageRessources.treasureMap, 10, height-550, 200F* 2.6F, 166F*2.8F);
        boolean isWhite = true;
        stroke(color(191,147,84));
        for (int x = 0; x < 350; x+=45) {
            for (int y = 0; y < 350; y+=45) {
                if(isWhite)
                    fill(color(194,145,78));
                else
                    fill(color(229,189,128));
                rect(x + xOffset, y + yOffset, 40, 40, 12);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }

        gameBoard.draw(this);
    }

    public void gameOverScreen() {

    }

    @Override
    public void mousePressed() {
        System.out.println(mouseX);
        System.out.println(mouseY);

        switch (currentState) {
            case STARTSCREEN -> {
                   if (mouseX > 152 && mouseX < 389 && mouseY > 578 && mouseY < 616) {
                       startGame();

                   }
            }
            case INSTRUCTION -> {
                if (mouseX > 189 && mouseX < 358 && mouseY > 626 && mouseY < 638) {
                    startInstruction();
                }
                break;
            }
            case GAME -> {
                int posX = (mouseX - 90)/45;
                int posY = (mouseY - 171)/45;
                gameBoard.executeMove(whiteTurn, posX, posY);
                whiteTurn = !whiteTurn;
            }
            case GAMEOVER -> restart();
        }
    }

    private void startGame() {
        currentState = GameState.GAME;
    }

    private void startInstruction() {
        currentState = GameState.INSTRUCTION;
    }

    private void restart() {
        currentState = GameState.STARTSCREEN;
    }


}

