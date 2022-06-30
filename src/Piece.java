import processing.core.PApplet;

public class Piece extends PApplet {
    private int posX, posY, startX, startY;
    public FieldStatus playerStatus;
    private String ImgName;
    private PApplet canvas;


    public Piece(FieldStatus playerStatus) {
        switch (playerStatus) {
            case WHITE -> {
                setImage("./ressources/greenCrab.png");
                setStart(0, 7);
            }
            case BLACK -> {
                setImage("./ressources/redCrab.png");
                setStart(7,0);
            }
        }
        resetPosition();
    }

    public void setImage(String s) {

    }


}