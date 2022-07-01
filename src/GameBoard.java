import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameBoard {

    private Isolation isolationInterface;
    public Piece[][] gameBoard;
    private final int size = 8;
    private Piece red, green;
    ArrayList<ArrayList<Move>> availableMoves = new ArrayList<>();

    public GameBoard() {
        gameBoard = new Piece[size][size];
    }

    public void setPiece(Piece piece) {
        gameBoard[piece.getPiecePosX()][piece.getPiecePosY()] = piece;
        switch (piece.getPlayerStatus()) {
            case RED -> red = piece;
            case GREEN -> green = piece;
        }
    }

    public Piece getPiece(int piecePosX, int piecePosY) {
        return gameBoard[piecePosX][piecePosY];
    }

    public void draw(PApplet canvas) {
        Arrays.stream(gameBoard).forEach(row -> Arrays.stream(row).filter(Objects::nonNull).forEach(cell -> cell.draw(canvas)));
    }

    public void executeMove(boolean whiteTurn, int piecePosX, int piecePosY) {

        if (whiteTurn && green == null) {
            setPiece(new Piece(FieldState.GREEN, piecePosX, piecePosY));
            Move move = new Move(piecePosX, piecePosY, piecePosX, piecePosY);
            isolationInterface = isolationInterface.play(move);
            return;
        }
        if (!whiteTurn && red == null) {
            setPiece(new Piece(FieldState.RED, piecePosX, piecePosY));
            Move move = new Move(piecePosX, piecePosY, piecePosX, piecePosY);
            isolationInterface = isolationInterface.play(move);
            return;
        }

        availableMoves.add(isolationInterface.availableMoves(piecePosX, piecePosY));

    }
}
