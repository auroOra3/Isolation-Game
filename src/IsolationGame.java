import java.util.ArrayList;

public class IsolationGame {
    private FieldState[][] field;
    public IsolationGame(int size) {
        field = new FieldState[size][size];
    }
    public ArrayList<Move> getAvaiableMoves(int x, int y) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, 0, 1));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, 0, -1));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, 1, 0));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, -1, 0));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, 1, 1));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, -1, -1));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, 1, -1));
        availableMoves.addAll(getAvaiableMovesDirections(field, x, y, -1, 1));
//        for (int i = 0; i <= field.length - 1; i++) {
//            Move possibleMove = new Move(x, y, x, y+i);
//            if (possibleMove.isValidMove(field)) availableMoves.add(possibleMove)
//            else break;
//        }
//
//        for (int i = 0; i <= field.length - 1; i++) {
//            Move possibleMove = new Move(x, y, x+i, y);
//            if (possibleMove.isValidMove(field)) availableMoves.add(possibleMove)
//            else break;
//        }
//
//        for (int i = 0; i <= field.length - 1; i++) {
//            Move possibleMove = new Move(x, y, x+i, y+i);
//            if (possibleMove.isValidMove(field)) availableMoves.add(possibleMove)
//            else break;
//        }
//
//        for (int i = 0; i <= field.length - 1; i++) {
//            Move possibleMove = new Move(x, y, x-i, y);
//            if (possibleMove.isValidMove(field)) availableMoves.add(possibleMove)
//            else break;
//        }
//
//        for (int i = 0; i <= field.length - 1; i++) {
//            Move possibleMove = new Move(x, y, x, y-i);
//            if (possibleMove.isValidMove(field)) availableMoves.add(possibleMove)
//            else break;
//        }
//
//        for (int i = 0; i <= field.length - 1; i++) {
//            Move possibleMove = new Move(x, y, x-i, y-i);
//            if (possibleMove.isValidMove(field)) availableMoves.add(possibleMove)
//            else break;
//        }
        return availableMoves;
    }

    private ArrayList<Move> getAvaiableMovesDirections(FieldState[][] field, int x, int y, int xMultiplier, int yMultiplier) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i <= field.length - 1; i++) {
            Move possibleMove = new Move(x, y, x-i*xMultiplier, y-i*yMultiplier);
            if (possibleMove.isValidMove(field)) moves.add(possibleMove);
            else break;
        }
        return moves;
    }
}

record Move(int x, int y, int destinationX, int destinationY) {

    public boolean isValidMove(FieldState[][] field) {
        if (x >= 0 && x < field.length && y >= 0 && y < field.length && destinationX >= 0 && destinationX < field.length && destinationY >= 0 && destinationY < field.length)
            return false;
        FieldState player = field[x][y];
        // Hier eventuell dest nicht extra speichern
        FieldState dest = field[destinationX][destinationY];
        assert player != null && player != FieldState.BLOCKED;
        return dest == null;
    }
}

enum FieldState {
    BLACK,
    WHITE,
    BLOCKED;
}