package socCli;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Cli implements Runnable {

	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private ReadInput read;
	private WriteOutput write;

	private Thread reader;
	private Thread writer;

	private String hostName = "503-A107-20";
	private int portNumber = 3434;

	public final static int HEIGHT = 500;
	public final static int WIDTH = 500;

	private JFrame f = new JFrame();
	private JPanel p = new JPanel(new BorderLayout());
	// caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	private Dimension d = new Dimension(WIDTH, HEIGHT);

	public static void main(String[] args) {
		Thread th = new Thread(new Cli());
		th.setName("Main Thread");
		th.start();
	}
	
	public Cli() {
		hostName = JOptionPane.showInputDialog("Enter IP", null);
	}

	@Override
	public void run() {
		// Scanner s = new Scanner(System.in);
		// System.out.println("Enter User Name");
		// s.close();
		boolean connectionFailed;
		f.add(p);
		p.setPreferredSize(d);
		p.setMinimumSize(d);
		p.setMaximumSize(d);
		p.setToolTipText("::");
		p.setName("hello");
		f.pack();
		p.setVisible(true);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setVisible(true);
		do {
			connectionFailed = false;
		try {
			connection = new Socket(hostName, portNumber);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			connectionFailed = true;
		} catch (IOException e) {
			e.printStackTrace();
			connectionFailed = true;
		}

		if (!connectionFailed) {
			try {
				out = new ObjectOutputStream(connection.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				connectionFailed = true;
			}

		}

		if (!connectionFailed) {
			try {
				in = new ObjectInputStream(connection.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
				connectionFailed = true;
			}

		}
		if (!connectionFailed) {

			write = new WriteOutput(out);
			writer = new Thread(write);
			writer.setName("Write Thread");
			writer.start();

			read = new ReadInput(in);
			reader = new Thread(read);
			reader.setName("Read Thread");
			reader.start();

			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					write.disconnect();
				}
			});

		}
		} while(connectionFailed);

	}
}
