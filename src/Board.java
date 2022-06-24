import java.util.ArrayList;

public class Board {
    private Integer[][] gameBoard;
    private final Integer dimension=8;
    Integer posPlayer, posCom, numMoveCom, numPlayerCom, first;
    private ArrayList<Integer[]> moveSetCom;
    private ArrayList<Integer[]> moveSetPlayer;
    int f;

    public Board() {
        moveSetCom = new ArrayList<>();
        moveSetPlayer = new ArrayList<>();
        this.f = getFValue();
        gameBoard = new Integer[8][8];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if ((i+j)%2!=0)
                    gameBoard[i][j]=0;
                gameBoard[i][j]=1;
            }
        }

        updatePos(0, 0, 'x');
        updatePos(8, 8, 'o');
    }

    public Board(Board board, int row, int col, String c){
        this.gameBoard = new Integer[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
        this.playerPos = board.getPosPlayer();
        this.posComX = board.getPosCom();
        this.moveSetCom = new ArrayList<Integer[]>();
        this.moveSetPlayer = new ArrayList<Integer[]>();
        this.first = board.first;

        if (c.)

    }

    public int size(){
        return dimension;
    }

    public static Piece pieceAt(int x, int y, ArrayList<Piece> pieces){
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getX() == x && pieces.get(i).getY() == y)
                return pieces.get(i);
        }
        return null;
    }




}
