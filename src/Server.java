package tiko.game;

import java.net.*;
import java.io.*;

public class Server extends Thread
{
	public ServerSocket serversock;

	public Server() throws IOException
	{
			serversock = new ServerSocket(774);
			serversock.setSoTimeout(10000);
	}

	public void run(){
		while(true){
			System.out.println("Waiting for Client");
			try{
				serversock.accept();
				System.out.println("Connection Found!");

				serversock.close();
			} catch(SocketTimeoutException s){
				System.out.println("Socket timed out!");
				break;
			} catch(IOException e){
				e.printStackTrace();
				break;
			}
		}
	}

	public void Recieve()
	{
		//serversock.
	}

}