package main.java;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.sound.*;

public class Images {

    public static PImage redCrab;
    public static PImage greenCrab;
    public static PImage[] menuScreenBackground;
    public static PImage[] gameOverScreenPirate;
    public static PImage islandScreen;
    public static PImage treasureIslandScreen;
    public static PImage treasureMap;
    public static PImage treasureMapNew;
    public static PImage blockedField;
    public static PFont beteFont;
    public static PFont pirateFont;
    public static int numOfFramesInitScreen = 92;
    public static int numOfFramesGameOverScreen = 21;
    public static SoundFile soundFile;
    public static PImage speechBubble;
    public static PImage speechBubble2;

    public static String redCrabPath = "./resources/redCrab.png";
    public static String whiteCrabPath = "./resources/greenCrab.png";
    public static String islandScreenPath = "./resources/frame_42_delay-0.12s.png";
    public static String treasureIslandPath = "./resources/treasureIsland.png";
    public static String betePath = "./resources/BeteNoirNF.ttf";
    public static String pirateFontPath = "./resources/PiratesBay.ttf";
    public static String treasureMapPath = "./resources/treasureMap.png";
    public static String treasureMapNewPath = "./resources/treasureMapTreasure.png";
    public static String blockedFieldPath = "./resources/blocked.png";
    public static String soundFilePath = "/resources/seashore_sound.wav";
    public static String speechBubblePath = "/resources/speech.png";
    public static String speechBubblePath2 = "/resources/speech2.png";

    public static void imagePath(PApplet canvas) {
        soundFile = new SoundFile(canvas, soundFilePath);
        menuScreenBackground = new PImage[numOfFramesInitScreen];
        redCrab = canvas.loadImage(redCrabPath);
        greenCrab = canvas.loadImage(whiteCrabPath);
        for (int i = 0; i < numOfFramesInitScreen; i++) {
            String initScreen = "frame_" + PApplet.nf(i, 2) + "_delay-0.12s.png";
            menuScreenBackground[i] = canvas.loadImage("./resources/" + initScreen);
        }
        speechBubble = canvas.loadImage(speechBubblePath);
        speechBubble.resize(175, 175);
        speechBubble2 = canvas.loadImage(speechBubblePath2);
        speechBubble2.resize(175, 175);
        beteFont = canvas.createFont(betePath, 128);
        pirateFont = canvas.createFont(pirateFontPath, 128);
        islandScreen = canvas.loadImage(islandScreenPath);
        treasureIslandScreen = canvas.loadImage(treasureIslandPath);
        treasureMap = canvas.loadImage(treasureMapPath);
        treasureMapNew = canvas.loadImage(treasureMapNewPath);
        blockedField = canvas.loadImage(blockedFieldPath);
        gameOverScreenPirate = new PImage[numOfFramesGameOverScreen];
        for (int i = 0; i < numOfFramesGameOverScreen ; i++) {
            String pirateScreen = "frame_" + PApplet.nf(i, 2) + "_delay-0.1s.gif";
            gameOverScreenPirate[i] = canvas.loadImage("./resources/" + pirateScreen);
        }
    }
}
