/*
 * Ishanvi Kommula
 * PaintCards.java
 * paints each card
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;  
public class PaintCards
{
	
	BackgroundImage img = new BackgroundImage();
	int red, green, blue;
	public void paintCard1(Graphics g) //paints everything on card 1
	{
		Image title = new ImageIcon("StartScreen.png").getImage();
		g.drawImage(title, 0, 0, 1000, 850, null);
		g.setColor(Color.WHITE);
		Image selectmode = new ImageIcon("SelectModeButton.png").getImage();
		g.drawImage(selectmode, 280, 540, 400, 80, null);
		Image instruction = new ImageIcon("InstructionButton.png").getImage();
		g.drawImage(instruction, 280, 640, 400, 80, null);
	}
	public void paintCard2(Graphics g, int counter) //paints everything on card 2
	{
		Image backgroundImage = new ImageIcon("Background.png").getImage();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Dialog", Font.PLAIN, 15));
		g.drawString("Back", 60, 80);
		g.drawRect(50, 50, 50, 50);
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		g.drawString("Start", 875, 320);
		g.drawRect(850, 290, 100, 40);
		
		g.setColor(new Color(Math.max(red - 20, 0), Math.max(green - 20, 0), Math.max(blue - 20, 0)));
		g.fillRect(110, 121, 607, 607);
		g.setColor(new Color(Math.max(red - 40, 0), Math.max(green - 40, 0), Math.max(blue - 40, 0)));
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				g.drawRect(110 + 5 * (i + 1) + 81*i, 121 + 5 * (j + 1) + 81*j, 81, 81);
			}
		}
		for(int i = 0; i < 3; i++)
		{
			g.setColor(new Color(Math.max(red - 20, 0), Math.max(green - 20, 0), Math.max(blue - 20, 0)));
			g.fillRect(196 + 172*i, 35, 91, 91);
			g.setColor(new Color(Math.max(red - 40, 0), Math.max(green - 40, 0), Math.max(blue - 40, 0)));
			g.fillRect(201 + 172*i, 40, 81, 81);
		}
		for(int i = 0; i < 3; i++)
		{
			g.setColor(new Color(Math.max(red - 20, 0), Math.max(green - 20, 0), Math.max(blue - 20, 0)));
			g.fillRect(196 + 172*i, 723, 91, 91);
			g.setColor(new Color(Math.max(red - 40, 0), Math.max(green - 40, 0), Math.max(blue - 40, 0)));
			g.fillRect(201 + 172 * i, 728, 81, 81);
		}
		for(int i = 0; i < 3; i++)
		{
			g.setColor(new Color(Math.max(red - 20, 0), Math.max(green - 20, 0), Math.max(blue - 20, 0)));
			g.fillRect(24, 207 + 172*i, 91, 91);
			g.setColor(new Color(Math.max(red - 40, 0), Math.max(green - 40, 0), Math.max(blue - 40, 0)));
			g.fillRect(29, 212 + 172*i, 81, 81);
		}
		for(int i = 0; i < 3; i++)
		{
			g.setColor(new Color(Math.max(red - 20, 0), Math.max(green - 20, 0), Math.max(blue - 20, 0)));
			g.fillRect(712, 207 + 172*i, 91, 91);
			g.setColor(new Color(Math.max(red - 40, 0), Math.max(green - 40, 0), Math.max(blue - 40, 0)));
			g.fillRect(717, 212 + 172*i, 81, 81);
		}
		g.setColor(new Color(Math.max(red - 20, 0), Math.max(green - 20, 0), Math.max(blue - 20, 0)));
		g.fillRect(825, 115, 160, 160);
		g.setColor(new Color(Math.max(red - 40, 0), Math.max(green - 40, 0), Math.max(blue - 40, 0)));
		g.drawRect(830, 120, 150, 150);
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		g.drawString("Score: " + counter, 850, 370);
		
	}
	public void paintCard3(Graphics g) //paints card 3(the level select card)
	{
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		Image pixelMazePic = new ImageIcon("PixelMaze.png").getImage();
		g.drawImage(pixelMazePic, 0, 0, 1000, 850, null);
		Image easybutton = new ImageIcon("EasyButton.png").getImage();
		g.drawImage(easybutton, 260, 100, 469, 112, null);
		Image medbutton = new ImageIcon("MedButton.png").getImage();
		g.drawImage(medbutton, 260, 300, 469, 112, null);
		Image hardbutton = new ImageIcon("HardButton.png").getImage();
		g.drawImage(hardbutton, 260, 500, 469, 112, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Dialog", Font.PLAIN, 15));
		g.drawString("Back", 60, 80);
		g.drawRect(50, 50, 50, 50);
	}
	public void paintGameOver(Graphics g, int counter) //paints the game over end screen
	{
		Image gameOverImg = new ImageIcon("GameOver.png").getImage();
		g.drawImage(gameOverImg, 0, 0, 1000, 850, null);
		g.setFont(new Font("Dialog", Font.BOLD, 100));
		g.setColor(Color.WHITE);
		g.drawString("Score: " + counter, 300, 400);
		g.setFont(new Font("Dialog", Font.PLAIN, 15));
		g.drawString("Back", 60, 80);
		g.drawRect(50, 50, 50, 50);
	}
	public void getRGB(int cur1, int cur2, int cur3)
	{
		red = cur1; green = cur2; blue = cur3; 
	}
	public void paintCard4(Graphics g, int frame) //paints card 4(instruction card)
	{
		String imgname = "instruction_";
		if(frame < 10) imgname += "0";
		imgname += frame + ".gif";
		Image cur_instruct_frame = new ImageIcon(imgname).getImage();
		g.drawImage(cur_instruct_frame, 0, 0, 1000, 850, null);
		
		img.paintInstructionText(g);
		img.paintGIF2(g, 388, 498, 0);
		
		Image lightning = new ImageIcon("Lightning.png").getImage();
		g.drawImage(lightning, 638, 448, 50, 100, null);
		
		Image samplepic = new ImageIcon("sample.png").getImage();
		g.drawImage(samplepic, 800, 120, 182, 182, null);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Dialog", Font.PLAIN, 15));
		g.drawString("Back", 60, 80);
		g.drawRect(50, 50, 50, 50);
	}
}
