package main.java;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Images {

    public static PImage redCrab;
    public static PImage greenCrab;
    public static PImage[] menuScreenBackground;
    public static PImage islandScreen;
    public static PImage treasureIslandScreen;
    public static PImage treasureMap;
    public static PImage blockedField;
    public static PFont beteFont;
    public static PFont pirateFont;
    public static int numOfFrames = 92;

    public static String redCrabPath = "./resources/redCrab.png";
    public static String whiteCrabPath = "./resources/greenCrab.png";
    public static String islandScreenPath = "./resources/frame_42_delay-0.12s.png";
    public static String treasureIslandPath = "./resources/treasureIsland.png";
    public static String betePath = "./resources/BeteNoirNF.ttf";
    public static String pirateFontPath = "./resources/PiratesBay.ttf";
    public static String treasureMapPath = "./resources/treasureMap.png";
    public static String blockedFieldPath = "./resources/blocked.png";

    public static void imagePath(PApplet canvas) {
        menuScreenBackground = new PImage[numOfFrames];
        redCrab = canvas.loadImage(redCrabPath);
        greenCrab = canvas.loadImage(whiteCrabPath);
        for (int i = 0; i < numOfFrames; i++) {
            String imageName = "frame_" + PApplet.nf(i, 2) + "_delay-0.12s.png";
            menuScreenBackground[i] = canvas.loadImage("./resources/" + imageName);
        }
        beteFont = canvas.createFont(betePath, 128);
        pirateFont = canvas.createFont(pirateFontPath, 128);
        islandScreen = canvas.loadImage(islandScreenPath);
        treasureIslandScreen = canvas.loadImage(treasureIslandPath);
        treasureMap = canvas.loadImage(treasureMapPath);
        blockedField = canvas.loadImage(blockedFieldPath);
    }
}
