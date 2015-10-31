package checkers;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TutorialGUI {
    public JFrame tutorial = new JFrame("Tutorial Window");
    public JPanel board = new JPanel();
    public JLabel pic = new JLabel();
    public JPanel buttonPanel = new JPanel();
    public JTextField tips = new JTextField("Tips will go here");
    public JMenuBar menuBar = new JMenuBar();
    public JMenu menu = new JMenu("Tutorial Steps");
    public JMenuItem steps = new JMenuItem("List of Tutorial steps to come");
    public JButton next = new JButton("Next");
    public JButton previous = new JButton("Previous");
    public TutorialGUI (){
        //sets image to JLabel
       ImageIcon icon = new ImageIcon(new ImageIcon("Images/checkers_board.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));
       pic.setIcon(icon);
        
        tutorial.setSize(900,700);
        tutorial.setVisible(true);
        tutorial.setLayout(new BorderLayout());
        menuBar.add(menu);
        menu.add(steps);
        board.add(pic);
        tutorial.add(board, BorderLayout.CENTER);
        tutorial.add(tips, BorderLayout.WEST);
        buttonPanel.setLayout(new GridLayout(1,2));
        tutorial.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(previous);
        buttonPanel.add(next);
        tutorial.add(menuBar, BorderLayout.NORTH);
        
        
        tutorial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

}
