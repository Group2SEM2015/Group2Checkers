package Checkers;

import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;

public class CheckersPiece extends JPanel
{

    private int x, y, pieceType;
    private int px, py; //Used for the x and y locations of the paint method
    private int pfill; //Used for the fill width and height of Oval
    private CheckersPiece[][] board;
    private CheckersBoard boardControl;
    private JPanel boardPanel;
    private boolean drag = false;

    final int BOARDEDGE = 8;
    final int NW = 1;
    final int NE = 2;
    final int SE = 3;
    final int SW = 4;
    final int P1P = 1;      //Player 1 Piece
    final int P1K = 2;      //Player 1 King
    final int CP2P = 3;     //CPU/Player 2 Piece
    final int CP2K = 4;     //CPU/Player 2 King
    final int OFFSET = 10;

    public CheckersPiece(int pieceType, CheckersBoard boardControl,
            int x, int y, JPanel boardPanel)
    {
        this.pieceType = pieceType;
        this.boardControl = boardControl;
        board = boardControl.getBoard();
        this.x = x;
        this.y = y;
        this.boardPanel = boardPanel;
        calculatePosition();
    }
    
    /**
     * Method calculatePosition
     * 
     * Description: Gets the x and y position of the panel the piece is in,
     * then uses their position in the CheckersBoard to calculate where they
     * should be drawn in the boardPanel.
     */
    private void calculatePosition(){
        if(!drag){
            Dimension xy = boardPanel.getSize();
            px = (((int) xy.getWidth()) /8) *x;
            py = (((int) xy.getHeight())/8) *y;
            px += OFFSET;
            py += OFFSET;
            System.out.println(xy.getWidth() + ":" + xy.getHeight());
            System.out.println(x + ":" + y);
            pfill = ((int) (xy.getWidth()))/8;
            pfill -= OFFSET * 2; //OFFSET variable only accounts for one side
        }
        
    }

    /**
     * Method move
     *
     * Determines whether to move or jump by looking at the x,y coordinates of
     * the destination and then performs that action.
     *
     * Note: If the current player performed a jump the previous turn, their
     * turn does not end until they can no longer jump.
     *
     * @param xdest The x coordinate of the destination.
     * @param ydest The y coordinate of the destination.
     * @return true if successfully moved, false otherwise.
     */
    public boolean movePiece(int xdest, int ydest)
    {
        board = boardControl.getBoard();
        if (xdest < 0 || xdest >= BOARDEDGE || ydest < 0 || ydest >= BOARDEDGE)
        {
            return false;
        }

        int direction = determineDirection(x, y, xdest, ydest);
        double distance = moveDistanceLin(x, y, xdest, ydest);
        int piece = board[x][y].getPieceType();

        switch (piece)
        {
            case 1:
                if (direction <= NE)
                {
                    if (distance == 1.0 && board[xdest][ydest] == null
                            && boardControl.getTurnsRepeated() == 0)
                    {
                        board[x][y] = null;
                        board[xdest][ydest] = this;
                        x = xdest;
                        y = ydest;
                    }
                    else
                    {
                        if (distance == 2.0)
                        {
                            jump(x, y, xdest, ydest, direction);
                        }
                    }
                }
                break;
            case 2:
                if (distance == 1.0 && board[xdest][ydest] == null
                        && boardControl.getTurnsRepeated() == 0)
                {
                    board[x][y] = null;
                    board[xdest][ydest] = this;
                    x = xdest;
                    y = ydest;
                }
                else
                {
                    if (distance == 2.0)
                    {
                        jump(x, y, xdest, ydest, direction);
                    }
                }
                break;
            case 3:
                if (direction >= SE)
                {
                    if (distance == 1.0 && board[xdest][ydest] == null
                            && boardControl.getTurnsRepeated() == 0)
                    {
                        board[x][y] = null;
                        board[xdest][ydest] = this;
                        x = xdest;
                        y = ydest;
                    }
                    else
                    {
                        if (distance == 2.0)
                        {
                            jump(x, y, xdest, ydest, direction);
                        }
                    }
                }
                break;
            case 4:
                if (distance == 1.0 && board[xdest][ydest] == null
                        && boardControl.getTurnsRepeated() == 0)
                {
                    board[x][y] = null;
                    board[xdest][ydest] = this;
                    x = xdest;
                    y = ydest;
                }
                else
                {
                    if (distance == 2.0)
                    {
                        jump(x, y, xdest, ydest, direction);
                    }
                }
                break;
            default:
                System.out.println("Invalid piece type.");
                return false;
        }

        boardControl.flipTurn();
        boardControl.setBoard(board);
        return true;
    }

