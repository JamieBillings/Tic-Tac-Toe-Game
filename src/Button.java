 package tiko.game;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.Rectangle;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.MouseInfo;

import java.util.Scanner;

import java.io.*;

public class Button implements MouseListener, KeyListener
{
	static public Main game;

	BaseButton reset_btn 				= new BaseButton(		  0, 256,  96, 32, 		 10, 206,  96, 32);
	BaseButton forfeit_btn 				= new BaseButton(	 	 96, 256,  96, 32, 		 10, 206,  96, 32);
	BaseButton easy_btn 				= new BaseButton(		192, 256,  96, 32, 		116, 206,  96, 32);
	BaseButton normal_btn 				= new BaseButton(		288, 256,  96, 32, 		116, 248,  96, 32);
	BaseButton hard_btn 				= new BaseButton(		  0, 288,  96, 32, 		116, 292,  96, 32);
	BaseButton online_btn 				= new BaseButton(		 96, 288,  96, 32, 		 10, 290,  96, 32);
	BaseButton host_btn 				= new BaseButton(		192, 288,  96, 32, 		116, 206,  96, 32);
	BaseButton connect_btn 				= new BaseButton(		288, 288,  96, 32, 		116, 248,  96, 32);
	BaseButton disconnect_btn 			= new BaseButton(	      0, 320,  96, 32, 		  0,   0,   0,  0);
	BaseButton start_btn 				= new BaseButton(		 96, 320,  96, 32, 		116, 292,  96, 32);
	BaseButton bot_btn 					= new BaseButton(		192, 320,  96, 32, 		 10, 248,  96, 32);
	BaseButton network_inputbox_ip 		= new BaseButton(  		  0, 352,  96, 32,		222, 206, 154, 32);
	BaseButton network_inputbox_port 	= new BaseButton(  		  0, 352,  96, 32,		222, 248, 154, 32);

	public Button(Main game){
		this.game = game;
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void keyPressed(KeyEvent e){}	
	public void keyTyped(KeyEvent e){}
	
	private String ip_string = new String();
	private String port_string = new String();

	public boolean network_active = false;

	public void keyReleased(KeyEvent e)
	{
		System.out.println(e.getKeyCode());
		//System.out.println(e.getKeyCode());
		if(network_inputbox_port.active){
			if(e.getKeyCode() == 8){
				if(port_string.length() > 0){port_string = port_string.substring(0, port_string.length() -1);}
			}
			else if(e.getKeyCode() >= 48 && e.getKeyCode() <= 57 || e.getKeyCode() >= 97 && e.getKeyCode() <= 105){
				port_string += e.getKeyChar();
			}
			System.out.println(port_string);

		}

		if(network_inputbox_ip.active){
			if(e.getKeyCode() == 8){
				if(ip_string.length() > 0){ip_string = ip_string.substring(0, ip_string.length() -1);}
			}
			else{ip_string += e.getKeyChar();}
			System.out.println(ip_string);
		}
	}

	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();

		if(reset_btn.Collision(mx,my)){reset_btn.state = BtnState.Push;}
		if(online_btn.Collision(mx,my)){online_btn.state = BtnState.Push;}
		if(online_btn.active){
			if(host_btn.Collision(mx,my)){online_btn.state = BtnState.Push;}
			if(connect_btn.Collision(mx,my)){online_btn.state = BtnState.Push;}
			if(start_btn.Collision(mx,my)){online_btn.state = BtnState.Push;}
		}
		if(bot_btn.Collision(mx,my)){bot_btn.state = BtnState.Push;}
		if(bot_btn.active){
			if(easy_btn.Collision(mx,my)){easy_btn.state = BtnState.Push;}
			if(normal_btn.Collision(mx,my)){normal_btn.state = BtnState.Push;}
			if(hard_btn.Collision(mx,my)){hard_btn.state = BtnState.Push;}
		}
	}

