package main.java;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameBoard {

    private final Random random = new Random();
    private Isolation isolationInterface = new IsolationGame();
    ArrayList<Move> legalMovesArray = new ArrayList<>();
    public Crab[][] gameBoard;
    private Crab redCrab, greenCrab;

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
        legalMovesArray.forEach(move -> {
            canvas.noFill();
            canvas.stroke(canvas.color(190, 0, 0));
            canvas.strokeWeight(2);
            canvas.circle(move.destX() * 45 + 88 + 45 / 2, move.destY() * 45 + 169 + 45 / 2, 35);
            canvas.strokeWeight(1);
        });
    }

    public boolean executeMove(boolean redTurn, int crabPosX, int crabPosY) {
        if (redTurn && redCrab == null) {
            setPiece(new Crab(GameBoardState.RED, crabPosX, crabPosY));
            Move move = new Move(crabPosX, crabPosY, crabPosX, crabPosY);
            isolationInterface = isolationInterface.play(move);
            return true;
        }

        if (legalMovesArray.stream().anyMatch(move -> move.destX() == crabPosX && move.destY() == crabPosY)) {
            Move move = new Move(redCrab.getCrabPosX(), redCrab.getCrabPosY(), crabPosX, crabPosY);
            movePiece(redCrab, crabPosX, crabPosY);
            isolationInterface = isolationInterface.play(move);
        } else {
            return false;
        }
        return true;
    }

    public boolean executeBotMove() {
        if (greenCrab == null) {
            Move move;
            do {
                int randomX = random.nextInt(0, 7);
                int randomY = random.nextInt(0, 7);
                move = new Move(randomX, randomY, randomX, randomY);
            } while (gameBoard[move.sourceX()][move.sourceY()] != null);
            isolationInterface = isolationInterface.play(move);
            greenCrab = new Crab(GameBoardState.GREEN, move.sourceX(), move.sourceY());
            gameBoard[move.sourceX()][move.sourceY()] = greenCrab;
            legalMovesArray = isolationInterface.legalMoves(redCrab.getCrabPosX(), redCrab.getCrabPosY());
            return true;
        }

        Move move = isolationInterface.bestMove();
        movePiece(greenCrab, move.destX(), move.destY());
        isolationInterface = isolationInterface.play(move);
        legalMovesArray = isolationInterface.legalMoves(redCrab.getCrabPosX(), redCrab.getCrabPosY());
        return true;
    }

    private void movePiece(Crab crab, int destX, int destY) {
        gameBoard[crab.getCrabPosX()][crab.getCrabPosY()] = new Crab(GameBoardState.BLOCKED, crab.getCrabPosX(), crab.getCrabPosY());
        crab.setCrabPosX(destX);
        crab.setCrabPosY(destY);
        gameBoard[destX][destY] = crab;
    }

    public GameBoardState whoLost() {
        if (greenCrab == null || redCrab == null)
            return null;
        if (isolationInterface.isGameOver(redCrab.getCrabPosX(), redCrab.getCrabPosY()))
            return GameBoardState.RED;
        return isolationInterface.isGameOver(greenCrab.getCrabPosX(), greenCrab.getCrabPosY()) ? GameBoardState.GREEN : null;
    }
}
