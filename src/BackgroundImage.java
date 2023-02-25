/*
 * ishanvi Kommula
 * BackgroundImage.java
 * Paints all the images that are BufferedImages(sparkles, bomb, background, translucent stuff)
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*; 
 
import javax.imageio.ImageIO;

public class BackgroundImage {
	static double alpha = 0.3;
    public static void paint(Graphics g, int red, int green, int blue, int x, int y, int dim)  { //paints the translucent colors on top of maze
 
        int width = dim;
        int height = dim;

        if(dim == 90)
        {
			height = 290;
			red = Math.max(0, red - 70);
			green = 20;
			blue = Math.min(255, blue -= 2 + 70);
		}
 
        BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
 
        Graphics2D g2d = bufferedImage.createGraphics();
        
        if(dim == 90)
        {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.6));
		}
        else g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
        
        Color color = new Color(red, green, blue);
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
 
        g2d.dispose();
        
		g.drawImage(bufferedImage, x, y, null);

    }
    public static void paintGIF(Graphics g, int x, int y, int frameNum) //paints sparkle gif
    {
		//11x21
		int width = 21, height = 11;
        BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
        Graphics2D g2d = bufferedImage.createGraphics();
		Image sparkle = new ImageIcon("sparkle_frame" + frameNum + ".gif").getImage();
		g2d.drawImage(sparkle, 0, 0, width, height, null);
		
		
		g2d.dispose();
		
		g.drawImage(bufferedImage, x - 5, y - 30, null);
	}
	public static void paintBackground(Graphics g, int frameNum, int red, int green, int blue) //paints background gif
	{
		frameNum++;
		frameNum = 136 - frameNum;
		int width = 1000, height = 850;
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		String imgname = "frame_";
		if(frameNum / 10 < 10) imgname += "0";
		if(frameNum / 10 == 0) imgname += "0";
		imgname += frameNum + "_delay-0.03s.gif";
		Image bg = new ImageIcon(imgname).getImage();
		
		g2d.drawImage(bg, 0, 0, width, height, null);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.3));
		g2d.setColor(new Color(red, green, blue));
		g2d.fillRect(0, 0, width, height);
		
		g2d.dispose();
		
		g.drawImage(bufferedImage, 0, 0, null);
	}
    public static void paintGIF2(Graphics g, int x, int y, int frameNum) //paints bomb gif
    {
		// 14 frames. 
		int width = 200, height = 150;
		if(x == 388 && y == 498) {
			width = 1000; height = 750;
		}
        BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
        Graphics2D g2d = bufferedImage.createGraphics();
        String imgname = "bomb_";
        if(frameNum < 10) imgname += "0";
        imgname += frameNum;
        imgname += "_delay-0.1s.gif";
		Image bomb = new ImageIcon(imgname).getImage();

		g2d.drawImage(bomb, 0, 0, width, height, null);
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				int pixel = bufferedImage.getRGB(j, i);
				Color color = new Color (pixel, true);
				int temp_red = color.getRed();
				int temp_green = color.getGreen();
				int temp_blue = color.getBlue();
				
				if(!(temp_red > 190 && temp_blue > 190 && temp_green > 190)) {
					g.setColor(color);
					g.drawRect(x - width / 2 + j + 10, y - height / 2 + i + 10, 1, 1);
				}
			}
		}
		g2d.dispose();
	}
	public static void paintBlood(Graphics g, int x, int y) //paints the blood
	{
		int width = 40; int height = 33;
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		Image blood = new ImageIcon("Blood.png").getImage();
		
		g2d.drawImage(blood, 0, 0, width, height, null);
		
		g.drawImage(bufferedImage, x - 10, y - 10, null);
	}
	public static void paintLightning(Graphics g, int x, int y) //paints the lightning power up
	{
		int width = 10;
		int height = 24;
		
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		Image lightning = new ImageIcon("Lightning.png").getImage();
		
		g2d.drawImage(lightning, 0, 0, width, height, null);
		g.drawImage(bufferedImage, x, y, null);
	}
	public static void paintTintCharacter(int dir, int char1[][], Graphics g, int xCoor, int yCoor) //tints the character during the lightning power up
	{
		int width = 12;
		int height = 18;
		
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.3)); 
		
		g2d.setColor(Color.YELLOW);
		for(int i = 0; i < 18; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				if(char1[i][j] == 0) continue;
				if(dir == 3)
					g2d.fillRect(12 - j, i, 1, 1);
				else 
					g2d.fillRect(j, i, 1, 1);
			}
		}
		g.drawImage(bufferedImage, xCoor, yCoor, null);
	}
	public static void paintCuckoo(Graphics g, int cuckooIncrement, boolean cuckooBird) //paints the cuckoo clock images
	{
		int width = 64;
		int height = 190;
		if(cuckooBird)
		{
			width = 103;
		}
		
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		Image cuckoo1 = new ImageIcon("cuckoo1.gif").getImage();
		Image cuckoo2 = new ImageIcon("cuckoo2.gif").getImage();
		Image cuckoo3 = new ImageIcon("cuckoo3.gif").getImage();
		Image cuckoo4 = new ImageIcon("cuckoo4.gif").getImage();
		if(cuckooBird && cuckooIncrement % 2 == 0) {
			g2d.drawImage(cuckoo2, 0, 0, width, height, null);
		}
		else if (cuckooBird && cuckooIncrement % 2 == 1) {
			g2d.drawImage(cuckoo1, 0, 0, 64, height, null);
		}
		else if(cuckooIncrement % 2 == 1) {
			g2d.drawImage(cuckoo1, 0, 0, width, height, null);
		}
		else {
			g2d.drawImage(cuckoo4, 0, 0, width, 65, null);
			g2d.drawImage(cuckoo3, 0, 63, width, 127, null);
		}
		
		g.drawImage(bufferedImage, 870, 420, null);
	}
	public static void paintInstructionText(Graphics g) //paints the image with the instruction text as buffered image
	{
		int width = 1000, height = 850;
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		
		Image text = new ImageIcon("InstructionText.png").getImage();
		g2d.drawImage(text, 0, 0, width, height, null);
		
		g.drawImage(bufferedImage, 0, 0, width, height, null);
	}
	public static void paintGriever(Graphics g, int x, int y, int dir)
	{
		int width = 20, height = 20;
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		
		Image griever = new ImageIcon("Griever.png").getImage();
		g2d.drawImage(griever, 0, 0, width, height, null);
		
		for(int i = 0; i < 20; i ++)
		{
			for(int j = 0; j < 20; j ++)
			{
				int pixel; 
				
				if(dir == 2) pixel = bufferedImage.getRGB(j, 19 - i);
				else if(dir == 3) pixel = bufferedImage.getRGB(i, j);
				else if(dir == 4) pixel = bufferedImage.getRGB(19 - i, 19 - j);
				else pixel = bufferedImage.getRGB(j, i);
				
				Color color = new Color (pixel, true);
				int temp_red = color.getRed();
				int temp_green = color.getGreen();
				int temp_blue = color.getBlue();

				g.setColor(color);
				g.drawRect(x + j, y + i, 1, 1); 
			}
		}
	}
	public static int[][] backgroundMaze(int blocked[][])
	{
		int width = 1000, height = 850;
		BufferedImage bufferedImage = new BufferedImage(width, height, java.awt.Transparency.TRANSLUCENT);
		Graphics2D g2d = bufferedImage.createGraphics();
		
		Image backgroundMaze = new ImageIcon("StartScreen.png").getImage();
		g2d.drawImage(backgroundMaze, 0, 0, width, height, null);
		
		for(int i = 0; i < 34; i ++)
		{
			for(int j = 0; j < 40; j ++)
			{
				int pixel = bufferedImage.getRGB(j * 25 + 5, i * 25 + 5);
				
				Color color = new Color (pixel, true);
				int temp_red = color.getRed();
				int temp_green = color.getGreen();
				int temp_blue = color.getBlue();
				
				if(temp_red > 245 && temp_green > 245 && temp_blue > 245)
				{
					blocked[i][j] = 1;
				}
			}
		}
		return blocked;
	}
}
