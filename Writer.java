import java.io.IOException;
import java.io.ObjectOutputStream;

import commands.Command;

public class Writer implements Runnable {

	private ObjectOutputStream out;

	public Writer(ObjectOutputStream out) {
		this.out = out;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (RatWindow.getWindow().left_pressed) {
				System.out.println("Left Click Writer");
				Command com = new Command("click", 1, 0);
				try {
					out.writeObject(com);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RatWindow.getWindow().left_pressed = false;
			}
			if (RatWindow.getWindow().right_pressed) {
				System.out.println("Right Click Writer");
				Command com = new Command("click", 2, 0);
				try {
					out.writeObject(com);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RatWindow.getWindow().right_pressed = false;
			}
			if (RatWindow.getWindow().right_released) {
				Command com = new Command("release", 2, 0);
				try {
					out.writeObject(com);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				RatWindow.getWindow().right_released = false;
			}
			if (RatWindow.getWindow().left_released) {
				Command com = new Command("release", 1, 0);
				try {
					out.writeObject(com);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				RatWindow.getWindow().left_released = false;
			}
			if (RatWindow.getWindow().mouseChanged) {
				if(!RatWindow.getWindow().hiddenListen)
				{
			
					int sW = RatWindow.getWindow().canvas.getWidth();
					int sH = RatWindow.getWindow().canvas.getHeight();
					//System.out.println(RatWindow.getWindow().menuBar.getHeight());
					int iH = RatWindow.getWindow().imageHeight;
					int iW = RatWindow.getWindow().imageWidth;
					double wScaling = (double)iW/(double)sW;
					double hScaling = (double)iH/(double)sH;
					int x = RatWindow.getWindow().x;
					int y = RatWindow.getWindow().y - RatWindow.getWindow().menuBar.getHeight();
					Command c = new Command("move", (int)(x * wScaling), (int)(y * hScaling));
					try {
						out.writeObject(c);
						out.flush();
						// System.out.println("Sent object: " + c);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					RatWindow.getWindow().mouseChanged = false;
				}
			}
			if (RatWindow.getWindow().pressedCodes.size() > 0) {
				try {
					while (RatWindow.getWindow().pressedCodes.size() > 0) {
						int key = RatWindow.getWindow().pressedCodes.remove(0);
						Command c = new Command("kClicked", key, 0);
						try {
							out.writeObject(c);
							out.flush();
							// System.out.println("Sent object: " + c);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			if (RatWindow.getWindow().releasedCodes.size() > 0) {
				try {
					while (RatWindow.getWindow().releasedCodes.size() > 0) {
						int key = RatWindow.getWindow().releasedCodes.remove(0);
						Command c = new Command("kReleased", key, 0);
						try {
							out.writeObject(c);
							out.flush();
							// System.out.println("Sent object: " + c);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			if (RatWindow.getWindow().ripper) {
				Command c = new Command("rip", 0, 0);
				try {
					out.writeObject(c);
					out.flush();
					// System.out.println("Sent object: " + c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RatWindow.getWindow().ripper = false;
				RatWindow.getWindow().ripping = true;
			}
			if (RatWindow.getWindow().stoprip) {
				Command c = new Command("stopRip", 0, 0);
				try {
					out.writeObject(c);
					out.flush();
					// System.out.println("Sent object: " + c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RatWindow.getWindow().stoprip = false;
				RatWindow.getWindow().ripping = false;
			}
			if (RatWindow.getWindow().enableLock) {
				Command c = new Command("lock", 0, 0);
				try {
					out.writeObject(c);
					out.flush();
					// System.out.println("Sent object: " + c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RatWindow.getWindow().enableLock = false;
				RatWindow.getWindow().locked = true;
			}
			if (RatWindow.getWindow().stopLock) {
				Command c = new Command("unlock", 0, 0);
				try {
					out.writeObject(c);
					out.flush();
					// System.out.println("Sent object: " + c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RatWindow.getWindow().stopLock = false;
				RatWindow.getWindow().locked = false;
			}
			if(RatWindow.getWindow().hasCommand) {
				Command c = new Command("command:"+RatWindow.getWindow().commandText,0,0);
				try {
					out.writeObject(c);
					out.flush();
				}catch(IOException e) {
					e.printStackTrace();
				}
				RatWindow.getWindow().hasCommand = false;
			}
		}
	}

}
