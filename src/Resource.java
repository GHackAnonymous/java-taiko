package tw.jxcode.taiko;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Resource
{
	// temporary
	private static Image tmp_judgements;
	private static Image tmp_notes;
	private static Image tmp_senotes;
	private static Image tmp_comboBalloon;
	private static Image tmp_comboBalloonNumber;
	
	public static Image background;
	public static Image topBackground;
	public static Image energy_bg;
	public static Image energy_fg;
	public static Image line;
	public static Image mob;
	public static Image mtaikoflash_blue;
	public static Image mtaikoflash_red;
	public static Image sfieldflash_blue;
	public static Image sfieldflash_red;
	public static Image sfieldflash_gogo;
	public static Image mtaiko_red;
	public static Image taikoHandUp;
	public static Image rollBalloon;
	public static Image flower;
	
	public static Image[] judgement = new Image[3];
	public static Image[] whiteFont = new Image[8];
	public static Image[] imgNotes = new Image[8];
	
	// numbers
	public static Image[] comboBalloonNumber = new Image[10];
	public static Image[] combonumber = new Image[10];
	public static Image[] combonumber_l = new Image[10];
	
	// Animations
	public static Animation comboBalloon;
	public static Animation taiko;
	public static Animation taikoDance;
	public static Animation fire;
	public static Animation gogoFirework;
	public static Animation[] explodeAnimation = new Animation[8];
	public static Animation soul;
	private static void loadResource() throws SlickException
	{
		mob = new Image("img/mob.png");
		line = new Image("img/mtaiko_p2.png");
		energy_bg = new Image("img/normagauge.png").getSubImage(0, 0, 180, 30);
		energy_fg = new Image("img/normagauge.png").getSubImage(0, 30, 180, 30);
		mtaiko_red = new Image("img/mtaiko_red.png");
		background = new Image("img/bg.png");
		topBackground = new Image("img/bg_top.png");
		sfieldflash_red = new Image("img/sfieldflash_red.png");
		mtaikoflash_red = new Image("img/mtaikoflash_red.png");
		sfieldflash_blue = new Image("img/sfieldflash_blue.png");
		mtaikoflash_blue = new Image("img/mtaikoflash_blue.png");
		sfieldflash_gogo = new Image("img/sfieldflash_yellow.png");
		rollBalloon = new Image("img/rollballoon.png");
		
		
		
		// temporary, dispose later
		tmp_notes = new Image("img/notes.png");
		tmp_senotes = new Image("img/senotes.png");
		tmp_judgements = new Image("img/judgement.png");
		tmp_comboBalloonNumber = new Image("img/combonumber_balloon.png");
		tmp_comboBalloon = new Image("img/comboballoon_p1.png");
	}
	public static void init() throws SlickException
	{
		loadResource();
		init_miscs();
		init_comboBalloon();
		init_taikoDance();
		init_explosion();
		init_number();
		taiko = initAnimation("playerchar_p1n.png", 2, 250, 205, 189, .8f, 1f);
		fire = initAnimation("fire.png", 8, 50, 188, 184, 1f, .7f);
		soul = initAnimation("soul.png", 8, 50, 190, 190, 1f, 1f);
		soul.stop();
		soul.setLooping(false);
		soul.setCurrentFrame(0);
		gogoFirework = initAnimation("gogosplash.png", 30, 20, 115, 230, 1f, 1f);
		gogoFirework.stop();
		gogoFirework.setLooping(false);
	}
	
	private static void init_number() throws SlickException
	{
		Image tmp1 = new Image("img/combonumber.png");
		Image tmp2 = new Image("img/combonumber_l.png");
		flower = new Image("img/mtaikoflower.png");
		for(int i=0;i<10;i++)
		{
			combonumber[i] = tmp1.getSubImage(i*22, 0, 22, 30);
			combonumber_l[i] = tmp2.getSubImage(i*32, 0, 32, 38);
		}
	}
	private static void init_miscs() throws SlickException
	{
		sfieldflash_blue.setAlpha(0.3f);
		sfieldflash_red.setAlpha(0.3f);
		sfieldflash_gogo.setAlpha(0.5f);
		
		for (int i=0;i<3;i++)
			judgement[i] = tmp_judgements.getSubImage(0, i * 32, 45, 32);
		
		for(int i=0;i<5;i++)
			imgNotes[i] = tmp_notes.getSubImage(60*(i+1), 0, 60, 59);
		imgNotes[7] = tmp_notes.getSubImage(60*7, 0, 60, 59);
		imgNotes[5] = tmp_notes.getSubImage(60*6, 0, 60, 59);
		
		whiteFont[0] = tmp_senotes.getSubImage(0, 0, 60, 18);
		whiteFont[1] = tmp_senotes.getSubImage(180, 0, 60, 18);
		whiteFont[2] = tmp_senotes.getSubImage(300, 0, 60, 18);
		whiteFont[3] = tmp_senotes.getSubImage(360, 0, 60, 18);
		whiteFont[4] = tmp_senotes.getSubImage(420, 0, 60, 18);
		whiteFont[5] = tmp_senotes.getSubImage(540, 0, 60, 18);
		whiteFont[7] = tmp_senotes.getSubImage(0, 0, 1, 1);
		
		taikoHandUp = new Image("img/playerchar_p1n.png").getSubImage(205*2,  0, 205, 189).getScaledCopy(0.8f);
	}
	
	private static void init_taikoDance() throws SlickException
	{
		Image t = new Image("img/playerchar_p1.png");
		Image[] arr = {
			t.getSubImage(205*0, 0, 205, 186).getScaledCopy(0.8f),
			t.getSubImage(205*1, 0, 205, 186).getScaledCopy(0.8f),
			t.getSubImage(205*2, 0, 205, 186).getScaledCopy(0.8f),
			t.getSubImage(205*3, 0, 205, 186).getScaledCopy(0.8f),
			t.getSubImage(205*2, 0, 205, 186).getScaledCopy(0.8f),
			t.getSubImage(205*1, 0, 205, 186).getScaledCopy(0.8f),
		};
		int[] dur = {500,50,50,500,50,50,};
		taikoDance = new Animation(arr, dur);
	}
	
	private static void init_comboBalloon() throws SlickException
	{
		for(int i=0;i<10;i++)
			comboBalloonNumber[i] = tmp_comboBalloonNumber.getSubImage(26*i, 0, 26, 32);
		
		
		Image[] tmp_comboBalloons = {
			tmp_comboBalloon.getScaledCopy(0.8f),
			tmp_comboBalloon.getScaledCopy(0.82f),
			tmp_comboBalloon.getScaledCopy(0.84f),
			tmp_comboBalloon.getScaledCopy(0.86f),
			tmp_comboBalloon.getScaledCopy(0.88f),
			tmp_comboBalloon.getScaledCopy(0.86f),
			tmp_comboBalloon.getScaledCopy(0.84f),
			tmp_comboBalloon.getScaledCopy(0.82f),
			tmp_comboBalloon.getScaledCopy(0.80f)
		};
		int[] comboBalloonsDur = new int[9];
		for(int i=0;i<9;i++)
			comboBalloonsDur[i] = 10;
		comboBalloon = new Animation(tmp_comboBalloons, comboBalloonsDur);
		comboBalloon.setLooping(false);
	}
	
	private static void init_explosion() throws SlickException
	{
		Image expl = new Image("img/explosion_lower.png");
		Image expu = new Image("img/explosion_upper.png");
		int[] dur = new int[9];
		Image[] epl = new Image[9];
		Image[] epu = new Image[9];
		Image[] enl = new Image[9];
		Image[] enu = new Image[9];
		
		Image[] bepl = new Image[9];
		Image[] benl = new Image[9];
		Image[] bepu = new Image[9];
		Image[] benu = new Image[9];
		for(int i=0;i<9;i++)
		{
			dur[i] = 20;
			epl[i] = expl.getSubImage(i*80, 0, 80, 80);
			enl[i] = expl.getSubImage(i*80, 80, 80, 80);
			bepl[i]= expl.getSubImage(i*80, 160, 80, 80);
			benl[i]= expl.getSubImage(i*80, 240, 80, 80);
			epu[i] = expu.getSubImage(i*150, 0, 150, 150);
			enu[i] = expu.getSubImage(i*150, 150, 150, 150);
			bepu[i] = expu.getSubImage(i*150, 300, 150, 150);
			benu[i] = expu.getSubImage(i*150, 450, 150, 150);
		}
		benu[8] = expu.getSubImage(8*150, 150, 150, 150);
		bepu[8] = expu.getSubImage(8*150, 150, 150, 150);
		explodeAnimation[0] = new Animation(epl, dur);
		explodeAnimation[1] = new Animation(epu, dur);
		explodeAnimation[2] = new Animation(enl, dur);
		explodeAnimation[3] = new Animation(enu, dur);
		
		explodeAnimation[4] = new Animation(bepl, dur);
		explodeAnimation[5] = new Animation(bepu, dur);
		explodeAnimation[6] = new Animation(benl, dur);
		explodeAnimation[7] = new Animation(benu, dur);
		for(int i=0;i<8;i++)
			explodeAnimation[i].setLooping(false);
		
	}
	
	
	private static Animation initAnimation(String srcName, int count, int dur, int w, int h, float scale, float alpha) throws SlickException
	{
		Image source = new Image(Settings.FOLDER_IMG + srcName);
		Image[] frame = new Image[count];
		int[] duration = new int[count];
		for(int i=0;i<count;i++)
		{
			frame[i] =  source.getSubImage(w*i, 0, w, h).getScaledCopy(scale);
			frame[i].setAlpha(alpha);
			duration[i] = dur;
		}
		return new Animation(frame, duration);
	}
}
