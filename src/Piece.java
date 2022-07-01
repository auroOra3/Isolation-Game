import processing.core.PApplet;
import processing.core.PImage;

public class Piece {
    private int posX, posY;
    public FieldState playerStatus;
    private PImage img;

    public Piece(FieldState playerStats, int posX, int posY) {
        this.playerStatus = playerStats;
        this.posX = posX;
        this.posY = posY;
        switch (playerStatus) {
            case WHITE -> {
                img = ImageRessources.whitePawn;
            }
            case BLACK -> {
                img = ImageRessources.blackPawn;
            }
        }
    }

    public void draw(PApplet canvas) {
        canvas.image(img, posX * 45 + 88, posY * 45 + 171, 45, 45);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public FieldState getPlayerStatus() {
        return playerStatus;
    }
}