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
                            board[y][x] = 3;
                        }else{
                            board[y][x] = 0;
                        }
                    }else{
                        if(y >= 5){
                            if(x%2 == 1){
                                board[y][x] = 1;
                            }else{
                                board[y][x] = 0;
                            }
                        }
                    }
                }else{
                    if(y<3){
                        if(x%2 == 0){
                            board[y][x] = 3;
                        }else{
                            board[y][x] = 0;
                        }
                    }else{
                        if(y>=5){
                            if(x%2 == 0){
                                board[y][x] = 1;
                            }else{
                                board[y][x] = 0;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public int move(int xi, int yi, int xdest, int ydest){
        int direction = determineDirection(xi,yi,xdest,ydest);
        int piece = board[yi][xi];
        return 0;
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
        int NW = 1;
        int NE = 2;
        int SE = 3;
        int SW = 4;
        
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
