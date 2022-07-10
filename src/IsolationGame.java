import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class IsolationGame implements Isolation {

    private FieldState[][] board = new FieldState[8][8];
    Random random = new Random();
    int initialDepth = 3;

    public IsolationGame() { }

    public IsolationGame(IsolationGame isolationGame) {
        IntStream.range(0, 8).forEach(x -> System.arraycopy(isolationGame.board[x], 0, board[x], 0, 8));
    }

    public ArrayList<Move> availableMoves(int crabPosX, int crabPosY) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, 0, 1));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, 0, -1));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, 1, 0));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, -1, 0));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, 1, 1));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, -1, -1));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, 1, -1));
        availableMoves.addAll(generateDirection(board, crabPosX, crabPosY, -1, 1));
        return availableMoves;
    }

    @Override
    public Move bestMove(int xPos, int yPos) {
        return randomMove(xPos, yPos);
    }

    public Move randomMove(int posX, int posY) {
        ArrayList<Move> compMoves = availableMoves(posX, posY);
        return compMoves.get(random.nextInt(compMoves.size()));
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

//    @Override
//    public Move bestMove(int crabPosX, int crabPosY) {
//        IsolationGame isolationGame = new IsolationGame(this);
//        return maxValue(board, initialDepth, crabPosX, crabPosY, Integer.MIN_VALUE, Integer.MAX_VALUE);
//    }

//    public Move maxValue(FieldState[][] maxBoard, int depth, int maxCrabPosX, int maxCrabPosY, int alpha, int beta) {
//        int value = Integer.MIN_VALUE;
//        ArrayList<Move> maxAvailable = availableMoves(maxCrabPosX, maxCrabPosY);
//
//        for (int i = 0; i < maxAvailable.size(); i++) {
//            int minUtil = minValue(maxAvailable.get(i), depth-1, maxCrabPosX, maxCrabPosY, alpha, beta);
//        }
//    }

    public void minValue(FieldState[][] maxBoard, int depth, int maxCrabPosX, int maxCrabPosY, int alpha, int beta) {

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

record Node(GameBoard board, Node node, IsolationGame game) {

    public ArrayList<Node> generateSuccessor(Crab currentPlayer) {
        GameBoard currentBoard = this.board;
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (currentPlayer.getPlayerStatus() == FieldState.GREEN || currentPlayer.getPlayerStatus() == FieldState.RED) {
            possibleMoves = game.availableMoves(currentPlayer.getCrabPosX(), currentPlayer.getCrabPosY());
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            GameBoard child = new GameBoard(currentBoard, possibleMoves.get(i), currentPlayer);
        }
    }

}

enum FieldState {
    RED,
    GREEN,
    BLOCKED;
}