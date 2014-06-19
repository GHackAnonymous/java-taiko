package tw.jxcode.taiko;

import org.newdawn.slick.Input;

public class Settings
{
	// constants
	public static final float SCALE = 2f;
	public static final int WIDTH = (int)(512 * SCALE);
	public static final int HEIGHT = (int)(384 * SCALE);
	public static final String TITLE = "Taiko";
	public static final String FOLDER_IMG = "img/";
	public static final int MAX_PASS = 40;
	public static final int MAX_YELLOW = 500;
	public static final int MAX_EXPLOSION = 450;
	public static final int MAX_JUDGE = 300;
	public static final int MAX_HANDUP = 500;
	public static final int MAX_HITHIT = 150;
	public static final int MAX_LIFE = 175;
	
	
	// global variables
	public static int key[] = new int[4];
	public static float bpm;
	public static String songName;
	public static int songOffset;
	public static int songTime;
	public static int score;
	public static int scoreDiff;
	
	
	// on off
	public static boolean pauseSong;
	public static boolean debugInfo;
	public static boolean autoplayEnable;
	public static boolean soulEnable;
	public static void load()
	{
		key[0] = Input.KEY_Z;
		key[1] = Input.KEY_X;
		key[2] = Input.KEY_N;
		key[3] = Input.KEY_M;
		score = 0;
		pauseSong = false;
		autoplayEnable = true;
		debugInfo = true;
	}
	
	public static void toggleAutoplay()
	{
		autoplayEnable = !autoplayEnable;
	}
	
	public static void toggleDebugInfo()
	{
		debugInfo = !debugInfo;
	}
	
	public static void toggleSoul()
	{
		soulEnable = !soulEnable;
		if (!soulEnable)
		{
			Resource.soul.stop();
			Resource.soul.setCurrentFrame(0);
		}
	}
	
	public static void togglePauseSong()
	{
		pauseSong = !pauseSong;
		Sound.toggleSong();
	}
}

