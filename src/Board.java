package tiko.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.util.Random;

import tiko.game.tools.screen.*;


enum ID{Empty,     Naught,     Cross,	Draw};
enum State{Online, Local, Computer};

public class Board
{
	public Object map[] = new Object[9];
	public ID turn = ID.Empty;

	public ID winner = ID.Empty;

	public Texture sheet;

	private Rectangle grid_src = new Rectangle(288,0,96,96);
	public Rectangle grid_dst = new Rectangle(0,0,192,192);

	private Rectangle sheet_src = new Rectangle(0,0,128,128);
	private Rectangle sheet_dst = new Rectangle(0,0,128,128);

	static Main main;
	private Random rng = new Random();

	private Animation cross_grow = new Animation(6, new Rectangle(0,0,32,32), new double[]{0.05,0.05,0.05,0.05,0.05,0.05});
	private Animation cross_fade = new Animation(6, new Rectangle(0,32,32,32), new double[]{0.05,0.05,0.05,0.05,0.05,0.05});

	private Animation naught_grow = new Animation(6, new Rectangle(0,160,32,32), new double[]{0.05,0.05,0.05,0.05,0.05,0.05});
	private Animation naught_melt = new Animation(11, new Rectangle(0,192,32,32), new double[]{0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05});

	public Rectangle reset_btn_src = new Rectangle(0,256,96,32);
	public Rectangle reset_btn_dst = new Rectangle(10,256,96,32);

	public AI bot;


	public Board(Main main)
	{
		this.main = main;

		sheet = new Texture("Textures\\Animations.png");

		for(int i = 0; i < 9; i++){map[i] = new Object();}
		turn = ID.Cross;

		bot = new AI(this);
		bot.active = false;
		bot.self = ID.Naught;
	}

	public void placeTile(int _pos)
	{
		if(map[_pos].getID() == ID.Empty){
			if(turn == ID.Cross){
				if(rng.nextInt() % 2 == 0){map[_pos] = new Object(turn, new Animation(cross_grow));}
				else{map[_pos] = new Object(turn, new Animation(cross_fade));}
			}

			if(turn == ID.Naught){
				if(rng.nextInt() % 2 == 0){map[_pos] = new Object(turn, new Animation(naught_grow));}
				else{map[_pos] = new Object(turn, new Animation(naught_melt));}
			}
			
			if(turn == ID.Cross){turn = ID.Naught;}
			else{turn = ID.Cross;}
		}
	}

	public void draw(Graphics2D _g2d)
	{
		sheet.setSrcPos(grid_src);
		sheet.setDstPos(grid_dst);
		sheet.draw(_g2d);

		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 3; x++){
				sheet.setDstPos(grid_dst.x + (64 * x) , grid_dst.y +  (64 * y) ,64 ,64);
				map[y * 3 + x].draw(sheet,_g2d);

				if(map[y * 3 + x].highlight == true){
					sheet.setSrcPos(256,0,32,32);
					sheet.draw(_g2d);
				}
			}
		}

		scan();

		int temp = bot.start();
		if(temp >= 0){
			map[temp] = new Object(bot.self,naught_grow);
			turn = ID.Cross;
		}
	}

	public void scan()
	{
		if(winner == ID.Empty){
			for(int i = 0; i < 3; i++){
				if(map[i * 3 + 0].getID() == map[i * 3 + 1].getID() && map[i * 3 + 0].getID() == map[i * 3 + 2].getID() && map[i * 3 + 0].getID() != ID.Empty){
					//Win
					winner = map[i * 3 + 0].getID();
					map[i * 3 + 0].highlight = true;
					map[i * 3 + 1].highlight = true;
					map[i * 3 + 2].highlight = true;
					System.out.println("Win");			
				}
				if(map[0 * 3 + i].getID() == map[1 * 3 + i].getID() && map[0 * 3 + i].getID() == map[2 * 3 + i].getID() && map[0 * 3 + i].getID() != ID.Empty){
					//Win
					winner = map[0 * 3 + i].getID();
					map[0 * 3 + i].highlight = true;
					map[1 * 3 + i].highlight = true;
					map[2 * 3 + i].highlight = true;
					System.out.println("Win");
				}
			}

			if(map[0].getID() == map[4].getID() && map[0].getID() == map[8].getID() && map[0].getID() != ID.Empty){
				//Win
				winner = map[0].getID();
				map[0].highlight = true;
				map[4].highlight = true;
				map[8].highlight = true;
				System.out.println("Win");
			}
			if(map[2].getID() == map[4].getID() && map[2].getID() == map[6].getID() && map[2].getID() != ID.Empty){
				//Win
				winner = map[2].getID();
				map[2].highlight = true;
				map[4].highlight = true;
				map[6].highlight = true;
				System.out.println("Win");
			}
		}

		if(winner == ID.Empty){
			for(int i = 0; i < 9; i++){
				if(map[i].getID() == ID.Empty){
					break;
				}
				if(i == 8){
					winner = ID.Draw;
					System.out.println("Draw");
				}
			}
		}
	}

	public void reset()
	{
		for(int i = 0; i < 9; i++){map[i] = new Object();}
		winner = ID.Empty;
	}



}

class Object
{
	ID id;
	Animation animation;

	boolean highlight;

	Object()
	{
		this.id = ID.Empty;
		this.animation = new Animation(0,new Rectangle(0,0,0,0), new double[]{0});
		this.highlight = false;
	}

	Object(Object object)
	{
		this.id = object.id;
	}

	Object(ID id)
	{
		this.id = id;
	}

	Object(ID id, Animation animation)
	{
		this.id = id;
		this.animation = animation;
		this.highlight = false;
	}

	public void draw(Texture texture, Graphics2D _g2d)
	{
		animation.draw(texture, _g2d);
	}

	public ID getID()
	{
		return id;
	}
}

