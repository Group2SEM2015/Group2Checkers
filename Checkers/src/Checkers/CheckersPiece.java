package Checkers;

import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;

public class CheckersPiece extends JPanel
{

    private int x, y, pieceType;
    private int px, py; //Used for the x and y locations of the paint method
    private int pfill;  //Used for the fill width and height of Oval
    private int kingFill;
    private CheckersPiece[][] board;
    private CheckersBoard boardControl;
    private JPanel boardPanel;
    private boolean drag = false;
    private JLayeredPane display;

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
    final int KING_OFFSET = 10;
    final Color P1KCOLOR = new Color(255,165,0);
    final Color CP2KCOLOR = new Color(140,140,140);
    final Color NAVY = new Color(22,145,217); //Navy Blue

    public CheckersPiece(int pieceType, CheckersBoard boardControl,
            int x, int y, JPanel boardPanel, JLayeredPane display)
    {
        this.pieceType = pieceType;
        this.display = display;
        this.boardControl = boardControl;
        board = boardControl.getBoard();
        this.x = x;
        this.y = y;
        this.setOpaque(false);
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
            px = (((int) xy.getWidth()) /BOARDEDGE) * x;
            py = (((int) xy.getHeight())/BOARDEDGE) * y;
            px += OFFSET;
            py += OFFSET;
            pfill = ((int) (xy.getWidth()))/BOARDEDGE;
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
        
        if(canMove(direction, piece, xdest, ydest) && distance == 1){
            board[xdest][ydest] = this;
            board[x][y] = null;
            x = xdest;
            y = ydest;
        }else if(distance == 2 && canJump(x, y, xdest, ydest)){
            jump(x,y,xdest,ydest,direction);
        }else{
            return false;
        }
        checkKing();
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
        int oppIndex; //Index of opponent object in JLayeredPane display
        
        switch (direction)
        {
            case NW:
                if (canDir(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest + 1][ydest + 1]);
                    display.remove(oppIndex);
                    board[xdest + 1][ydest + 1] = null;
                }
                break;
            case NE:
                if (canDir(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest - 1][ydest + 1]);
                    display.remove(oppIndex);
                    board[xdest - 1][ydest + 1] = null;
                }
                break;
            case SE:
                if (canDir(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest - 1][ydest - 1]);
                    display.remove(oppIndex);
                    board[xdest - 1][ydest - 1] = null;
                }
                break;
            case SW:
                if (canDir(direction, piece) && canJump(xi, yi, xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest + 1][ydest - 1]);
                    display.remove(oppIndex);
                    board[xdest + 1][ydest - 1] = null;
                }
                break;
            default:
        }
        board[xdest][ydest] = this;
        display.repaint();
        board[xi][yi] = null;
        x = xdest;
        y = ydest;
        checkJumpOptions();
        return true;
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
                if (canJump(x, y, x - 2, y - 2) || canJump(x, y, x + 2, y - 2))
                {
                    boardControl.flipTurn();
                }
                break;
            case CP2K:
            case P1K:
                if (canJump(x, y, x - 2, y - 2) || canJump(x, y, x + 2, y - 2)
                        || canJump(x, y, x - 2, y + 2) || canJump(x, y, x + 2, y + 2))
                {
                    boardControl.flipTurn();
                }
                break;
            case CP2P:
                if (canJump(x, y, x - 2, y + 2) || canJump(x, y, x + 2, y + 2))
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
     * Description: Checks if the destination is free, if this piece can
     * move in the selected direction and if this piece hasn't already made
     * a move this turn.
     * 
     * @param direction the direction of the destination.
     * @param piece The type of piece this object is.
     * @param xdest the x position of the destination.
     * @param ydest the y position of the destination.
     * @return true if both statements are true, false otherwise.
     */
    private boolean canMove(int direction, int piece, int xdest, int ydest){
        boolean mov = board[xdest][ydest] == null;
        boolean dir = canDir(direction,piece);
        boolean turn = boardControl.getTurnsRepeated() == 0;
        return mov && dir && turn;
    }
    
    /**
     * Method canDir
     *
     * Determines whether a given piece can move towards the given direction.
     * 
     * Legend:
     * NW = 1
     * NE = 2
     * SE = 3
     * SW = 4
     * 
     * @param direction The direction the piece wants to move to.
     * @param piece The piece that wants to move.
     * @return True if the piece can move in that direction, false otherwise.
     */
    private boolean canDir(int direction, int piece)
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
        
        int piece = board[xi][yi].getPieceType();
        int direction = determineDirection(xi, yi, xdest, ydest);
        
        if (xdest < 0 || xdest >= BOARDEDGE || ydest < 0 || ydest >= BOARDEDGE
                || board[xdest][ydest] != null || !canDir(direction, piece)){
            System.out.println("Failing");
            return false;
        }

        switch (direction)
        {
            case NW:
                return opponentPiece(board[xdest + 1][ydest + 1], piece);
            case NE:
                return opponentPiece(board[xdest - 1][ydest + 1], piece);
            case SE:
                return opponentPiece(board[xdest - 1][ydest - 1], piece);
            case SW:
                return opponentPiece(board[xdest + 1][ydest - 1], piece);
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
     * Legend: 
     * P1P  : 1 : Player Piece 
     * P1K  : 2 : Player King 
     * CP2P : 3 : CPU/Player 2 Piece 
     * CP2K : 4 : CPU/Player 2 King
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
        double xChange = Math.abs(xi-xdest);
        double yChange = Math.abs(yi-ydest);
        double xDistance = xChange / 2;
        double yDistance = yChange / 2;
        return xChange/yChange == 1 ? xDistance + yDistance : 0.00;
    }

    /**
     * Method determineDirection
     *
     * Determines the direction that a piece wants to move to.
     *
     * Legend: 
     * NW : 1 
     * NE : 2 
     * SE : 3 
     * SW : 4
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
            }else
            {
                return SW;
            }
        }
        else
        {
            if (yi > ydest)
            {
                return NE;
            }else
            {
                return SE;
            }
        }
    }
    
    /**
     * Method checkKing
     * 
     * Description: Checks if the piece is in the position to become king, and
     * changes the piece type to king if it is.
     */
    private void checkKing(){
        switch(pieceType){
            case P1P:
                if(y == 0){
                    pieceType = P1K;
                }
                break;
            case CP2P:
                if(y == BOARDEDGE-1){
                    pieceType = CP2K;
                }
                break;
            default:
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
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //must call the parent's paint method before drawing the piece
        Color theColor; //Color of the CheckersPiece
        if(pieceType <= P1K) //If the piece is Player 1 King or Piece
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
        setLocation(px,py);
        g.setColor(theColor);
        g.fillOval(0, 0, pfill, pfill);
        if(pieceType == P1K || pieceType == CP2K){
            if(pieceType == P1K){
                g.setColor(P1KCOLOR);
            }else{
                g.setColor(CP2KCOLOR);
            }
            kingFill = pfill-(KING_OFFSET * 2);
            g.fillOval(KING_OFFSET, KING_OFFSET, kingFill, kingFill);
        }
    }
    
    public int getDrawX(){
        calculatePosition();
        return px;
    }
    
    public int getDrawY(){
        calculatePosition();
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
     * Method getDrag
     * 
     * Description: An accessor method for the drag boolean.
     * @return the value in the drag boolean.
     */
    public boolean getDrag(){
        return drag;
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