import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;


public class SocketManager implements Runnable{
	int port = 3434;
	String hostname = "50";
	ServerSocket ss;
	Socket client;
	
	@Override
	public void run() {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(" Failed to initialize Server!");
			System.exit(1);
		}
		
		try {
			client = ss.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = RatWindow.getWindow();
		SocketThread st = new SocketThread(client);
		Thread sThread = new Thread(st);
		sThread.start();
		
	}

}
			