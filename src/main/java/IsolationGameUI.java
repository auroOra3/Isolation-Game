package main.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processing.core.PApplet;


public class IsolationGameUI extends PApplet {

    GameState currentState = GameState.STARTSCREEN;
    private GameBoard gameBoard;
    private boolean redTurn = true;
    private int currentInitFrame = 0;
    private int currentPirateFrame = 0;
    private GameBoardState whoHasLost;
    private static Logger UILog = LogManager.getLogger(IsolationGameUI.class);

    public IsolationGameUI(boolean isDev) {
        if (isDev)
            currentState = GameState.GAMEOVER;
    }

    public static void main(String[] args) {
        boolean isDev = args.length == 1 && args[0].contains("-dev");
        PApplet.runSketch(new String[]{""}, new IsolationGameUI(isDev));
    }

    @Override
    public void settings() {
        size(540, 670);
    }

    @Override
    public void setup() {
        Images.imagePath(this);
        Images.soundFile.loop();
        this.gameBoard = new GameBoard();
    }

    @Override
    public void draw() {
        switch (currentState) {
            case STARTSCREEN -> gameInitScreen();
            case INSTRUCTION -> gameInstructionScreen();
            case GAME -> gamePlayScreen();
            case GAMEOVER -> gameOverScreen();
        }
    }

    public void gameInitScreen() {
        frameRate(9.0f);
        currentInitFrame = (currentInitFrame + 1) % Images.numOfFramesInitScreen;
        int offset = 0;
        for (int x = -100; x < width; x += Images.menuScreenBackground[0].width) {
            background(Images.menuScreenBackground[(currentInitFrame + offset) % Images.numOfFramesInitScreen]);
            offset += 2;
        }
        textFont(Images.beteFont);
        textAlign(CENTER);
        fill(252, 241, 201);
        textSize(40);
        text("Don´t get stranded", width / 2, height - 550);
        fill(0, 95, 177);
        textSize(25);
        text("Click to Start", width / 2, height - 60);
        textSize(15);
        text("Game Instructions", width / 2, height - 30);
    }

    public void gameInstructionScreen() {
        background(Images.islandScreen);
        image(Images.treasureMap, 10, height - 550, 200F * 2.6F, 166 * 2.8F);
        textFont(Images.pirateFont);
        textAlign(CENTER);
        fill(103, 40, 5);
        textSize(25);
        text("Game Instruction", width / 2, height - 460);
        textSize(18);
        text("The first player chooses a cell to start\n" +
                "Each player takes turns\nmoving their player to a new cell\n" +
                "A player can move to any cell with a clear path\n" +
                "up down left right or diagonal\n" +
                "as long as the path is not blocked by a previously visited cell\n" +
                "or by the other player\n" +
                "If a player is unable to make any further move\n" +
                "the opponent wins\n" +
                "Thus the goal of the maingame is\n" +
                "to be the last player with a remaining move available", width / 2, height - 420);
        textFont(Images.beteFont);
        fill(0, 95, 177);
        textSize(25);
        text("Go Back", width / 2, height - 40);

    }

    public void gamePlayScreen() {
        int xOffset = 90, yOffset = 171;
        background(Images.islandScreen);
        image(Images.treasureMap, 10, height - 550, 200F * 2.6F, 166F * 2.8F);
        stroke(color(194, 145, 78));
        boolean isGreenTurn = true;
        for (int x = 0; x < 350; x += 45) {
            for (int y = 0; y < 350; y += 45) {
                if (isGreenTurn)
                    fill(color(194, 145, 78));
                else
                    fill(color(229, 189, 128));
                rect(x + xOffset, y + yOffset, 40, 40, 12);
                isGreenTurn = !isGreenTurn;
            }
            isGreenTurn = !isGreenTurn;
        }
        gameBoard.draw(this);
    }

    public void gameOverScreen() {
        background(Images.islandScreen);
        image(Images.treasureMapNew, 10, height - 550, 200F * 2.6F, 166F * 2.8F);
        frameRate(12.0f);
        currentPirateFrame = (currentPirateFrame + 1) % Images.numOfFramesGameOverScreen;
        int offset = 0;
        for (int i = -100; i < width; i += Images.gameOverScreenPirate[0].width) {
            image(Images.gameOverScreenPirate[(currentPirateFrame + offset) % Images.numOfFramesGameOverScreen], 70, 180);

        }
        textFont(Images.beteFont);
        textAlign(CENTER);
        fill(252, 241, 201);
        textSize(40);
        text("GAME OVER", width / 2, height - 600);
        textFont(Images.pirateFont);
        fill(0);
        textSize(30);
        text("Congrats",width / 2 + 65, height - 470);
        image(whoHasLost == GameBoardState.GREEN ? Images.redCrab : Images.greenCrab, width/2 + 22, height-439, 90, 90);
        text("The Sweet Coffer\n" +
                        "is all Yours\n" +
                        "Arrrggghhh", width/2 -65, height-250);
        textFont(Images.beteFont);
        fill(0, 95, 177);
        textSize(25);
        text("Play Again", width / 2, height - 40);
    }

    @Override
    public void mousePressed() {

        switch (currentState) {
            case STARTSCREEN -> {
                if (mouseX > 152 && mouseX < 389 && mouseY > 578 && mouseY < 616) {
                    UILog.info("User startes playing Isolation Game");
                    startGameScreen();
                } else if (mouseX > 189 && mouseX < 354 && mouseY > 626 && mouseY < 642) {
                    UILog.info("User clicked on Game Instruction");
                    startInstructionScreen();
                }
            }
            case INSTRUCTION -> {
                if (mouseX > 205 && mouseX < 337 && mouseY > 612 && mouseY < 640) {
                    startInitScreen();
                }
            }
            case GAME -> {
                int posX = (mouseX - 90) / 45;
                int posY = (mouseY - 171) / 45;

                UILog.info("User clicked at (%d/%d)".formatted(posX, posY));
                if (redTurn) {
                    UILog.info("It´s red crabby´s turn.");
                    boolean isValidMove = gameBoard.executeMove(true, posX, posY);
                    if (isValidMove) {
                        redTurn = !redTurn;
                    }
                }

                if (!redTurn) {
                    UILog.info("It´s green crabby´s turn.");
                    boolean isValidMove = gameBoard.executeBotMove();
                    if (isValidMove) {
                        redTurn = !redTurn;
                    }
                }

                whoHasLost = gameBoard.whoLost();
                if (whoHasLost != null) {
                    UILog.info(whoHasLost + " crabby lost the game");
                    if (whoHasLost == GameBoardState.GREEN || whoHasLost == GameBoardState.RED) {
                        startGameOverScreen();
                    }
                }
            }
            case GAMEOVER -> {
                if (mouseX > 190 && mouseX < 353 && mouseY > 610 && mouseY < 630) {
                    gameBoard = new GameBoard();
                    startInitScreen();
                }
            }
        }
    }

    private void startGameScreen() {
        currentState = GameState.GAME;
    }

    private void startInitScreen() {
        currentState = GameState.STARTSCREEN;
    }

    private void startInstructionScreen() {
        currentState = GameState.INSTRUCTION;
    }

    private void startGameOverScreen() {
        currentState = GameState.GAMEOVER;
    }

}

