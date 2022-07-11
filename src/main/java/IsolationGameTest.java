package main.java;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class IsolationGameTest {

    @Test
    public void testRedCrab() {
        IsolationGame isolationGame = new IsolationGame();
        Move move = new Move(0, 7, 2, 3);
        isolationGame = isolationGame.play(move);
        ArrayList<Move> legalMoves = isolationGame.legalMoves(move.sourceX(), move.sourceY());
        assertTrue(m(4, 4, legalMoves));


    }

    boolean m(int x, int y, ArrayList<Move> moves) {
        return moves.stream().anyMatch(move -> move.destX() == x && move.destY() == y);
    }



}
