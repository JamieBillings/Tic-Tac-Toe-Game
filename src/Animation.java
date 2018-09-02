package tiko.game.tools.screen;

import tiko.game.Main;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Animation
{
	public int frame_limit;
	public Rectangle src_pos;
	public int current_frame;
	public double sec_per_frame[];
	public double delta;

	static Main timer_host;

	static public void init(Main _timer_host)
	{
		timer_host = _timer_host;
	}

	public Animation(int frame_limit, Rectangle src_pos, double[] sec_per_frame)
	{
		this.frame_limit = frame_limit;
		this.src_pos = src_pos;
		this.sec_per_frame = sec_per_frame;
	}

	public Animation(Animation animation)
	{
		this.frame_limit = animation.frame_limit;
		this.src_pos = animation.src_pos;
		this.sec_per_frame = animation.sec_per_frame;
	}

	public void draw(Texture texture, Graphics2D _g2d)
	{
		texture.setSrcPos(src_pos);
		texture.setSrcPos('x', src_pos.x + (src_pos.width * current_frame));
		texture.draw(_g2d);

		delta += timer_host.step_timer;

		if(current_frame < frame_limit){
			if(sec_per_frame[current_frame] < delta){
				delta -= sec_per_frame[current_frame];
				current_frame++;
			}
		}
	}
}

