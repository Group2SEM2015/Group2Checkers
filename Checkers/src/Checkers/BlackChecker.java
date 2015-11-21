package Checkers;

import java.awt.*;
import javax.swing.*;

public class BlackChecker extends JPanel
{
   public BlackChecker()
	{
		this.setOpaque(false); //make the background translucent so that this jpanel does not hide the board
	}
    public void paint (Graphics g)
    {
    	super.paint(g); //must call the parent's paint method before drawing the piece
        g.setColor(Color.white);
        g.fillOval(20,10,60,60);
    }   
}
