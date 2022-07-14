package main.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Random;

public class IsolationGame implements Isolation {

    GameBoardState[][] board = new GameBoardState[8][8];
    Location redCrab = null;
    Location greenCrab = null;
    private static Random random = new Random();
    private static Logger logicLog = LogManager.getLogger(IsolationGame.class);

    public IsolationGame() {
    }

    public IsolationGame(IsolationGame oldIsolationGame) {
        this.greenCrab = oldIsolationGame.greenCrab;
        this.redCrab = oldIsolationGame.redCrab;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                board[x][y] = oldIsolationGame.board[x][y];
            }
        }
    }

    public ArrayList<Move> legalMoves(int crabPosX, int crabPosY) {
        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, 0, 1));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, 0, -1));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, 1, 0));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, -1, 0));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, 1, 1));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, -1, -1));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, 1, -1));
        legalMoves.addAll(legalDirection(board, crabPosX, crabPosY, -1, 1));
        return legalMoves;
    }

    private ArrayList<Move> legalDirection(GameBoardState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i <= field.length - 1; i++) {
            Move possibleMove = new Move(x, y, x + i * xMultiplier, y + i * yMultiplier);
            if (possibleMove.isLegalMove(field)) {
                moves.add(possibleMove);
            } else break;
        }
        return moves;
    }

    @Override
    public Move bestMove() {
        assert greenCrab != null : "Green crabby must be set";
        ArrayList<Move> legalMoves = legalMoves(greenCrab.posX(), greenCrab.posY());
        Move bestMove = null;
        int calculatedBestMove;
        if (legalMoves.size() > 0) {
            calculatedBestMove = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                logicLog.info("Calculate guarantee of success for move from (%d/%d) to (%d/%d)".formatted(move.sourceX(), move.sourceY(), move.destX(), move.destY()));
                int calculate = play(move).negaMaxAlphaBeta(2, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                logicLog.info("The calculation returns (%d)".formatted(calculate));
                if (calculatedBestMove < calculate) {
                    calculatedBestMove = calculate;
                    bestMove = move;
                }
            }
            assert bestMove != null : "Every crabby must have at least one move";
            logicLog.info("The randomized best move from (%d/%d) to (%d/%d).".formatted(bestMove.sourceX(), bestMove.sourceY(), bestMove.destX(), bestMove.destY()));
            return bestMove;
        }
        return null;
    }

    private int calculateRemainingMoves(boolean redCrabTurn) {
        assert redCrab != null && greenCrab != null : "Green crabby and red crabby must be set";
        if (redCrabTurn) {
            return -legalMoves(greenCrab.posX(), greenCrab.posY()).size();
        } else {
            return -legalMoves(redCrab.posX(), redCrab.posY()).size();
        }
    }

    private int negaMaxAlphaBeta(int depth, int alpha, int beta, boolean maxCrab) {
        assert redCrab != null && greenCrab != null : "Green crabby and red crabby must be set";
        logicLog.info("If maxCrab true, alphaBeta will try maximizing red player´s advantage :" + maxCrab);
        if (depth == 0) {
            Move move = monteCarloAlgorithm(maxCrab);
            if (move == null) {
                return calculateRemainingMoves(maxCrab);
            }
            return play(move).calculateRemainingMoves(maxCrab);
        }
        if (isGameOver(redCrab.posX(), redCrab.posY()) || isGameOver(greenCrab.posX(), greenCrab.posY())) {
            return calculateRemainingMoves(maxCrab);
        }

        if (maxCrab) {
            int calculatedMaxValue = Integer.MIN_VALUE;
            for (Move move : legalMoves(redCrab.posX(), redCrab.posY())) {
                logicLog.info("Calculate guarantee of success for move from (%d/%d) to (%d/%d)".formatted(move.sourceX(), move.sourceY(), move.destX(), move.destY()));
                int calculate = play(move).negaMaxAlphaBeta(depth - 1, alpha, beta, false);
                logicLog.info("The calculation returns (%d)".formatted(calculate));
                calculatedMaxValue = Math.max(calculatedMaxValue, calculate);
                alpha = Math.max(alpha, calculate);
                if (beta <= alpha) {
                    break;
                }
            }
            logicLog.info("Calculated max value (%d) will be returned".formatted(calculatedMaxValue));
            return calculatedMaxValue;
        }

        int calculatedMaxValue = Integer.MIN_VALUE;
        for (Move move : legalMoves(greenCrab.posX(), greenCrab.posY())) {
            logicLog.info("Calculate guarantee of success for move from (%d/%d) to (%d/%d)".formatted(move.sourceX(), move.sourceY(), move.destX(), move.destY()));
            int calculate = play(move).negaMaxAlphaBeta(depth - 1, alpha, beta, true);
            logicLog.info("The calculation returns (%d)".formatted(calculate));
            calculatedMaxValue = Math.max(calculatedMaxValue, calculate);
            alpha = Math.max(alpha, calculate);
            if (beta <= alpha) {
                break;
            }
        }
        logicLog.info("Calculated max value (%d) will be returned".formatted(calculatedMaxValue));
        return calculatedMaxValue;
    }

    private Move monteCarloAlgorithm(boolean maxCrab) {
        ArrayList<Move> allLegalMoves;
        Move bestMove = null;
        IsolationGame isoGame;

        if (maxCrab) {
            allLegalMoves = legalMoves(redCrab.posX(), redCrab.posY());
        } else {
            allLegalMoves = legalMoves(greenCrab.posX(), greenCrab.posY());
        }

        if (allLegalMoves.isEmpty()) {
            logicLog.info("Cancel monte carlo: no legal moves available");
            return null;
        }

        int countWinsForBestMove = Integer.MIN_VALUE;
        boolean decideWhichTurn;
        for (Move move : allLegalMoves) {
            int count = 0;
            for (int i = 0; i < 5; i++) {
                decideWhichTurn = maxCrab;
                isoGame = this.play(move);
                while (!isoGame.isGameOver(isoGame.redCrab.posX(), isoGame.redCrab.posY()) && !isoGame.isGameOver(isoGame.greenCrab.posX(), isoGame.greenCrab.posY())) {
                    ArrayList<Move> randomLegalMoves = decideWhichTurn ?
                            isoGame.legalMoves(isoGame.redCrab.posX(), isoGame.redCrab.posY()) : isoGame.legalMoves(isoGame.greenCrab.posX(), isoGame.greenCrab.posY());
                    Move randomMove = randomLegalMoves.get(random.nextInt(randomLegalMoves.size()));
                    isoGame = isoGame.play(randomMove);
                    decideWhichTurn = !decideWhichTurn;
                }
                if (isoGame.isGameOver(redCrab.posX(), redCrab.posY())) {
                    count++;
                } else {
                    count--;
                }
            }
            if (countWinsForBestMove < count) {
                bestMove = move;
                countWinsForBestMove = count;
            }
        }
        assert bestMove != null;
        logicLog.info("Calculated best move from (%d/%d) to (%d/%d) - wins: %d".formatted(bestMove.sourceX(), bestMove.sourceY(), bestMove.destX(), bestMove.destY(), countWinsForBestMove));
        logicLog.info("Calculated wins for best move: " + countWinsForBestMove);
        return bestMove;
    }

    @Override
    public IsolationGame play(Move move) {
        IsolationGame isolationGame = new IsolationGame(this);
        assert redCrab == null && greenCrab == null || legalMoves(move.sourceX(), move.sourceY()).stream().anyMatch(allowedMove -> allowedMove.destX() == move.destX() && allowedMove.destY() == move.destY()) : "Move must be an allowed move";
        if (move.isLegalMove(isolationGame.board)) {
            if (move.sourceX() == move.destX() && move.sourceY() == move.destY()) {
                if (isolationGame.redCrab == null) {
                    isolationGame.board[move.sourceX()][move.sourceY()] = GameBoardState.RED;
                    isolationGame.redCrab = new Location(move.sourceX(), move.sourceY());
                } else {
                    isolationGame.board[move.sourceX()][move.sourceY()] = GameBoardState.GREEN;
                    isolationGame.greenCrab = new Location(move.sourceX(), move.sourceY());
                }
            } else {
                if (isolationGame.board[move.sourceX()][move.sourceY()] == GameBoardState.GREEN) {
                    isolationGame.greenCrab = new Location(move.destX(), move.destY());
                } else if (isolationGame.board[move.sourceX()][move.sourceY()] == GameBoardState.RED) {
                    isolationGame.redCrab = new Location(move.destX(), move.destY());
                }
                isolationGame.board[move.destX()][move.destY()] = isolationGame.board[move.sourceX()][move.sourceY()];
                isolationGame.board[move.sourceX()][move.sourceY()] = GameBoardState.BLOCKED;
            }
        }
        return isolationGame;
    }

    @Override
    public boolean isGameOver(int posX, int posY) {
        ArrayList<Move> moves = legalMoves(posX, posY);
        return moves.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("-  -  -  -  -  -  -  -\n");

        for (int x = 0; x < board.length; x++) {
            for (GameBoardState[] gameBoardStates : board) {
                GameBoardState state = gameBoardStates[x];
                stringBuilder.append("|");
                if (state == null) {
                    stringBuilder.append(" ");
                } else {
                    if (state == GameBoardState.GREEN) {
                        stringBuilder.append("x");
                    }
                    if (state == GameBoardState.RED) {
                        stringBuilder.append("o");
                    }
                    if (state == GameBoardState.BLOCKED) {
                        stringBuilder.append("#");
                    }
                }
                stringBuilder.append("|");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(" -  -  -  -  -  -  -  -\n");
        return stringBuilder.toString();
    }
}

interface Isolation {
    ArrayList<Move> legalMoves(int posX, int posY);
    Move bestMove();
    Isolation play(Move move);
    boolean isGameOver(int posX, int posY);
}

record Location(int posX, int posY) {
}

record Move(int sourceX, int sourceY, int destX, int destY) {
    public boolean isLegalMove(GameBoardState[][] board) {
        if (sourceX < 0 || sourceX >= board.length || sourceY < 0 || sourceY >= board.length || destX < 0 || destX >= board.length || destY < 0 || destY >= board.length)
            return false;
        GameBoardState playerPos = board[sourceX][sourceY];
        GameBoardState destination = board[destX][destY];
        assert playerPos != null || playerPos != GameBoardState.BLOCKED;
        return destination == null;
    }
}

enum GameBoardState {
    RED,
    GREEN,
    BLOCKED;
}