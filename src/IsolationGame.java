import java.util.ArrayList;

public class IsolationGame implements Isolation {
    private FieldState[][] board;
    public IsolationGame(int size) {
        board = new FieldState[size][size];
    }

    public ArrayList<Move> getAvailableMoves(int x, int y) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, 0, 1));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, 0, -1));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, 1, 0));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, -1, 0));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, 1, 1));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, -1, -1));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, 1, -1));
        availableMoves.addAll(getAvailableMovesDirections(board, x, y, -1, 1));
        return availableMoves;
    }

    private ArrayList<Move> getAvailableMovesDirections(FieldState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i <= field.length - 1; i++) {
            Move possibleMove = new Move(x, y, x-i*xMultiplier, y-i*yMultiplier);
            if (possibleMove.isValidMove(field)) moves.add(possibleMove);
            else break;
        }
        return moves;
    }

    @Override
    public Isolation play(Move move) {
        if (move.isValidMove(board))
            board[move.destinationX()][move.destinationY()] = FieldState.BLOCKED;


    }
}

record Move(int x, int y, int destinationX, int destinationY) {

    public boolean isValidMove(FieldState[][] board) {
        if (x >= 0 && x < board.length && y >= 0 && y < board.length && destinationX >= 0 && destinationX < board.length && destinationY >= 0 && destinationY < board.length)
            return false;
        FieldState player = board[x][y];
        // Hier eventuell dest nicht extra speichern
        FieldState dest = board[destinationX][destinationY];
        assert player != null && player != FieldState.BLOCKED;
        return dest == null;
    }
}

enum FieldState {
    BLACK,
    WHITE,
    BLOCKED;
}