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
    private final CheckersPiece piece;
    private final CheckersBoard chkBrd;
    private final JPanel boardPanel;
    private final int BOARDEDGE = 8;
    private final boolean PLAYER;
    
    public CheckersMouseAdapter(){
        chkBrd = null;
        piece = null;
        boardPanel = null;
        PLAYER = false;
    }
    
    public CheckersMouseAdapter(CheckersPiece piece, CheckersBoard chkBrd,
            JPanel boardPanel){
        this.piece = piece;
        this.chkBrd = chkBrd;
        this.boardPanel = boardPanel;
        this.PLAYER = piece.getPlayer();
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        if(chkBrd.getTurn() == PLAYER && piece.getAi() == false){
            piece.dragFlip();
        }
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(chkBrd.getTurn() == PLAYER && piece.getAi() == false){
            if(!piece.dragFlip()){
                piece.dragFlip();
            }
            int moveX, moveY;
            int x = piece.getDrawX();
            int y = piece.getDrawY();
            x += e.getX();
            y += e.getY();
            if(x>=0 && x<boardPanel.getWidth() && y>=0
                    && y<boardPanel.getHeight()){
                piece.setDrawX(x);
                piece.setDrawY(y);
                moveX = convertX(x);
                moveY = convertY(y);
                piece.movePiece(moveX, moveY);
                piece.dragFlip();
            }else{
                piece.dragFlip();
                x = piece.getDrawX();
                y = piece.getDrawY();
            }
            piece.setLocation(x, y);
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        if(chkBrd.getTurn() == PLAYER && piece.getAi() == false){
            int x = piece.getDrawX();
            int y = piece.getDrawY();
            int offset = piece.getDrawFill()/2;
            x += e.getX();
            y += e.getY();
            if(x>=0 && x<boardPanel.getWidth() && y>=0
                    && y<boardPanel.getHeight()){
                piece.setDrawX(x-offset);
                piece.setDrawY(y-offset);
                piece.setLocation(x-offset, y-offset);
            }
        }
    }
    
    private int convertX(int x){
        int w = (int) boardPanel.getWidth();
        x = (x)/(w/BOARDEDGE);
        if(x >= BOARDEDGE){
            return BOARDEDGE-1;
        }else if(x < 0){
            return 0;
        }else{
            return x;
        }
    }
    
    private int convertY(int y){
        int h = (int) boardPanel.getHeight();
        y = (y)/(h/BOARDEDGE);
        if(y >= BOARDEDGE){
            return BOARDEDGE-1;
        }else if(y < 0){
            return 0;
        }else{
            return y;
        }
    }
}
