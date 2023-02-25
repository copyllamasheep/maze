/*
 * Ishanvi Kommula
 * CreateMaze.java
 * Contains the array for the main maze, paints each piece, rotates pieces, changes array after pieces are moved
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;  
public class CreateMaze
{
	int [][][] coor = new int [7][7][2];
	int xloc = 830, yloc = 120;
	int [][] id = new int [7][7];
	int last, cur_orientation;
	int [] pos = new int [3];
	int [][] orientation = new int [7][7];
	int cur_box = 0;
	int [][] maze = new int [21][21];
	int [][] piece1 = {{0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
	int [][] piece2 = {{0, 1, 0}, {0, 1, 1}, {0, 0, 0}};
	int [][] piece3 = {{0, 1, 0}, {0, 1, 1}, {0, 1, 0}};
	int [][] piece4 = {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
	int [][] last_array = new int [3][3];
	int red, green, blue;
	int row, col;
	int speed;
	Image piece1_1 = new ImageIcon("p1.1.png").getImage();
	Image piece1_2 = new ImageIcon("p1.2.png").getImage();
	Image piece2_1 = new ImageIcon("p2.1.png").getImage();
	Image piece2_2 = new ImageIcon("p2.2.png").getImage();
	Image piece2_3 = new ImageIcon("p2.3.png").getImage();
	Image piece2_4 = new ImageIcon("p2.4.png").getImage();
	Image piece3_1 = new ImageIcon("p3.1.png").getImage();
	Image piece3_2 = new ImageIcon("p3.2.png").getImage();
	Image piece3_3 = new ImageIcon("p3.3.png").getImage();
	Image piece3_4 = new ImageIcon("p3.4.png").getImage();
	Image piece4_0   = new ImageIcon("p4.png").getImage();
	BackgroundImage img = new BackgroundImage();
	public void assignCoordinates() //puts the coordinates of each piece into an array
	{
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				coor[i][j][0] = 110 + 5 * (j + 1) + 81*j;
				coor[i][j][1] = 121 + 5 * (i + 1) + 81*i;
			}
		}
	}
	public void makeMaze() //randomly chooses a piece to put in a spot [i][j] and randomly orients it, the number of each piece is evenly distributed
	{
		int pick;
		pos[0] = 0; pos[1] = 0; pos[2] = 0;
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				boolean done = false;
				do {
					pick = (int)(Math.random()*3);	//0 = straight, 1 = corner, 2 = 3way
					if(pick == 0 && pos[pick] < 17){ 
						pos[pick]++; done = true; 
					}
					if(pick == 1 && pos[pick] < 16){ 
						pos[pick]++; done = true; 
					}
					if(pick == 2 && pos[pick] < 16){ 
						pos[pick]++; done = true; 
					}
				} while(!done);
				id[i][j] = pick;
			}
		}
		id[6][6] = 1;
		last = (int)(Math.random()*3);
		if(last == 0)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					last_array[i][j] = piece1[i][j];
				}
			}
		}
		if(last == 1)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					last_array[i][j] = piece2[i][j];
				}
			}
		}
		if(last == 2)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					last_array[i][j] = piece3[i][j];
				}
			}
		}
		if(last == 3)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					last_array[i][j] = piece4[i][j];
				}
			}
		}
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				orientation[i][j] = (int)(Math.random()*4);
			}
		}
		orientation[6][6] = 1;
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				if(id[i][j] == 0)
				{
					if(orientation[i][j] == 0 || orientation[i][j] == 2)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece1[k - i * 3][l - j * 3];
							}
						}
					}
					if(orientation[i][j] == 1 || orientation[i][j] == 3)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece1[l - j * 3][k - i * 3];
							}
						}
					}
				}
				if(id[i][j] == 1)
				{
					if(orientation[i][j] == 0)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece2[k - i * 3][l - j * 3];
							}
						}
					}
					if(orientation[i][j] == 1)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece2[k - i * 3][(2 - (l - j * 3))];
							}
						}
					}
					if(orientation[i][j] == 2)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece2[2 - (k - i * 3)][(2 - (l - j * 3))];
							}
						}
					}
					if(orientation[i][j] == 3)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece2[2 - (k - i * 3)][l - j * 3];
							}
						}
					}
				}
				if(id[i][j] == 2)
				{
					if(orientation[i][j] == 0)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece3[k - i * 3][l - j * 3];
							}
						}
					}
					if(orientation[i][j] == 1)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece3[2 - (l - j * 3)][2 - (k - i * 3)];
							}
						}
					}
					if(orientation[i][j] == 2)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece3[k - i * 3][2 - (l - j * 3)];
							}
						}
					}
					if(orientation[i][j] == 3)
					{
						for(int k = i * 3; k < i * 3 + 3; k++)
						{
							for(int l = j * 3; l < j * 3 + 3; l++)
							{
								maze[k][l] = piece3[l - j * 3][k - i * 3];
							}
						}
					}
				}
				if(id[i][j] == 3)
				{
					for(int k = i * 3; k < i * 3 + 3; k++)
					{
						for(int l = j * 3; l < j * 3 + 3; l++)
						{
							maze[k][l] = piece4[k - i * 3][l - j * 3];
						}
					}
				}
			}
		}
	}
	public void drawMaze(Graphics g, boolean first, int xAdd, int yAdd) //draws each piece on maze
	{
		row = 100; col = 100;
		if(cur_box == 12) { }
		else if(cur_box == 0 || cur_box == 3) { col = 1; }
		else if(cur_box == 1 || cur_box == 4) { col = 3; }
		else if(cur_box == 2 || cur_box == 5) { col = 5; }
		
		else if(cur_box == 6 || cur_box == 9) { row = 1; }
		else if(cur_box == 7 || cur_box == 10) { row = 3; }
		else if(cur_box == 8 || cur_box == 11) { row = 5; }
		
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				int cur_xAdd = 0, cur_yAdd = 0;
				if(row == i) {cur_xAdd = xAdd; cur_yAdd = yAdd; }
				else if(col == j) {cur_xAdd = xAdd; cur_yAdd = yAdd; }		
				if(speed == 0) {cur_xAdd = 0; cur_yAdd = 0; /*System.out.println("asd");*/}	
				if(id[i][j] == 0){
					if(orientation[i][j] == 0 || orientation[i][j] == 2) {
						g.drawImage(piece1_1, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					}
					if(orientation[i][j] == 1 || orientation[i][j] == 3) {
						g.drawImage(piece1_2, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					}
				}
				if(id[i][j] == 1){
					if(orientation[i][j] == 0) g.drawImage(piece2_1, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					if(orientation[i][j] == 1) g.drawImage(piece2_2, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					if(orientation[i][j] == 2) g.drawImage(piece2_3, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					if(orientation[i][j] == 3) g.drawImage(piece2_4, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
				}
				if(id[i][j] == 2){
					if(orientation[i][j] == 0) g.drawImage(piece3_1, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					if(orientation[i][j] == 1) g.drawImage(piece3_2, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					if(orientation[i][j] == 2) g.drawImage(piece3_3, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
					if(orientation[i][j] == 3) g.drawImage(piece3_4, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);					
				}
				if(id[i][j] == 3) {
					g.drawImage(piece4_0, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81, 81, null);
				}
				img.paint(g, red, green, blue, coor[i][j][0] + cur_xAdd, coor[i][j][1] + cur_yAdd, 81);
			}
		}
		g.setColor(Color.GRAY);
		g.fillRect(825, 115, 160, 160);
		if(first)
		{
			if(last == 0) {
				if(cur_orientation == 0 || cur_orientation == 2) g.drawImage(piece1_1, xloc, yloc, 150, 150, null);
				if(cur_orientation == 1 || cur_orientation == 3) g.drawImage(piece1_2, xloc, yloc, 150, 150, null);
			}
			if(last == 1) {
				if(cur_orientation == 0) g.drawImage(piece2_1, xloc, yloc, 150, 150, null);
				if(cur_orientation == 1) g.drawImage(piece2_2, xloc, yloc, 150, 150, null);
				if(cur_orientation == 2) g.drawImage(piece2_3, xloc, yloc, 150, 150, null);
				if(cur_orientation == 3) g.drawImage(piece2_4, xloc, yloc, 150, 150, null);
			}
			if(last == 2) {
				if(cur_orientation == 0) g.drawImage(piece3_1, xloc, yloc, 150, 150, null);
				if(cur_orientation == 1) g.drawImage(piece3_2, xloc, yloc, 150, 150, null);
				if(cur_orientation == 2) g.drawImage(piece3_3, xloc, yloc, 150, 150, null);
				if(cur_orientation == 3) g.drawImage(piece3_4, xloc, yloc, 150, 150, null);
			}
			if(last == 3) {
				g.drawImage(piece4_0, xloc, yloc, 150, 150, null);
			}
			img.paint(g, red, green, blue, xloc, yloc, 150);

		}
	}
	public void rotatePiece(Graphics g) //when piece is clicked, rotates it 90 degrees
	{
		g.setColor(Color.GRAY);
		g.fillRect(825, 115, 160, 160);
		cur_orientation++;
		cur_orientation %= 4;
		int [][] temp_copy = new int [3][3];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				temp_copy[i][j] = last_array[i][j];
			}
		}
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				last_array[2 - j][i] = temp_copy[i][j];
			}
		}
		if(last == 0) {
			if(cur_orientation == 0 || cur_orientation == 2) g.drawImage(piece1_1, xloc, yloc, 150, 150, null);
			if(cur_orientation == 1 || cur_orientation == 3) g.drawImage(piece1_2, xloc, yloc, 150, 150, null);
		}
		if(last == 1) {
			if(cur_orientation == 0) g.drawImage(piece2_1, xloc, yloc, 150, 150, null);
			if(cur_orientation == 1) g.drawImage(piece2_2, xloc, yloc, 150, 150, null);
			if(cur_orientation == 2) g.drawImage(piece2_3, xloc, yloc, 150, 150, null);
			if(cur_orientation == 3) g.drawImage(piece2_4, xloc, yloc, 150, 150, null);
		}
		if(last == 2) {
			if(cur_orientation == 0) g.drawImage(piece3_1, xloc, yloc, 150, 150, null);
			if(cur_orientation == 1) g.drawImage(piece3_2, xloc, yloc, 150, 150, null);
			if(cur_orientation == 2) g.drawImage(piece3_3, xloc, yloc, 150, 150, null);
			if(cur_orientation == 3) g.drawImage(piece3_4, xloc, yloc, 150, 150, null);
		}
		if(last == 3) {
			g.drawImage(piece4_0, xloc, yloc, 150, 150, null);
		}
		img.paint(g, red, green, blue, xloc, yloc, 150);
	}
	public void repaintLast(Graphics g, int xMouse, int yMouse) //repaints the piece after being dragged
	{
		g.setColor(Color.GRAY);
		g.fillRect(825, 115, 160, 160);
		if(last == 0) {
			if(cur_orientation == 0 || cur_orientation == 2) g.drawImage(piece1_1, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 1 || cur_orientation == 3) g.drawImage(piece1_2, xMouse, yMouse, 150, 150, null);
		}
		if(last == 1) {
			if(cur_orientation == 0) g.drawImage(piece2_1, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 1) g.drawImage(piece2_2, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 2) g.drawImage(piece2_3, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 3) g.drawImage(piece2_4, xMouse, yMouse, 150, 150, null);
		}
		if(last == 2) {
			if(cur_orientation == 0) g.drawImage(piece3_1, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 1) g.drawImage(piece3_2, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 2) g.drawImage(piece3_3, xMouse, yMouse, 150, 150, null);
			if(cur_orientation == 3) g.drawImage(piece3_4, xMouse, yMouse, 150, 150, null);
		}
		if(last == 3) {
			g.drawImage(piece4_0, xMouse, yMouse, 150, 150, null);
		}
		img.paint(g, red, green, blue, xMouse, yMouse, 150);
	}
	public void repaintLastLast(Graphics g, int xMouse, int yMouse, int dim, boolean push) // repaints the piece if it changes dimensions and/or is pushed
	{
		g.setColor(Color.GRAY);
		g.fillRect(825, 115, 160, 160);
		if(push) dim = 81;
		if(last == 0) {
			if(cur_orientation == 0 || cur_orientation == 2) g.drawImage(piece1_1, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 1 || cur_orientation == 3) g.drawImage(piece1_2, xMouse, yMouse, dim, dim, null);
		}
		if(last == 1) {
			if(cur_orientation == 0) g.drawImage(piece2_1, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 1) g.drawImage(piece2_2, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 2) g.drawImage(piece2_3, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 3) g.drawImage(piece2_4, xMouse, yMouse, dim, dim, null);
		}
		if(last == 2) {
			if(cur_orientation == 0) g.drawImage(piece3_1, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 1) g.drawImage(piece3_2, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 2) g.drawImage(piece3_3, xMouse, yMouse, dim, dim, null);
			if(cur_orientation == 3) g.drawImage(piece3_4, xMouse, yMouse, dim, dim, null);
		}
		if(last == 3) {
			g.drawImage(piece4_0, xMouse, yMouse, dim, dim, null);
		}
		img.paint(g, red, green, blue, xMouse, yMouse, dim);
	}
	public void getCurBox(int cur){ //gets value of cur_box in the file GameProject.java
		cur_box = cur;
	}
	public void getSpeed(int cur){ //gets value of donemoving in the file GameProject.java
		speed = cur;
	}
	public void changeMaze(){
		if(cur_box >= 6 && cur_box <= 8)
		{
			int [][] prev_last_array = new int[3][3];
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					prev_last_array[i][j] = last_array[i][j];
				}
			}
			for(int i = row * 3; i < row * 3 + 3; i++)
			{
				for(int j = 18; j < 21; j++)
				{
					last_array[i - row*3][j - 18] = maze[i][j];
				}
			}
			for(int j = row * 3; j < row * 3 + 3; j++)
			{
				for(int i = 20; i >= 3; i--) {
					maze[j][i] = maze[j][i - 3];
				}
			}
			for(int i = row * 3; i < row * 3 + 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					maze[i][j] = prev_last_array[i - row * 3][j];
				}
			}
			int prev_last = last, prev_cur_orientation = cur_orientation;
			last = id[row][6];
			cur_orientation = orientation[row][6];
			for(int i = 6; i >= 1; i --)
			{
				orientation[row][i] = orientation[row][i - 1];
				id[row][i] = id[row][i - 1];
			}
			id[row][0] = prev_last;
			orientation[row][0] = prev_cur_orientation;
			cur_box += 3;
		}
		else if(cur_box >= 9 && cur_box <= 11)
		{
			int [][] prev_last_array = new int[3][3];
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					prev_last_array[i][j] = last_array[i][j];
				}
			}
			for(int i = row * 3; i < row * 3 + 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					last_array[i - row*3][j] = maze[i][j];
				}
			}
			for(int j = row * 3; j < row * 3 + 3; j++)
			{
				for(int i = 0; i < 18; i++) {
					maze[j][i] = maze[j][i + 3];
				}
			}
			for(int i = row * 3; i < row * 3 + 3; i++)
			{
				for(int j = 20; j >= 18; j--)
				{
					maze[i][j] = prev_last_array[i - row * 3][j - 18];
				}
			}
			int prev_last = last, prev_cur_orientation = cur_orientation;
			last = id[row][0];
			cur_orientation = orientation[row][0];
			for(int i = 0; i < 6; i ++)
			{
				orientation[row][i] = orientation[row][i + 1];
				id[row][i] = id[row][i + 1];
			}
			id[row][6] = prev_last;
			orientation[row][6] = prev_cur_orientation;
			cur_box -= 3;
		}
		else if(cur_box >= 0 && cur_box <= 2)
		{
			int [][] prev_last_array = new int[3][3];
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					prev_last_array[i][j] = last_array[i][j];
				}
			}
			for(int i = 18; i < 21; i++)
			{
				for(int j = col * 3; j < col * 3 + 3; j++)
				{
					last_array[i - 18][j - col * 3] = maze[i][j];
				}
			}
			for(int i = 20; i >= 3; i--)
			{
				for(int j = col * 3; j < col * 3 + 3; j++) {
					maze[i][j] = maze[i - 3][j];
				}
			}
			for(int i = 0; i < 3; i++)
			{
				for(int j = col * 3; j < col * 3 + 3; j++)
				{
					maze[i][j] = prev_last_array[i][j - col * 3];
				}
			}
			int prev_last = last, prev_cur_orientation = cur_orientation;
			last = id[6][col];
			cur_orientation = orientation[6][col];
			for(int i = 6; i >= 1; i --)
			{
				orientation[i][col] = orientation[i - 1][col];
				id[i][col] = id[i - 1][col];
			}
			id[0][col] = prev_last;
			orientation[0][col] = prev_cur_orientation;
			cur_box += 3;
		}
		else if(cur_box >= 3 && cur_box <= 5)
		{
			int [][] prev_last_array = new int[3][3];
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					prev_last_array[i][j] = last_array[i][j];
				}
			}
			for(int i = 0; i < 3; i++)
			{
				for(int j = col * 3; j < col * 3 + 3; j++)
				{
					last_array[i][j - col * 3] = maze[i][j];
				}
			}
			for(int i = 0; i < 18; i++)
			{
				for(int j = col * 3; j < col * 3 + 3; j++) {
					maze[i][j] = maze[i + 3][j];
				}
			}
			for(int i = 20; i >= 18; i--)
			{
				for(int j = col * 3; j < col * 3 + 3; j++)
				{
					maze[i][j] = prev_last_array[i - 18][j - col * 3];
				}
			}
			int prev_last = last, prev_cur_orientation = cur_orientation;
			last = id[0][col];
			cur_orientation = orientation[0][col];
			for(int i = 0; i < 6; i ++)
			{
				orientation[i][col] = orientation[i + 1][col];
				id[i][col] = id[i + 1][col];
			}
			id[6][col] = prev_last;
			orientation[6][col] = prev_cur_orientation;
			cur_box -= 3;
		}
		row = 100; col = 100;
	}
	public boolean checkIfBlocked2(int xChar, int yChar) //makes sure the character isn't going on blocked areas
	{
		if(xChar < 0 || xChar > 20) return true;
		if(yChar < 0 || yChar > 20) return true;
		if(maze[yChar][xChar] == 1)
		{
			return false;
		}
		return true;
	}
	public void getRGB(int cur1, int cur2, int cur3) //gets the red, green, and blue values currently
	{
		red = cur1; green = cur2; blue = cur3;
	}
	public void explosion(int xBombLoc, int yBombLoc) //changes the maze when a wall is exploded
	{
		id[yBombLoc][xBombLoc] = 3;
		orientation[yBombLoc][xBombLoc] = 0;
		for(int i = yBombLoc * 3; i < 3 + yBombLoc * 3; i++)
		{
			for(int j = xBombLoc * 3; j < 3 + xBombLoc * 3; j++)
			{
				maze[i][j] = piece4[i - yBombLoc * 3][j - xBombLoc * 3];
			}
		}

	}
}
