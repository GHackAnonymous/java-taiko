package tw.jxcode.taiko;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.newdawn.slick.SlickException;

public class Level
{
	public static void switchSong(String name, EffectManager em) throws SlickException
	{
		Settings.score = 0;
		Settings.scoreDiff = 200;
		Settings.songName = name;
//		Settings.bpm = 80;
		Settings.songOffset = -250; // rainbow
//		int songOffset = 2750; treasure
		Sound.loadSong();
		loadFileAndParse();
		em.reset();
		em.addEffect(Effect.JIZZ, 1735);
		em.addEffect(Effect.FISH, 20000);
		em.addEffect(Effect.JIZZ, 35389);
		em.addEffect(Effect.FISH, 35000);
		em.addEffect(Effect.FISH, 55700);
		em.addEffect(Effect.FISH, 65400);
	}
	
	private static void loadFileAndParse()
	{
		String tko_notes = "";
		byte[] encoded;
		try
		{
			encoded = Files.readAllBytes(Paths.get("songs/" + Settings.songName + ".tja"));
			String tmp = new String(encoded, Charset.defaultCharset());
			tko_notes = "";
			for(int i=0;i<tmp.length();i++)
			{
				if (tmp.charAt(i) == '\r' || tmp.charAt(i) == '\n')
					continue;
				else
					tko_notes += tmp.charAt(i);
			}
		}
		catch (IOException e)
		{
			System.out.println("Loading tja failed!");
			e.printStackTrace();
		}
		
		// parser start
		int type;
		boolean multistart = false;
		int hitTime = 0;
		for(int i=0;i<tko_notes.length();i++)
		{
			
			type = -1;
			if (multistart)
				type = 5;
			if (tko_notes.charAt(i) == '1')
				type = 0;
			else if (tko_notes.charAt(i) == '2')
				type = 1;
			else if (tko_notes.charAt(i) == '3')
				type = 2;
			else if (tko_notes.charAt(i) == '4')
				type = 3;
			else if (tko_notes.charAt(i) == '5' || tko_notes.charAt(i) == '6' || tko_notes.charAt(i) == '7')
			{
				type = 4;
				multistart = true;
			}
			else if (tko_notes.charAt(i) == '8')
			{
				type = 7;
				multistart = false;
			}
			if (type >= 0 && type <= 7)
			{
				// when can i 
//				hitTime = (int)(i * (117.1875f) + songOffset);
				// treasure
//				hitTime = (int)(i * (129.31f) + songOffset);
				// rainbow
				if (i < 192)
				{
					hitTime = (int)(i * (178.5714285f - 0.9f + 0.002f*i) + Settings.songOffset);
				}
				else if (i < 240)
				{
					hitTime = (int)((i-191) * 59.5238095f + Settings.songOffset+ 191 * 178.5714285);
				}
				else
				{
					hitTime = (int)((i-239) * 178.5714285f + Settings.songOffset+ 191 * 178.5714285+ 47 * 59.5238095f);
				}


//				float v = 3000f * rnd.nextFloat() + 3000f;
				float v = 3000f;
//				if (i < 16)
//					v = 1000f;
//				else if (i < 32)
//					v = 8000f;
//				else if (i < 48)
//					v = 3000;
//				else if (i < 80)
//					v = 6000f;
//				else if (i < 160)
//					v = 4000f;
//				else if (i < 176)
//					v = 3000f;
//				else if (i < 192)
//					v = 6000f;
				Taiko.notes.add(new Note(type, hitTime, v));
				if (i % 8 == 0)
				{
					Note splite = new Note(-1, hitTime, v);
					splite.display = false;
					Taiko.notes.add(splite);
				}
			}
		}
		System.out.println("Parse TJA successfully.");
	}
}
