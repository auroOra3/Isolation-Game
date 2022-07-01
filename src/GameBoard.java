import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameBoard {

    private Isolation isolationInterface;
    public Piece[][] gameBoard;
    private final int size = 8;
    private Piece black;
    private Piece white;
    ArrayList<Piece> pieces = new ArrayList<>();

    public GameBoard() {
        gameBoard = new Piece[8][8];

    }

    public void setPiece(Piece piece) {
        gameBoard[piece.getPiecePosX()][piece.getPiecePosY()] = piece;
        if (piece.getPlayerStatus() == FieldState.BLACK) {
            black = piece;
        } else if (piece.getPlayerStatus() == FieldState.WHITE) {
            white = piece;
        }
    }

    public void draw(PApplet canvas) {
        Arrays.stream(gameBoard).forEach(row -> Arrays.stream(row).filter(Objects::nonNull).forEach(cell -> cell.draw(canvas)));
    }

    public Piece getPiece(int xPos, int yPos) {
        return gameBoard[xPos][yPos];
    }

    public void executeMove(boolean whiteTurn, int posX, int posY) {

        if (whiteTurn && white == null) {
            setPiece(new Piece(FieldState.WHITE, posX, posY));

            Move m = new Move(posX, posY, posX, posY);
            isolationInterface = isolationInterface.play(m);
            return;
        }
        if (!whiteTurn && black == null) {
            setPiece(new Piece(FieldState.BLACK, posX, posY));
            Move m = new Move(posX, posY, posX, posY);
            isolationInterface = isolationInterface.play(m);
            return;
        }

        //isolationInterface.getAvailableMoves();
    }
}
