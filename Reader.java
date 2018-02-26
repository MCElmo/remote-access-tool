import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

public class Reader implements Runnable {
	private ObjectInputStream in;

	public Reader(ObjectInputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		BufferedImage image;
		byte[] ba;
		Object o;
		RatWindow window = RatWindow.getWindow();
		ByteArrayInputStream iS;
		while (true) {
			try {
				// System.out.println("Got in reader");
				o = in.readObject();
				ba = (byte[]) o;
				iS = new ByteArrayInputStream(ba);
				image = ImageIO.read(iS);
				window.image = image;
				window.imageHeight = image.getHeight();
				window.imageWidth = image.getWidth();
				window.draw();
				iS.close();
				image.flush();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
