package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PvEGUI {
        public JFrame pveGUI;
        
        public PvEGUI(){
            pveGUI = new JFrame("Player Vs. Computer");
            pveGUI.setSize(900,700);
            pveGUI.setVisible(true);
            pveGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}