import processing.core.PImage;

import java.util.ArrayList;

public abstract class Piece {
    private int startX, startY, xPos, yPos;
    private boolean held;
    private int side;
    private ArrayList<Integer> moveSet = new ArrayList<>();

    abstract ArrayList<Integer> generateMove(int x, int y, ArrayList<Pieces> pieces);

    public void setMoveSet(ArrayList<Integer> moves) {
        this.moveSet = moves;
    }

    public ArrayList<Integer> getMoveSet() {
        return this.moveSet;
    }

    public boolean legalMove(int currX, int currY, int x, int y, ArrayList pieces) {
        boolean legalMove=false;
        for (int i = 0; i < moveSet.size(); i++) {
            if (moveSet.get(i) == x && moveSet.get(i+1) == y)
                legalMove = true;
        }
        return legalMove;
    }

    public void move(int currX, int currY, int x, int y, ArrayList<Piece> pieces) {
        if (this.legalMove(currX, currY, x, y, pieces)){
            xPos = x;
            yPos = y;
        }
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
    }

}
