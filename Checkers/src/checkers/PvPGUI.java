package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PvPGUI {
    public JFrame pvpGUI;
    public JPanel board = new JPanel();
    public JLabel pic = new JLabel();
    public PvPGUI(){
        pvpGUI = new JFrame("Player Vs. Player");
        pvpGUI.setSize(900,700);
        pvpGUI.setVisible(true);
        //sets image to Jlabel
        ImageIcon icon = new ImageIcon(new ImageIcon("Images/checkers_board.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));
        pic.setIcon(icon);
        board.add(pic);
        pvpGUI.add(board);
        pvpGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
