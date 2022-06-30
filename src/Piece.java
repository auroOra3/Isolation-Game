import processing.core.PApplet;
import processing.core.PImage;

public class Piece {
    private int posX, posY;
    public FieldStatus playerStatus;
    private PImage img;

    public Piece(FieldStatus playerStatus, PApplet canvas) {
        loadImage(canvas);
    }

    public void loadImage(PApplet canvas) {
        switch (playerStatus) {
            case WHITE -> {
                img = canvas.loadImage("./ressources/greenCrab.png");
            }
            case BLACK -> {
                img = canvas.loadImage("./ressources/redCrab.png");
            }
        }
    }

    public void draw(PApplet canvas) {

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public FieldStatus getPlayerStatus() {
        return playerStatus;
    }
}