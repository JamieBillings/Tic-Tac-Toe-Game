package tiko.game;

import tiko.game.tools.*;
import tiko.game.tools.screen.Animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JPanel
{

	static final public long SCREEN_TICKS = 1000 / 60;
	static public float step_timer = 0;

	public Board game_board = new Board(this);
	public Button events = new Button(this);

	Main()
	{
		setBackground(Color.BLACK);
		addMouseListener(events);
		addKeyListener(events);
		setFocusable(true);
		requestFocusInWindow();

		Animation.init(this);
	}

	public static void main(String[] args) throws InterruptedException
	{
		JFrame frame = new JFrame("TicTac");
		frame.getContentPane().setBackground(Color.black);

		Main game = new Main();
		frame.add(game);

		frame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(400,400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer fps_counter = new Timer();
		Timer cap_counter = new Timer();
		Timer delta_counter = new Timer();

		int frame_rate = 120;

		fps_counter.start();
		delta_counter.start();

		while(true){
			cap_counter.start();
		
			//Calculate Framerate
			float current_fps = fps_counter.getTime();
			float avg_fps = (frame_rate / (current_fps / 1000));
			//System.out.println("AVG FPS: " + avg_fps);

			//game.invalidate();
			game.events.update();
			game.repaint();
			
			long current_time = cap_counter.getTime();
			if(current_time < SCREEN_TICKS){
				Thread.sleep(SCREEN_TICKS - current_time);
			}

			step_timer = delta_counter.getTime();
			step_timer /= 1000;

			delta_counter.start();
			frame_rate++;
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		//Clear the Screen
		g.setColor(Color.BLACK);
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		game_board.draw(g2d);
		events.draw(g2d);




	}

}