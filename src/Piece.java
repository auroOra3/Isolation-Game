import processing.core.PApplet;
import processing.core.PImage;

public class Piece {
    private int piecePosX, piecePosY;
    public FieldState playerStatus;
    private PImage img;

    public Piece(FieldState playerStatus, int posX, int posY) {
        this.playerStatus = playerStatus;
        this.piecePosX = posX;
        this.piecePosY = posY;
        switch (this.playerStatus) {
            case GREEN -> img = Resources.greenCrab;
            case RED -> img = Resources.redCrab;
            case BLOCKED -> img = Resources.blockedField;
        }
    }

    public void draw(PApplet canvas) {
        canvas.image(img, piecePosX * 45 + 88, piecePosY * 45 + 169, 45, 45);
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

    public void setPiecePosX(int piecePosX) {
        this.piecePosX = piecePosX;
    }

    public void setPiecePosY(int piecePosY) {
        this.piecePosY = piecePosY;
    }
}