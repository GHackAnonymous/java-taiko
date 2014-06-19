package tw.jxcode.taiko;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ComboManager
{
	public static int combo = 0;
	private static float top;
	private static int pass;
	private static int maxPass = 4000;
	public static int add()
	{
		combo++;
		if (combo == 50)
			Sound.Combo(0);
		top = 10;
		if (combo % 100 == 0)
		{
			pass = maxPass;
			Sound.Combo(combo / 100);
		}
		
		if (combo >= 10 && combo % 10 == 0)
			return combo / 10;
		else
			return -1;
	}
	public static void clear()
	{
		combo = 0;
	}
	public static void update(int delta)
	{
		if (pass > 0)
			pass -= delta;
		if (top > 0)
		{
			top -= delta * .1f;
		}
	}
	public static void render(Graphics g)
	{
		if (pass > 0)
			g.drawImage(Resource.flower, 0, 75);
		
		if (combo >= 10)
		{
			if (combo >= 100)
			{
				g.drawImage(Resource.combonumber_l[combo % 10].getScaledCopy(32, (int) (38 + top)), 55, -top+106);
				g.drawImage(Resource.combonumber_l[(combo / 10) % 10].getScaledCopy(32, (int) (38 + top)), 30, -top+106);
				g.drawImage(Resource.combonumber_l[combo / 100].getScaledCopy(32, (int) (38 + top)), 5, -top+106);
			}
			else
			{
				g.drawImage(Resource.combonumber[combo % 10].getScaledCopy(22, (int) (30 + top)), 46, -top+108);
				g.drawImage(Resource.combonumber[combo / 10].getScaledCopy(22, (int) (30 + top)), 25, -top+108);
			}
		}
	}
}
