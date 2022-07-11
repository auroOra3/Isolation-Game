package test.game;

import main.game.IsolationGame;
import main.game.Move;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IsolationGameTest {

    @Test
    public void testRedCrab() {
        IsolationGame isolationGame = new IsolationGame();
        Move move = new Move(0, 7, 2, 3);
        isolationGame = isolationGame.play(move);


    }



}
