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
    
    public void newGame(){
        for(int i = 0; i < BOARDEDGE; i++){
            for(int j = 0; j < BOARDEDGE; j++){
                if(i%2 == 0){
                    if(i <= 3){
                        if(j%2 == 1){
                            board[i][j] = 3;
                        }else{
                            board[i][j] = 0;
                        }
                    }else{
                        if(i >= 6){
                            if(j%2 == 1){
                                board[i][j] = 1;
                            }else{
                                board[i][j] = 0;
                            }
                        }
                    }
                }else{
                    if(i<=3){
                        if(j%2 == 0){
                            board[i][j] = 3;
                        }else{
                            board[i][j] = 0;
                        }
                    }else{
                        if(i>=6){
                            if(j%2 == 0){
                                board[i][j] = 1;
                            }else{
                                board[i][j] = 0;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public int[][] getBoard(){
        return board;
    }
}
