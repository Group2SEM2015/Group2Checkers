package Checkers;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLayeredPane;

/**
 *
 * @author Samir
 */
public class CheckersAI {
    public class moveNode{
        CheckersPiece piece;
        int destX;
        int destY;
    }
    
    final int NW = 1;
    final int NE = 2;
    final int SE = 3;
    final int SW = 4;
    final int BOARDEDGE = 8;
    final int BOARD_WIDTH = 650;
    final int BOARD_HEIGHT = 630;
    final int OFFSET = 10;
    final int DRAW_INC = 1;
    CheckersBoard board;
    CheckersPiece jumpLock;
    JLayeredPane display;
    ArrayList<CheckersPiece> cpuPieces;
    ArrayList<moveNode> moves;
    ArrayList<moveNode> jumps;
    Random rng;
    
    public CheckersAI(CheckersBoard board, JLayeredPane display){
        this.board = board;
        cpuPieces = new ArrayList<>();
        calculatePieces();
        moves = new ArrayList<>();
        jumps = new ArrayList<>();
        rng = new Random();
        this.display = display;
    }
    
    public void removePiece(CheckersPiece piece){
        cpuPieces.remove(piece);
    }
    
    public void recalculatePieces(){
        cpuPieces.clear();
        calculatePieces();
    }
    
    private void calculatePieces(){
        ArrayList<CheckersPiece> allPieces = board.getPieceList();
        for(CheckersPiece piece : allPieces){
            if(piece.getPlayer()){ //True == CPU/P2
                cpuPieces.add(piece);
                if(!piece.getAi()){
                    piece.flipAi();
                }
            }
        }
    }
    
    private void calculateJumps(){
        jumps.clear();
        for(CheckersPiece piece : cpuPieces){
            if(piece.hasJumpExternal()){
                //if(jumpLock != null){
                    addJumps(piece);
                //}else{
                    //if(jumpLock == piece){
                        //addJumps(piece);
                    //}
                //}
            }
        }
    }
    
    private void calculateMoves(){
        moves.clear();
        for(CheckersPiece piece : cpuPieces){
            if(piece.hasMoveExternal()){
                addMoves(piece);
            }
        }
    }
    
    private void addMoves(CheckersPiece piece){
        int x = piece.getBoardX();
        int y = piece.getBoardY();
        moveNode movePair;
        if(piece.canMove(NW, x-1, y-1)){
            movePair = new moveNode();
            movePair.piece = piece;
            movePair.destX = x-1;
            movePair.destY = y-1;
            moves.add(movePair);
        }
        if(piece.canMove(NE, x+1, y-1)){
            movePair = new moveNode();
            movePair.piece = piece;
            movePair.destX = x+1;
            movePair.destY = y-1;
            moves.add(movePair);
        }
        if(piece.canMove(SE, x+1, y+1)){
            movePair = new moveNode();
            movePair.piece = piece;
            movePair.destX = x+1;
            movePair.destY = y+1;
            moves.add(movePair);
        }
        if(piece.canMove(SW, x-1, y+1)){
            movePair = new moveNode();
            movePair.piece = piece;
            movePair.destX = x-1;
            movePair.destY = y+1;
            moves.add(movePair);
        }
    }
    
    private void addJumps(CheckersPiece piece){
        int x = piece.getBoardX();
        int y = piece.getBoardY();
        moveNode jump;
        if(piece.canJump(x-2,y-2)){
            jump = new moveNode();
            jump.piece = piece;
            jump.destX = x-2;
            jump.destY = y-2;
            jumps.add(jump);
        }
        if(piece.canJump(x+2, y-2)){
            jump = new moveNode();
            jump.piece = piece;
            jump.destX = x+2;
            jump.destY = y-2;
            jumps.add(jump);
        }
        if(piece.canJump(x+2, y+2)){
            jump = new moveNode();
            jump.piece = piece;
            jump.destX = x+2;
            jump.destY = y+2;
            jumps.add(jump);
        }
        if(piece.canJump(x-2, y+2)){
            jump = new moveNode();
            jump.piece = piece;
            jump.destX = x-2;
            jump.destY = y+2;
            jumps.add(jump);
        }
    }
    
    public void makeMove(){
        calculateMoves();
        calculateJumps();
        int randomNum;
        int xi, yi, xf, yf;
        moveNode movePair;
        CheckersPiece piece;
        if(jumps.size() > 0){
            randomNum = rng.nextInt(jumps.size());
            movePair = jumps.get(randomNum);
            piece = movePair.piece;
            xi = piece.getDrawX();
            yi = piece.getDrawY();
            //piece.setCpuDraw(true);
            xf = convertX(movePair.destX);
            yf = convertY(movePair.destY);
            piece.setCpuDrawSettings(xi, yi, xf, yf,DRAW_INC, DRAW_INC,
                    movePair.destX, movePair.destY);            
            //piece.movePiece(movePair.destX, movePair.destY);
            //display.repaint();
            if(piece.hasJumpExternal()){
                jumpLock = piece;
            }else{
                jumpLock = null;
            }
        }else if(moves.size() > 0){
            randomNum = rng.nextInt(moves.size());
            movePair = moves.get(randomNum);
            piece = movePair.piece;
            xi = piece.getDrawX();
            yi = piece.getDrawY();
            //piece.setCpuDraw(true);
            xf = convertX(movePair.destX);
            yf = convertY(movePair.destY);
            //piece.movePiece(movePair.destX, movePair.destY);
            //display.repaint();
            piece.setCpuDrawSettings(xi, yi, xf, yf,DRAW_INC, DRAW_INC,
                    movePair.destX, movePair.destY);
        }
    }
    
    private int convertX(int x){
        int w = BOARD_WIDTH;
        x = (w /BOARDEDGE) * x;
        return x;
    }
    
    private int convertY(int y){
        int h = BOARD_HEIGHT;
        y = (h/BOARDEDGE) * y;
        return y;
    }
}
