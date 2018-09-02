package tiko.game;

import java.net.*;
import java.io.*;

public class Network extends Thread
{
	public String port;
	public DatagramSocket connection;

	public Connection_Type conn_type;
	public Connection_State conn_state;
	public Button parent;

	public long time;

	public Network(Button parent)
	{
		this.parent = parent;
		//connection new Socket(192.168.0.14, 774);
	}

	public void getBoardInfo()
	{
		
	}
	
	public void netWorkMainLoop(Connection_Type _conn_type)
	{
		conn_type = _conn_type;

		//if(conn_type)

		while(parent.network_active){
			if(conn_state == Connection_State.CONNECTING){
				time += parent.game.step_timer;
				if((time / 1000) > 1.0){
					parent.network_active = CloseNetwork();
				}
			}


		}
	}

	public boolean CloseNetwork()
	{
		if(conn_state == Connection_State.CONNECTED){connection.disconnect();}
		conn_type = Connection_Type.NONE;
		conn_state = Connection_State.NONE;
		connection.close();

		return false;
	}

}

enum Connection_Type {
	NONE,
	HOST,
	CLIENT
}

enum Connection_State {
	NONE,
	CONNECTING,	//Client
	WAITING,	//Server
	CONNECTED
}