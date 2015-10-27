package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TutorialGUI {
    public JFrame tutorial = new JFrame("Tutorial Window");
    public JPanel board = new JPanel();
    public JTextField tips = new JTextField("Tips will go here");
    public JMenuBar menuBar = new JMenuBar();
    public JMenu menu = new JMenu();
    public JButton next = new JButton("Next");
    public JButton previous = new JButton("Previous");
    public TutorialGUI (){
        tutorial.setSize(900,700);
        tutorial.setVisible(true);
        tutorial.setLayout(new BorderLayout());
        board.setBackground(Color.GREEN);
        menuBar.add(menu);
        tutorial.add(board, BorderLayout.CENTER);
        board.setBackground(Color.GREEN);
        tutorial.add(tips, BorderLayout.WEST);
        tutorial.add(next, BorderLayout.SOUTH);
        tutorial.add(previous, BorderLayout.SOUTH);
        tutorial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
