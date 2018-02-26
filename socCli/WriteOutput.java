package socCli;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

public class WriteOutput implements Runnable {
	private ObjectOutputStream out;
	private Robot r;
	public WriteOutput(ObjectOutputStream out) {
		this.out = out;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		
	}

	@Override
	public void run() {
		while (true) {
			
			BufferedImage image =  r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "jpg", ba);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			try {
				out.writeObject(ba.toByteArray());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
