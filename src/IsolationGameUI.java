import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class IsolationGameUI extends PApplet {

    GameState currentState = GameState.STARTSCREEN;
    int currentFrame = 0, numOfFrames = 92;
    PImage[] images = new PImage[numOfFrames];
    PImage gamePlayScreen;
    PImage treasureMap;
    PFont bete;

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{""}, new IsolationGameUI());
    }

    @Override
    public void settings() {
        size(540, 668);
    }

    @Override
    public void setup() {
        for (int i = 0; i < numOfFrames; i++) {
            String imageName = "frame_" + nf(i, 2) + "_delay-0.12s.png";
            images[i] = loadImage("./ressources/" + imageName);
        }
        bete = createFont("./ressources/BeteNoirNF.ttf", 128);
        gamePlayScreen = loadImage("./ressources/frame_42_delay-0.12s.png");
        treasureMap = loadImage("./ressources/treasureMap.png");



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
        currentFrame = (currentFrame+1) % numOfFrames;
        int offset = 0;
        for (int x = -100; x < width; x += images[0].width) {
            background(images[(currentFrame+offset) % numOfFrames]);
            offset+=2;
        }
        textFont(bete);
        textAlign(CENTER);
        fill(252, 241, 201);
        textSize(40);
        text("Don´t get stranded", width/2, height-590);
        fill(0, 95, 177);
        textSize(25);
        text("Click to start", width/2, height-60);
        textSize(15);
        text("Game Instructions", width/2, height-30);

    }

    public void gameInstructionScreen() {


    }

    public void gamePlayScreen() {
        int xOffset = 80, yOffset = 167;
        background(gamePlayScreen);
        image(treasureMap, 10, height-550, 200F* 2.6F, 166F*2.8F);
        boolean white = true;
        stroke(color(191,147,84));
        for (int x = 0; x < 350; x+=45) {
            for (int y = 0; y < 350; y+=45) {
                if(white) fill(color(194,145,78));
                else fill(color(229,189,128));
                rect(x + xOffset, y + yOffset, 40, 40, 12);
                white = !white;
            }
            white = !white;
        }

    }

    public void gameOverScreen() {

    }

    public void mousePressed() {
        System.out.println(mouseX);
        System.out.println(mouseY);
        switch (currentState) {
            case STARTSCREEN -> {
                   if (mouseX > 152 && mouseX < 389 && mouseY > 578 && mouseY < 616) {
                       startGame();
                   }
            }
            case GAMEOVER -> restart();
        }
    }

    private void restart() {
        currentState = GameState.STARTSCREEN;
    }

    private void startGame() {
        currentState = GameState.GAME;
    }
}

