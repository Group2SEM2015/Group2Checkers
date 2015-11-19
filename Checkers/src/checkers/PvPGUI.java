package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PvPGUI {
    public JFrame pvpGUI;
    //public JPanel board = new JPanel();
    public JPanel[][] checkerBoard1;
    public JPanel checkerBoardPanel = new JPanel();
    public JPanel redPanel;
    public JPanel blackPanel;
    //public JLabel pic = new JLabel();
    public PvPGUI(){
        pvpGUI = new JFrame("Player Vs. Player");
        pvpGUI.setSize(900,700);
        pvpGUI.setVisible(true);
        pvpGUI.setLayout(new BorderLayout());
        createCheckerBoard();
        //sets image to Jlabel
        //ImageIcon icon = new ImageIcon(new ImageIcon("Images/checkers_board.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));
       // pic.setIcon(icon);
        //board.add(pic);
        pvpGUI.add(checkerBoardPanel, BorderLayout.CENTER);
        //pvpGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pvpGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pvpGUI.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                CheckersGUI gui1 = new CheckersGUI();
                pvpGUI.dispose();
            }
        });
    }
     public final void createCheckerBoard()
    {
        checkerBoard1 = new JPanel[8][8];
        checkerBoardPanel.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++)
        {
            for (int k = 0; k < 8; k++)
            {
                if ((k + i) % 2 == 0)
                {
                    redPanel = new JPanel();
                    redPanel.setBackground(Color.red);
                    redPanel.setVisible(true);
                    checkerBoard1[i][k] = (redPanel);
                }
                else
                {
                    blackPanel = new JPanel();
                    blackPanel.setBackground(Color.black);
                    blackPanel.setVisible(true);
                    checkerBoard1[i][k] = (blackPanel);
                }
                checkerBoardPanel.add(checkerBoard1[i][k]);
            }
        }
    }
}

