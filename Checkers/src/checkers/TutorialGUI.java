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
    public JPanel[][] checkerBoard1;
    public JPanel checkerBoardPanel = new JPanel();
    public JPanel redPanel;
    public JPanel blackPanel;
    public JPanel buttonPanel = new JPanel();
    public JTextField tips = new JTextField("Tips will go here");
    public JMenuBar menuBar = new JMenuBar();
    public JMenu menu = new JMenu("Tutorial Steps");
    public JMenuItem steps = new JMenuItem("List of Tutorial steps to come");
    public JButton next = new JButton("Next");
    public JButton previous = new JButton("Previous");
    
    public TutorialGUI()
    {
        tutorial.setSize(900, 700);
        tutorial.setVisible(true);
        tutorial.setLayout(new BorderLayout());

        menuBar.add(menu);
        menu.add(steps);
        createCheckerBoard();

        tutorial.add(checkerBoardPanel, BorderLayout.CENTER);
        tutorial.add(tips, BorderLayout.WEST);
        buttonPanel.setLayout(new GridLayout(1, 2));
        tutorial.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(previous);
        buttonPanel.add(next);
        tutorial.add(menuBar, BorderLayout.NORTH);

        tutorial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tutorial.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                CheckersGUI gui1 = new CheckersGUI();
                tutorial.dispose();
            }
        });
    }

    public void createCheckerBoard()
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
