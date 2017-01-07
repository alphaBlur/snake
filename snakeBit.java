import java.awt.Shape;
import java.awt.geom.Rectangle2D;


public class snakeBit extends Food {
	
	Shape viola;
	
	public snakeBit()
	{
		xPos = 60;
		yPos = 60;
	}
	public Shape createSnake()
	{
		viola = new Rectangle2D.Float(xPos,yPos,20,20);
		return viola;
	}
	
	public void OneStepR()
	{
		xPos+=20;
		viola = createSnake();
	}
	public void OneStepL()
	{
		xPos-=20;
		viola = createSnake();
	}
	public void OneStepU()
	{
		yPos-=20;
		viola = createSnake();
	}
	public void OneStepD()
	{
		yPos+=20;
		viola = createSnake();
	}
}