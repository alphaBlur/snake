//Snake Game completed



import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.io.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*;

@SuppressWarnings("serial")
public class gameBoard extends JFrame{
	Area playArea;
	Food newFood;
	Snake mySnake;
	JLabel scoreLabel = new JLabel("0");
	JLabel banner = new JLabel("Mr.Noob's Score = ");
	JLabel crtl = new JLabel("r-Restart                   ");
	int direction = 0;
	int direction2 = 0;
	int boardWidth = 800;
	int boardHeight = 600;
	int isPaused =0;
	ScheduledThreadPoolExecutor pool ;
	drowRanger xyz = new drowRanger(this);
	
	public static void main(String[] args)
	{
		new gameBoard();
	}
	
	public gameBoard()
	{
		this.setSize(boardWidth,boardHeight+80);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		playArea = new Area();
		this.add(playArea,BorderLayout.CENTER);
		newFood = new Food(boardWidth,boardHeight);
		mySnake = new Snake();
		
		ListenForKeys lforkeys = new ListenForKeys();
		this.addKeyListener(lforkeys);
		pool = new ScheduledThreadPoolExecutor(4);
		pool.scheduleAtFixedRate(xyz, 0L, 10L, TimeUnit.MILLISECONDS);
		JPanel score = new JPanel();
		this.add(score,BorderLayout.SOUTH);
		score.add(crtl);
		score.add(banner);
		score.add(scoreLabel);
		
		Font scoreFont = new Font(Font.DIALOG_INPUT, 0, 40);
		scoreLabel.setFont(scoreFont);
		Font bannerFont = new Font(Font.MONOSPACED, 0, 40);
		banner.setFont(bannerFont);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public class Area extends JComponent
	{
		public void paint(Graphics g)
		{
			scoreLabel.setText(mySnake.score());
			Graphics2D gSet = (Graphics2D)g;
			gSet.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gSet.setPaint(Color.GREEN);
			gSet.fill(new Rectangle2D.Float(0,0,boardWidth,boardHeight-40));
			gSet.setStroke(new BasicStroke(5));
			
			isPaused =TailBite();
			
			if(isPaused == 0)
			{
				for(int i=0;i<mySnake.body.size();++i)
				{
					gSet.setPaint(Color.BLACK);
					gSet.draw(mySnake.body.get(i).createSnake());
					gSet.setPaint(Color.YELLOW);
					gSet.fill(mySnake.body.get(i).createSnake());
				}
				int Ex = mySnake.body.getLast().xPos;
				int Ey = mySnake.body.getLast().yPos;
				int Ex1 = 0,Ex2 = 0,Ey1 = 0,Ey2 = 0;
				if(direction2 == 1 || direction2 == 2)
				{
					Ex1 = Ex + 3; Ex2 = Ex + 12;
					Ey1 = Ey2 = Ey + 7;
				}
				else if(direction2 == 3 || direction2 == 4 || direction2 == 0)
				{
					Ey1 = Ey + 3; Ey2 = Ey + 12;
					Ex1 =  Ex2 = Ex + 7;
				}
				gSet.setPaint(Color.black);
				gSet.fill(new Ellipse2D.Float(Ex1, Ey1, 5, 5));
				gSet.fill(new Ellipse2D.Float(Ex2, Ey2, 5, 5));
				
				switch(direction)
				{
				case 1: mySnake.moveSnakeU();
						break;
						
				case 2: mySnake.moveSnakeD();
						break;
				
				case 3: mySnake.moveSnakeL();
						break;
				
				case 4: mySnake.moveSnakeR();
						break;	
				}			
				gSet.setPaint(Color.blue);
				if(newFood.strike==1)
				for(snakeBit x :mySnake.body)
				{
					do
					{
						gSet.fill(newFood.createFood());	
					}while(x.xPos == newFood.xPos && x.yPos == newFood.yPos);
				}
				else
				gSet.fill(newFood.createFood());
				gSet.setStroke(new BasicStroke(3));
				gSet.setPaint(Color.black);
				gSet.draw(new Rectangle2D.Float(1, 1, boardWidth-18, boardHeight-40));
			
				checkBounds();
				snakeEatFood();
				direction = direction2;
			}
			else
			{
				for(int i=0;i<mySnake.body.size();++i)
				{
					gSet.setPaint(Color.BLACK);
					gSet.draw(mySnake.body.get(i).createSnake());
					gSet.setPaint(Color.RED);
					gSet.fill(mySnake.body.get(i).createSnake());
				}
				int Ex = mySnake.body.getLast().xPos;
				int Ey = mySnake.body.getLast().yPos;
				int Ex1 = 0,Ex2 = 0,Ey1 = 0,Ey2 = 0;
				if(direction2 == 1 || direction2 == 2)
				{
					Ex1 = Ex + 3; Ex2 = Ex + 12;
					Ey1 = Ey2 = Ey + 7;
				}
				else if(direction2 == 3 || direction2 == 4 || direction2 == 0)
				{
					Ey1 = Ey + 3; Ey2 = Ey + 12;
					Ex1 =  Ex2 = Ex + 7;
				}
				gSet.setPaint(Color.black);
				gSet.fill(new Ellipse2D.Float(Ex1, Ey1, 10, 10));
				gSet.fill(new Ellipse2D.Float(Ex2, Ey2, 10, 10));
				
				
				gSet.setPaint(Color.blue);
				gSet.fill(newFood.createFood());
				gSet.setStroke(new BasicStroke(3));
				gSet.setPaint(Color.black);
				gSet.draw(new Rectangle2D.Float(1, 1, boardWidth-18, boardHeight-40));
			}
		}
		
	}
	
	public class drowRanger implements Runnable
	{
		gameBoard obj;
		public drowRanger(gameBoard o)
		{
			this.obj=o;
		}
		
		public void run()
		{
			obj.repaint();
		}
	}
	public class ListenForKeys implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e) {
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyChar()=='r' || e.getKeyChar()=='R')
			{
				//mySnake.body.clear();
				mySnake = new Snake();
				direction=direction2=0;
			}
				
