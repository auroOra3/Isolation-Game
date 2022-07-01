import java.util.ArrayList;

public interface Isolation {

    public ArrayList<Move> availableMoves(int posX, int posY);
    public Isolation play(Move move);

}
