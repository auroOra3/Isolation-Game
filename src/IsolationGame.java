import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class IsolationGame implements Isolation {

    private FieldState[][] board = new FieldState[8][8];
    private Location redCrab = null, greenCrab = null;
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

    public ArrayList<Move> availableMoves(int crabPosX, int crabPosY) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, 0, 1));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, 0, -1));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, 1, 0));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, -1, 0));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, 1, 1));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, -1, -1));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, 1, -1));
        availableMoves.addAll(availableDirection(board, crabPosX, crabPosY, -1, 1));
        return availableMoves;
    }

    private ArrayList<Move> availableDirection(FieldState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i <= field.length - 1; i++) {
            Move possibleMove = new Move(x, y, x + i * xMultiplier, y + i * yMultiplier);
            if (possibleMove.isValidMove(field)) {
                moves.add(possibleMove);
            } else break;
        }
        return moves;
    }

    @Override
    public Move bestMove() {
        assert greenCrab != null: "Green crabby must be set.";
        ArrayList<Move> moves = availableMoves(greenCrab.x(), greenCrab.y());
        Move bestMove = null;
        int bestMoveEval = Integer.MAX_VALUE;
        for (Move move : moves) {
            int eval = miniMaxAlphaBeta(3, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (bestMoveEval > eval) {
                bestMove = move;
                bestMoveEval = eval;
            }
        }
        return bestMove;
    }

    private int evaluate() {
        assert redCrab != null && greenCrab != null : "Green crabby and red crabby must be set";
        return availableMoves(redCrab.x(), redCrab.y()).size() - availableMoves(greenCrab.x(), greenCrab.y()).size();
    }

    private int miniMaxAlphaBeta(int depth, int alpha, int beta, boolean maxCrab) {
        assert redCrab != null && greenCrab != null : "Green crabby and red crabby must be set";
        if (depth == 0 || isGameOver(redCrab.x(), redCrab.y()) || isGameOver(greenCrab.x(), greenCrab.y()))
            return evaluate();

        if (maxCrab) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move: availableMoves(redCrab.x(), redCrab.y())) {
                int eval = play(move).miniMaxAlphaBeta(depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        }

        int minEval = Integer.MAX_VALUE;
        for (Move move: availableMoves(greenCrab.x(), greenCrab.y())) {
            int eval = play(move).miniMaxAlphaBeta(depth - 1, alpha, beta, true);
            minEval = Math.min(minEval, eval);
            alpha = Math.min(alpha, eval);
            if (beta <= alpha) {
                break;
            }
        }
        return minEval;
    }

    @Override
    public IsolationGame play(Move move) {
        IsolationGame isolationGame = new IsolationGame(this);
        if (move.isValidMove(isolationGame.board)) {
            if (move.x() == move.destX() && move.y() == move.destY()) {
                if (isolationGame.redCrab == null) {
                    isolationGame.board[move.x()][move.y()] = FieldState.RED;
                    isolationGame.redCrab = new Location(move.x(), move.y());
                } else {
                    isolationGame.board[move.x()][move.y()] = FieldState.GREEN;
                    isolationGame.greenCrab = new Location(move.x(), move.y());
                }
            } else {
                isolationGame.board[move.destX()][move.destY()] = isolationGame.board[move.x()][move.y()];
                isolationGame.board[move.x()][move.y()] = FieldState.BLOCKED;
            }
            if (isolationGame.board[move.x()][move.y()] == FieldState.GREEN) {
                isolationGame.greenCrab = new Location(move.destX(), move.destY());
            } else if (isolationGame.board[move.x()][move.y()] == FieldState.RED) {
                isolationGame.redCrab = new Location(move.destX(), move.destY());
            }
        }
        return isolationGame;
    }

    @Override
    public boolean isGameOver(int piecePosX, int piecePosY) {
        ArrayList<Move> moves = availableMoves(piecePosX, piecePosY);
        return moves.isEmpty();
    }
}

record Location(int x, int y) {
}

record Move(int x, int y, int destX, int destY) {
    public boolean isValidMove(FieldState[][] board) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length || destX < 0 || destX >= board.length || destY < 0 || destY >= board.length)
            return false;
        FieldState playerPos = board[x][y];
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