import java.util.ArrayList;
import java.util.Arrays;

public class IsolationGame implements Isolation {

    private FieldState[][] board = new FieldState[8][8];

    public IsolationGame() { }

    public IsolationGame(IsolationGame isolationGame) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++)
                board[x][y] = isolationGame.board[x][y];
        }
    }

    public ArrayList<Move> availableMoves(int piecePosX, int piecePosY) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, 0, 1));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, 0, -1));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, 1, 0));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, -1, 0));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, 1, 1));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, -1, -1));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, 1, -1));
        availableMoves.addAll(generateDirection(board, piecePosX, piecePosY, -1, 1));
        return availableMoves;
    }

    private ArrayList<Move> generateDirection(FieldState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 1; i <= field.length - 1; i++) {
            Move possibleMove = new Move(x, y, x-i*xMultiplier, y-i*yMultiplier);
            if (possibleMove.isValidMove(field)) {
                moves.add(possibleMove);
            } else break;
        }
        return moves;
    }

    @Override
    public Isolation play(Move move) {
        IsolationGame isolationGame = new IsolationGame(this);
        if (move.isValidMove(board)) {
            isolationGame.board[move.destX()][move.destY()] = isolationGame.board[move.x()][move.y()];
            isolationGame.board[move.x()][move.y()] = FieldState.BLOCKED;
        }
        return isolationGame;

    }

    @Override
    public boolean isGameOver(int piecePosX, int piecePosY) {
        ArrayList<Move> moves = availableMoves(piecePosX, piecePosY);
        return moves.isEmpty();
    }
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