import java.awt.*;
import java.util.ArrayList;

public class IsolationGame {

    private FieldStatus[][] field;

    public IsolationGame(int size) {
        field = new FieldStatus[size][size];
    }

    public ArrayList<Move> getAvaiableMoves(int x, int y) {

        ArrayList<Move> availableMoves = new ArrayList<>();
        activePlayer = FieldStatus[x][y];

        availableMoves.addAll(getAvaiableMovesDirections(field, 0, 1));
        availableMoves.addAll(getAvaiableMovesDirections(field, 0, -1));
        availableMoves.addAll(getAvaiableMovesDirections(field, 1, 0));
        availableMoves.addAll(getAvaiableMovesDirections(field, -1, 0));
        availableMoves.addAll(getAvaiableMovesDirections(field, 1, 1));
        availableMoves.addAll(getAvaiableMovesDirections(field, -1, -1));
        availableMoves.addAll(getAvaiableMovesDirections(field, 1, -1));
        availableMoves.addAll(getAvaiableMovesDirections(field, -1, 1));

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
    }

    private ArrayList<Move> getAvaiableMovesDirections(FieldStatus[][] field, int xMultiplier, int yMultiplier) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i <= field.length - 1; i++) {
            Move possibleMove = new Move(x, y, x-i*xMultiplier, y-i*yMultiplier);
            if (possibleMove.isValidMove(field)) moves.add(possibleMove)
            else break;
        }
        return
    }
}

record Move(int x, int y, int destinationX, int destinationY) {

    public boolean isValidMove(FieldStatus[][] field) {
        if (x >= 0 && x < field.length && y >= 0 && y < field.length && destinationX >= 0 && destinationX < field.length && destinationY >= 0 && destinationY < field.length) {
            return false;
        }
        FieldStatus player = field[x][y];
        // Hier eventuell dest nicht extra speichern
        FieldStatus dest = field[destinationX][destinationY];
        assert player != null && player != FieldStatus.BLOCKED;
        return dest == null;
    }
}

enum FieldStatus {
    BLACK,
    WHITE,
    BLOCKED;
}