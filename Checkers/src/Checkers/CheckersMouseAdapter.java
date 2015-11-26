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
    private final int BOARDEDGE = 8;
    
    private int xi = 0;
    private int yi = 0;
    
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
        CheckersPiece piece = board[index];
        xi = piece.getDrawX();
        yi = piece.getDrawY();
        board[index].dragFlip();
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e){
        CheckersPiece piece = board[index];
        int moveX, moveY;
        int x = piece.getDrawX();
        int y = piece.getDrawY();
        x += e.getX();
        y += e.getY();
        piece.setDrawX(x);
        piece.setDrawY(y);
        moveX = convertX(x);
        moveY = convertY(y);
        piece.movePiece(moveX, moveY);
        piece.dragFlip();
        piece.setLocation(x, y);
        System.out.println(x+":"+y+" on "+moveX +":"+moveY);
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        CheckersPiece piece = board[index];
        int x = piece.getDrawX();
        int y = piece.getDrawY();
        int offset = piece.getDrawFill()/2;
        x += e.getX();
        y += e.getY();
        piece.setDrawX(x-offset);
        piece.setDrawY(y-offset);
        piece.setLocation(x-offset, y-offset);
    }
    
    private int convertX(int x){
        //System.out.println("raw x: "+x);
        int w = (int) boardPanel.getWidth();
        x = (x)/(w/BOARDEDGE);
        //System.out.println("converted x: "+x);
        return x;
    }
    
    private int convertY(int y){
        //System.out.println("raw y: "+y);
        int h = (int) boardPanel.getHeight();
        y = (y)/(h/BOARDEDGE);
        //System.out.println("converted y: "+y);
        return y;
    }
}
