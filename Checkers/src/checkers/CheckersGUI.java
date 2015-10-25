package checkers;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CheckersGUI {
    public JFrame start;
    public JButton tutorialButton;
    public JButton pvpButton;
    public JButton pveButton;
    
    public CheckersGUI(){
        start = new JFrame("Start Window");
        tutorialButton = new JButton("Tutorial");
        pvpButton = new JButton("Player Vs. Player");
        pveButton = new JButton("Player Vs. Computer");
        start.setSize(300,400);
        start.setLayout(new GridLayout(3,1));
        start.setVisible(true);
        
        start.add(tutorialButton);
        start.add(pvpButton);
        start.add(pveButton);
        
        start.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        tutorialButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                start.dispose();
                TutorialGUI tutorialGUI = new TutorialGUI();
            }
            
        });
        
        pvpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                start.dispose();
                PvPGUI pvpGUI = new PvPGUI();
            }
            
        });
        
        pveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                start.dispose();
                PvEGUI pveGUI = new PvEGUI();
            }
            
        });
    }
}
