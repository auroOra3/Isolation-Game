import java.util.ArrayList;

public interface Isolation {

    ArrayList<Move> availableMoves(int posX, int posY);
    Isolation play(Move move);
    boolean isGameOver(int x, int y);

}
