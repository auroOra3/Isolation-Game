import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameBoard {

    private Isolation isolationInterface = new IsolationGame();
    public Crab[][] gameBoard;
    private Crab redCrab, greenCrab;
    ArrayList<Move> availableMovesArray = new ArrayList<>();

    public GameBoard() {
        int size = 8;
        gameBoard = new Crab[size][size];
    }

    public void setPiece(Crab crab) {
        gameBoard[crab.getCrabPosX()][crab.getCrabPosY()] = crab;
        switch (crab.getPlayerStatus()) {
            case RED -> redCrab = crab;
            case GREEN -> greenCrab = crab;
        }
    }

    public Crab getPiece(int piecePosX, int piecePosY) {
        return gameBoard[piecePosX][piecePosY];
    }

    public void draw(PApplet canvas) {
        Arrays.stream(gameBoard).forEach(row -> Arrays.stream(row).filter(Objects::nonNull).forEach(cell -> cell.draw(canvas)));
        for (Move move : availableMovesArray) {
            canvas.noFill();
            canvas.stroke(canvas.color(190, 0, 0));
            canvas.strokeWeight(2);
            canvas.circle(move.destX() * 45 + 88 + 45 / 2, move.destY() * 45 + 169 + 45 / 2, 35);
            canvas.strokeWeight(1);
        }
    }

    public boolean executeMove(boolean redTurn, int crabPosX, int crabPosY) {


//        if (!redTurn && greenCrab == null) {
//            setPiece(new Crab(FieldState.GREEN, crabPosX, crabPosY));
//            Move move = new Move(crabPosX, crabPosY, crabPosX, crabPosY);
//            isolationInterface = isolationInterface.play(move);
//            if (redCrab != null) {
//                availableMovesArray = isolationInterface.availableMoves(redCrab.getCrabPosX(), redCrab.getCrabPosY());
//            }
//            return true;
//        }
        if (redTurn && redCrab == null) {
            setPiece(new Crab(FieldState.RED, crabPosX, crabPosY));
            Move move = new Move(crabPosX, crabPosY, crabPosX, crabPosY);
            isolationInterface = isolationInterface.play(move);
            return true;
        }

        if (availableMovesArray.stream().anyMatch(move -> move.destX() == crabPosX && move.destY() == crabPosY)) {
            Move move;
            if (!redTurn) {
                move = new Move(greenCrab.getCrabPosX(), greenCrab.getCrabPosY(), crabPosX, crabPosY);
                movePiece(greenCrab, crabPosX, crabPosY);
            } else {
                move = new Move(redCrab.getCrabPosX(), redCrab.getCrabPosY(), crabPosX, crabPosY);
                movePiece(redCrab, crabPosX, crabPosY);
            }
            isolationInterface = isolationInterface.play(move);
        } else {
            return false;
        }

        if (!redTurn) {
            availableMovesArray = isolationInterface.availableMoves(redCrab.getCrabPosX(), redCrab.getCrabPosY());

        } else {
            availableMovesArray = isolationInterface.availableMoves(greenCrab.getCrabPosX(), greenCrab.getCrabPosY());
        }
        return true;
    }

    public boolean executeBotMove() {
        if (greenCrab == null) {
            // TODO Deside on random first move
            setPiece(new Crab(FieldState.GREEN, 5, 5));
            Move move = isolationInterface.bestMove(5, 5);
            isolationInterface = isolationInterface.play(move);
            availableMovesArray = isolationInterface.availableMoves(redCrab.getCrabPosX(), redCrab.getCrabPosY());
            return true;
        }

        Move move = isolationInterface.bestMove(greenCrab.getCrabPosX(), greenCrab.getCrabPosY());
        movePiece(greenCrab, move.destX(), move.destY());
        isolationInterface = isolationInterface.play(move);
        availableMovesArray = isolationInterface.availableMoves(redCrab.getCrabPosX(), redCrab.getCrabPosY());
        return true;
    }

    private void movePiece(Crab crab, int destX, int destY) {
        gameBoard[crab.getCrabPosX()][crab.getCrabPosY()] = new Crab(FieldState.BLOCKED, crab.getCrabPosX(), crab.getCrabPosY());
        crab.setCrabPosX(destX);
        crab.setCrabPosY(destY);
        gameBoard[destX][destY] = crab;
    }

    public FieldState whoLost() {
        if (greenCrab == null || redCrab == null) {
            return null;
        }

        if (isolationInterface.isGameOver(redCrab.getCrabPosX(), redCrab.getCrabPosY())) {
            return FieldState.RED;
        }

        if (isolationInterface.isGameOver(greenCrab.getCrabPosX(), greenCrab.getCrabPosY())) {
            return FieldState.GREEN;
        }
        return null;
    }
}