    public void mouseReleased(MouseEvent e) 
    {
		int mx = e.getX();
		int my = e.getY();

		Rectangle rect = new Rectangle(game.game_board.grid_dst);
		rect.width /= 3;
		rect.height /= 3;

		if(game.game_board.winner == ID.Empty){
			for(int y = 0; y < 3; y++){
				for(int x = 0; x < 3; x++){
					if(mCollision(mx, my, rect.x + (rect.width * x), rect.y + (rect.height * y) ,rect.width, rect.height)){
						game.game_board.placeTile(y * 3 + x);
					}
				}
			}
		}

		if(reset_btn.Collision(mx,my)){
			reset_btn.state = BtnState.None;
			game.game_board.reset();
		}

		if(bot_btn.Collision(mx,my)){
			bot_btn.active = !bot_btn.active;
			online_btn.active = false;
			bot_btn.state = BtnState.None;
		}

		if(bot_btn.active){
			if(easy_btn.Collision(mx,my)){
				easy_btn.active = !easy_btn.active;
				easy_btn.state = BtnState.None;
				normal_btn.active = false;
				hard_btn.active = false;
				game.game_board.reset();
			}

			if(normal_btn.Collision(mx,my)){
				easy_btn.active = false;
				normal_btn.active = !normal_btn.active;
				normal_btn.state = BtnState.None;
				hard_btn.active = false;
				game.game_board.reset();
			}

			if(hard_btn.Collision(mx,my)){
				easy_btn.active = false;
				normal_btn.active = false;
				hard_btn.active = !hard_btn.active;
				hard_btn.state = BtnState.None;
				game.game_board.reset();
			}
		}else{
			easy_btn.active = false;
			easy_btn.state = BtnState.None;
			normal_btn.active = false;
			normal_btn.state = BtnState.None;
			hard_btn.active = false;
			hard_btn.state = BtnState.None;
		}

		if(online_btn.Collision(mx,my)){
			online_btn.active = !online_btn.active;
			bot_btn.active = false;
			online_btn.state = BtnState.None;
		}

		if(online_btn.active)
		{
			if(host_btn.Collision(mx,my)){
				connect_btn.active = false;
				host_btn.active = !host_btn.active;
				network_inputbox_port.active = false;
				network_inputbox_ip.active = false;
			}

			if(connect_btn.Collision(mx,my)){
				host_btn.active = false;
				connect_btn.active = !connect_btn.active;
				network_inputbox_ip.active = false;
				network_inputbox_port.active = false;
			}

			if(start_btn.Collision(mx,my)){
				if(host_btn.active){
					System.out.println("Server Start");
					try{
						Thread server = new Server();
						server.start();
					} catch(IOException er){
						er.printStackTrace();
					}
				}
			}

			if(host_btn.active){
				if(network_inputbox_port.Collision(mx,my)){
					network_inputbox_port.active = !network_inputbox_port.active;
				}
			}

			if(connect_btn.active){
				if(network_inputbox_port.Collision(mx,my)){
					network_inputbox_port.active = !network_inputbox_port.active;
					network_inputbox_ip.active = false;
				}


				if(network_inputbox_ip.Collision(mx,my)){
					network_inputbox_ip.active = !network_inputbox_ip.active;
					network_inputbox_port.active = false;
				}
			}
		}
    }

    public void update()
    {
    	PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();

		int mx = ((int) p.getX() - ((int) game.getLocationOnScreen().getX()));
		int my = ((int) p.getY() - ((int) game.getLocationOnScreen().getY()));

		//RESET BUTTON
		if(reset_btn.Collision(mx,my)){
			if(reset_btn.state == BtnState.None){reset_btn.state = BtnState.Hover;}
		}else{reset_btn.state = BtnState.None;}

		//BOT BUTTON
		if(bot_btn.Collision(mx,my)){
			if(bot_btn.state == BtnState.None){bot_btn.state = BtnState.Hover;}
		}else{bot_btn.state = BtnState.None;}

		if(bot_btn.active){
			//EASY BUTTON
			if(easy_btn.Collision(mx,my)){
				if(easy_btn.state == BtnState.None){easy_btn.state = BtnState.Hover;}
			}else{easy_btn.state = BtnState.None;}
			if(easy_btn.active){
				game.game_board.bot.active = true;
				game.game_board.bot.difficulty = 2;
			}

			//NORMAL BUTTON
			if(normal_btn.Collision(mx,my)){
				if(normal_btn.state == BtnState.None){normal_btn.state = BtnState.Hover;}
			}else{normal_btn.state = BtnState.None;}
			if(normal_btn.active){
				game.game_board.bot.active = true;
				game.game_board.bot.difficulty = 8;
			}

			//HARD BUTTON
			if(hard_btn.Collision(mx,my)){
				if(hard_btn.state == BtnState.None){hard_btn.state = BtnState.Hover;}
			}else{hard_btn.state = BtnState.None;}
			if(hard_btn.active){
				game.game_board.bot.active = true;
				game.game_board.bot.difficulty = 20;
			}
		}

		//ONLINE BUTTON
		if(online_btn.Collision(mx,my)){
			if(online_btn.state == BtnState.None){online_btn.state = BtnState.Hover;}
		}else{online_btn.state = BtnState.None;}

		if(online_btn.active){
			//HOST BUTTON
			if(host_btn.Collision(mx,my)){
				if(host_btn.state == BtnState.None){host_btn.state = BtnState.Hover;}
			}else{host_btn.state = BtnState.None;}

			//CONNECT BUTTON
			if(connect_btn.Collision(mx,my)){
				if(connect_btn.state == BtnState.None){connect_btn.state = BtnState.Hover;}
			}else{connect_btn.state = BtnState.None;}

			//START BUTTON
			if(start_btn.Collision(mx,my)){
				if(start_btn.state == BtnState.None){start_btn.state = BtnState.Hover;}
			}else{start_btn.state = BtnState.None;}
		}
    }

