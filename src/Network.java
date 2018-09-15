package tiko.game;

import java.net.*;
import java.io.*;

public class Network extends Thread implements Runnable
{
	public int port;
	public DatagramSocket connection;

	public Connection_Type conn_type = Connection_Type.NONE;
	public Connection_State conn_state;
	public Button parent;

	public byte[] recv_buffer = new byte[256];
	public byte[] send_buffer = new byte[256];
	public DatagramPacket recv_packet = new DatagramPacket(recv_buffer, recv_buffer.length); 
	public DatagramPacket send_packet = null;

	public InetAddress address;

	public long time;

	public Network(Button parent)
	{
		this.parent = parent;
		//connection new Socket(192.168.0.14, 774);
	}

	public void getBoardInfo()
	{
		
	}

	public void run()
	{
		System.out.println("IN");
		if(parent.host_btn.active){conn_type = Connection_Type.HOST;}
		else if(parent.connect_btn.active){conn_type = Connection_Type.CLIENT;}

		networkMainLoop(conn_type);
	}
	
	public void networkMainLoop(Connection_Type _type)
	{
		conn_type = _type;

		port = Integer.parseInt(parent.port_string);
		if(conn_type == Connection_Type.HOST){
			conn_state = Connection_State.WAITING;
			try{connection = new DatagramSocket(port);}
			catch(SocketException e){
				e.printStackTrace();
				parent.network_active = CloseNetwork();
			}
		}

		if(conn_type == Connection_Type.CLIENT){
			conn_state = Connection_State.CONNECTING;
			try{connection = new DatagramSocket();}
			catch(SocketException e){
				e.printStackTrace();
				parent.network_active = CloseNetwork();
			}
			try{address = InetAddress.getByName(parent.ip_string);}
			catch(UnknownHostException e){
				e.printStackTrace();
				parent.network_active = CloseNetwork();
			}
			send_packet = new DatagramPacket(send_buffer, send_buffer.length, address, port);
		}

		try{connection.setSoTimeout(100);}
		catch(SocketException e){
			e.printStackTrace();
			parent.network_active = CloseNetwork();
		}


		//if(conn_type)

		while(parent.network_active){
			if(conn_state == Connection_State.CONNECTING){
				time += parent.game.step_timer;
				if((time / 1000) > 1.0){
					parent.network_active = CloseNetwork();
				}
				try{connection.send(send_packet);}
				catch(IOException e){
					e.printStackTrace();
					parent.network_active = CloseNetwork();
				}
				System.out.println("TIME: " + time);
			}

			System.out.println("TEST 1");

			if(conn_state == Connection_State.WAITING){

			}

			while(true){
				try{connection.receive(recv_packet);}
				catch(IOException e){break;}
			}

			System.out.println("CONDITION: " + parent.network_active);
		}
	}

	public boolean CloseNetwork()
	{
		if(conn_state == Connection_State.CONNECTED){connection.disconnect();}
		conn_type = Connection_Type.NONE;
		conn_state = Connection_State.NONE;
		connection.close();

		System.out.println("CLOSING NETWORK");

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