package tw.jxcode.taiko;

public class Note
{
	public float x;
	public int time;
	public float v;
	public int type;
	public boolean display;
	public boolean zeroCombo = false;
	Note(int type, int time, float ve)
	{
		this.type = type;
		this.time = time;
		this.display = true;
		this.v = 600f / ve;
		this.x = v * time + 100;
	}
	Note(int type, int time)
	{
		this.type = type;
		this.time = time;
		this.display = true;
		this.v = 600f / 3000f;
		this.x = v * time + 100;
	}
	
	public void update(int delta, int songTime)
	{
		if (!Settings.pauseSong)
			x -= delta * v;
//		x = (float)((this.time - songTime)  * 1280/ scroll + 100);
	}
	
	public boolean outOfRangeLeft()
	{
		return x < 0;
	}
	public boolean outOfRangeRight()
	{
		return x > 512;
	}
	public int check()
	{
		return 0;
	}
}
