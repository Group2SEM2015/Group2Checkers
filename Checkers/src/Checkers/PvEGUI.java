package Checkers;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PvEGUI {
    public JFrame pveGUI;
    public JPanel board = new JPanel();
    public JLabel pic = new JLabel();
    public JPanel[][] checkerBoard1;
    public JPanel checkerBoardPanel = new JPanel();
    public JPanel redPanel;
    public JPanel blackPanel;
    public JLayeredPane boardLayer = new JLayeredPane();
    public PvEGUI(){
        pveGUI = new JFrame("Player Vs. Computer");
        pveGUI.setSize(665,670);
        pveGUI.setVisible(true);
        //sets image to JLabel
        //ImageIcon icon = new ImageIcon(new ImageIcon("Images/checkers_board.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));
        //pic.setIcon(icon);
        //board.add(pic);
        createCheckerBoard(checkerBoardPanel);
        pveGUI.add(boardLayer);
        //pveGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pveGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        pveGUI.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                CheckersGUI gui1 = new CheckersGUI();
                pveGUI.dispose();
            }
        });
    }
    public void createCheckerBoard(JPanel checkerBoardPanel)
    {
        int dx,dy, dfill;
        checkerBoard1 = new JPanel[8][8];
        checkerBoardPanel.setLayout(new GridLayout(8, 8));
        checkerBoardPanel.setSize(650, 630);
        CheckersBoard cb = new CheckersBoard(checkerBoardPanel, boardLayer);
        ArrayList<CheckersPiece> pieceList = cb.getPieceList();
        CheckersPiece[][] tmp = cb.getBoard();
        cb.newAi();
        
        for (int i = 0; i < 8; i++)
        {
            for (int k = 0; k < 8; k++)
            {
                if ((k + i) % 2 == 0)
                {
                    redPanel = new JPanel();
                    redPanel.setLayout(new BorderLayout());
                    redPanel.setBackground(Color.red);
                    redPanel.setVisible(true);
                    checkerBoard1[i][k] = (redPanel);
                }
                else
                {
                    blackPanel = new JPanel();
                    blackPanel.setLayout(new BorderLayout());
                    blackPanel.setBackground(Color.black);
                    blackPanel.setVisible(true);
                    checkerBoard1[i][k] = (blackPanel);
                }
                checkerBoardPanel.add(checkerBoard1[i][k]);
            }
        }
        boardLayer.add(checkerBoardPanel, Integer.valueOf(1));
        int layer = 30;
        for(int y = 0; y<8; y++){
            for(int x = 0; x<8; x++){
                if(tmp[x][y] != null){
                    dx = tmp[x][y].getDrawX();
                    dy = tmp[x][y].getDrawY();
                    dfill = tmp[x][y].getDrawFill();
                    tmp[x][y].setBounds(dx,dy,dfill,dfill);
                    boardLayer.add(tmp[x][y],Integer.valueOf(layer));
                }
            }
        }
        
        for(CheckersPiece piece : pieceList){
            CheckersMouseAdapter adap = new CheckersMouseAdapter(
                        piece,cb,checkerBoardPanel);
            piece.addMouseListener(adap);
            piece.addMouseMotionListener(adap);
        }  
        
    }
}