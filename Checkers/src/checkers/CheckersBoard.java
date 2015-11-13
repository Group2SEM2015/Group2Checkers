package checkers;

/**
 *
 * @author Samir
 */
public class CheckersBoard {
    final int BOARDEDGE = 8;
    final int NW = 1;
    final int NE = 2;
    final int SE = 3;
    final int SW = 4;
    final int P1P = 1;      //Player 1 Piece
    final int P1K = 2;      //Player 1 King
    final int CP2P = 3;     //CPU/Player 2 Piece
    final int CP2K = 4;     //CPU/Player 2 King
    private CheckersPiece[][] board = new CheckersPiece[BOARDEDGE][BOARDEDGE];
    
    /**
     * Method newGame
     * 
     * Sets up the board for a new game by creating CheckersPieces for each
     * player and placing them in their respective starting locations.
     * 
     * Legend:
     * P1P  : 1 : Player Piece
     * P1K  : 2 : Player King
     * CP2P : 3 : CPU/Player 2 Piece
     * CP2K : 4 : CPU/Player 2 King
     */
    public void newGame(){
        for(int y = 0; y < BOARDEDGE; y++){
            for(int x = 0; x < BOARDEDGE; x++){
                if(y%2 == 0){
                    if(y < 3){
                        if(x%2 == 1){
                            board[x][y] = new CheckersPiece(CP2P, this, x, y);
                        }else{
                            board[x][y] = null;
                        }
                    }else{
                        if(y >= 5){
                            if(x%2 == 1){
                                board[x][y] = new CheckersPiece(P1P, this, x, y);
                            }else{
                                board[x][y] = null;
                            }
                        }
                    }
                }else{
                    if(y<3){
                        if(x%2 == 0){
                            board[x][y] = new CheckersPiece(CP2P, this, x, y);
                        }else{
                            board[x][y] = null;
                        }
                    }else{
                        if(y>=5){
                            if(x%2 == 0){
                                board[x][y] = new CheckersPiece(P1P, this, x, y);
                            }else{
                                board[x][y] = null;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean move(int xdest, int ydest){
        //Will implement move in just one moment
        return false;
    }
    
    public CheckersPiece[][] getBoard(){
        return board;
    }
    
    public void setBoard(CheckersPiece[][] newBoard){
        board = newBoard;
    }
}
