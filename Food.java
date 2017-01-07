
import java.awt.Shape;
import java.awt.geom.*; 
public class Food {
		
	int xPos,yPos,strike,xBound,yBound;
	
	public Food(int x, int y)
	{
		xBound = x;
		yBound = y;
		strike = 0;
		//xPos = (int) (Math.random() * (xBound-40)/20 + 1);
		//yPos = (int) (Math.random() * (yBound-70)/20 + 1);
		
		//xPos*=20;
		//yPos*=20;
		xPos = 300;
		yPos = 60;
		
		xPos =xBound-40;
		yPos =yBound-60;
		
	}
	public Food()
	{
		
	}
	
	public Shape createFood()
	{
		if(strike==1)
		{
			xPos = (int) (Math.random() * (xBound-40)/20 + 1);
			yPos = (int) (Math.random() * (yBound-60)/20 + 1);
			xPos*=20;
			yPos*=20;
			strike =0;
		}
		Shape x = new Ellipse2D.Float(xPos,yPos,20,20);
		return x;
	}
	
	
}