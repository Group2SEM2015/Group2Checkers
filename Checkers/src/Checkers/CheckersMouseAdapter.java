/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checkers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author Samir
 */
public class CheckersMouseAdapter extends MouseAdapter{
    private final CheckersPiece[] board;
    private final int index;
    private final CheckersBoard chkBrd;
    private final JPanel boardPanel;
    
    public CheckersMouseAdapter(){
        chkBrd = null;
        board = null;
        index = 0;
        boardPanel = null;
    }
    
    public CheckersMouseAdapter(CheckersPiece[] board, CheckersBoard chkBrd,
            int index, JPanel boardPanel){
        this.board = board;
        this.chkBrd = chkBrd;
        this.index = index;
        this.boardPanel = boardPanel;
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        board[index].dragFlip();
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e){
        CheckersPiece piece = board[index];
        int x = piece.getDrawX();
        int y = piece.getDrawY();
        x += e.getX();
        y += e.getY();
        piece.setDrawX(x);
        piece.setDrawY(y);
        board[index].setLocation(x, y);
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        //int x = e.getX();
        //int y = e.getY();
        //board[index].dragFlip();
    }
}