			if(e.getKeyCode()== KeyEvent.VK_UP)
			{
				if(direction == 2)
					return ;
				direction2 = 1;
			}
			else if(e.getKeyCode()== KeyEvent.VK_DOWN)
			{
				if(direction == 1)
					return ;
				direction2 = 2;
			}
			else if(e.getKeyCode()== KeyEvent.VK_LEFT)
			{
				if(direction == 4 || direction == 0)
					return ;
				direction2 = 3;
			}
			else if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			{
				if(direction == 3)
					return ;
				direction2 = 4;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	
	
	public void snakeEatFood()
	{
		int xNext =mySnake.body.getLast().xPos;
		int yNext =mySnake.body.getLast().yPos;
		if( xNext == newFood.xPos && yNext == newFood.yPos)
		{			
			mySnake.eatFood(newFood);
			newFood.strike=1;
			soundEat();
		}
	}
	
	public void checkBounds()
	{
		
		if(mySnake.body.getLast().xPos < 0)
		{
			mySnake.body.getLast().xPos = boardWidth-40;
		}
		else if(mySnake.body.getLast().xPos > boardWidth-40)
		{	
			mySnake.body.getLast().xPos = 0;
		}
		
		if(mySnake.body.getLast().yPos < 0)
		{
			mySnake.body.getLast().yPos = boardHeight-60;
		}
		else if(mySnake.body.getLast().yPos > boardHeight-60)
		{		
			mySnake.body.getLast().yPos = 0;
		}
			
	}

	public int TailBite()
	{		
		for(int i=0; i<mySnake.body.size()-2;++i)
		{
			if(mySnake.body.getLast().xPos == mySnake.body.get(i).xPos 
					&& mySnake.body.getLast().yPos == mySnake.body.get(i).yPos)
			{
				direction2=0;
				return 1;				
			}
		}
		return 0;
	}
	
	public void soundSnake()
	{
		try
		{
			String file = new String ("D:\\Java Program1\\Snake\\src\\______.wav");
			InputStream input = new FileInputStream(file);
			AudioStream audio = new AudioStream(input);
			
			AudioPlayer.player.start(audio);
		}
		catch(FileNotFoundException e)
		{
			e.getStackTrace();
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
	}
	
	public void soundEat()
	{
		try
		{
			String file = new String ("D:\\Java Program1\\Snake\\src\\movei.wav");
			InputStream input = new FileInputStream(file);
			AudioStream audio = new AudioStream(input);
			
			AudioPlayer.player.start(audio);
		}
		catch(FileNotFoundException e)
		{
			e.getStackTrace();
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
		
	}
}



/*
//Snake Game completed



import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.io.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*;

@SuppressWarnings("serial")
public class gameBoard extends JFrame{
	Area playArea;
	Food newFood;
	Snake mySnake;
	JLabel scoreLabel = new JLabel("0");
	JLabel banner = new JLabel("Mr.Noob's Score = ");
	JLabel crtl = new JLabel("r=Restart,Space=Reset                            ");
	int direction = 0;
	int direction2 = 0;
	int boardWidth = 800;
	int boardHeight = 600;
	int isPaused =0;
	ScheduledThreadPoolExecutor pool ;
	drowRanger xyz = new drowRanger(this);
	
	public static void main(String[] args)
	{
		new gameBoard();
	}
	
	public gameBoard()
	{
		this.setSize(boardWidth,boardHeight+80);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		playArea = new Area();
		this.add(playArea,BorderLayout.CENTER);
		newFood = new Food(boardWidth,boardHeight);
		mySnake = new Snake();
		
		ListenForKeys lforkeys = new ListenForKeys();
		this.addKeyListener(lforkeys);
		pool = new ScheduledThreadPoolExecutor(4);
		pool.scheduleAtFixedRate(xyz, 0L, 50L, TimeUnit.MILLISECONDS);
		JPanel score = new JPanel();
		this.add(score,BorderLayout.SOUTH);
		score.add(crtl);
		score.add(banner);
		score.add(scoreLabel);
		
		Font scoreFont = new Font(Font.DIALOG_INPUT, 0, 40);
		scoreLabel.setFont(scoreFont);
		Font bannerFont = new Font(Font.MONOSPACED, 0, 40);
		banner.setFont(bannerFont);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public class Area extends JComponent
	{
		public void paint(Graphics g)
		{
			scoreLabel.setText(mySnake.score());
			Graphics2D gSet = (Graphics2D)g;
			gSet.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gSet.setPaint(Color.GREEN);
			gSet.fill(new Rectangle2D.Float(0,0,boardWidth,boardHeight-40));
			gSet.setStroke(new BasicStroke(5));
			for(int i=0;i<mySnake.body.size();++i)
			{
				gSet.setPaint(Color.BLACK);
				gSet.draw(mySnake.body.get(i).createSnake());
				gSet.setPaint(Color.YELLOW);
				gSet.fill(mySnake.body.get(i).createSnake());
			}
			int Ex = mySnake.body.getLast().xPos;
			int Ey = mySnake.body.getLast().yPos;
			int Ex1 = 0,Ex2 = 0,Ey1 = 0,Ey2 = 0;
			if(direction2 == 1 || direction2 == 2)
			{
				Ex1 = Ex + 3; Ex2 = Ex + 12;
				Ey1 = Ey2 = Ey + 7;
			}
			else if(direction2 == 3 || direction2 == 4 || direction2 == 0)
			{
				Ey1 = Ey + 3; Ey2 = Ey + 12;
				Ex1 =  Ex2 = Ex + 7;
			}
			gSet.setPaint(Color.black);
			gSet.fill(new Ellipse2D.Float(Ex1, Ey1, 5, 5));
			gSet.fill(new Ellipse2D.Float(Ex2, Ey2, 5, 5));
			
			TailBite();
			
			switch(direction)
			{
			case 1: mySnake.moveSnakeU();
					break;
					
			case 2: mySnake.moveSnakeD();
					break;
			
			case 3: mySnake.moveSnakeL();
					break;
			
			case 4: mySnake.moveSnakeR();
					break;	
			}			
			gSet.setPaint(Color.blue);
			
			if(newFood.strike==1)
			for(snakeBit x :mySnake.body)
			{
				do
				{
					gSet.fill(newFood.createFood());	
				}while(x.xPos == newFood.xPos && x.yPos == newFood.yPos);
			}
			else
			gSet.fill(newFood.createFood());
			gSet.setStroke(new BasicStroke(3));
			gSet.setPaint(Color.black);
			gSet.draw(new Rectangle2D.Float(1, 1, boardWidth-18, boardHeight-40));
			
			checkBounds();
			snakeEatFood();
			direction = direction2;
		}
		
	}
	
	public class drowRanger implements Runnable
	{
		gameBoard obj;
		public drowRanger(gameBoard o)
		{
			this.obj=o;
		}
		
		public void run()
		{
			obj.repaint();
		}
	}
	public class ListenForKeys implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e) {
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyChar()=='r' || e.getKeyChar()=='R')
			{
				mySnake.body.clear();
				mySnake = new Snake();
				direction=direction2=0;
			}
			
			if(e.getKeyCode()==KeyEvent.VK_SPACE && isPaused == 1)
			{
				mySnake = new Snake();
				pool = new ScheduledThreadPoolExecutor(4);
				direction=direction2=0;
				
				pool.scheduleAtFixedRate(xyz, 0L, 50L, TimeUnit.MILLISECONDS);
				isPaused=0;
			}
				
			if(e.getKeyCode()== KeyEvent.VK_UP)
			{
				if(direction == 2)
					return ;
				direction2 = 1;
			}
			else if(e.getKeyCode()== KeyEvent.VK_DOWN)
			{
				if(direction == 1)
					return ;
				direction2 = 2;
			}
			else if(e.getKeyCode()== KeyEvent.VK_LEFT)
			{
				if(direction == 4 || direction == 0)
					return ;
				direction2 = 3;
			}
			else if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			{
				if(direction == 3)
					return ;
				direction2 = 4;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	
	
	public void snakeEatFood()
	{
		int xNext =mySnake.body.getLast().xPos;
		int yNext =mySnake.body.getLast().yPos;
		if( xNext == newFood.xPos && yNext == newFood.yPos)
		{			
			mySnake.eatFood(newFood);
			newFood.strike=1;
			soundEat();
		}
	}
	
	public void checkBounds()
	{
		
		if(mySnake.body.getLast().xPos < 0)
		{
			mySnake.body.getLast().xPos = boardWidth-40;
		}
		else if(mySnake.body.getLast().xPos > boardWidth-40)
		{	
			mySnake.body.getLast().xPos = 0;
		}
		
		if(mySnake.body.getLast().yPos < 0)
		{
			mySnake.body.getLast().yPos = boardHeight-60;
		}
		else if(mySnake.body.getLast().yPos > boardHeight-60)
		{		
			mySnake.body.getLast().yPos = 0;
		}
			
	}

	public void TailBite()
	{		
		for(int i=0; i<mySnake.body.size()-2;++i)
		{
			if(mySnake.body.getLast().xPos == mySnake.body.get(i).xPos 
					&& mySnake.body.getLast().yPos == mySnake.body.get(i).yPos)
			{
				
				mySnake.body.clear();
				isPaused=1;
				pool.shutdown();
				mySnake = new Snake();				
			}
		}
	}
	
	public void soundSnake()
	{
		try
		{
			String file = new String ("D:\\Java Program1\\Snake\\src\\______.wav");
			InputStream input = new FileInputStream(file);
			AudioStream audio = new AudioStream(input);
			
			AudioPlayer.player.start(audio);
		}
		catch(FileNotFoundException e)
		{
			e.getStackTrace();
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
	}
	
	public void soundEat()
	{
		try
		{
			String file = new String ("D:\\Java Program1\\Snake\\src\\movei.wav");
			InputStream input = new FileInputStream(file);
			AudioStream audio = new AudioStream(input);
			
			AudioPlayer.player.start(audio);
		}
		catch(FileNotFoundException e)
		{
			e.getStackTrace();
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
		
	}
}*/