    public void draw(Graphics2D _g2d)
    {
    	reset_btn.active = true;
    	reset_btn.draw(_g2d);
    	bot_btn.draw(_g2d);
    	if(bot_btn.active){
    		easy_btn.draw(_g2d);
    		normal_btn.draw(_g2d);
    		hard_btn.draw(_g2d);
    	}
    	online_btn.draw(_g2d);
    	if(online_btn.active){
    		host_btn.draw(_g2d);
    		connect_btn.draw(_g2d);
    		start_btn.draw(_g2d);

    		if(host_btn.active || connect_btn.active){
    			_g2d.setColor(Color.WHITE);
    			if(host_btn.active){
    				if(port_string.length() == 0){_g2d.drawString("Port", 225, 263);}
    				_g2d.drawString(port_string, 225, 263);
    			}
    			if(connect_btn.active){
    				if(ip_string.length() == 0){_g2d.drawString("IP", 225, 226);}
    				if(port_string.length() == 0){_g2d.drawString("Port", 225, 263);}
    				_g2d.drawString(ip_string, 225, 226);
    				_g2d.drawString(port_string, 225, 263);
    				network_inputbox_ip.draw(_g2d);
    			}
    			network_inputbox_port.draw(_g2d);
    			_g2d.setColor(Color.BLACK);
    		}
    	}
    }

    static Boolean mCollision(int mx, int my, int pos_x, int pos_y, int pos_w, int pos_h)
    {
    	if((mx > pos_x) && (mx < pos_x + pos_w)){
    		if((my > pos_y) && (my < pos_y + pos_h)){
    			return true;
    		}
    	}

    	return false;
    }

}

enum BtnState{None, Hover, Push};

class BaseButton
{
	public Rectangle src_pos;
	public Rectangle dst_pos;

	public BtnState state = BtnState.None; 
	public boolean active = false;

	boolean hover;

	BaseButton()
	{

	}

	BaseButton(int _sx, int _sy, int _sw, int _sh, int _dx, int _dy, int _dw, int _dh)
	{
		src_pos = new Rectangle(_sx, _sy, _sw, _sh);
		dst_pos = new Rectangle(_dx, _dy, _dw, _dh);
	}

	void setSrcXY(int x, int y)
	{
		src_pos.x = x;
		src_pos.y = y;
	}

	boolean Collision(int mx, int my)
	{
		if((mx > dst_pos.x) && (mx < dst_pos.x + dst_pos.width)){
    		if((my > dst_pos.y) && (my < dst_pos.y + dst_pos.height)){
    			return true;
    		}
    	}

    	return false;
	}

	void draw(Graphics2D _g2d)
	{
		Rectangle temp = new Rectangle(dst_pos);

		if(state == BtnState.Hover){
			temp.x -= 2;
			temp.y -= 2;
			temp.width += 4;
			temp.height += 4;
		}

		if(state == BtnState.Push)
		{
			temp.x += 2;
			temp.y += 2;
			temp.width -= 4;
			temp.height -= 4;
		}

		Button.game.game_board.sheet.setSrcPos(src_pos);
		Button.game.game_board.sheet.setDstPos(temp);
		Button.game.game_board.sheet.draw(_g2d);

		if(!active)
		{
			temp = new Rectangle(288,320,96,32);
			Button.game.game_board.sheet.setSrcPos(temp);
			Button.game.game_board.sheet.setDstPos(dst_pos);
			Button.game.game_board.sheet.draw(_g2d);
		}
	}



}