package tiko.game.tools;

import java.lang.System;



public class Timer
{
	private long timer_start;

	public Timer(){
		timer_start = 0;
	}

	public void start(){
		timer_start = System.currentTimeMillis();
	}

	//Return the Timer in Nanoseconds
	public long getTime(){
		return (System.currentTimeMillis() - timer_start);
	}

}