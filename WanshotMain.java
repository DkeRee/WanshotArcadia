import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

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
}