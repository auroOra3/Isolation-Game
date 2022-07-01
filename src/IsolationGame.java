import java.util.ArrayList;
import java.util.stream.IntStream;

public class IsolationGame implements Isolation {

    private FieldState[][] board;

    public IsolationGame(IsolationGame isolationGame) {
        IntStream.range(0, 8).forEach(x -> System.arraycopy(isolationGame.board[x], 0, board[x], 0, 8));
    }



    public ArrayList<Move> availableMoves(int x, int y) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        availableMoves.addAll(generateDirection(board, x, y, 0, 1));
        availableMoves.addAll(generateDirection(board, x, y, 0, -1));
        availableMoves.addAll(generateDirection(board, x, y, 1, 0));
        availableMoves.addAll(generateDirection(board, x, y, -1, 0));
        availableMoves.addAll(generateDirection(board, x, y, 1, 1));
        availableMoves.addAll(generateDirection(board, x, y, -1, -1));
        availableMoves.addAll(generateDirection(board, x, y, 1, -1));
        availableMoves.addAll(generateDirection(board, x, y, -1, 1));
        return availableMoves;
    }

    private ArrayList<Move> generateDirection(FieldState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
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
        IsolationGame isolationGame = new IsolationGame(this);
        if (move.isValidMove(board)) {
            isolationGame.board[move.destinationX()][move.destinationY()] = isolationGame.board[move.sourceX()][move.sourceY()];
            isolationGame.board[move.sourceX()][move.sourceY()] = FieldState.BLOCKED;
        }
        FieldState playerPos = isolationGame.board[move.destinationX()][move.destinationY()];


        return isolationGame;

    }
}

record Move(int sourceX, int sourceY, int destinationX, int destinationY) {

    public boolean isValidMove(FieldState[][] board) {
        if (sourceX >= 0 && sourceX < board.length && sourceY >= 0 && sourceY < board.length && destinationX >= 0 && destinationX < board.length && destinationY >= 0 && destinationY < board.length)
            return false;
        FieldState playerPos = board[sourceX][sourceY];
        FieldState destination = board[destinationX][destinationY];
        assert playerPos != null && playerPos != FieldState.BLOCKED;
        return destination == null;
    }
}

enum FieldState {
    RED,
    GREEN,
    BLOCKED;
}