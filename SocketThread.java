import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SocketThread implements Runnable{
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public SocketThread(Socket client) {
		this.client = client;
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch(IOException e)
		{
			System.err.println("Failed to initialize Object Streams!");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread reader = new Thread(new Reader(in));
		Thread writer = new Thread(new Writer(out));
		reader.start();
		writer.start();
	}
}
