package checkers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TutorialGUI {
    public JFrame tutorial;
    
    public TutorialGUI (){
        tutorial = new JFrame("Tutorial Window");
        tutorial.setSize(900,700);
        tutorial.setVisible(true);
        tutorial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
