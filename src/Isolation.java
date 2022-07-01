import java.util.ArrayList;

public interface Isolation {

    public ArrayList<Move> getAvailableMoves(int posX, int posY);

    public Isolation play(Move move);

}
