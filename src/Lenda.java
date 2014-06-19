package tw.jxcode.taiko;

import org.newdawn.slick.Graphics;

public class Lenda
{
	public static float top;
	public static int combo = 0;
	public static void add()
	{
		top = 10;
		combo++;
		Settings.score += 100;
	}
	public static void clear()
	{
		combo = 0;
	}
	public static void update(int delta)
	{
		if (top > 0)
		{
			top -= delta * .1f;
		}
	}
	public static void render(Graphics g)
	{
		if (combo > 0)
		{
			g.drawImage(Resource.rollBalloon, 114, 12);
			if (combo >= 10)
			{
				g.drawImage(Resource.combonumber[combo % 10].getScaledCopy(22, (int) (30 + top)), 197, 44-top);
				g.drawImage(Resource.combonumber[combo / 10].getScaledCopy(22, (int) (30 + top)), 180, 44-top);
			}
			else
			{
				g.drawImage(Resource.combonumber[combo].getScaledCopy(22, (int) (30 + top)), 197, 44-top);
			}
		}
	}
}
