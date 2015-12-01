package Checkers;

import java.util.ArrayList;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Samir
 */
public class CheckersBoard
{

    final int BOARDEDGE = 8;
    final int PIECETOTAL = 24;
    final int NW = 1;
    final int NE = 2;
    final int SE = 3;
    final int SW = 4;
    final int P1P = 1;          //Player 1 Piece
    final int P1K = 2;          //Player 1 King
    final int CP2P = 3;         //CPU/Player 2 Piece
    final int CP2K = 4;         //CPU/Player 2 King
    private ArrayList<CheckersPiece> pieceList = new ArrayList<>();
    private CheckersPiece[][] board = new CheckersPiece[BOARDEDGE][BOARDEDGE];
    private boolean turn = false;
    private int turnsRepeated = 0;
    private JPanel boardPanel;
    private JLayeredPane display;
    private boolean p1CanJump = false;
    private boolean p2CanJump = false;
    private boolean lockTurn = false;
    
    
    /**
     * Method CheckersBoard
     * 
     * Parameterized constructor for CheckersBoard that populates the board.
     * @param boardPanel    The JPanel that holds the board;
     */
    public CheckersBoard(JPanel boardPanel, JLayeredPane display){
        this.display = display;
        this.boardPanel = boardPanel;
        newGame();
    }
    
    /**
     * Method newGame
     *
     * Sets up the board for a new game by creating CheckersPieces for each
     * player and placing them in their respective starting locations.
     *
     * Legend: P1P : 1 : Player Piece P1K : 2 : Player King CP2P : 3 :
     * CPU/Player 2 Piece CP2K : 4 : CPU/Player 2 King
     */
    public void newGame()
    {
        int i = 0;
        for (int y = 0; y < BOARDEDGE; y++)
        {
            for (int x = 0; x < BOARDEDGE; x++)
            {
                if (y % 2 == 0)
                {
                    if (y < 3)
                    {
                        if (x % 2 == 1)
                        {
                            board[x][y] = new CheckersPiece(CP2P, this, x, y, 
                                    boardPanel, display);
                            pieceList.add(board[x][y]);
                        }
                        else
                        {
                            board[x][y] = null;
                        }
                    }
                    else
                    {
                        if (y >= 5)
                        {
                            if (x % 2 == 1)
                            {
                                board[x][y] = new CheckersPiece(P1P, this, x, y, 
                                        boardPanel, display);
                                pieceList.add(board[x][y]);
                            }
                            else
                            {
                                board[x][y] = null;
                            }
                        }
                    }
                }
                else
                {
                    if (y < 3)
                    {
                        if (x % 2 == 0)
                        {
                            board[x][y] = new CheckersPiece(CP2P, this, x, y, 
                                    boardPanel, display);
                            pieceList.add(board[x][y]);
                        }
                        else
                        {
                            board[x][y] = null;
                        }
                    }
                    else
                    {
                        if (y >= 5)
                        {
                            if (x % 2 == 0)
                            {
                                board[x][y] = new CheckersPiece(P1P, this, x, y, 
                                        boardPanel, display);
                                pieceList.add(board[x][y]);
                            }
                            else
                            {
                                board[x][y] = null;
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Method checkJumps
     * 
     * Description: Called by the pieces after they move to see if any piece
     * can jump. If they can, that respective player's canJump boolean will
     * be flipped to reflect whether or not there is a piece in their possession
     * that can jump.
     */
    public void checkJumps(){
        p1CanJump = false;
        p2CanJump = false;
        for(CheckersPiece piece : pieceList){
            if(piece != null){
                piece.checkJumpsExternal();
            }
        }
    }
    
    /**
     * Method deletePiece
     * 
     * Description: Deletes a piece from the list of pieces
     * 
     * @param piece The piece to be removed.
     */
    public void deletePiece(CheckersPiece piece){
        pieceList.remove(piece);
    }
    
    /**
     * Method deleteAllPieces
     * 
     * Description: Deletes all the CheckersPieces from the pieceList.
     */
    public void deleteAllPieces(){
        for(int x=0; x<BOARDEDGE; x++){
            for(int y=0; y<BOARDEDGE; y++){
                board[x][y] = null;
            }
        }
        pieceList.clear();
        turn = false;
        p1CanJump = false;
        p2CanJump = false;
    }
    
    /**
     * Method addPiece
     * 
     * Description: Adds a piece, provided by parameter, into the pieceList.
     * @param piece the piece to be added to the pieceList.
     */
    public void addPiece(CheckersPiece piece){
        pieceList.add(piece);
    }
    
    /**
     * Method lockTurns
     * 
     * Description: Locks the turn boolean so that it doesn't flip when the 
     * method flipTurn() is called.
     * 
     * @param player The player to lock the turns for.
     */
    public void lockTurns(boolean player){
        lockTurn = true;
        turn = player;
    }
    
    /**
     * Method unlockTurns
     * 
     * Description: Unlocks the turn boolean so that it may flip when the method
     * flipTurn() is called.
     */
    public void unlockTurns(){
        lockTurn = false;
    }

    /**
     * Method getBoard
     *
     * An accessor method for the board variable.
     *
     * @return a reference to the 2-Dimensional array board.
     */
    public CheckersPiece[][] getBoard()
    {
        return board;
    }

    /**
     * Method setBoard
     *
     * A mutator method for the board variable.
     *
     * @param newBoard 2-Dimensional CheckersPiece array
     */
    public void setBoard(CheckersPiece[][] newBoard)
    {
        board = newBoard;
    }

    /**
     * Method flipTurn
     *
     * Description: If the turns aren't locked, it flips the turn boolean.
     * Otherwise, it doesn't do anything.
     */
    public void flipTurn()
    {
        if(!lockTurn){
            turn = !turn;
        }
    }
    
    /**
     * Method flipCanJump
     * 
     * Description: Flips the p1CanJump or p2CanJump booleans depending on which
     * player is calling this method.
     * 
     * @param player    The player who is calling this method.
     * @param canJump   The value that the player's canJump will be set to.
     */
    public void flipCanJump(boolean player, boolean canJump){
        if(!player){ //Player 1
            p1CanJump = canJump;
        }else{
            p2CanJump = canJump;
        }
    }
    
    /**
     * method getCanJump
     * 
     * Description: An accessor method for the p1CanJump and p2CanJump booleans.
     * The boolean returned depends on the player who called the method.
     * 
     * @param player The player who is calling this method.
     * @return p1canJump if player 1 called the method, p2CanJump otherwise.
     */
    public boolean getCanJump(boolean player){
        if(!player){
            return p1CanJump;
        }else{
            return p2CanJump;
        }
    }

    /**
     * Method getTurn
     *
     * An accessor method for the variable turn.
     *
     * Legend: False: Player 1 Turn True : CPU/Player 2 Turn
     *
     * @return false if Player 1 Turn, true if CPU/Player 2 Turn
     */
    public boolean getTurn()
    {
        return turn;
    }
    
    public ArrayList<CheckersPiece> getPieceList(){
        return pieceList;
    }
}
