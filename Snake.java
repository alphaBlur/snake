import java.util.LinkedList;
public class Snake {
	
	LinkedList<snakeBit> body;
		
	public Snake()
	{
		body = new LinkedList<snakeBit>();
		snakeBit e = new snakeBit();
		e.xPos = 60;
		e.yPos = 60;
		body.add(e);
		
		snakeBit f = new snakeBit();
		f.xPos = 80;
		f.yPos = 60;
		body.add(f);

		snakeBit g = new snakeBit();
		g.xPos = 100;
		g.yPos = 60;
		body.add(g);
		
		snakeBit h = new snakeBit();
		h.xPos = 120;
		h.yPos = 60;
		body.add(h);
	}
	
	public void eatFood(Food x)
	{
		snakeBit temp = new snakeBit();
		temp.xPos = x.xPos;
		temp.yPos = x.yPos;
		temp.createSnake();
		body.addLast(temp);
	}
	
	public void moveSnakeR()
	{		
		snakeBit temp = new snakeBit();
		temp.xPos = body.getLast().xPos;
		temp.yPos = body.getLast().yPos;
		temp.createSnake();
		temp.OneStepR();
		
		body.removeFirst();
		body.addLast(temp);
	}
	public void moveSnakeL()
	{		
		snakeBit temp = new snakeBit();
		temp.xPos = body.getLast().xPos;
		temp.yPos = body.getLast().yPos;
		temp.createSnake();
		temp.OneStepL();
		
		body.removeFirst();
		body.addLast(temp);
	}
	public void moveSnakeU()
	{		
		snakeBit temp = new snakeBit();
		temp.xPos = body.getLast().xPos;
		temp.yPos = body.getLast().yPos;
		temp.createSnake();
		temp.OneStepU();
		
		
		
		body.removeFirst();
		body.addLast(temp);
	}
	public void moveSnakeD()
	{		
		snakeBit temp = new snakeBit();
		temp.xPos = body.getLast().xPos;
		temp.yPos = body.getLast().yPos;
		temp.createSnake();
		temp.OneStepD();
		
		body.removeFirst();
		body.addLast(temp);
	}
	
	public String score()
	{
		int score = (body.size()-4);
		String res = Integer.toString(score);
		return res;
	}	
}