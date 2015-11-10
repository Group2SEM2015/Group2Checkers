/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private int[][] board = new int[BOARDEDGE][BOARDEDGE];
    
    /**
     * Method newGame
     * 
     * Sets up the board for a new game.
     * Legend:
     * 0 : Empty
     * 1 : Player Piece
     * 2 : Player King
     * 3 : CPU/Player 2 Piece
     * 4 : CPU/Player 2 King
     */
    public void newGame(){
        for(int y = 0; y < BOARDEDGE; y++){
            for(int x = 0; x < BOARDEDGE; x++){
                if(y%2 == 0){
                    if(y < 3){
                        if(x%2 == 1){
                            board[x][y] = 3;
                        }else{
                            board[x][y] = 0;
                        }
                    }else{
                        if(y >= 5){
                            if(x%2 == 1){
                                board[x][y] = 1;
                            }else{
                                board[x][y] = 0;
                            }
                        }
                    }
                }else{
                    if(y<3){
                        if(x%2 == 0){
                            board[x][y] = 3;
                        }else{
                            board[x][y] = 0;
                        }
                    }else{
                        if(y>=5){
                            if(x%2 == 0){
                                board[x][y] = 1;
                            }else{
                                board[x][y] = 0;
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
     * Moves a Checker piece.
     * 
     * Legend of Return values:
     * 1 : successful move
     * 2 : invalid piece selected
     * 
     * 
     * @param xi
     * @param yi
     * @param xdest
     * @param ydest
     * @return 
     */
    public int move(int xi, int yi, int xdest, int ydest){
        int direction = determineDirection(xi,yi,xdest,ydest);
        double distance = moveDistanceLin(xi,yi,xdest,ydest);
        int piece = board[xi][yi];
        
        switch(piece){
            case 1:
                if(direction <= NE){
                    if(distance == 1.0 && board[xdest][ydest] == 0){
                        board[xi][yi] = 0;
                        board[xdest][ydest] = piece;
                    }else{
                        if(distance == 2.0){
                            jump(xi,yi,xdest,ydest,direction);
                        }
                    }
                }
                break;
            case 2:
                if(distance == 1.0 && board[xdest][ydest] == 0){
                    board[xi][yi] = 0;
                    board[xdest][ydest] = piece;
                }else{
                    if(distance == 2.0){
                        jump(xi,yi,xdest,ydest,direction);
                    }
                }
                break;
            case 3:
                if(direction >= SE){
                    if(distance == 1.0 && board[xdest][ydest] == 0){
                        board[xi][yi] = 0;
                        board[xdest][ydest] = piece;
                    }else{
                        if(distance == 2.0){
                            jump(xi,yi,xdest,ydest,direction);
                        }
                    }
                }
                break;
            case 4:
                if(distance == 1.0 && board[xdest][ydest] == 0){
                    board[xi][yi] = 0;
                    board[xdest][ydest] = piece;
                }else{
                    if(distance == 2.0){
                        jump(xi,yi,xdest,ydest,direction);
                    }
                }
                break;
            default:
                System.out.println("Invalid piece selected");
                return 2;
        }
        
        return 1;
    }
    
    /**
     * Method jump
     * 
     * Determines whether a jump can be performed, and if it can, it performs
     * it by moving the selected piece to the destination and deleting the
     * piece in between.
     * 
     * @param xi        The initial x position of the selected piece.
     * @param yi        The initial y position of the selected piece.
     * @param xdest     The final x position of the selected piece.
     * @param ydest     The final y position of the selected piece.
     * @param direction The direction that the piece will be moving in.
     * @return 
     */
    
    private boolean jump(int xi, int yi, int xdest, int ydest, int direction){
        int piece = board[xi][yi];
        
        switch(direction){
            case NW:
                if(canMove(direction, piece) && canJump(xi,yi, xdest, ydest)){
                    board[xdest][ydest] = piece;
                    board[xdest+1][ydest+1] = 0;
                    return true;
                }
                break;
            case NE:
                if(canMove(direction, piece) && canJump(xi,yi, xdest, ydest)){
                    board[xdest][ydest] = piece;
                    board[xdest-1][ydest+1] = 0;
                    return true;
                }
                break;
            case SE:
                if(canMove(direction, piece) && canJump(xi,yi, xdest, ydest)){
                    board[xdest][ydest] = piece;
                    board[xdest+1][ydest-1] = 0;
                    return true;
                }
                break;
            case SW:
                if(canMove(direction, piece) && canJump(xi,yi, xdest, ydest)){
                    board[xdest][ydest] = piece;
                    board[xdest-1][ydest-1] = 0;
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
     * Determines whether a given piece can move towards the given direction.
     * 
     * @param direction The direction the piece wants to move to.
     * @param piece     The piece that wants to move.
     * @return          True if the piece can move in that direction, false
     *                  otherwise.
     */
    private boolean canMove(int direction, int piece){
        switch(piece){
            case 1:
                if(direction <= NE){
                    return true;
                }
                break;
            case 3:
                if(direction >= SE){
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
     * @param xi    The initial x position of the acting piece.
     * @param yi    The initial y position of the acting piece.
     * @param xdest The final x position of the acting piece.
     * @param ydest The final y position of the acting piece.
     * @return      True if a jump can be performed, false otherwise.
     */
    private boolean canJump(int xi, int yi, int xdest, int ydest){
        int piece = board[xi][yi];
        int direction = determineDirection(xi,yi,xdest,ydest);
        
        switch(direction){
            case NW:
                return opponentPiece(board[xdest+1][ydest+1], piece);
            case NE:
                return opponentPiece(board[xdest-1][ydest+1], piece);
            case SE:
                return opponentPiece(board[xdest+1][ydest-1], piece);
            case SW:
                return opponentPiece(board[xdest-1][ydest-1], piece);
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
     * @param targetPiece   The piece whose alignment needs evaluation.
     * @param playerPiece   The player's piece.
     * @return              True if target piece is opponent's, false otherwise.
     */
    private boolean opponentPiece(int targetPiece, int playerPiece){
        if(targetPiece == 0){
            return false;
        }
        if(playerPiece <= 2 && targetPiece >= 3){
            return true;
        }else{
            return playerPiece >= 3 && targetPiece <= 2;
        }
    }

    /**
     * Method moveDistanceLin
     * 
     * Calculates the linear distance between two squares. This method is
     * useful in determining whether or not the player wants to move to an
     * adjacent spot or make a more complicated move.
     * 
     * Note: The results are halved to make a regular move have a
     * value of 1.
     * 
     * @param xi    Initial x position of the Checker piece.
     * @param yi    Initial y position of the Checker piece.
     * @param xdest The x destination of the Checker piece.
     * @param ydest The y destination of the Checker piece.
     * @return      the distance between the initial and final positions.
     */
    private double moveDistanceLin(int xi, int yi, int xdest, int ydest){
        double xDistance = Math.abs(xi-xdest)/2;
        double yDistance = Math.abs(yi-ydest)/2;
        return xDistance + yDistance;
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
     * @param xi    Initial x position of the Checker piece.
     * @param yi    Initial y position of the Checker piece.
     * @param xdest The x destination of the Checker piece.
     * @param ydest The y destination of the Checker piece.
     * @return 
     */
    private int determineDirection(int xi, int yi, int xdest, int ydest){        
        if(xi > xdest){
            if(yi > ydest){
                return NW;
            }else{
                return SW;
            }
        }else{
            if(yi > ydest){
                return NE;
            }else{
                return SE;
            }
        }
    }
    
    public int[][] getBoard(){
        return board;
    }
}
