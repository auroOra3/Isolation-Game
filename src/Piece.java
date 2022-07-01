import processing.core.PApplet;
import processing.core.PImage;

public class Piece {
    private int piecePosX, piecePosY;
    public FieldState playerStatus;
    private PImage crabImg;

    public Piece(FieldState playerStatus, int posX, int posY) {
        this.playerStatus = playerStatus;
        this.piecePosX = posX;
        this.piecePosY = posY;
        switch (this.playerStatus) {
            case WHITE -> {
                crabImg = Resources.greenCrab;
            }
            case BLACK -> {
                crabImg = Resources.redCrab;
            }
        }
    }

    public void draw(PApplet canvas) {
        canvas.image(crabImg, piecePosX * 45 + 88, piecePosY * 45 + 171, 45, 45);
    }

    public int getPiecePosX() {
        return piecePosX;
    }

    public int getPiecePosY() {
        return piecePosY;
    }

    public FieldState getPlayerStatus() {
        return playerStatus;
    }
}