package Checkers;

import java.awt.*;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.event.*;
import javax.swing.*;

public class TutorialGUI
{

    public JFrame tutorial = new JFrame("Tutorial Window");
    int i = 1;
    public JPanel[][] checkerBoard1;
    public JPanel checkerBoardPanel = new JPanel();
    public JPanel redPanel;
    public JPanel blackPanel;
    public JPanel buttonPanel = new JPanel();
    public JTextArea tips = new JTextArea();
    public JMenuBar menuBar = new JMenuBar();
    public JMenu menu = new JMenu("Tutorial Steps");
    public JMenuItem step1 = new JMenuItem("Move");
    public JMenuItem step2 = new JMenuItem("Jump");
    public JMenuItem step3 = new JMenuItem("Making a King");
    public JMenuItem step4 = new JMenuItem("Winning");
    //public JMenuItem steps = new JMenuItem("List of Tutorial steps to come");
    public JButton next = new JButton("Next");
    public JButton previous = new JButton("Previous");
    public JLayeredPane boardLayer = new JLayeredPane();

    public TutorialGUI()
    {
        tutorial.setSize(900, 700);
        tutorial.setVisible(true);
        tutorial.setLayout(new BorderLayout());

        menuBar.add(menu);
        menu.add(step1);
        menu.add(step2);
        menu.add(step3);
        menu.add(step4);
        createCheckerBoard(checkerBoardPanel);
//        addChecker();

        //tutorial.add(checkerBoardPanel, BorderLayout.CENTER);
        tutorial.add(boardLayer, BorderLayout.CENTER);
        tutorial.add(tips, BorderLayout.WEST);
        buttonPanel.setLayout(new GridLayout(1, 2));
        tutorial.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(previous);
        buttonPanel.add(next);
        tutorial.add(menuBar, BorderLayout.NORTH);
        
        System.out.println(checkerBoardPanel.getSize());

        tips.setEditable(false);
        tips.setText("Hello and Welcome to the wonderfull\n"
                + "world ofcheckers. In this tutorial we \n"
                + "will help youunderstand the basic \n"
                + "mechanics of this timless game. \n"
                + "Let's get started shall we!\n"
                + "Please Clicked the next button down below\n"
                + "or select the step you wish to learn in\n"
                + "the menu above");

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

        //switch the boardpanel and text box to the move step
        step1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tips.setText("In this step you will learn the\n"
                        + "basic move function this allows you\n "
                        + "move your checker piece(Red) to a new\n"
                        + "square.\n"
                        + "To do this please click on the the\n"
                        + "highlighted checker piece then\n"
                        + "please drage this piece to the highlighter\n "
                        + "square. This is the move function.\n"
                        + "The goal to moving pieces is to set\n"
                        + "them up in postions to allow you to\n "
                        + "jump the other players pieces");

            }
        });

        //switch the boardpanel and text box to the jump step
        step2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tips.setText("In this step you will learn the\n"
                        + "jump function. The jump allows the\n"
                        + "player to take his/hers oppenets\n"
                        + "piece. To demostrate this we have set\n"
                        + "up the board that allows you jump your\n"
                        + "oppenet. Please select the highlighted\n"
                        + "checker piece. And move the checker\n"
                        + "piece to the highighted square.\n"
                        + "As you can see with the jump you\n"
                        + "are able to move over the oppenets\n"
                        + "piece and take it. You can only do this\n"
                        + "if the space behind the oppents piece\n"
                        + "However do becareful, you do not want\n"
                        + "to setup your peice to get jump after.");
            }
        });
        //switch the boardpanel and text box to the king step
        step3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tips.setText("In this step you will learn what\n"
                        + "a king is and what you can do with\n"
                        + "one. The king is a checker peice that\n"
                        + "has reached the oppisite side of the\n"
                        + "board. When this happens another piece\n"
                        + "is placed on top of the one that reached\n"
                        + "the end. This allows the checker piece to\n"
                        + "move in any forward or back.\n"
                        + "This piece can dominate the field.\n"
                        + "Please select the higlighted piece and\n"
                        + "drag it to the highlighted box. As you\n"
                        + "can see the piece has changed.\n"
                        + "Now move the peice to the previos spot\n"
                        + "you where just at.\n"
                        + "This is called multidirectional movement.\n"
                        + "This will allow you to set up new jump\n"
                        + "options and a faster way to bring down your\n"
                        + "oppenet.");

            }
        });
        step4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tips.setText("In this step we will cover winning.\n"
                        + "To win at checkers you have to take all\n"
                        + "of your oppenets pieces. We have set up\n"
                        + "the board so all that is left is one piece\n"
                        + "on your oppenets side.\n"
                        + "Select the highlighted peice and jump the\n"
                        + "the final oppenets piece.\n"
                        + "This is the game of Checkers a critical\n"
                        + "thinking game that makes you think steps\n"
                        + "in advance to assure your WIN!");
            }
        });
        next.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        
        checkerBoardPanel.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                System.out.println(x);
                System.out.println(y);
                
            }
        });
        checkerBoardPanel.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
               // System.out.println(x);
                //System.out.println(y);
            }
        });
    }
    
    public void createCheckerBoard(JPanel checkerBoardPanel)
    {
        int dx,dy, dfill;
        checkerBoard1 = new JPanel[8][8];
        checkerBoardPanel.setLayout(new GridLayout(8, 8));
        checkerBoardPanel.setSize(650, 612);
        CheckersBoard cb = new CheckersBoard(checkerBoardPanel);
        final CheckersPiece[] pieceList = cb.getPieceList();
        CheckersPiece[][] tmp = cb.getBoard();
        
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
        
        for(int i = 0; i < 24; i++){ //8 = BOARDEDGE
            pieceList[i].addMouseListener(new CheckersMouseAdapter(pieceList,
                    cb,i, checkerBoardPanel));
        }
        
    }

    public void addChecker()
    {
        /*
         CheckerPieces checker = new CheckerPieces();
         checker.white11X = X11;
         checker.white11Y = Y11;
         JPanel tmp= new JPanel();
         tmp.add(checker);

         //checkerBoard1[0][0].setLayout(new BorderLayout());
         //checkerBoard1[0][0].add(tips, BorderLayout.CENTER);
         //checkerBoard1[0][0].setBackground(Color.yellow);
         //checkerBoard1[0][0].add("what the fuck", BorderLayout.CENTER);
         checkerBoard1[0][0].add(tmp, BorderLayout.CENTER);*/

        //checkerBoard1[0][1].setLayout(new BorderLayout());
        //checkerBoard1[0][1].add(new BlackChecker(), BorderLayout.CENTER);
        //add white checkers
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((j + i) % 2 != 0)
                {
                    checkerBoard1[i][j].setLayout(new BorderLayout());
                    checkerBoard1[i][j].add(new BlackChecker(), BorderLayout.CENTER);
                }
            }
        }

        //add yellow checkers
        for (int i = 5; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((j + i) % 2 != 0)
                {
                    checkerBoard1[i][j].setLayout(new BorderLayout());
                    checkerBoard1[i][j].add(new RedChecker(), BorderLayout.CENTER);
                }
            }
        }
    
    }
}
