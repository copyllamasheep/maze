/*
 * Ishanvi Kommula
 * GameProject.java
 * creates frame, creates cards, animates motion of character and pieces
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;  
import java.awt.Toolkit;
import java.util.Date;
import java.time.ZonedDateTime;

public class GameProject extends JFrame
{
	public static void main(String args[]) //makes GameProject object and runs constructor
	{
		GameProject ishanvi = new GameProject();
		ishanvi.createFrame();
	}
	public void createFrame() //makes frame
	{
		JFrame frame = new JFrame("GameProject.java");
		frame.setSize(1000, 870);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Maze1 thing = new Maze1();
		frame.setContentPane( thing );
		thing.addComponentToPane(frame.getContentPane());
		
		frame.setResizable( false );
		frame.setVisible(true);
	}
}
class Maze1 extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
	JPanel cards;
	int cur_card = 1, counter = 0, xSun = 756, ySun = 386, red = 254 * 1, green = 130 * 1, blue = 184 * 1, sparkleframe, backgroundframe, xBombLoc, yBombLoc, xBombLocCoor, yBombLocCoor, bombframe;
	double sun = 0;
	int xLightningLoc, yLightningLoc, xLightningLocCoor, yLightningLocCoor, lightning, sunIncrement, backgroundIncrement, cuckooIncrement, instruction_incre;
	long bombframe_start, bombframe_stop, char_start, char_stop, blood_start, blood_stop, lightning_start, lightning_stop, sun_start, sun_stop, instruction_start, instruction_stop, piece_start, piece_stop;
	int xloc, yloc, xBox = 678, yBox = 477, xMouse, yMouse;
	int xPiece = 830, yPiece = 120, prev_xPiece = 830, prev_yPiece = 120, xAdd, yAdd, xChar = 19, yChar = 19, xCharCoor = 153 + 27*19, yCharCoor = 161 + 27*19, dir = 0, xTargetLoc, xTargetLocCoor, yTargetLoc, yTargetLocCoor;
	Rectangle start_button, back, box, lastpiece, PUSH, instruction_button, Ebutton, Mbutton, Hbutton;
	boolean rotate, dragging, first = true, found, push, change_maze, charStart, targetCompleted = true, gameover, firstTarget = true, bombCompleted, bomb_in_possession, bomb_dropped;
	boolean bombJustDropped = false, blood = true, speed_increase = false, firstbomb = true, firstSun = true, cuckooBird = false, first_instruction = true, start;
	int cur_box = 12; int dir2;
	Timer piecetimer, suntimer, sparkletimer, backgroundtimer;
	int speed, speedChar, mode;
	Image image;
	CreateMaze drawImages = new CreateMaze();
	StartScreenMaze check = new StartScreenMaze();
	GameSound sounds = new GameSound();
	PaintCards paint = new PaintCards();
	Date date;
	public Maze1() //constructor, adds listeners and runs methods that creat random maze
	{
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		drawImages.assignCoordinates();
		drawImages.makeMaze();
		check.createStartMaze();
	}
	public void addComponentToPane(Container pane){ //makes cards in cardlayout
		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		JPanel card4 = new JPanel();
		JPanel card5 = new JPanel();
		
		cards = new JPanel(new CardLayout());
		cards.add(card1, "Start Card");
		cards.add(card2, "Hard Card");
		cards.add(card3, "Levels Card");
		cards.add(card4, "Instructions Card");
		cards.add(card5, "GameOver Card");
		pane.add(cards, BorderLayout.CENTER);
		repaint();
	}
	public void paintComponent(Graphics g) //runs methods for each card
	{
		super.paintComponent(g);
		start_button = new Rectangle(280, 540, 400, 80);
		instruction_button = new Rectangle(280, 640, 400, 80);
		
		Ebutton = new Rectangle(260, 100, 469, 112);
		Mbutton = new Rectangle(260, 300, 469, 112);
		Hbutton = new Rectangle(260, 500, 469, 112);
		
		back = new Rectangle(50, 50, 50, 50);
		box = new Rectangle(xBox, yBox, 20, 20);
		lastpiece = new Rectangle(xPiece, yPiece, 150, 150);
		PUSH = new Rectangle(850, 190, 100, 40);
		Font font = new Font("Dialogue", Font.BOLD, 60);
		SparkleMover sparklemover = new SparkleMover();
		sparkletimer = new Timer(0, sparklemover);
		g.setFont(font);
		Sprite character = new Sprite();
		BackgroundImage bgimg = new BackgroundImage();
		
		if(cur_card == 1){
			paint.paintCard1(g);
			bgimg.paintGriever(g, xBox, yBox, dir2);
		}
		else if(cur_card == 2)
		{
			date = new Date();
			paint.getRGB(red / 1, green / 1, blue / 1);
			bgimg.paintBackground(g, backgroundframe, red / 1, green / 1, blue / 1);
			if(gameover)
			{
				paint.paintGameOver(g, counter);
			}
			paint.paintCard2(g, counter);
			drawImages.getRGB(red / 1, green / 1, blue / 1);
			drawImages.drawMaze(g, first, xAdd, yAdd);
			if(targetCompleted)
			{
				bgimg.paintGIF(g, xTargetLocCoor, yTargetLocCoor, sparkleframe / 500);
				sparkletimer.start();
				if(sparkleframe == 4500 || firstTarget) {
					if(!firstTarget) counter++;
					firstTarget = false;
					newTarget();
					sparkleframe = 0;
					sparkletimer.stop(); 
					targetCompleted = false;
					if(mode == 3 && !speed_increase) { newLightningLoc(); }
				}
			}
			if((!bomb_in_possession && mode >= 2 && bombframe == 14 && !gameover) || firstbomb) { newBombLoc(); bombframe_start = date.getTime(); bombframe = 0; firstbomb = false; bombJustDropped = false;}
			if(bomb_in_possession && !bombJustDropped)
			{
				xBombLoc = xChar; yBombLoc = yChar;
				xBombLocCoor = 153 + 27*xBombLoc - ((20 - xBombLoc) / 3) * 5;
				yBombLocCoor = 161 + 27*yBombLoc - ((20 - yBombLoc) / 3) * 5;
			}
			if(rotate)
			{
				drawImages.rotatePiece(g);
				rotate = false;
			}
			if(found) piece_start = date.getTime();
			if(start) {
				if(speed != 0) {
					pieceTimer();
					pieceTimer();
				}
				if(firstSun)
				{
					sun_start = date.getTime();
					firstSun = false;
				}
			}
			sunTimer();
			if(speed == 0)
			{	
				charStart = true;
			}
			if(charStart)
			{
				moveChar();
			}	
			if(dragging)
			{
				bgimg.paintBackground(g, backgroundframe, red / 1, green / 1, blue / 1);
				paint.paintCard2(g, counter);
				if(counter % 2 == 0 && counter != 0) bgimg.paintGIF2(g, xBombLocCoor, yBombLocCoor, bombframe);
				drawImages.drawMaze(g, first, xAdd, yAdd);
				drawImages.repaintLast(g, xPiece, yPiece);
				first = false;
			}
			else {
				if(found || (xPiece == prev_xPiece && yPiece == prev_yPiece && xPiece != 830)) {
					drawImages.repaintLastLast(g, xPiece, yPiece, 81, push);
				}
				else {
					if(xPiece == 830 && yPiece == 120) drawImages.repaintLastLast(g, xPiece, yPiece, 150, push);
					else if(!push) drawImages.repaintLastLast(g, xPiece, yPiece, 81, push);
				}
				found = false;
				prev_xPiece = xPiece;
				prev_yPiece = yPiece;
			}
			if(!gameover && blood) {
				character.paintCharacter(xCharCoor, yCharCoor, g, dir, speed_increase);
				character.paintSun(xSun, ySun, g, ((sun_stop - sun_start) / 1000));
				bgimg.paintCuckoo(g, cuckooIncrement, cuckooBird);
			}
			if(!targetCompleted) character.paintTarget(xTargetLocCoor, yTargetLocCoor, g);
			push = false;
			
			if((bomb_dropped || !bombCompleted) && mode >= 2)
			{	
				int drop_speed = 8000;
				if(bomb_dropped) drop_speed = 1000;
				bombframe_stop = date.getTime();
				if(bombframe < 3)
				{
					if((bombframe_stop - bombframe_start) / ((bombframe + 1) * drop_speed) == 1)
					{
						bombframe++;
					}	
				}
				else {
					if(Math.abs(xBombLoc - xChar) < 3 && Math.abs(yBombLoc - yChar) < 3 && bombframe != 14)
					{
						if(blood)
						{
							blood_start = date.getTime();
							blood = false;
						}
						blood_stop = date.getTime();
						bgimg.paintBlood(g, xCharCoor, yCharCoor);
						//System.out.println(blood_stop - blood_start);
						if(blood_stop - blood_start >= 500 && blood_stop != blood_start) {
							gameover = true;
						}
					}
					if(bombframe == 3)
					{
						bombframe_start = date.getTime();
					}
					bombframe_stop = date.getTime();
					if(((bombframe_stop - bombframe_start) / ((bombframe - 2) * 100) == 1 && bombframe < 14) || bombframe == 3)
					{
						bombframe++;
					}
				}
				bgimg.paintGIF2(g, xBombLocCoor, yBombLocCoor, bombframe);
				if(bombframe == 8 && bomb_dropped)
				{
					drawImages.explosion(xBombLoc / 3, yBombLoc / 3);
					bomb_dropped = false;
					bomb_in_possession = false;
				}
				if(bombCompleted || bombframe == 13)
				{
					if(bombCompleted){
						bomb_in_possession = true;
					}
					bombCompleted = false;
				}
			}
			if(mode == 3)
			{
				if((xChar == xLightningLoc && yChar == yLightningLoc) && !speed_increase)
				{
					lightning = 4;
					speed_increase = true;
					lightning_start = date.getTime();
				}
				else
				{
					lightning_stop = date.getTime();
					if(!speed_increase)
						bgimg.paintLightning(g, xLightningLocCoor, yLightningLocCoor);				
					if(lightning_stop - lightning_start >= 10000)
					{
						speed_increase = false;
						lightning = 0;
					}
				}
			}
			drawImages.getCurBox(cur_box);
			drawImages.getSpeed(speed);
			if(gameover)
			{
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.next(cards);
				cl.next(cards); 
				cl.next(cards); 
				cur_card += 3; 
			}
		}
		else if (cur_card == 3)
		{
			paint.paintCard3(g);
		}
		else if(cur_card == 4)
		{
			paint.paintCard4(g, instruction_incre % 40);
			card3(g);
			paint.paintCard4(g, instruction_incre % 40);
		}
		else if(cur_card == 5)
		{
			paint.paintGameOver(g, counter);
		}
	}
	public void mouseReleased(MouseEvent e){ //sets dragging to false
		if(dragging)
		{
			inBox(e.getX(), e.getY());
		}
		dragging = false;
	}
	public void mouseEntered(MouseEvent e){ //checks if mouse enters frame, also gets focus when entered
		requestFocus();
		xloc = e.getX();
		yloc = e.getY();
	}
	public void mouseExited(MouseEvent e){} //doesn't do anything
	public void mousePressed(MouseEvent e){ //sets dragging to true
		xloc = e.getX();
		yloc = e.getY();
		if(lastpiece.contains(e.getX(), e.getY()) && !gameover)
		{
			dragging = true;
		}
	}
	public void mouseClicked(MouseEvent e){ //checks if certain areas are clicked
		CardLayout cl = (CardLayout)(cards.getLayout());
		xloc = e.getX();
		yloc = e.getY();	
		if(start_button.contains(xloc, yloc) && cur_card == 1)
		{
			cl.next(cards);
			cl.next(cards);
			cur_card+= 2;
		}
		else if(instruction_button.contains(xloc, yloc) && cur_card == 1)
		{
			cl.next(cards);
			cl.next(cards);
			cl.next(cards);
			cur_card+= 3;
			date = new Date();
		}
		else if(Ebutton.contains(xloc, yloc) && cur_card == 3)
		{
			mode = 1;
			cl.previous(cards);
			cur_card--;
			drawImages.makeMaze();
		}
		else if(Mbutton.contains(xloc, yloc) && cur_card == 3)
		{
			mode = 2;
			cl.previous(cards);
			cur_card--;
			drawImages.makeMaze();
		}
		else if(Hbutton.contains(xloc, yloc) && cur_card == 3)
		{
			mode = 3;
			cl.previous(cards);
			cur_card--;
			drawImages.makeMaze();
		}
		else if(back.contains(xloc, yloc))
		{
			cl.previous(cards);
			cur_card--;
			reset();
		}
		if(xloc > 830 && xloc < 980 && yloc > 120 && yloc < 270 && cur_card == 2 && !dragging) 
		{
			rotate = true;
		}
		if(xloc > 850 && xloc < 950 && yloc > 290 && yloc < 330 && cur_card == 2 && cur_box != 12 && !dragging)
		{
			push = true;
			start = true;
			change_maze = true;
			speed = 10;
		}
		repaint();
	}
	public void mouseMoved(MouseEvent e){
	} //doesn't do anything
	public void mouseDragged(MouseEvent e){ // if you're dragging the piece, it changes the coordinates of the piece to the mouse coordinates
		xAdd = 0; yAdd = 0;
		push = false;
		if(dragging) {
			xPiece = xPiece + (e.getX() - xloc);
			yPiece = yPiece + (e.getY() - yloc);
			xloc = e.getX();
			yloc = e.getY();
			repaint();
		}
	} 
	public void keyTyped(KeyEvent e){} //doesn't do anything
	public void keyPressed(KeyEvent e){ //controls the yellow box thing on start screen(first card)
		int origx = xBox, origy = yBox;
		int ch = e.getKeyCode();
		if(cur_card == 1)
		{
			if(ch == KeyEvent.VK_UP) { yBox -= 25; dir2 = 1; }
			if(ch == KeyEvent.VK_DOWN) { yBox += 25; dir2 = 2; }
			if(ch == KeyEvent.VK_LEFT) { xBox -= 25; dir2 = 3; }
			if(ch == KeyEvent.VK_RIGHT) { xBox += 25; dir2 = 4; }
			if(xBox > 1000) xBox = 3;
			if(xBox < 0) xBox = 978;
			if(yBox > 840) yBox = 2;
			if(yBox < 0) yBox = 827;
			if(!check.checkIfBlocked(xBox, yBox)){
				xBox = origx; yBox = origy;
			}
			repaint();
		}
		if(cur_card == 2 && !gameover) {
			origx = xChar; origy = yChar;
			if(ch == KeyEvent.VK_UP) {
				yChar -= 1;
				dir = 1;
			} 
			if(ch == KeyEvent.VK_DOWN) {
				yChar += 1;
				dir = 2;
			}
			if(ch == KeyEvent.VK_LEFT) {
				xChar -= 1;
				dir = 3;
			}
			if(ch == KeyEvent.VK_RIGHT) {
				xChar += 1;
				dir = 4;
			}
			if(ch == KeyEvent.VK_SHIFT && bomb_in_possession) {
				bomb_dropped = true;
				bombframe = 0;
				bomb_in_possession = false;
				bombCompleted = false;
				bombJustDropped = true;
				bombframe_start = date.getTime();
			}
			char_start = date.getTime();
			char_start /= 10;
			if(drawImages.checkIfBlocked2(xChar, yChar)){
				xChar = origx; yChar = origy;
			}
			if(xChar == xTargetLoc && yChar == yTargetLoc)
			{
				targetCompleted = true;
			}
			if(xChar == xBombLoc && yChar == yBombLoc && bombframe < 3)
			{
				bombCompleted = true;
				bomb_in_possession = true;
			}
			charStart = true;
			repaint();
		}
	} 
	public void keyReleased(KeyEvent e){} //doesn't do anything
	
	public void inBox(int xcoor, int ycoor) { //checks if the piece is close enough to the inside of one of the slots after dragging stops
		for(int i = 0; i < 3; i++)
		{
			if(xcoor > 201 + 172*i && xcoor < 282 + 172*i && ycoor > 40 && ycoor < 121)
			{
				xPiece = 201 + 172*i; yPiece = 40;
				found = true;
				cur_box = i;
			}
			if(xcoor > 201 + 172*i && xcoor < 282 + 172*i && ycoor > 728 && ycoor < 809)
			{
				xPiece = 201 + 172*i; yPiece = 728;
				found = true;
				cur_box = 3 + i;
			}
			if(xcoor > 29 && xcoor < 110 && ycoor > 212 + 172*i && ycoor < 293 + 172*i)
			{
				xPiece = 29; yPiece = 212 + 172*i;
				found = true;
				cur_box = 6 + i;
			}
			if(xcoor > 717 && xcoor < 798 && ycoor > 212 + 172*i && ycoor < 293 + 172*i)
			{
				xPiece = 717; yPiece = 212 + 172*i;
				found = true;
				cur_box = 9 + i;
			}
		}
		if(!found)
		{
			xPiece = 830; yPiece = 120;
			cur_box = 12;
		}
		else {
			speed = 10;
			change_maze = true;
			if(cur_box == 0 && xChar / 3 == 1)  yChar+=3;
			if(cur_box == 1 && xChar / 3 == 3)  yChar+=3;
			if(cur_box == 2 && xChar / 3 == 5)  yChar+=3;
			
			if(cur_box == 3 && xChar / 3 == 1)  yChar-=3;
			if(cur_box == 4 && xChar / 3 == 3)  yChar-=3;
			if(cur_box == 5 && xChar / 3 == 5)  yChar-=3;
			
			if(cur_box == 6 && yChar / 3 == 1)  xChar+=3;
			if(cur_box == 7 && yChar / 3 == 3)  xChar+=3;
			if(cur_box == 8 && yChar / 3 == 5)  xChar+=3;
			
			if(cur_box == 9 && yChar / 3 == 1)  xChar-=3;
			if(cur_box == 10 && yChar / 3 == 3)  xChar-=3;
			if(cur_box == 11 && yChar / 3 == 5)  xChar-=3;
			
			
			if(cur_box == 0 && xTargetLoc / 3 == 1)  yTargetLoc +=3;
			if(cur_box == 1 && xTargetLoc / 3 == 3)  yTargetLoc +=3;
			if(cur_box == 2 && xTargetLoc / 3 == 5)  yTargetLoc +=3;
			
			if(cur_box == 3 && xTargetLoc / 3 == 1)  yTargetLoc -=3;
			if(cur_box == 4 && xTargetLoc / 3 == 3)  yTargetLoc -=3;
			if(cur_box == 5 && xTargetLoc / 3 == 5)  yTargetLoc -=3;
			
			if(cur_box == 6 && yTargetLoc / 3 == 1)  xTargetLoc +=3;
			if(cur_box == 7 && yTargetLoc / 3 == 3)  xTargetLoc +=3;
			if(cur_box == 8 && yTargetLoc / 3 == 5)  xTargetLoc +=3;
			
			if(cur_box == 9 && yTargetLoc / 3 == 1)  xTargetLoc -=3;
			if(cur_box == 10 && yTargetLoc / 3 == 3)  xTargetLoc -=3;
			if(cur_box == 11 && yTargetLoc / 3 == 5)  xTargetLoc -=3;
			
						
			if(cur_box == 0 && xBombLoc / 3 == 1)  yBombLoc +=3;
			if(cur_box == 1 && xBombLoc / 3 == 3)  yBombLoc +=3;
			if(cur_box == 2 && xBombLoc / 3 == 5)  yBombLoc +=3;
			
			if(cur_box == 3 && xBombLoc / 3 == 1)  yBombLoc -=3;
			if(cur_box == 4 && xBombLoc / 3 == 3)  yBombLoc -=3;
			if(cur_box == 5 && xBombLoc / 3 == 5)  yBombLoc -=3;
			
			if(cur_box == 6 && yBombLoc / 3 == 1)  xBombLoc +=3;
			if(cur_box == 7 && yBombLoc / 3 == 3)  xBombLoc +=3;
			if(cur_box == 8 && yBombLoc / 3 == 5)  xBombLoc +=3;
			
			if(cur_box == 9 && yBombLoc / 3 == 1)  xBombLoc -=3;
			if(cur_box == 10 && yBombLoc / 3 == 3)  xBombLoc -=3;
			if(cur_box == 11 && yBombLoc / 3 == 5)  xBombLoc -=3;
			
			if(cur_box == 0 && xLightningLoc / 3 == 1)  yLightningLoc +=3;
			if(cur_box == 1 && xLightningLoc / 3 == 3)  yLightningLoc +=3;
			if(cur_box == 2 && xLightningLoc / 3 == 5)  yLightningLoc +=3;
			
			if(cur_box == 3 && xLightningLoc / 3 == 1)  yLightningLoc -=3;
			if(cur_box == 4 && xLightningLoc / 3 == 3)  yLightningLoc -=3;
			if(cur_box == 5 && xLightningLoc / 3 == 5)  yLightningLoc -=3;
			
			if(cur_box == 6 && yLightningLoc / 3 == 1)  xLightningLoc +=3;
			if(cur_box == 7 && yLightningLoc / 3 == 3)  xLightningLoc +=3;
			if(cur_box == 8 && yLightningLoc / 3 == 5)  xLightningLoc +=3;
			
			if(cur_box == 9 && yLightningLoc / 3 == 1)  xLightningLoc -=3;
			if(cur_box == 10 && yLightningLoc / 3 == 3)  xLightningLoc -=3;
			if(cur_box == 11 && yLightningLoc / 3 == 5)  xLightningLoc -=3;
		}
		repaint();
	}
	public void card3(Graphics g)
	{
		date = new Date();
		instruction_stop = date.getTime();
		instruction_stop /= 10;
		if(first_instruction)
		{
			instruction_start = date.getTime();
			first_instruction = false;
			instruction_start/=10;
		}
		if((instruction_stop - instruction_start) / (10 * (instruction_incre + 1)) >= 1)
			instruction_incre ++;
		repaint();
	}
	public void newTarget() //randomly sets location of coin
	{
		xTargetLoc = (int)(Math.random() * 21);
		yTargetLoc = (int)(Math.random() * 21);
		if(mode == 1) {
			xTargetLoc = xChar - 2 + (int)(Math.random() * 4);
			yTargetLoc = yChar - 2 + (int)(Math.random() * 4);
		}
		if(mode == 2) {
			xTargetLoc = xChar - 6 + (int)(Math.random() * 12);
			yTargetLoc = yChar - 6 + (int)(Math.random() * 12);
		}
		if(drawImages.checkIfBlocked2(xTargetLoc, yTargetLoc) || (xTargetLoc == xChar && yTargetLoc == yChar))
		{
			newTarget();
		}
		xTargetLocCoor = 153 + 27*xTargetLoc - ((20 - xTargetLoc) / 3) * 5;
		yTargetLocCoor = 161 + 27*yTargetLoc - ((20 - yTargetLoc) / 3) * 5;
	}
	public void newBombLoc() //randomly sets location of bomb
	{
		xBombLoc = (int)(Math.random() * 21);
		yBombLoc = (int)(Math.random() * 21);
		if(mode == 2) {
			xBombLoc = xChar - 2 + (int)(Math.random() * 4);
			yBombLoc = yChar - 2 + (int)(Math.random() * 4);
		}
		if(drawImages.checkIfBlocked2(xBombLoc, yBombLoc) || (xBombLoc == xChar && yBombLoc == yChar))
		{
			newBombLoc();
		}
		xBombLocCoor = 153 + 27*xBombLoc - ((20 - xBombLoc) / 3) * 5;
		yBombLocCoor = 161 + 27*yBombLoc - ((20 - yBombLoc) / 3) * 5;
	}
	public void newLightningLoc() //randomly sets location of lightning
	{
		xLightningLoc = (int)(Math.random() * 21);
		yLightningLoc = (int)(Math.random() * 21);
		
		
		if(drawImages.checkIfBlocked2(xLightningLoc, yLightningLoc) || (xLightningLoc == xChar && yLightningLoc == yChar))
		{
			newLightningLoc();
		}
		xLightningLocCoor = 153 + 27*xLightningLoc - ((20 - xLightningLoc) / 3) * 5;
		yLightningLocCoor = 161 + 27*yLightningLoc - ((20 - yLightningLoc) / 3) * 5;
	}
	public void moveChar() //uses Date class to get time in milliseconds and animates movement of character
	{
		date = new Date();
		boolean done_moving_char = false;
		if(char_stop < date.getTime()) {
			char_stop = date.getTime();
			char_stop /= 10;
		}
		if(dir == 1 && yCharCoor > 161 + 27*yChar - ((20 - yChar) / 3) * 5 && ((char_stop - char_start) % 1 == 0) && ((char_stop - char_start) != 0))
		{
			yCharCoor -= (2 + lightning);
		}
		else if (dir == 1 && yCharCoor < 161 + 27*yChar - ((20 - yChar) / 3) * 5) { done_moving_char = true; }
		if(dir == 2 && yCharCoor < 161 + 27*yChar - ((20 - yChar) / 3) * 5 && ((char_stop - char_start) % 1 == 0) && ((char_stop - char_start) != 0))
		{
			yCharCoor += (2 + lightning);
		}
		else if (dir == 2 && yCharCoor > 161 + 27*yChar - ((20 - yChar) / 3) * 5) done_moving_char = true;
		if(dir == 3 && xCharCoor > 153 + 27*xChar - ((20 - xChar) / 3) * 5 && ((char_stop - char_start) % 1 == 0) && ((char_stop - char_start) != 0))
		{
			xCharCoor -= (2 + lightning);
		}
		else if (dir == 3 && xCharCoor < 153 + 27*xChar - ((20 - xChar) / 3) * 5) done_moving_char = true;
		if(dir == 4 && xCharCoor < 153 + 27*xChar - ((20 - xChar) / 3) * 5 && ((char_stop - char_start) % 1 == 0) && ((char_stop - char_start) != 0))
		{
			xCharCoor += (2 + lightning);
		}
		else if (dir == 4 && xCharCoor > 153 + 27*xChar - ((20 - xChar) / 3) * 5) done_moving_char = true;
		if(done_moving_char) {
			charStart = false;
		}
		repaint();
	}
	public void reset() //resets all variables and stuff when back button is pressed
	{
		cur_card = 1; counter = 0; ySun = 400; sun = 0;
		red = 254 * 1; green = 130 * 1; blue = 184 * 1; 
		sparkleframe = 0; backgroundframe = 0; backgroundIncrement = 0; cuckooIncrement = 0; instruction_start = 0; instruction_stop = 0; instruction_incre = 0;
		xBombLoc = 0; yBombLoc = 0; xBombLocCoor = 0; yBombLocCoor = 0; bombframe = 14;
		bombframe_start = 0; bombframe_stop = 0; char_start = 0; char_stop = 0;
		xloc = 0; yloc = 0; xBox = 678; yBox = 477; xMouse = 0; yMouse = 0;
		xPiece = 830; yPiece = 120; prev_xPiece = 830; prev_yPiece = 120; 
		xAdd = 0; yAdd = 0; xChar = 19; yChar = 19; xCharCoor = 153 + 27*19; yCharCoor = 161 + 27*19;
		dir = 0; xTargetLoc = 0; xTargetLocCoor = 0; yTargetLoc = 0; yTargetLocCoor = 0;
		rotate = false; dragging = false; first = true; found = false; 
		push = false; change_maze = false; charStart = false; targetCompleted = true; gameover = false; 
		firstTarget = true; bombCompleted = false; bomb_in_possession  = false; bomb_dropped = false; bombJustDropped = false; blood = true; sun_start = 0; sun_stop = 0; firstSun = true;
		cur_box = 12; xSun = 756; cuckooBird = false; 
		xLightningLoc = 0; yLightningLoc = 0; xLightningLocCoor = 0; yLightningLocCoor = 0; first_instruction = true;
		speed = 0; speedChar = 0; mode = 0;	push = false; start = false; piece_start = 0; piece_stop = 0;
	}
	public void sunTimer() { //uses a millisecond timer to time the 2 minute duration of the game
		if(firstSun) return;
		sun_stop = date.getTime();
		if(sun_stop - sun_start <= 120000)
		{
			xSun = (int)(Math.cos(((sun_stop - sun_start) / (1000.0 / 3) * Math.PI) / 180.0) * 386) + 370;
			ySun = (int)(-1 * Math.sin(((sun_stop - sun_start) / (1000.0 / 3) * Math.PI) / 180.0) * 386) + 400;
			if((sun_stop - sun_start) / (235 * (sunIncrement + 1)) == 1) {
				sunIncrement ++;
				boolean doneOne = false;
				if(red == 254 * 1 && green == 130 * 1 && blue > 90 * 1 && !doneOne)
				{
					doneOne = true;
					blue -=1;
				}
				if(blue == 90 && green < 196 * 1 && !doneOne)
				{
					doneOne = true;
					green+=1;
				}
				if(green == 196 * 1 && red > 186 * 1 && !doneOne)
				{
					doneOne = true;
					red-=1;
					blue+= 1;
				}
				if(green == 196 * 1 && red > 12 * 1 && blue < 254 * 1 && !doneOne)
				{
					doneOne = true;
					blue+=1;
					red-=1;
				}
				if(blue == 254 * 1 && green > 44 * 1 && red > 12 * 1 && !doneOne)
				{
					doneOne = true;
					green-=1;
					red-=1;
				}
				if(green <= 187 * 1 && red < 255 * 1 && !doneOne)
				{
					doneOne = true;
					red+=1;
				}
			}
			if((sun_stop - sun_start) / (882 * (backgroundIncrement + 1)) == 1) {
				backgroundIncrement++;
				backgroundframe ++;
			}
			if((sun_stop - sun_start) / (500 * (cuckooIncrement + 1)) == 1) {
				cuckooIncrement++;
				if(cuckooIncrement >= 108 && cuckooIncrement <= 132)
				{
					cuckooBird = true;
					sounds.playMusic("cuckoo.wav");
				}
				else cuckooBird = false;
			}
		}
		else
		{
			gameover = true;
		}
		repaint();
	}
	public void pieceTimer() //animates movement of pieces
	{
		date = new Date();
		piece_stop = date.getTime();
		if(dragging) return;
		charStart = false;
		if(cur_box >= 0 && cur_box <= 2 && yPiece < 126) {
			yPiece ++;
			yAdd++;
			if(cur_box == 0 && xChar / 3 == 1) yCharCoor ++;
			else if(cur_box == 1 && xChar / 3 == 3) yCharCoor ++;
			else if(cur_box == 2 && xChar / 3 == 5) yCharCoor ++;
			
			if(cur_box ==  0 && xTargetLoc / 3 == 1) yTargetLocCoor++;
			else if(cur_box == 1 && xTargetLoc / 3 == 3) yTargetLocCoor++;
			else if(cur_box == 2 && xTargetLoc / 3 == 5) yTargetLocCoor ++;
			
			if(cur_box ==  0 && xBombLoc / 3 == 1) yBombLocCoor++;
			else if(cur_box == 1 && xBombLoc / 3 == 3) yBombLocCoor++;
			else if(cur_box == 2 && xBombLoc / 3 == 5) yBombLocCoor ++;
			
			if(cur_box ==  0 && xLightningLoc / 3 == 1) yLightningLocCoor++;
			else if(cur_box == 1 && xLightningLoc / 3 == 3) yLightningLocCoor++;
			else if(cur_box == 2 && xLightningLoc / 3 == 5) yLightningLocCoor ++;
			
			if(yPiece == 125){ 
				yPiece = 728;
				speed = 0;
			}
		}
		if(cur_box >= 3 && cur_box <= 5 && yPiece > 642) {
			yPiece --;
			yAdd--;
			if(cur_box == 3 && xChar / 3 == 1) yCharCoor --;
			else if(cur_box == 4 && xChar / 3 == 3) yCharCoor --;
			else if(cur_box == 5 && xChar / 3 == 5) yCharCoor --;
			
			if(cur_box == 3 && xTargetLoc / 3 == 1) yTargetLocCoor--;
			else if(cur_box == 4 && xTargetLoc / 3 == 3) yTargetLocCoor--;
			else if(cur_box == 5 && xTargetLoc / 3 == 5) yTargetLocCoor--;
			
			if(cur_box == 3 && xBombLoc / 3 == 1) yBombLocCoor--;
			else if(cur_box == 4 && xBombLoc / 3 == 3) yBombLocCoor--;
			else if(cur_box == 5 && xBombLoc / 3 == 5) yBombLocCoor--;
			
			if(cur_box == 3 && xLightningLoc / 3 == 1) yLightningLocCoor--;
			else if(cur_box == 4 && xLightningLoc / 3 == 3) yLightningLocCoor--;
			else if(cur_box == 5 && xLightningLoc / 3 == 5) yLightningLocCoor--;
			
			if(yPiece == 643){ 
				yPiece = 40;
				speed = 0;
			}
		}
		if(cur_box >= 6 && cur_box <= 8 && xPiece < 115) {
			xPiece ++;
			xAdd++;
			if(cur_box == 6 && yChar / 3 == 1) xCharCoor ++;
			else if(cur_box == 7 && yChar / 3 == 3) xCharCoor ++;
			else if(cur_box == 8 && yChar / 3 == 5) xCharCoor ++;

			if(cur_box == 6 && yTargetLoc / 3 == 1) xTargetLocCoor++;
			else if(cur_box == 7 && yTargetLoc / 3 == 3) xTargetLocCoor++;
			else if(cur_box == 8 && yTargetLoc / 3 == 5) xTargetLocCoor ++;
			
			if(cur_box == 6 && yBombLoc / 3 == 1) xBombLocCoor++;
			else if(cur_box == 7 && yBombLoc / 3 == 3) xBombLocCoor++;
			else if(cur_box == 8 && yBombLoc / 3 == 5) xBombLocCoor ++;
			
			if(cur_box == 6 && yLightningLoc / 3 == 1) xLightningLocCoor++;
			else if(cur_box == 7 && yLightningLoc / 3 == 3) xLightningLocCoor++;
			else if(cur_box == 8 && yLightningLoc / 3 == 5) xLightningLocCoor ++;
			
			if(xPiece == 114){ 
				xPiece = 717;
				speed = 0;
			}
		}
		if(cur_box >= 9 && cur_box <= 11 && xPiece > 631) {
			xPiece --;
			xAdd--;
			if(cur_box == 9 && yChar / 3 == 1) xCharCoor --;
			else if(cur_box == 10 && yChar / 3 == 3) xCharCoor --;
			else if(cur_box == 11 && yChar / 3 == 5) {xCharCoor --; }
			
			if(cur_box == 9 && yTargetLoc / 3 == 1) xTargetLocCoor --;
			else if(cur_box == 10 && yTargetLoc / 3 == 3) xTargetLocCoor --;
			else if(cur_box == 11 && yTargetLoc / 3 == 5) xTargetLocCoor --;
			
			if(cur_box == 9 && yBombLoc / 3 == 1) xBombLocCoor--;
			else if(cur_box == 10 && yBombLoc / 3 == 3) xBombLocCoor--;
			else if(cur_box == 11 && yBombLoc / 3 == 5) xBombLocCoor--;
			
			if(cur_box == 9 && yLightningLoc / 3 == 1) xLightningLocCoor--;
			else if(cur_box == 10 && yLightningLoc / 3 == 3) xLightningLocCoor--;
			else if(cur_box == 11 && yLightningLoc / 3 == 5) xLightningLocCoor--;
			
			if(xPiece == 632){ 
				xPiece = 29;
				speed = 0;
			}
		}
		if(speed == 0 && change_maze) {
			drawImages.changeMaze();
			change_maze = false;
			if(xChar > 20) { xChar = 1; xCharCoor = 153 + 27*xChar - ((20 - xChar) / 3) * 5; yChar = yChar / 3 * 3 + 1; yCharCoor = 153 + 27*yChar - ((20 - yChar) / 3) * 5;}
			if(yChar > 20) { yChar = 1; yCharCoor = 161 + 27*yChar - ((20 - yChar) / 3) * 5; xChar = xChar / 3 * 3 + 1; xCharCoor = 153 + 27*xChar - ((20 - xChar) / 3) * 5;}
			if(xChar < 0) { xChar = 19; xCharCoor = 153 + 27*xChar - ((20 - xChar) / 3) * 5; yChar = yChar / 3 * 3 + 1; yCharCoor = 153 + 27*yChar - ((20 - yChar) / 3) * 5;}
			if(yChar < 0) { yChar = 19; yCharCoor = 161 + 27*yChar - ((20 - yChar) / 3) * 5; xChar = xChar / 3 * 3 + 1; xCharCoor = 153 + 27*xChar - ((20 - xChar) / 3) * 5;}
			
			if(xTargetLoc < 0) { xTargetLoc = 19; xTargetLocCoor = 153 + 27*xTargetLoc - ((20 - xTargetLoc) / 3) * 5; yTargetLoc = yTargetLoc / 3 * 3 + 1; yTargetLocCoor = 153 + 27*yTargetLoc - ((20 - yTargetLoc) / 3) * 5; }
			if(xTargetLoc > 20) { xTargetLoc = 1; xTargetLocCoor = 153 + 27*xTargetLoc - ((20 - xTargetLoc) / 3) * 5; yTargetLoc = yTargetLoc / 3 * 3 + 1; yTargetLocCoor = 153 + 27*yTargetLoc - ((20 - yTargetLoc) / 3) * 5; }
			if(yTargetLoc < 0) { yTargetLoc = 19; yTargetLocCoor = 161 + 27*yTargetLoc - ((20 - yTargetLoc) / 3) * 5; xTargetLoc = xTargetLoc / 3 * 3 + 1; xTargetLocCoor = 153 + 27*xTargetLoc - ((20 - xTargetLoc) / 3) * 5;}
			if(yTargetLoc > 20) { yTargetLoc = 1; yTargetLocCoor = 161 + 27*yTargetLoc - ((20 - yTargetLoc) / 3) * 5; xTargetLoc = xTargetLoc / 3 * 3 + 1; xTargetLocCoor = 153 + 27*xTargetLoc - ((20 - xTargetLoc) / 3) * 5;}
			
			if(xBombLoc < 0) { xBombLoc = 19; xBombLocCoor = 153 + 27*xBombLoc - ((20 - xBombLoc) / 3) * 5; yBombLoc = yBombLoc / 3 * 3 + 1; yBombLocCoor = 153 + 27*yBombLoc - ((20 - yBombLoc) / 3) * 5; }
			if(xBombLoc > 20) { xBombLoc = 1; xBombLocCoor = 153 + 27*xBombLoc - ((20 - xBombLoc) / 3) * 5; yBombLoc = yBombLoc / 3 * 3 + 1; yBombLocCoor = 153 + 27*yBombLoc - ((20 - yBombLoc) / 3) * 5;}
			if(yBombLoc < 0) { yBombLoc = 19; yBombLocCoor = 161 + 27*yBombLoc - ((20 - yBombLoc) / 3) * 5; xBombLoc = xBombLoc / 3 * 3 + 1; xBombLocCoor = 153 + 27*xBombLoc - ((20 - xBombLoc) / 3) * 5;}
			if(yBombLoc > 20) { yBombLoc = 1; yBombLocCoor = 161 + 27*yBombLoc - ((20 - yBombLoc) / 3) * 5; xBombLoc = xBombLoc / 3 * 3 + 1; xBombLocCoor = 153 + 27*xBombLoc - ((20 - xBombLoc) / 3) * 5;}
			
			if(xLightningLoc < 0) { xLightningLoc = 19; xLightningLocCoor = 153 + 27*xLightningLoc - ((20 - xLightningLoc) / 3) * 5; yLightningLoc = yLightningLoc / 3 * 3 + 1; yLightningLocCoor = 153 + 27*yLightningLoc - ((20 - yLightningLoc) / 3) * 5;}
			if(xLightningLoc > 20) { xLightningLoc = 1; xLightningLocCoor = 153 + 27*xLightningLoc - ((20 - xLightningLoc) / 3) * 5; yLightningLoc = yLightningLoc / 3 * 3 + 1; yLightningLocCoor = 153 + 27*yLightningLoc - ((20 - yLightningLoc) / 3) * 5;}
			if(yLightningLoc < 0) { yLightningLoc = 19; yLightningLocCoor = 161 + 27*yLightningLoc - ((20 - yLightningLoc) / 3) * 5; xLightningLoc = xLightningLoc / 3 * 3 + 1; xLightningLocCoor = 153 + 27*xLightningLoc - ((20 - xLightningLoc) / 3) * 5;}
			if(yLightningLoc > 20) { yLightningLoc = 1; yLightningLocCoor = 161 + 27*yLightningLoc - ((20 - yLightningLoc) / 3) * 5; xLightningLoc = xLightningLoc / 3 * 3 + 1; xLightningLocCoor = 153 + 27*xLightningLoc - ((20 - xLightningLoc) / 3) * 5;}
			//System.out.println(xTargetLoc + " " + yTargetLoc);
		}
		repaint();
		grabFocus();
	}
	class SparkleMover implements ActionListener { //times the frames of sparkle gif
		public void actionPerformed(ActionEvent e)
		{
			if(sparkleframe < 4500 && targetCompleted){
				sparkleframe++;
			}
		}
	}
}
