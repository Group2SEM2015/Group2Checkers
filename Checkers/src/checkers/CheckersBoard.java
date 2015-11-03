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
                        board[xdest][ydest] = 1;
                    }else{
                        //jump
                    }
                }
                break;
            case 2:
                if(distance == 1.0 && board[xdest][ydest] == 0){
                    board[xi][yi] = 0;
                    board[xdest][ydest] = 2;
                }else{
                    //jump
                }
                break;
            case 3:
                if(direction >= SE){
                    if(distance == 1.0 && board[xdest][ydest] == 0){
                        board[xi][yi] = 0;
                        board[xdest][ydest] = 3;
                    }else{
                        //jump
                    }
                }
                break;
            case 4:
                if(distance == 1.0 && board[xdest][ydest] == 0){
                    board[xi][yi] = 0;
                    board[xdest][ydest] = 4;
                }else{
                    //jump
                }
                break;
            default:
                System.out.println("Invalid piece selected");
                return 2;
        }
        
        return 1;
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