    /**
     * Method jump
     *
     * Determines whether a jump can be performed, and if it can, it performs it
     * by moving the selected piece to the destination and deleting the piece in
     * between.
     *
     * @param xi The initial x position of the selected piece.
     * @param yi The initial y position of the selected piece.
     * @param xdest The final x position of the selected piece.
     * @param ydest The final y position of the selected piece.
     * @param direction The direction that the piece will be moving in.
     * @return true if the jump is successful, false otherwise.
     */
    private boolean jump(int xi, int yi, int xdest, int ydest, int direction)
    {
        int piece = board[xi][yi].getPieceType();

        switch (direction)
        {
            case NW:
                if (canMove(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    board[xdest][ydest] = this;
                    board[xdest + 1][ydest + 1] = null;
                    x = xdest;
                    y = ydest;
                    checkJumpOptions();
                    return true;
                }
                break;
            case NE:
                if (canMove(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    board[xdest][ydest] = this;
                    board[xdest - 1][ydest + 1] = null;
                    x = xdest;
                    y = ydest;
                    checkJumpOptions();
                    return true;
                }
                break;
            case SE:
                if (canMove(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    board[xdest][ydest] = this;
                    board[xdest + 1][ydest - 1] = null;
                    x = xdest;
                    y = ydest;
                    checkJumpOptions();
                    return true;
                }
                break;
            case SW:
                if (canMove(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    board[xdest][ydest] = this;
                    board[xdest - 1][ydest - 1] = null;
                    x = xdest;
                    y = ydest;
                    checkJumpOptions();
                    return true;
                }
                break;
            default:
        }

        return false;
    }

    /**
     * Method checkJumpOptions
     *
     * Determines if a sequential jump is possible from the lading location, and
     * if it is, flips the turn boolean in the boardControl object to extend the
     * current player's turn.
     */
    private void checkJumpOptions()
    {
        switch (pieceType)
        {
            case P1P:
                if (canJump(x, y, x - 1, y - 1) || canJump(x, y, x + 1, y - 1))
                {
                    boardControl.flipTurn();
                }
                break;
            case CP2K:
            case P1K:
                if (canJump(x, y, x - 1, y - 1) || canJump(x, y, x + 1, y - 1)
                        || canJump(x, y, x - 1, y + 1) || canJump(x, y, x + 1, y + 1))
                {
                    boardControl.flipTurn();
                }
                break;
            case CP2P:
                if (canJump(x, y, x - 1, y + 1) || canJump(x, y, x + 1, y + 1))
                {
                    boardControl.flipTurn();
                }
                break;
            default:
        }
    }

    /**
     * Method canMove
     *
     * Determines whether a given piece can move towards the given direction.
     *
     * @param direction The direction the piece wants to move to.
     * @param piece The piece that wants to move.
     * @return True if the piece can move in that direction, false otherwise.
     */
    private boolean canMove(int direction, int piece)
    {
        switch (piece)
        {
            case 1:
                if (direction <= NE)
                {
                    return true;
                }
                break;
            case 3:
                if (direction >= SE)
                {
                    return true;
                }
                break;
            default:
                return true;
        }
        return false;
    }

    /**
     * canJump
     *
     * Determines if a jump can be performed given a location and destination.
     *
     * @param xi The initial x position of the acting piece.
     * @param yi The initial y position of the acting piece.
     * @param xdest The final x position of the acting piece.
     * @param ydest The final y position of the acting piece.
     * @return True if a jump can be performed, false otherwise.
     */
    private boolean canJump(int xi, int yi, int xdest, int ydest)
    {
        if (xdest < 0 || xdest >= BOARDEDGE || ydest < 0 || ydest >= BOARDEDGE)
        {
            return false;
        }
        int piece = board[xi][yi].getPieceType();
        int direction = determineDirection(xi, yi, xdest, ydest);

        switch (direction)
        {
            case NW:
                return opponentPiece(board[xdest + 1][ydest + 1], piece);
            case NE:
                return opponentPiece(board[xdest - 1][ydest + 1], piece);
            case SE:
                return opponentPiece(board[xdest + 1][ydest - 1], piece);
            case SW:
                return opponentPiece(board[xdest - 1][ydest - 1], piece);
            default:
        }

        return false;
    }

    /**
     * Method opponentPiece
     *
     * Determines whether the target piece is a piece belonging to the opponent,
     * with respect of the player piece.
     *
     * Legend: P1P : 1 : Player Piece P1K : 2 : Player King CP2P : 3 :
     * CPU/Player 2 Piece CP2K : 4 : CPU/Player 2 King
     *
     * @param targetPiece The piece whose alignment needs evaluation.
     * @param playerPiece The player's piece.
     * @return True if target piece is opponent's, false otherwise.
     */
    private boolean opponentPiece(CheckersPiece targetPiece, int playerPiece)
    {
        if (targetPiece == null)
        {
            return false;
        }
        if (playerPiece <= P1K && targetPiece.getPieceType() >= CP2P)
        {
            return true;
        }
        else
        {
            return playerPiece >= CP2P && targetPiece.getPieceType() <= P1K;
        }
    }

    /**
     * Method moveDistanceLin
     *
     * Calculates the linear distance between two squares. This method is useful
     * in determining whether or not the player wants to move to an adjacent
     * spot or make a more complicated move.
     *
     * Note: The results are halved to make a regular move have a value of 1.
     *
     * @param xi Initial x position of the Checker piece.
     * @param yi Initial y position of the Checker piece.
     * @param xdest The x destination of the Checker piece.
     * @param ydest The y destination of the Checker piece.
     * @return the distance between the initial and final positions.
     */
    private double moveDistanceLin(int xi, int yi, int xdest, int ydest)
    {
        double xDistance = Math.abs(xi - xdest) / 2;
        double yDistance = Math.abs(yi - ydest) / 2;
        return xDistance + yDistance;
    }

    /**
     * Method determineDirection
     *
     * Determines the direction that a piece wants to move to.
     *
     * Legend: NW : 1 NE : 2 SE : 3 SW : 4
     *
     * @param xi Initial x position of the Checker piece.
     * @param yi Initial y position of the Checker piece.
     * @param xdest The x destination of the Checker piece.
     * @param ydest The y destination of the Checker piece.
     * @return
     */
    private int determineDirection(int xi, int yi, int xdest, int ydest)
    {
        if (xi > xdest)
        {
            if (yi > ydest)
            {
                return NW;
            }
            else
            {
                return SW;
            }
        }
        else
        {
            if (yi > ydest)
            {
                return NE;
            }
            else
            {
                return SE;
            }
        }
    }

    /**
     * Method getPieceType
     *
     * An accessor method for the variable pieceType
     *
     * @return the value of pieceType
     */
    public int getPieceType()
    {
        return pieceType;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g); //must call the parent's paint method before drawing the piece
        Color theColor; //Will soon have the color of the CheckersPiece
        if(pieceType <= 2)
        {
            theColor = Color.YELLOW;
        }
        else
        {
            theColor = Color.WHITE;
        }
        setOpaque(false);
        if(!drag){
            calculatePosition();
        }
        //setLocation(px,py);
        g.setColor(theColor);
        g.fillOval(0, 0, pfill, pfill);
    }
    
    public int getDrawX(){
        return px;
    }
    
    public int getDrawY(){
        return py;
    }
    
    public int getDrawFill(){
        return pfill;
    }
    
    public void setDrawX(int newX){
        px = newX;
    }
    
    public void setDrawY(int newY){
        py = newY;
    }
    
    /**
     * Method dragFlip
     * 
     * Description flips the drag boolean from true to false and vice versa.
     * This method is used to keep the CheckersPiece from recalculating where
     * it is supposed to be.
     * 
     * @return the new value of the boolean drag
     */
    public boolean dragFlip(){
        drag = !drag;
        return drag;
    }
}