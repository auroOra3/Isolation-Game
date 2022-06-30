import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameBoard {

    public Piece[][] field;
    private Piece black;
    private Piece white;

    public GameBoard(int size) {
        this.field = new Piece[size][size];
    }

    public void setPiece(Piece piece) {
        field[piece.getPosX()][piece.getPosY()] = piece;
        if (piece.getPlayerStatus() == FieldStatus.BLACK) {
            black = piece;
        } else if (piece.getPlayerStatus() == FieldStatus.WHITE) {
            white = piece;
        }
    }

    public void draw(PApplet canvas) {
        Arrays.stream(field).forEach(row -> Arrays.stream(row).filter(Objects::nonNull).forEach(cell -> cell.draw(canvas)));
    }
}
