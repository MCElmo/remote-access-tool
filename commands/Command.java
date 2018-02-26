package commands;

import java.io.Serializable;


public class Command implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int x;
	private int y;

	public Command(String type, int x, int y) {
			this.type = type;
			this.x = x;
			this.y = y;		
	}
}
