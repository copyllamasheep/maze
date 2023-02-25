	/*
 * Ishanvi Kommula
 * StartScreenMaze.java
 * Contains the array for the maze on the start screen
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*; 
public class StartScreenMaze
{
	int [][] blocked = new int [34][40];
	public boolean checkIfBlocked(int xBox, int yBox) //makes sure yellow ball isn't on wall
	{
		if(blocked[yBox/25][xBox/25] == 1)
		{
			return false;
		}
		return true;
	}
	public void createStartMaze()
	{
		BackgroundImage bgimg = new BackgroundImage();
		blocked = bgimg.backgroundMaze(blocked);
	}
}
