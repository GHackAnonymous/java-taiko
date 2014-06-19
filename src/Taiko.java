package tw.jxcode.taiko;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Taiko extends BasicGame
{
	Random rnd = new Random();
	Input input;
	Image buffer;
	Graphics bufg;
	float offset_bg_top;
	int life;
	int sidePass = 0;
	int cntPass = 0;
	int sfield_blue = 0;
	int sfield_red = 0;
	boolean[] down = {false, false, false, false};
	boolean[] press = {false, false, false, false};
	int[] drawDong = {0, 0, 0, 0};
	ArrayList<Dancer> dancers = new ArrayList<Dancer>();
	ArrayList<FlyNote> rfly_notes = new ArrayList<FlyNote>();
	ArrayList<FlyNote> fly_notes = new ArrayList<FlyNote>();
	public static ArrayList<Note> notes = new ArrayList<Note>();
	ArrayList<Note> rnotes = new ArrayList<Note>();
	float mob_top = 500;
	float top_score;
	float mob_v;
	int handUp;
	int comboBalloonCount = 0;
	int explosion;
	int judge = 0;
	int state = 0;
	int hitJudge = 0;
	int judgeTop = 0;
	int passYellow;
	int passJudge = 0;
	int hithit;
	EffectManager effectManager;
	public Taiko(String title) {super(title);}
	public static void main(String args[]) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Taiko(Settings.TITLE));
		app.setVSync(true);
		app.setTargetFrameRate(60);
		app.setDisplayMode(Settings.WIDTH, Settings.HEIGHT, false);
		app.setShowFPS(false);
		app.setAlwaysRender(true);
		app.start();
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		input = gc.getInput();
		Settings.load(); // must do this first		
		// initialize
		buffer = new Image(Settings.WIDTH, Settings.HEIGHT);
		bufg = buffer.getGraphics();
		bufg.setColor(Color.gray); // for drawLine
		Sound.init();
		Resource.init();
		dancers.add(new Dancer(0,150));
		effectManager = new EffectManager(this);
		Level.switchSong("123", effectManager);
	}

	int type = -1;
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		bufg.drawImage(Resource.background, 0, 0); // fill background
		bufg.drawImage(Resource.topBackground, offset_bg_top, 0); // fill top background
		// say combo
		if (handUp > 0)
		{
			bufg.drawImage(Resource.taikoHandUp, 0, -15);
			bufg.drawImage(Resource.line, -416, 70);
			bufg.drawAnimation(Resource.comboBalloon, 125-Resource.comboBalloon.getFrame() * 0.2f, 40-Resource.comboBalloon.getFrame() * 0.2f);
			bufg.drawImage(Resource.comboBalloonNumber[0], 230, 55);
			bufg.drawImage(Resource.comboBalloonNumber[comboBalloonCount % 10], 230-26, 55);
			if (comboBalloonCount >= 10)
				bufg.drawImage(Resource.comboBalloonNumber[comboBalloonCount / 10], 230-52, 55);
		}
		else
		{
			if (Settings.soulEnable)
				bufg.drawAnimation(Resource.taikoDance, 10, -5);
			else
				bufg.drawAnimation(Resource.taiko, 0, -10);
			bufg.drawImage(Resource.line, -416, 70);
		}
		
		// draw energy
		bufg.drawImage(Resource.energy_bg, 290, 35);
		bufg.drawImage(Resource.energy_fg, 290, 35, 290+life, 65, 0, 0, life, 30);
		
		for(Dancer d : dancers)
		{
			d.render(bufg);
		}
		bufg.drawImage(Resource.mob, 0, mob_top);
		
		if (Settings.soulEnable)
			bufg.drawImage(Resource.sfieldflash_gogo, 93, 94);
		if (sfield_red > 0)
			bufg.drawImage(Resource.sfieldflash_red, 93, 94);
		if (sfield_blue > 0)
			bufg.drawImage(Resource.sfieldflash_blue, 93, 94);
		
		if (Settings.soulEnable)
			bufg.drawAnimation(Resource.fire, 33, 33);
		
		if (state == 3)
		{
			for(int j = notes.size() - 1; j >= 0; j--)
			{
				if (notes.get(j).outOfRangeRight())
					continue;
				else
				{
					if (notes.get(j).type == -1)
						bufg.drawLine(29 + notes.get(j).x, 94, 29 + notes.get(j).x, 154);
					if (notes.get(j).display && notes.get(j).time + 200 > Settings.songTime && notes.get(j).time - Settings.songTime < 6000)
					{
						if (notes.get(j).type >= 0)
							bufg.drawImage(Resource.whiteFont[notes.get(j).type], notes.get(j).x, 157);
						
						if (!notes.get(j).zeroCombo && notes.get(j).display && notes.get(j).x < 70)
						{
							if (notes.get(j).type >= 0 && notes.get(j).type <= 3)
							{
								passJudge = Settings.MAX_JUDGE;
								hitJudge = 2;
								ComboManager.clear();
								subtractSoul();
							}
							notes.get(j).zeroCombo = true;
							if (notes.get(j).type == 7)
								Lenda.clear();
						}
						if (notes.get(j).type >= 0 && notes.get(j).type <= 3)
						{
							bufg.drawImage(Resource.imgNotes[notes.get(j).type], notes.get(j).x, 96);
						}
						else
						{
							if (passYellow > 0)
								bufg.drawImage(Resource.imgNotes[notes.get(j).type], notes.get(j).x, 96, Color.red);
							else
								bufg.drawImage(Resource.imgNotes[notes.get(j).type], notes.get(j).x, 96);
						}
						
					}
				}
			}
			
		}
		bufg.drawImage(Resource.mtaiko_red, 3, 91);
		if(drawDong[0] > 0)
			bufg.drawImage(Resource.mtaikoflash_blue, -3, 74, 51-4, 102+74, 0, 0, 51, 102);
		if(drawDong[3] > 0)
			bufg.drawImage(Resource.mtaikoflash_blue, 51-4, 74, 102-4, 102+74, 51, 0, 102, 102);
		
		if(drawDong[1] > 0)
			bufg.drawImage(Resource.mtaikoflash_red, -4, 74, 51-4, 102+74, 0, 0, 51, 102);
		if(drawDong[2] > 0)
			bufg.drawImage(Resource.mtaikoflash_red, 51-4, 74, 102-4, 102+74, 51, 0, 102, 102);
		if (explosion > 0)
		{
			bufg.drawAnimation(Resource.explodeAnimation[judge+1], 52, 50);
			bufg.drawAnimation(Resource.explodeAnimation[judge], 87, 84);
		}
		if (Settings.soulEnable && Resource.soul.isStopped())
		{
			Resource.soul.setCurrentFrame(1);
			Resource.soul.start();
		}
		else if (Settings.soulEnable == false)
		{
			Resource.soul.stop();
			Resource.soul.setCurrentFrame(0);
		}
		bufg.drawAnimation(Resource.soul, 382, -48);
		for(FlyNote fn : fly_notes)
		{
			fn.render(bufg);
		}
		for(int i=0;i<4;i++)
			if (!Resource.gogoFirework.isStopped())
				bufg.drawAnimation(Resource.gogoFirework, 20+i*115, 155);
		ComboManager.render(bufg);
		if (passJudge > 0)
			bufg.drawImage(Resource.judgement[hitJudge], 106, 80+judgeTop);
		
		Lenda.render(bufg);
		String strScore = "" + Settings.score;
		for(int i=0;i<strScore.length();i++)
		{
			bufg.drawImage(Resource.combonumber[strScore.charAt(i) - '0'].getScaledCopy(22, (int) (30 + top_score)), 500 + (i - strScore.length()) * 17, 60-top_score);
		}
		// render buffer image to screen
		g.drawImage(buffer.getScaledCopy(Settings.SCALE), 0, 0);
		if (Settings.debugInfo)
		{
			g.drawString("Mode: Normal", 0, 0);
			g.drawString("Soul: " + Settings.soulEnable, 0, 15);
			g.drawString("Song: " + Settings.songName, 0, 30);
			g.drawString("Time: " + Settings.songTime, 0, 45);
			g.drawString("Pos: " + Sound.oggSong.getPosition(), 0, 60);
		}
	}
	int x,y;
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		if (input.isKeyPressed(Input.KEY_ESCAPE))
		{
			gc.exit();
			return;
		}
		for(FlyNote fn : fly_notes)
		{
			if (fn.killed())
				rfly_notes.add(fn);
			fn.update(delta);
		}
		fly_notes.removeAll(rfly_notes);
		rfly_notes.removeAll(rfly_notes);
		if (input.isKeyPressed(Input.KEY_F3))
		{
			Settings.toggleDebugInfo();
		}
		if (input.isKeyPressed(Input.KEY_Q))
		{
			
		}
		if (input.isKeyPressed(Input.KEY_R))
		{
			System.out.println("Reloading the song: " + Settings.songName);
			Settings.score = 0;
			Settings.songTime = 0;
			notes.clear();
			rnotes.clear();
			Level.switchSong("123", effectManager);
			Lenda.clear();
			Sound.stopSong();
			Settings.pauseSong = false;
			Sound.playSong();
			ComboManager.clear();
			Settings.songTime = 0;
			life=0;
			state = 3;
			System.out.println("Reloading complete.");
		}
		if (input.isKeyDown(Input.KEY_L))
			life++;
		if (input.isKeyDown(Input.KEY_W))
			y--;
		else if (input.isKeyDown(Input.KEY_S))
			y++;
		if (input.isKeyDown(Input.KEY_A))
			x--;
		else if (input.isKeyDown(Input.KEY_D))
			x++;
		if (input.isKeyPressed(Input.KEY_SPACE))
		{
			System.out.println(x + ", " + y + ", S: " + Settings.songTime);
		}
		if (input.isKeyPressed(Input.KEY_T))
		{
			if (Dancer.no < 5)
				dancers.add(new Dancer(0,150));
		}
		
		if (input.isKeyPressed(Input.KEY_H))
		{
			Resource.comboBalloon.restart();
			comboBalloonCount += 1;
			handUp = Settings.MAX_HANDUP;
		}
		if (input.isKeyPressed(Input.KEY_K))
		{
			Random rnd = new Random();
			flyNote(rnd.nextInt(4));
		}
		if (input.isKeyPressed(Input.KEY_J))
		{
			Resource.gogoFirework.restart();
		}
		if (input.isKeyPressed(Input.KEY_F12))
		{
			Settings.toggleAutoplay();
		}
		if (input.isKeyPressed(Input.KEY_1))
		{
			state = 1;
			Sound.stopSong();
		}
		if (input.isKeyPressed(Input.KEY_2))
		{
			Sound.playSong();
			state = 2;
			Settings.songTime = 0;
			notes.clear();
			rnotes.clear();
		}
		if (input.isKeyPressed(Input.KEY_3))
		{
			Sound.playSong();
			ComboManager.clear();
			Settings.songTime = 0;
			state = 3;
		}
		if (input.isKeyPressed(Input.KEY_P))
		{
			Settings.togglePauseSong();
		}
		if (state == 2 || state == 3)
		{
			if (!Settings.pauseSong)
				Settings.songTime += delta;
		}
		if (state == 3)
		{
			effectManager.update();
			for(Note n : notes)
			{
				n.update(delta, Settings.songTime);
				if (Settings.autoplayEnable)
				{
					if (n.display && n.time - Settings.songTime < 10)
					{
						if (n.type == 0)
						{
							Sound.Dong();
							if (rnd.nextInt(2) == 0)
								drawDong[1] = Settings.MAX_PASS;
							else
								drawDong[2] = Settings.MAX_PASS;
						}
						if (n.type == 2)
						{
							Sound.Dong();
							drawDong[1] = Settings.MAX_PASS;
							drawDong[2] = Settings.MAX_PASS;
						}
						if (n.type == 1)
						{
							Sound.Kat();
							if (rnd.nextInt(2) == 0)
								drawDong[0] = Settings.MAX_PASS;
							else
								drawDong[3] = Settings.MAX_PASS;
						}
						if (n.type == 3)
						{
							Sound.Kat();
							drawDong[0] = Settings.MAX_PASS;
							drawDong[3] = Settings.MAX_PASS;
						}
						if (n.type >= 0 && n.type <= 3)
						{
							startBalloon(ComboManager.add());
							passJudge = Settings.MAX_JUDGE;
							judgeTop = 10;
							hitJudge = 0;
							startExplode(0);
							flyNote(n.type);
							n.display = false;
						}
						else if (n.type == 4 || n.type == 5)
						{
							if (hithit > Settings.MAX_HITHIT)
							{
								startExplode(0);
								if (rnd.nextInt(2) == 0)
								{
									flyNote(0);
									Sound.Dong();
								}
								else
								{
									flyNote(1);
									Sound.Kat();
								}
								hithit = 0;
								passYellow = Settings.MAX_YELLOW;
							}
							hithit += delta;
						}
					}
				}
				if (n.outOfRangeLeft())
					rnotes.add(n);
			}
		}
		notes.removeAll(rnotes);
		rnotes.removeAll(rnotes);
		if (handUp > 0)
			handUp -= delta;
		if (Settings.soulEnable)
		{
			if (mob_top < 250)
				mob_v = delta * .1f;
			if (mob_top > 300)
				mob_v = -delta * .5f;
			mob_top += mob_v;
		}
		else
		{
			if (mob_top < 500)
				mob_top += delta * .5f;
		}
		offset_bg_top -= delta * .05f;
		offset_bg_top %= 206;
		

		for(int i=0;i<4;i++)
			down[i] = input.isKeyDown(Settings.key[i]);
		if (down[0])
		{
			if (!press[0])
			{
				drawDong[0] = Settings.MAX_PASS;
				press[0] = true;
				if (sidePass < Settings.MAX_PASS && sidePass != 0)
					sideHit2();
				else
					sideHit();
			}
		}
		else
			press[0] = false;
		if (press[0])
			sidePass += delta;
		
		if (down[3])
		{
			if (!press[3])
			{
				drawDong[3] = Settings.MAX_PASS;
				press[3] = true;
				if (sidePass < Settings.MAX_PASS && sidePass != 0)
					sideHit2();
				else
					sideHit();
			}
		}
		else
			press[3] = false;
		if (press[3])
			sidePass += delta;
		
		if (!down[0] && !down[3])
			sidePass = 0;
		
		/*-----------------------------*/
		if (down[1])
		{
			if (!press[1])
			{
				drawDong[1] = Settings.MAX_PASS;
				press[1] = true;
				if (cntPass < Settings.MAX_PASS && cntPass != 0)
					cntHit2();
				else
					cntHit();
			}
		}
		else
			press[1] = false;
		if (press[1])
			cntPass += delta;
		
		if (down[2])
		{
			if (!press[2])
			{
				drawDong[2] = Settings.MAX_PASS;
				press[2] = true;
				if (cntPass < Settings.MAX_PASS && cntPass != 0)
					cntHit2();
				else
					cntHit();
			}
		}
		else
			press[2] = false;
		if (press[2])
			cntPass += delta;
		
		if (!down[1] && !down[2])
			cntPass = 0;
		
		ComboManager.update(delta);
		Lenda.update(delta);
		drawDong[0] -= drawDong[0] > 0 ? delta : 0;
		drawDong[1] -= drawDong[1] > 0 ? delta : 0;
		drawDong[2] -= drawDong[2] > 0 ? delta : 0;
		drawDong[3] -= drawDong[3] > 0 ? delta : 0;
		sfield_blue -= sfield_blue > 0 ? delta : 0;
		sfield_red  -= sfield_red  > 0 ? delta : 0;
		explosion  -= explosion  > 0 ? delta : 0;
		passJudge  -= passJudge  > 0 ? delta : 0;
		judgeTop  -= judgeTop  > 0 ? delta * .1f : 0;
		passYellow  -= passYellow  > 0 ? delta : 0;
		top_score  -= top_score  > 0 ? delta * .1f : 0;
		
		Settings.soulEnable = (life >= 170);
		
	}
	
	private void flyNote(int type) throws SlickException
	{
		fly_notes.add(new FlyNote(type));
	}
	
	float diff;
	private void sideHit() throws SlickException
	{
		if (state == 2)
			notes.add(new Note(1, Settings.songTime));
		else if (state == 3)
		{
			checkHit(1);
		}
		Sound.Kat();
		sfield_blue = 100;
	}
	private void sideHit2() throws SlickException
	{
		if (state == 2)
			notes.get(notes.size()-1).type = 3;
		else if (state == 3)
		{
			checkHit(3);
		}
		sfield_blue = 100;
	}
	
	private void cntHit() throws SlickException
	{
		if (state == 2)
			notes.add(new Note(0, Settings.songTime));
		else if (state == 3)
		{
			checkHit(0);
		}
		Sound.Dong();
		sfield_red = 100;
	}
	private void cntHit2() throws SlickException
	{
		if (state == 2)
			notes.get(notes.size()-1).type = 2;
		else if (state == 3)
		{
			checkHit(2);
		}
		sfield_red = 100;
	}
	
	private void checkHit(int type) throws SlickException
	{
		for(Note n : notes)
		{
			if (n.display == true && n.x > 70 && n.x < 125)
			{
				if (n.type == 4 || n.type == 5)
					Lenda.add();
				if (n.type == type)
				{
					n.display = false;
					
					int diff = Math.abs(n.time - Settings.songTime);
					startBalloon(ComboManager.add());
					passJudge = Settings.MAX_JUDGE;
					judgeTop = 10;
					if (diff <= 50)
					{
						hitJudge = 0;
						startExplode(0);
						flyNote(type);
						addSoul(2);
						if (type <= 1)
							addScore(390);
						else if (type <= 3)
							addScore(650);
					}
					else if (diff <= 90)
					{
						hitJudge = 1;
						startExplode(2);
						flyNote(type);
						if (type <= 1)
							addScore(145);
						else if (type <= 3)
							addScore(325);
					}
					else
					{
						hitJudge = 2;
						ComboManager.clear();
						subtractSoul();
					}
				}
				else if (n.type >= 4 && n.type <= 7)
				{
					startExplode(0);
					flyNote(type);
					passYellow = Settings.MAX_YELLOW;
				}
				System.out.println(n.time + ": " + Settings.songTime + "->" + (n.time - Settings.songTime));
				break;
			}
		}
	}
	
	private void startExplode(int newJudge)
	{
		Resource.explodeAnimation[judge].restart();
		Resource.explodeAnimation[judge+1].restart();
		judge = newJudge;
		explosion = Settings.MAX_EXPLOSION;
	}
	
	private void startBalloon(int n)
	{
		if (n == -1)
			return;
		Resource.comboBalloon.restart();
		comboBalloonCount = n;
		handUp = Settings.MAX_HANDUP;
	}
	
	private void addSoul(int value)
	{
		if (life < 180)
			life += value;
	}
	
	private void subtractSoul()
	{
		if (life > 0)
			life-=3;
	}
	
	private void addScore(int value)
	{
		top_score = 10;
		Settings.score += value + (ComboManager.combo / 10) * Settings.scoreDiff;
	}
	
	public void startEffect(Effect type) throws SlickException
	{
		switch(type)
		{
		case FISH:
			if (Dancer.no < 5)
				dancers.add(new Dancer(0,150));
			break;
		case JIZZ:
			Resource.gogoFirework.restart();
			break;
		default:
			break;
		}
	}
	public void clearDancer() throws SlickException
	{
		dancers.clear();
		Dancer.no = 0;
		dancers.add(new Dancer(0,150));
	}
}