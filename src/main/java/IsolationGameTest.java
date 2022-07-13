package main.java;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class IsolationGameTest {

    @Test
    public void testValidMoves() {
        IsolationGame game = new IsolationGame();
        game = game.play(new Move(5, 5, 5, 5));
        ArrayList<Move> moves = game.legalMoves(5, 5);
        assertEquals(25, moves.size());
        assertTrue(containsMove(5, 7, moves));
        assertTrue(containsMove(5, 6, moves));
        assertTrue(containsMove(5, 4, moves));
        assertTrue(containsMove(5, 3, moves));
        assertTrue(containsMove(5, 2, moves));
        assertTrue(containsMove(5, 1, moves));
        assertTrue(containsMove(5, 0, moves));
        assertTrue(containsMove(7, 5, moves));
        assertTrue(containsMove(6, 5, moves));
        assertTrue(containsMove(4, 5, moves));
        assertTrue(containsMove(3, 5, moves));
        assertTrue(containsMove(2, 5, moves));
        assertTrue(containsMove(1, 5, moves));
        assertTrue(containsMove(0, 5, moves));
        assertTrue(containsMove(7, 7, moves));
        assertTrue(containsMove(6, 6, moves));
        assertTrue(containsMove(4, 4, moves));
        assertTrue(containsMove(3, 3, moves));
        assertTrue(containsMove(2, 2, moves));
        assertTrue(containsMove(1, 1, moves));
        assertTrue(containsMove(0, 0, moves));
        assertTrue(containsMove(3, 7, moves));
        assertTrue(containsMove(4, 6, moves));
        assertTrue(containsMove(6, 4, moves));
        assertTrue(containsMove(7, 3, moves));
    }

    @Test
    public void testValidMovesWITH() {
        IsolationGame game = new IsolationGame();
        game = game.play(new Move(4, 4, 4, 4));
        game = game.play(new Move(6, 6, 6, 6));
        game = game.play(new Move(4, 4, 5, 5));
        ArrayList<Move> moves = game.legalMoves(5, 5);
        assertEquals(18, moves.size());
        assertTrue(containsMove(5, 7, moves));
        assertTrue(containsMove(5, 6, moves));
        assertTrue(containsMove(5, 4, moves));
        assertTrue(containsMove(5, 3, moves));
        assertTrue(containsMove(5, 2, moves));
        assertTrue(containsMove(5, 1, moves));
        assertTrue(containsMove(5, 0, moves));
        assertTrue(containsMove(7, 5, moves));
        assertTrue(containsMove(6, 5, moves));
        assertTrue(containsMove(4, 5, moves));
        assertTrue(containsMove(3, 5, moves));
        assertTrue(containsMove(2, 5, moves));
        assertTrue(containsMove(1, 5, moves));
        assertTrue(containsMove(0, 5, moves));
        assertFalse(containsMove(7, 7, moves));
        assertFalse(containsMove(6, 6, moves));
        assertFalse(containsMove(4, 4, moves));
        assertFalse(containsMove(3, 3, moves));
        assertFalse(containsMove(2, 2, moves));
        assertFalse(containsMove(1, 1, moves));
        assertFalse(containsMove(0, 0, moves));
        assertTrue(containsMove(3, 7, moves));
        assertTrue(containsMove(4, 6, moves));
        assertTrue(containsMove(6, 4, moves));
        assertTrue(containsMove(7, 3, moves));
    }

    @Test
    public void testPlay() {
        IsolationGame emptyGame = new IsolationGame();
        IsolationGame redCrabSetGame = emptyGame.play(new Move(4, 4, 4, 4));
        IsolationGame redCrabMovedGame = redCrabSetGame.play(new Move(4, 4, 3, 3));

        assertTrue(Arrays.stream(emptyGame.board).allMatch(fieldStates -> Arrays.stream(fieldStates).allMatch(Objects::isNull)));
        assertNull(emptyGame.redCrab);
        assertNull(emptyGame.greenCrab);
        assertEquals(FieldState.RED, redCrabSetGame.board[4][4]);
        assertTrue(Arrays.stream(redCrabSetGame.board).allMatch(fieldStates -> Arrays.stream(fieldStates).allMatch(fieldState -> fieldState != FieldState.BLOCKED)));
        assertNotNull(redCrabSetGame.redCrab);
        assertNull(redCrabSetGame.greenCrab);
        assertTrue(Arrays.stream(redCrabMovedGame.board).anyMatch(fieldStates -> Arrays.stream(fieldStates).anyMatch(fieldState -> fieldState == FieldState.BLOCKED)));
        assertNotNull(redCrabMovedGame.redCrab);
        assertNull(redCrabMovedGame.greenCrab);
        assertNotNull(redCrabMovedGame.play(new Move(2, 2, 2, 2)).greenCrab);
    }

    @Test
    public void testBestMove() {
        IsolationGame game = new IsolationGame();
        game = game.play(new Move(0, 0, 0, 0));
        game = game.play(new Move(0, 1, 0, 1));
        game.board[1][0] = FieldState.BLOCKED;
        Move move = game.bestMove();
        assertEquals(1, move.destX());
        assertEquals(1, move.destX());
    }

    boolean containsMove(int x, int y, ArrayList<Move> moves) {
        return moves.stream().anyMatch(move -> move.destX() == x && move.destY() == y);
    }


}
