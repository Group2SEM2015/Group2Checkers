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
    final int NORMAL_MAX = 12;  //The maximum pieces a player can have normally.
    private ArrayList<CheckersPiece> pieceList = new ArrayList<>();
    private CheckersPiece[][] board = new CheckersPiece[BOARDEDGE][BOARDEDGE];
    private CheckersAI ai;
    private boolean turn = false;
    private int turnsRepeated = 0;
    private int p1PieceCnt = 0;
    private int p2PieceCnt = 0;
    private JPanel boardPanel;
    private JLayeredPane display;
    private boolean p1CanJump = false;
    private boolean p2CanJump = false;
    private boolean lockTurn = false;
    private boolean p1HasMove = true;
    private boolean p2HasMove = true;
    private boolean winUnlocked = true;
    
    
    
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
        p1PieceCnt = NORMAL_MAX;
        p2PieceCnt = NORMAL_MAX;
    }
    
    /**
     * Method newAi
     * 
     * Description: Makes a new ai to take control of the white pieces on the
     * board.
     */
    public void newAi(){
        ai = new CheckersAI(this, display);
    }
    
    /**
     * Method turnAiOff
     * 
     * Description: Makes the ai null to remove control of the white pieces on
     * the board from the ai.
     */
    public void turnAiOff(){
        ai = null;
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
     * Description: Decrements the appropriate piece counter and deletes a piece
     * from the list of pieces.
     * 
     * @param piece The piece to be removed.
     */
    public void deletePiece(CheckersPiece piece){
        if(!piece.getPlayer()){
            p1PieceCnt--;
        }else{
            p2PieceCnt--;
        }
        pieceList.remove(piece);
        if(ai != null && piece.getPlayer()){
            ai.removePiece(piece);
        }
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
        p1PieceCnt = 0;
        p2PieceCnt = 0;
        p1HasMove = true;
        p2HasMove = true;
    }
    
    /**
     * Method addPiece
     * 
     * Description: Increments the appropriate piece counter and adds a piece
     * that's provided by parameter into the pieceList.
     * 
     * @param piece the piece to be added to the pieceList.
     */
    public void addPiece(CheckersPiece piece){
        if(piece.getPlayer()){
            p1PieceCnt++;
        }else{
            p2PieceCnt++;
        }
        pieceList.add(piece);
    }
    
    public void aiTurn(){
        boolean test = ai != null;
        if(ai != null && turn){
            ai.makeMove();
        }
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
     * Method getWinner
     * 
     * Description: Checks the status of the board and returns a number based
     * on the results found.
     * 
     * @return  0:  The game has not yet finished.
     *          1:  Player 1 is the winner.
     *          2:  Player 2 is the winner.
     *          3:  The game is a draw.
     */
    public int checkWinner(boolean player){
        if(winUnlocked){
            checkStalemate(player);
            if(p1PieceCnt <= 0 && p2PieceCnt <= 0){
                System.out.println("Neither players win! They realize friendship is"
                        + " more important!");
                return 3;
            }else if(p1PieceCnt <= 0 || !p2HasMove){
                System.out.println("Player 1 wins!");
                return 1;
            }else if(p2PieceCnt <= 0 || !p1HasMove){
                System.out.println("Player 2 wins!");
                return 2;
            }
        }
        return 0;
    }
    
    /**
     * Method checkStalemate
     * 
     * Description: Checks the state of the game to see if either player can
     * move. If one player can't make a legal move, their opponent wins.
     */
    public void checkStalemate(boolean player){
        p1HasMove = false;
        p2HasMove = false;
        for(CheckersPiece piece : pieceList){
            if(piece.checkMovesExternal()){
                if(!piece.getPlayer()){
                    p1HasMove = true;
                }else{
                    p2HasMove = true;
                }
            }
        }
        if(!p1HasMove && p1PieceCnt > 0 && !player){
            System.out.println("Player 1 does not have a legal move.");
        }else if(!p2HasMove && p2PieceCnt > 0 && player){
            System.out.println("Player 2 does not have a legal move.");
        }
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
    
    public void lockWin(){
        winUnlocked = false;
    }
    
    public void unlockWin(){
        winUnlocked = true;
    }
    
    public ArrayList<CheckersPiece> getPieceList(){
        return pieceList;
    }
}
