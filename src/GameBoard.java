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
        for (Move move : availableMovesArray) {
            canvas.noFill();
            canvas.stroke(canvas.color(190, 0, 0));
            canvas.strokeWeight(2);
            canvas.circle(move.destX() * 45 + 88 + 45 / 2, move.destY() * 45 + 171 + 45 / 2, 35);
            canvas.strokeWeight(1);
        }
    }

    public void executeMove(boolean greenTurn, int piecePosX, int piecePosY) {

        if (greenTurn && green == null) {
            setPiece(new Piece(FieldState.GREEN, piecePosX, piecePosY));
            Move move = new Move(piecePosX, piecePosY, piecePosX, piecePosY);
            isolationInterface = isolationInterface.play(move);
            if (red != null) {
                availableMovesArray = isolationInterface.availableMoves(red.getPiecePosX(), red.getPiecePosY());
            }
            return;
        }
        if (!greenTurn && red == null) {
            setPiece(new Piece(FieldState.RED, piecePosX, piecePosY));
            Move move = new Move(piecePosX, piecePosY, piecePosX, piecePosY);
            isolationInterface = isolationInterface.play(move);
            if (green != null) {
                availableMovesArray = isolationInterface.availableMoves(green.getPiecePosX(), green.getPiecePosY());
            }
            return;
        }

        if (availableMovesArray.stream().anyMatch(move -> {return move.destX() == piecePosX && move.destY() == piecePosY;})) {
            if (greenTurn) {
                Move move = new Move(green.getPiecePosX(), green.getPiecePosY(), piecePosX, piecePosY);
                movePiece(green, piecePosX, piecePosY);
                isolationInterface = isolationInterface.play(move);
            } else {
                Move move = new Move(red.getPiecePosX(), red.getPiecePosY(), piecePosX, piecePosY);
                movePiece(red, piecePosX, piecePosY);
                isolationInterface = isolationInterface.play(move);
            }
        }

        if (greenTurn) {
            availableMovesArray = isolationInterface.availableMoves(red.getPiecePosX(), red.getPiecePosY());
        } else {
            availableMovesArray = isolationInterface.availableMoves(green.getPiecePosX(), green.getPiecePosY());
        }
    }

    private void movePiece(Piece piece, int destX, int destY) {
        gameBoard[piece.getPiecePosX()][piece.getPiecePosY()] = new Piece(FieldState.BLOCKED, piece.getPiecePosX(), piece.getPiecePosY());
        piece.setPiecePosX(destX);
        piece.setPiecePosY(destY);
        gameBoard[destX][destY] = piece;
    }
}
