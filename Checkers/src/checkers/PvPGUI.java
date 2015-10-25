package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PvPGUI {
    public JFrame pvpGUI;
    
    public PvPGUI(){
        pvpGUI = new JFrame("Player Vs. Player");
        pvpGUI.setSize(900,700);
        pvpGUI.setVisible(true);
        pvpGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
