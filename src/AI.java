package tiko.game;

import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

import java.util.concurrent.ThreadLocalRandom;


public class AI
{
	Board game;
	ID self = ID.Naught;

	public boolean active;

	int[] values = new int[9];

	int difficulty = 0;

	public AI(Board game)
	{
		this.game = game;
	}

	int start()
	{
		//Check if its the AI's Turn
		if(game.turn == self && game.winner == ID.Empty && active){
			int retvalue = 0;

			//Recurse througha all possible choices
			for(int i = 0; i < 9; i++){
				if(game.map[i].id == ID.Empty){
					values[i] = recurse(game.map, i, difficulty, self);
				}
				else{
					values[i] = -1000000;
				}

				//Find which option has the best chance for winning
				if(values[i] > values[retvalue]){
					retvalue = i;
				}
				if(values[i] == values[retvalue]){
					int randint = ThreadLocalRandom.current().nextInt(0,2);
					if(randint == 0){retvalue = 0;}
				}
			}

			//Check if the enemy is about to win.
			for(int i = 0; i < 9; i++){
				if(game.map[i].id == ID.Empty){
					game.map[i].id = ID.Cross;
					if(scan(game.map)){retvalue = i;}

					game.map[i].id = ID.Naught;
					if(scan(game.map)){
						retvalue = i;
						game.map[i].id = ID.Empty;
						break;
					}

					game.map[i].id = ID.Empty;
				}
			}
			if(game.map[retvalue].id != ID.Empty){return -1;}
			
			return retvalue;
		}

		return -1;
	}

	int recurse(Object[] board, int place, int level, ID turn)
	{
		//Init Variables To Use
		Object[] temp = new Object[9];
		int value = -1;
		level--;
		for(int i = 0; i < 9; i++){temp[i] = new Object(board[i]);}

		//Put down object in grid and scans if won
		temp[place].id = turn;
		if(scan(temp)){
			if(turn == self){return 3;}
			else{return -3;}
		}
		else{
			//Check if its reached its level limit
			if(level <= 0){return 0;}

			//Check if theres any empty spaces and recurse
			for(int i = 0; i < 9; i++){
				if(temp[i].id == ID.Empty){
					ID newturn = ID.Cross;
					if(turn == ID.Cross){newturn = ID.Naught;}
					else{newturn = ID.Cross;}
					value += recurse(temp, i, level, newturn);
				}
			}

			//Check if Board is full and no Winners
			for(int i = 0; i < 9; i++){
				if(temp[i].id == ID.Empty){
					break;
				}
				if(i == 8){return -2;}
			}
		}

		//Returns Value of Recursion
		return value;
	}


	boolean scan(Object[] board)
	{
		//Scans Horizontally and Vertically
		for(int i = 0; i < 3; i++){
			if(board[0 * 3 + i].id == board[1 * 3 + i].id && board[0 * 3 + i].id == board[2 * 3 + i].id && board[0 * 3 + i].id != ID.Empty){return true;}
			if(board[i * 3 + 0].id == board[i * 3 + 1].id && board[i * 3 + 0].id == board[i * 3 + 2].id && board[i * 3 + 0].id != ID.Empty){return true;}
		}

		if(board[0].id == board[4].id && board[0].id == board[8].id && board[0].id != ID.Empty){return true;}
		if(board[2].id == board[4].id && board[2].id == board[6].id && board[2].id != ID.Empty){return true;}
		return false;
	}

	void draw(Graphics2D _g2d)
	{

	}

}