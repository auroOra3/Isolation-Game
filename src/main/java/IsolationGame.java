package main.java;

import java.util.ArrayList;
import java.util.Random;

public class IsolationGame implements Isolation {

    private FieldState[][] board = new FieldState[8][8];
    private Location redCrab = null;
    private Location greenCrab = null;
    private static Random random = new Random();

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

    private ArrayList<Move> legalDirection(FieldState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
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
        ArrayList<Move> legalMoves = legalMoves(greenCrab.x(), greenCrab.y());
        if (legalMoves.size() > 0) {
            ArrayList<Move> bestMoves = new ArrayList<>();
            int evaluateBestMove = Integer.MAX_VALUE;
            for (Move move : legalMoves) {
                int evaluate = miniMaxAlphaBeta(3, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                if (evaluateBestMove > evaluate) {
                    evaluateBestMove = evaluate;
                    bestMoves.clear();
                    bestMoves.add(move);
                } else if (evaluateBestMove == evaluate) {
                    bestMoves.add(move);
                }
            }
            int randomIndex = random.nextInt(bestMoves.size());
            return bestMoves.get(randomIndex);
        }
        return null;
    }

    private int evaluate() {
        assert redCrab != null && greenCrab != null : "Green crabby and red crabby must be set";
        return legalMoves(redCrab.x(), redCrab.y()).size() - legalMoves(greenCrab.x(), greenCrab.y()).size();
    }

    private int miniMaxAlphaBeta(int depth, int alpha, int beta, boolean maxCrab) {
        assert redCrab != null && greenCrab != null : "Green crabby and red crabby must be set";
        if (depth == 0) {
            Move move = monteCarloAlgorithm(maxCrab);
            if (move == null) {
                if (maxCrab) return Integer.MIN_VALUE;
                return Integer.MAX_VALUE;
            }
            return play(move).evaluate();
        }
        if (isGameOver(redCrab.x(), redCrab.y()) || isGameOver(greenCrab.x(), greenCrab.y())) {
            return evaluate();
        }

        if (maxCrab) {
            int evaluateMaxValue = Integer.MIN_VALUE;
            for (Move move : legalMoves(redCrab.x(), redCrab.y())) {
                int evaluate = play(move).miniMaxAlphaBeta(depth - 1, alpha, beta, false);
                evaluateMaxValue = Math.max(evaluateMaxValue, evaluate);
                alpha = Math.max(alpha, evaluate);
                if (beta <= alpha) {
                    break;
                }
            }
            return evaluateMaxValue;
        }

        int evaluateMinValue = Integer.MAX_VALUE;
        for (Move move : legalMoves(greenCrab.x(), greenCrab.y())) {
            int eval = play(move).miniMaxAlphaBeta(depth - 1, alpha, beta, true);
            evaluateMinValue = Math.min(evaluateMinValue, eval);
            alpha = Math.min(alpha, eval);
            if (beta <= alpha) {
                break;
            }
        }
        return evaluateMinValue;
    }

    private Move monteCarloAlgorithm(boolean maxCrab) {
        ArrayList<Move> allLegalMoves;
        if (maxCrab) {
            allLegalMoves = legalMoves(redCrab.x(), redCrab.y());
        } else {
            allLegalMoves = legalMoves(greenCrab.x(), greenCrab.y());
        }

        if (allLegalMoves.isEmpty()) {
            System.out.println("Cancel Monte Carlo: No Moves Avaiable");
            return null;
        }

        Move bestMove = null;
        int bestMoveWinning;

        if (maxCrab) bestMoveWinning = Integer.MIN_VALUE;
        else bestMoveWinning = Integer.MAX_VALUE;

        IsolationGame isoGame;
        boolean randomGameTurn;
        for (Move move : allLegalMoves) {
            int wins = 0;
            for (int i = 0; i < 5; i++) {
                randomGameTurn = maxCrab;
                isoGame = this.play(move);
                while (!isoGame.isGameOver(isoGame.redCrab.x(), isoGame.redCrab.y()) && !isoGame.isGameOver(isoGame.greenCrab.x(), isoGame.greenCrab.y())) {
                    ArrayList<Move> randomLegalMoves = randomGameTurn ? isoGame.legalMoves(isoGame.redCrab.x(), isoGame.redCrab.y()) : isoGame.legalMoves(isoGame.greenCrab.x(), isoGame.greenCrab.y());
                    Move rmdMove = randomLegalMoves.get(random.nextInt(randomLegalMoves.size()));
                    isoGame = isoGame.play(rmdMove);
                    randomGameTurn = !randomGameTurn;

                }
                if (isoGame.isGameOver(redCrab.x(), redCrab.y())) {
                    wins++;
                } else {
                    wins--;
                }
            }

            if (maxCrab) {
                if (bestMoveWinning < wins) {
                    bestMove = move;
                    bestMoveWinning = wins;
                }
            } else {
                if (bestMoveWinning > wins) {
                    bestMove = move;
                    bestMoveWinning = wins;
                }
            }
            //System.out.println("Calculated total wins: " + wins);
        }
        assert bestMove != null;
        System.out.printf("Calculated best Move (%d/%d) to (%d/%d) - wins: %d%n", bestMove.sourceX(), bestMove.sourceY(), bestMove.destX(), bestMove.destY(), bestMoveWinning);
        System.out.println("Calculated best Move wins: " + bestMoveWinning);
        return bestMove;
    }

    @Override
    public IsolationGame play(Move move) {
        IsolationGame isolationGame = new IsolationGame(this);
        if (move.isLegalMove(isolationGame.board)) {
            if (move.sourceX() == move.destX() && move.sourceY() == move.destY()) {
                if (isolationGame.redCrab == null) {
                    isolationGame.board[move.sourceX()][move.sourceY()] = FieldState.RED;
                    isolationGame.redCrab = new Location(move.sourceX(), move.sourceY());
                } else {
                    isolationGame.board[move.sourceX()][move.sourceY()] = FieldState.GREEN;
                    isolationGame.greenCrab = new Location(move.sourceX(), move.sourceY());
                }
            } else {
                if (isolationGame.board[move.sourceX()][move.sourceY()] == FieldState.GREEN) {
                    isolationGame.greenCrab = new Location(move.destX(), move.destY());
                } else if (isolationGame.board[move.sourceX()][move.sourceY()] == FieldState.RED) {
                    isolationGame.redCrab = new Location(move.destX(), move.destY());
                }
                isolationGame.board[move.destX()][move.destY()] = isolationGame.board[move.sourceX()][move.sourceY()];
                isolationGame.board[move.sourceX()][move.sourceY()] = FieldState.BLOCKED;
            }
        }
        return isolationGame;
    }

    @Override
    public boolean isGameOver(int posX, int posY) {
        ArrayList<Move> moves = legalMoves(posX, posY);
        return moves.isEmpty();
    }
}

interface Isolation {
    ArrayList<Move> legalMoves(int posX, int posY);

    Move bestMove();

    IsolationGame play(Move move);

    boolean isGameOver(int posX, int posY);
}

record Location(int posX, int posY) {
}

record Move(int sourceX, int sourceY, int destX, int destY) {
    public boolean isLegalMove(FieldState[][] board) {
        if (sourceX < 0 || sourceX >= board.length || sourceY < 0 || sourceY >= board.length || destX < 0 || destX >= board.length || destY < 0 || destY >= board.length)
            return false;
        FieldState playerPos = board[sourceX][sourceY];
        FieldState destination = board[destX][destY];
        assert playerPos != null || playerPos != FieldState.BLOCKED;
        return destination == null;
    }
}

enum FieldState {
    RED,
    GREEN,
    BLOCKED;
}