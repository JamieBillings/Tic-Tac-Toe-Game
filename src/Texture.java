package tiko.game.tools.screen;

import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Rectangle;

import java.awt.Graphics2D;

public class Texture
{
	private int src_x;
	private int src_y;
	private int src_w;
	private int src_h;

	private int dst_x;
	private int dst_y;
	private int dst_w;
	private int dst_h;


	private BufferedImage image;

	public Texture(String _location)
	{	
		try{image = ImageIO.read(new File(_location));}
		catch(IOException e){
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void setSrcPos(char _char, int _int)
	{
		if(_char == 'x'){src_x = _int;}
		if(_char == 'y'){src_y = _int;}
		if(_char == 'w'){src_w = _int;}
		if(_char == 'h'){src_h = _int;}
	}

	public void setSrcPos(int _x, int _y, int _w, int _h)
	{
		src_x = _x;
		src_y = _y;
		src_w = _w;
		src_h = _h;
	}

	public void setSrcPos(Rectangle _rect)
	{
		src_x = _rect.x;
		src_y = _rect.y;
		src_w = _rect.width;
		src_h = _rect.height;
	}

	public void setDstPos(char _char, int _int)
	{
		if(_char == 'x'){dst_x = _int;}
		if(_char == 'y'){dst_y = _int;}
		if(_char == 'w'){dst_w = _int;}
		if(_char == 'h'){dst_h = _int;}
	}

	public void setDstPos(int _x, int _y, int _w, int _h)
	{
		dst_x = _x;
		dst_y = _y;
		dst_w = _w;
		dst_h = _h;
	}

	public void setDstPos(Rectangle _rect)
	{
		dst_x = _rect.x;
		dst_y = _rect.y;
		dst_w = _rect.width;
		dst_h = _rect.height;
	}

	public void draw(Graphics2D _g2d)
	{
		_g2d.drawImage(image, dst_x, dst_y, dst_w + dst_x, dst_h + dst_y, src_x, src_y, 
			src_w + src_x, src_h + src_y, null);
	}
}

