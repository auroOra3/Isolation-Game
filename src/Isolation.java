import java.util.ArrayList;

public interface Isolation {

    ArrayList<Move> availableMoves(int posX, int posY);
//    Move bestMove(int posX, int posY);
    Move bestMove();
    IsolationGame play(Move move);
    boolean isGameOver(int x, int y);


}
