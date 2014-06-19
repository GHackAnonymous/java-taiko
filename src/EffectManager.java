package tw.jxcode.taiko;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class EffectManager
{
	
	private ArrayList<EffectNode> effects = new ArrayList<EffectNode>();
	private int index;
	private Taiko game;
	public EffectManager(Taiko newGame)
	{
		this.game = newGame;
	}
	public void reset() throws SlickException
	{
		index = 0;
		effects.clear();
		game.clearDancer();
	}
	public void addEffect(Effect type, int time)
	{
		effects.add(new EffectNode(type, time));
	}
	
	public void update() throws SlickException
	{
		if (index != effects.size() && Settings.songTime > effects.get(index).time)
		{
			System.out.println("[" + (index+1) + "/" + effects.size() + "]Effect: " + effects.get(index).type);
			game.startEffect(effects.get(index).type);
			index++;
		}
	}
	
	private class EffectNode
	{
		public int time;
		public Effect type;
		EffectNode(Effect type, int time)
		{
			this.type = type;
			this.time = time;
		}
	}
}
