package Checkers;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CheckersPiece extends JPanel
{

    private int x, y, pieceType;
    private int px, py; //Used for the x and y locations of the paint method
    private int pfill;  //Used for the fill width and height of Oval
    private int kingFill;
    private int cpuxi = 0;
    private int cpuyi = 0;
    private int cpuxf = 0;
    private int cpuyf = 0;
    private int cpuDestx = 0;
    private int cpuDesty = 0;
    private int cpuxInc = 1;
    private int cpuyInc = 1;
    private CheckersPiece[][] board;
    private CheckersBoard boardControl;
    private JPanel boardPanel;
    private boolean drag = false;
    private boolean ai = false;
    private boolean cpuDraw = false;
    private JLayeredPane display;
    private ActionListener animation;
    private Timer animationTimer;
    private Timer blinkTimer;

    final boolean PLAYER;
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
    final int ANIMA_DELAY = 1;
    final int BLINK_DELAY = 500;
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
        PLAYER = pieceType > P1K;
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
        System.err.println(x+":"+y+" Type: "+pieceType);
        int piece = board[x][y].getPieceType();
        boolean turn = boardControl.getTurn() == PLAYER;
        
        if(boardControl.checkWinner(PLAYER) > 0 && turn){
            return false;
        }else if(canMove(direction, xdest, ydest) && distance == 1){
            board[xdest][ydest] = this;
            board[x][y] = null;
            x = xdest;
            y = ydest;
        }else if(distance == 2 && canJump(xdest, ydest)){
            jump(x,y,xdest,ydest,direction);
        }else{
            return false;
        }
        checkKing();
        boardControl.flipTurn();
        boardControl.setBoard(board);
        boardControl.checkJumps();
        boardControl.checkWinner(PLAYER);
        boardControl.aiTurn();
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
                if (canDir(direction, piece) && canJump(xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest + 1][ydest + 1]);
                    display.remove(oppIndex);
                    boardControl.deletePiece(board[xdest+1][ydest+1]);
                    board[xdest + 1][ydest + 1] = null;
                }
                break;
            case NE:
                if (canDir(direction, piece) && canJump(xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest - 1][ydest + 1]);
                    display.remove(oppIndex);
                    boardControl.deletePiece(board[xdest-1][ydest+1]);
                    board[xdest - 1][ydest + 1] = null;
                }
                break;
            case SE:
                if (canDir(direction, piece) && canJump(xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest - 1][ydest - 1]);
                    display.remove(oppIndex);
                    boardControl.deletePiece(board[xdest-1][ydest-1]);
                    board[xdest - 1][ydest - 1] = null;
                }
                break;
            case SW:
                if (canDir(direction, piece) && canJump(xdest, ydest))
                {
                    oppIndex = display.getIndexOf(board[xdest + 1][ydest - 1]);
                    display.remove(oppIndex);
                    boardControl.deletePiece(board[xdest+1][ydest-1]);
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
        if(hasJumpOption()){
            boardControl.flipTurn();
        }
    }
    
    /**
     * Method hasJumpOption
     * 
     * Determines if a jump is possible from the piece's current location.
     * 
     * @return 
     */
    public boolean hasJumpOption(){
        switch (pieceType){
            case P1P:
                if (canJump(x - 2, y - 2) || canJump(x + 2, y - 2))
                {
                    return true;
                }
                break;
            case CP2K:
            case P1K:
                if (canJump(x - 2, y - 2) || canJump(x + 2, y - 2)
                        || canJump(x - 2, y + 2) || canJump(x + 2, y + 2))
                {
                    return true;
                }
                break;
            case CP2P:
                if (canJump(x - 2, y + 2) || canJump(x + 2, y + 2))
                {
                    return true;
                }
                break;
            default:
        }
        return false;
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
    public boolean canMove(int direction, int xdest, int ydest){
        if(xdest < 0 || xdest >= BOARDEDGE || ydest < 0 || ydest >= BOARDEDGE){
            return false;
        }
        boolean mov = board[xdest][ydest] == null;
        boolean dir = canDir(direction,pieceType);
        boolean jump = hasJumpOption();
        boolean hasToJump = boardControl.getCanJump(PLAYER);
        return mov && dir && (!jump && !hasToJump);
    }
    
    /**
     * Method hasMoveOption
     * 
     * Description: Checks if the selected piece can move in any direction.
     * 
     * @return true if the piece can move, false otherwise.
     */
    public boolean hasMoveOption(){
        if(canMove(NW, x-1, y-1) || canMove(NE, x+1,y-1) 
                || canMove(SE, x+1, y+1)
                || canMove(SW, x-1, y+1)){
            return true;
        }else{
            return false;
        }
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
    public boolean canJump(int xdest, int ydest)
    {
        int piece = board[x][y].getPieceType();
        int direction = determineDirection(x, y, xdest, ydest);
        
        if (xdest < 0 || xdest >= BOARDEDGE || ydest < 0 || ydest >= BOARDEDGE
                || board[xdest][ydest] != null || !canDir(direction, piece)){
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
    public int determineDirection(int xi, int yi, int xdest, int ydest)
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
     * Method checkJumpsExternal
     * 
     * Description: Checks if the piece can jump and calls the CheckersBoard's
     * flipCanJump method to set it to true or false depending on whether this
     * piece can jump.
     */
    public void checkJumpsExternal(){
        board = boardControl.getBoard();
        if(hasJumpOption()){
            boardControl.flipCanJump(PLAYER, true);
        }
    }
    
    /**
     * Method hasJumpExternal
     * 
     * Description: Sets the inner board to the CheckersBoard class's board,
     * then checks if the piece can jump.
     * 
     * @return true if the piece can jump, false otherwise.
     */
    public boolean hasJumpExternal(){
        board = boardControl.getBoard();
        if(hasJumpOption()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Method hasMoveExternal
     * 
     * Description: Sets the inner board to the CheckersBoard class's board,
     * then checks if the piece can make a regular move.
     * @return 
     */
    public boolean hasMoveExternal(){
        board = boardControl.getBoard();
        if(hasMoveOption()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Method checkMovesExternal
     * 
     * Description: Checks to see if the piece can move or jump in any
     * direction.
     * 
     * @return true if the piece can make a legal move, false otherwise.
     */
    public boolean checkMovesExternal(){
        board = boardControl.getBoard();
        if(hasMoveOption() || hasJumpOption()){
            return true;
        }else{
            return false;
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
        if(!drag || !cpuDraw){
            calculatePosition();
        }
        if(!cpuDraw){
            setLocation(px,py);
        }
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
    
    public void setCpuDraw(boolean setting){
        cpuDraw = setting;
    }
    
    public void setCpuDrawSettings(int xi, int yi, int xf, int yf, int xInc, 
            int yInc, int dx, int dy){
        cpuDraw = true;
        cpuxi = xi;
        cpuyi = yi;
        cpuxf = xf;
        cpuyf = yf;
        cpuxInc = directionModX(xInc, xi, yi, xf, yf);
        cpuyInc = directionModY(yInc, xi, yi, xf, yf);
        cpuDestx = dx;
        cpuDesty = dy;
        animation = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(!determineStop()){
                cpuDraw = true;
                cpuxi+= cpuxInc;
                System.out.println("Moving? " + cpuyi + " "+cpuyf);
                cpuyi+= cpuyInc;
                }else{
                    movePiece(cpuDestx, cpuDesty);
                    cpuDraw = false;
                    animationTimer.stop();
                }
                px = cpuxi;
                py = cpuyi;
                setLocation(px,py);
                display.repaint();
            }
        };
        if(animationTimer != null && animationTimer.isRunning()){
            animationTimer.stop();
            movePiece(dx,dy);
        }else{
            animationTimer = new Timer(ANIMA_DELAY, animation);
            animationTimer.start();
        }
    }
    
    private boolean determineStop(){
        int direction = determineDirection(cpuxi,cpuyi,cpuxf,cpuyf);
        switch(direction){
            case NW:
                if(cpuxi <= cpuxf || cpuyi <= cpuyf){
                    return true;
                }
                break;
            case NE:
                if(cpuxi >= cpuxf || cpuyi <= cpuyf){
                    return true;
                }
                break;
            case SE:
                if(cpuxi >= cpuxf || cpuyi >= cpuyf){
                    return true;
                }
                break;
            case SW:
                if(cpuxi <= cpuxf || cpuyi >= cpuyf){
                    return true;
                }
                break;
            default:
        }
        return false;
    }
    
    private int directionModX(int incX, int x, int y, int xf, int yf){
        int dir = determineDirection(x,y,xf,yf);
        if(dir == NW || dir == SW){
            return -incX;
        }else{
            return incX;
        }
    }
    
    private int directionModY(int incY, int x, int y, int xf, int yf){
        int dir = determineDirection(x,y,xf,yf);
        if(dir <= NE){
            return -incY;
        }else{
            return incY;
        }
    }
    
    public boolean getPlayer(){
        return pieceType > P1K;
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
    
    public int getBoardX(){
        return x;
    }
    
    public int getBoardY(){
        return y;
    }
    
    public boolean getAi(){
        return ai;
    }
    
    public void flipAi(){
        ai = !ai;
    }
}