package Checkers;

import javax.swing.JPanel;

/**
 *
 * @author Samir
 */
public class CheckersBoard
{

    final int BOARDEDGE = 8;
    final int NW = 1;
    final int NE = 2;
    final int SE = 3;
    final int SW = 4;
    final int P1P = 1;          //Player 1 Piece
    final int P1K = 2;          //Player 1 King
    final int CP2P = 3;         //CPU/Player 2 Piece
    final int CP2K = 4;         //CPU/Player 2 King
    private CheckersPiece[][] board = new CheckersPiece[BOARDEDGE][BOARDEDGE];
    private boolean turn = false;
    private int turnsRepeated = 0;
    private JPanel boardPanel;
    /**
     * Method CheckersBoard
     * 
     * Parameterized constructor for CheckersBoard that populates the board.
     * @param boardPanel    The JPanel that holds the board;
     */
    public CheckersBoard(JPanel boardPanel){
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
                            board[y][x] = new CheckersPiece(CP2P, this, x, y, boardPanel);
                            //System.out.println("position: " + y + "," + x +": "+board[y][x] + " gets a piece");
                        }
                        else
                        {
                            board[y][x] = null;
                            //System.out.println("position: " + y + "," + x +": "+board[y][x] + " does not get a piece");
                        }
                    }
                    else
                    {
                        if (y >= 5)
                        {
                            if (x % 2 == 1)
                            {
                                board[y][x] = new CheckersPiece(P1P, this, x, y, boardPanel);
                                //System.out.println("position: " + y + "," + x +": "+board[y][x] + " gets a piece");
                            }
                            else
                            {
                                board[y][x] = null;
                               // System.out.println("position: " + y + "," + x +": "+board[y][x] + " does not get a piece");
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
                            board[y][x] = new CheckersPiece(CP2P, this, x, y, boardPanel);
                            //System.out.println("position: " + y + "," + x +": "+board[y][x] + " gets a piece");
                        }
                        else
                        {
                            board[y][x] = null;
                            //System.out.println("position: " + y + "," + x +": "+board[y][x] + " does not get a piece");
                        }
                    }
                    else
                    {
                        if (y >= 5)
                        {
                            if (x % 2 == 0)
                            {
                                board[y][x] = new CheckersPiece(P1P, this, x, y, boardPanel);
                                //System.out.println("position: " + y + "," + x +": "+board[y][x] + " gets a piece");
                            }
                            else
                            {
                                board[y][x] = null;
                                //System.out.println("position: " + y + "," + x +": "+board[y][x] + " does not get a piece");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method move
     *
     * Calls the move method within the CheckersPiece object in the selected
     * square to determine whether to move or jump, and then perform that move.
     * Additionally, if the current player's turn has been in effect for more
     * than one move, turnsRepeated will increment.
     *
     * @param xi The initial x coordinate of the selected Piece.
     * @param yi The initial y coordinate of the selected Piece.
     * @param xdest The x coordinate of the destination for the selected Piece.
     * @param ydest The y coordinate of the destination for the selected Piece.
     * @return true if the move action was successful, false otherwise.
     */
    public boolean move(int xi, int yi, int xdest, int ydest)
    {
        boolean preTurn = turn;
        boolean result = board[xi][yi].movePiece(xdest, ydest);
        if (preTurn == turn)
        {
            turnsRepeated++;
        }
        else
        {
            turnsRepeated = 0;
        }
        return result;
    }

    /**
     * Method getTurnsRepeated
     *
     * An accessor method for the variable turnsRepeated.
     *
     * @return the value stored in turnsRepeated.
     */
    public int getTurnsRepeated()
    {
        return turnsRepeated;
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
     * Flips the turn boolean.
     */
    public void flipTurn()
    {
        turn = !turn;
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
}
