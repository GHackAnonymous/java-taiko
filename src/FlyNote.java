package tw.jxcode.taiko;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FlyNote
{
	public float x = 97;
	public float y = 95;
	public float vx = 22;
	public float vy = -20;
	public float g = 1.8f;
	public int type;
	public Image img;
	public Animation exp; 
	public static Image[] exps = new Image[8];
	public static int[] dur = new int[8];
	boolean startExp = false;
	FlyNote(int type) throws SlickException
	{
		this.type = type;
		img = new Image("img/notes.png").getSubImage(60*(type+1), 59, 60, 59);
		Image imgexp = new Image("img/explosion_soul.png");
		for(int i=0;i<8;i++)
		{
			exps[i] = imgexp.getSubImage(i*200, 0, 200, 200);
			dur[i] = 25;
		}
		exp = new Animation(exps, dur);
		exp.stop();
		exp.setLooping(false);
	}
	
	public boolean killed()
	{
		return (exp.isStopped() && startExp);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(img, x, y);
		if (startExp)
			g.drawAnimation(exp, 380, -50);
	}
	
	public void update(int delta)
	{
		if (x > 445)
		{
			startExp = true;
			exp.start();
		}
		else
		{
			vy += g;
			x += vx;
			y += vy;
		}
		if (startExp)
		{
			img.setAlpha(img.getAlpha() - 0.004f* delta);
		}
	}
}
