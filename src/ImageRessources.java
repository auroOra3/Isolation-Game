import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class ImageRessources {

    public static PImage blackPawn;
    public static PImage whitePawn;
    public static PImage[] menuScreenBackground;
    public static PImage islandScreen;
    public static PImage treasureMap;
    public static PFont bete;
    public static int numOfFrames = 92;

    public static String blackPawnPath = "./ressources/redCrab.png";
    public static String whitePawnPath = "./ressources/greenCrab.png";
    public static String islandScreenPath = "./ressources/frame_42_delay-0.12s.png";
    public static String betePath = "./ressources/BeteNoirNF.ttf";
    public static String treasureMapPath = "./ressources/treasureMap.png";

    public static void loadRessources(PApplet canvas) {
        menuScreenBackground = new PImage[numOfFrames];
        blackPawn = canvas.loadImage(blackPawnPath);
        whitePawn = canvas.loadImage(whitePawnPath);
        for (int i = 0; i < numOfFrames; i++) {
            String imageName = "frame_" + PApplet.nf(i, 2) + "_delay-0.12s.png";
            menuScreenBackground[i] = canvas.loadImage("./ressources/" + imageName);
        }
        bete = canvas.createFont(betePath, 128);
        islandScreen = canvas.loadImage(islandScreenPath);
        treasureMap = canvas.loadImage(treasureMapPath);
    }
}
