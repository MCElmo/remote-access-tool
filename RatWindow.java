import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class RatWindow extends JFrame implements MouseListener,KeyListener, MouseMotionListener,MouseWheelListener{
	public boolean left_pressed = false;
	public boolean right_pressed = false;
	public boolean left_released = false;
	public boolean right_released = false;
	public int x = 0;
	public int y = 0;
	public Image image;
	public boolean mouseChanged = false;
	public RatCanvas canvas;
	public JPanel panel = new JPanel();
	public JMenuBar menuBar = new JMenuBar();
	public JMenu menu = new JMenu("Tools");
	public JMenuItem tc;
	public JMenuItem rip;
	public JMenuItem command;
	public JMenuItem hidden;
	public JMenuItem min;
	public boolean hiddenListen = true;
	public boolean ripping = false;
	public boolean ripper = false;
	public boolean stoprip = false;
	public boolean hasCommand = false;
	public String commandText = "";
	
	public int imageHeight = 0;
	public int imageWidth = 0;
	
	
	public boolean locked = false;
	public boolean enableLock = false;
	public boolean stopLock = true;
	public ArrayList<Integer> pressedCodes = new ArrayList<Integer>();
	public ArrayList<Integer> releasedCodes = new ArrayList<Integer>();
	private static RatWindow window;
	
	public RatWindow() {
		this.setName("Window");
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setUndecorated(true);
		this.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		min = new JMenuItem("Minimize");
		min.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		tc = new JMenuItem("Restrict Mouse");
		tc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                if(locked)
    			{
    				stopLock = true;
    				tc.setText("Restrict Mouse");
    				
    			}else
    			{
    				enableLock = true;
    				tc.setText("Unrestrict Mouse");
    			}
              
            }

        });
		
		rip = new JMenuItem("Activate Ripper");
		rip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                if(ripping)
    			{
    				stoprip = true;
    				rip.setText("Release the RIP!@!@!");
    			}else
    			{
    				ripper = true;
    				rip.setText("De-Activate Ripper!");
    			}
            }

        });
		
		command = new JMenuItem("Send Command");
		command.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				commandText = JOptionPane.showInputDialog("Enter Command:");
				hasCommand = true;
			}
			
		});
		
		hidden = new JMenuItem("Un-Hide Mouse Movements");
		hidden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 if(hiddenListen)
	    			{
	    			
	    				hidden.setText("Hide Mouse Movements");
	    			}else
	    			{
	    			
	    				hidden.setText("Un Hide Mouse Movements");
	    			}
				 hiddenListen = !hiddenListen;
				
			}
			
		});
		menuBar.setVisible(true);
		menu.setVisible(true);
		menu.add(tc);
		menu.add(rip);
		menu.add(command);
		menu.add(hidden);
		menuBar.add(menu);
		
		
		
		panel.setVisible(true);
		panel.setLayout(new BorderLayout());
		
		this.add(panel);
		canvas = new RatCanvas();
		canvas.setVisible(true);
		//canvas.setSize(this.getSize());
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addKeyListener(this);
		panel.add(canvas,BorderLayout.CENTER);
		panel.add(menuBar, BorderLayout.NORTH);
		this.validate();
	}
	
	public static RatWindow getWindow() {
		if(window == null)
		{
			window = new RatWindow();
		}
		return window;
	}
	
	public void draw () {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null)
		{
			System.out.println("Buffer was null");
			canvas.createBufferStrategy(3);
			bs = canvas.getBufferStrategy();
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 100, 100);
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
		g.dispose();
		//System.out.println("Drew image");
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseChanged = true;
		x = arg0.getLocationOnScreen().x;
		y = arg0.getLocationOnScreen().y;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseChanged = true;
		x = arg0.getLocationOnScreen().x;
		y = arg0.getLocationOnScreen().y;
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	//	System.out.println("Mouse pRESSED");
		
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			left_pressed = true;
			//System.out.println("Left Click");
		}else if(arg0.getButton() == MouseEvent.BUTTON3)
		{
			right_pressed = true;
			//System.out.println("Right Click");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
//System.out.println("Mouse Clicked");
		
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			left_released = true;
			//System.out.println("Left Rel");
		}else if(arg0.getButton() == MouseEvent.BUTTON3)
		{
			right_released = true;
			//System.out.println("Right Rel ");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		pressedCodes.add(arg0.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		releasedCodes.add(arg0.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
