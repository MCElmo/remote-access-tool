import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;


public class Main{

	public static void main(String[] args) {
		Thread th = new Thread(new SocketManager());
		th.setName("Socket Manager");
		th.start();
	}

}
