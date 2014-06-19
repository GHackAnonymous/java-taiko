package tw.jxcode.taiko;

import java.io.IOException;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound
{
	private static Audio wavDong;
	private static Audio wavKat;
	public  static Music oggSong;
	private static Audio[] combo = new Audio[10];
	public static void init() throws SlickException
	{
		try
		{
			wavDong = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("wav/normal-hitnormal.wav"));
			wavKat = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("wav/soft-hitfinish.wav"));
//			oggSong = new Music("songs/123.ogg");
			combo[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("wav/voice_50combo_p1.wav"));
			for(int i=1;i<10;i++)
				combo[i] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("wav/voice_" + i * 100 + "combo_p1.wav"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void loadSong() throws SlickException
	{
		oggSong = new Music("songs/" + Settings.songName + ".ogg");
		
	}
	
	public static void Combo(int i)
	{
		combo[i].playAsSoundEffect(1, .9f, false);
	}
	
	public static void Dong()
	{
		wavDong.playAsSoundEffect(1, .6f, false);
	}
	
	public static void Kat()
	{
		wavKat.playAsSoundEffect(1, 1, false);
	}
	public static void playSong() throws SlickException
	{
//		oggSong.setPosition(-10f);
		oggSong.play();
	}
	public static void stopSong()
	{
		oggSong.stop();
	}
	public static void toggleSong()
	{
		if (Settings.pauseSong)
			oggSong.pause();
		else
			oggSong.resume();
	}
}
