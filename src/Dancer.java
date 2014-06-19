package tw.jxcode.taiko;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dancer
{
	int x;
	int y;
	static int no = 0;
	Animation start;
	static Animation loop;
	int[] mapping = {2, 1, 3, 0, 4};
	Dancer(int x, int y) throws SlickException
	{
		this.x = x + mapping[no] * 100 - 45;
		this.y = y;
		
		String prefix = "img/dancer/dancer_1";
		Image[] start_imgs = {
				new Image(prefix + "a48.png"),
				new Image(prefix + "a49.png"),
				new Image(prefix + "a50.png"),
				new Image(prefix + "a51.png"),
				new Image(prefix + "a52.png"),
				new Image(prefix + "a53.png"),
				new Image(prefix + "a54.png"),
				new Image(prefix + "a55.png"),
				new Image(prefix + "a56.png"),
		};
		int[] start_dur = new int[9];
		for(int i=0;i<9;i++)
			start_dur[i] = 50;
		start = new Animation(start_imgs, start_dur);
		
		
		if (loop == null)
		{
			Image[] loop_imgs = new Image[56];
			
			for(int i=0;i<56;i++)
			{
				loop_imgs[i] = new Image(prefix + "n" + (i+1) + ".png");
			}
			int[] loop_dur = new int[56];
			for(int i=0;i<56;i++)
				loop_dur[i] = 70;
			loop = new Animation(loop_imgs, loop_dur);
		}

		start.setLooping(false);
		no++;
	}
	public void render(Graphics g)
	{
		if (start.isStopped())
			g.drawAnimation(loop, x, y);
		else
			g.drawAnimation(start, x, y);
	}
}
