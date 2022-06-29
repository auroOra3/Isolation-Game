import java.util.ArrayList;

public class Board {
    private int[][] gameBoard;
    private final int dimension=8;

    public Board() {
        gameBoard = new int[8][8];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if ((i + j) % 2 == 0)
                    gameBoard[i][j] = 1;
                gameBoard[i][j]=0;
            }
        }
    }

    public boolean isWhite(int x, int y) {
        return gameBoard[x][y] == 0;
    }

    public int size() {
        return dimension;
    }

    // returns piece at a given index (x,y)
    public static Piece pieceAt(int x, int y, ArrayList<Piece> pieces){
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getX() == x && pieces.get(i).getY() == y)
                return pieces.get(i);
        }
        return null;
    }
}
