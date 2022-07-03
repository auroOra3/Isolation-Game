import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameBoard {

    private Isolation isolationInterface = new IsolationGame();
    public Piece[][] gameBoard;
    private final int size = 8;
    private Piece red, green, currentPlayer = null;
    ArrayList<Move> availableMovesArray = new ArrayList<>();
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
        for (Move move: availableMovesArray) {
            canvas.circle(move.destinationX(), move.destinationY(), 90);
        }
    }

    public void executeMove(boolean greenTurn, int piecePosX, int piecePosY) {

        if (greenTurn && green == null) {
            setPiece(new Piece(FieldState.GREEN, piecePosX, piecePosY));
            Move move = new Move(piecePosX, piecePosY, piecePosX, piecePosY);
            isolationInterface = isolationInterface.play(move);
            return;
        }
        if (!greenTurn && red == null) {
            setPiece(new Piece(FieldState.RED, piecePosX, piecePosY));
            Move move = new Move(piecePosX, piecePosY, piecePosX, piecePosY);
            isolationInterface = isolationInterface.play(move);
            return;
        }

        availableMovesArray = isolationInterface.availableMoves(piecePosX, piecePosY);



    }
}
