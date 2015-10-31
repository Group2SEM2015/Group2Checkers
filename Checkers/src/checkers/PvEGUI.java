package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PvEGUI {
    public JFrame pveGUI;
    public JPanel board = new JPanel();
    public JLabel pic = new JLabel();
    public PvEGUI(){
        pveGUI = new JFrame("Player Vs. Computer");
        pveGUI.setSize(900,700);
        pveGUI.setVisible(true);
        //sets image to JLabel
        ImageIcon icon = new ImageIcon(new ImageIcon("Images/checkers_board.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));
        pic.setIcon(icon);
        board.add(pic);
        pveGUI.add(board);
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
}