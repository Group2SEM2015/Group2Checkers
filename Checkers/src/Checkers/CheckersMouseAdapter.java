/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checkers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Samir
 */
public class CheckersMouseAdapter extends MouseAdapter{
    private final CheckersPiece[] board;
    private final int index;
    private final CheckersBoard chkBrd;
    
    public CheckersMouseAdapter(){
        chkBrd = null;
        board = null;
        index = 0;
    }
    
    public CheckersMouseAdapter(CheckersPiece[] board, CheckersBoard chkBrd,
            int index){
        this.board = board;
        this.chkBrd = chkBrd;
        this.index = index;
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        System.out.println(x);
        System.out.println(y);
        board[index].setLocation(x, y);
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        System.out.println(x);
        System.out.println(y);
        board[index].setLocation(x, y);
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        System.out.println(x);
        System.out.println(y);
        board[index].setLocation(x, y);
    }
}
