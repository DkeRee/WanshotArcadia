import java.awt.Dimension;
import java.awt.Toolkit;

import javax.sound.sampled.*;
import javax.swing.*;

import com.sun.tools.javac.Main;

public class WanshotMain {
	public static void main(String[] args) {
		JFrame window = new JFrame("Wanshot");
		WanshotRunner runner = new WanshotRunner();
		
        window.setContentPane(runner.getView());
        window.pack();  
        //****this centers the window on the users screen
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( (screensize.width - WanshotModel.WIDTH)/2,
                (screensize.height - WanshotModel.HEIGHT)/80 );
		window.setSize(WanshotModel.WIDTH + 15, WanshotModel.HEIGHT + 35);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);  
        window.setVisible(true);
                
		runner.start();
	}
	
	public static synchronized void playSound(String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
						Main.class.getClassLoader().getResource(url)
					);
					
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					
					if (url.equals("tankMovement.wav")) {
						FloatControl gainControl = 
							    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
							gainControl.setValue(-25.0f);
					}
					
					clip.start();
				} catch (Exception e) {
					System.out.println("Someting went wrong...");
				}
			}
		}).start();
	}
}