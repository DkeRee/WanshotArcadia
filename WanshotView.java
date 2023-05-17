import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class WanshotView extends JPanel {
	private WanshotModel model;
	
	//Important Consts
	static final Color BACKGROUND_COLOR_STRONG = Color.decode("#C2995D");
	static final Color BACKGROUND_COLOR_WEAK = Color.decode("#FFDFA8");
	static final Color SHADOW = new Color(0, 0, 0, 100);
	
	static AffineTransform oldTransform = null;
	
	public WanshotView(WanshotModel model) {
		this.model = model;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, WanshotModel.WIDTH, WanshotModel.HEIGHT);
		Graphics2D ctx = (Graphics2D)g;
        ctx.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);		

        GradientPaint gp = new GradientPaint(0, 0, WanshotView.BACKGROUND_COLOR_STRONG, WanshotModel.WIDTH / 2, WanshotModel.HEIGHT / 2, WanshotView.BACKGROUND_COLOR_WEAK);
		
		ctx.setPaint(gp);
		g.fillRect(0, 0, WanshotModel.WIDTH, WanshotModel.HEIGHT);
		
		WanshotView.oldTransform = ctx.getTransform();
		
		//Render Background Text!!!
		
		//AA!!!
	    ctx.setRenderingHint(
	    	         RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	    
	    if (this.model.getManager() != null) {
		    Font font = new Font("monospace", Font.BOLD, 96);
		    ctx.setFont(font);
		    ctx.setColor(Color.decode("#ffaa2e"));
			ctx.drawString("WANSHOT", 210, 300);
			
		    font = new Font("monospace", Font.BOLD, 70);
		    ctx.setFont(font);
			ctx.drawString("Level " + this.model.getManager().getLevel(), 330, 395);
			
		    font = new Font("monospace", Font.BOLD, 40);
		    ctx.setFont(font);
		    ctx.drawString("Wave " + this.model.getManager().getWave() + "/" + this.model.getManager().getMaxWave(), 365, 470);	
	    }

		//Render Shadows
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			WanshotModel.tiles.get(i).renderShadow(ctx);
		}
		
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			WanshotModel.shells.get(i).renderShadow(ctx);
		}
		
		//Render Assets				
		for (int i = 0; i < WanshotModel.particles.size(); i++) {
			Particle particle = WanshotModel.particles.get(i);
			if (particle != null) {
				particle.render(ctx);
			}
		}
		
		for (int i = 0; i < WanshotModel.tanks.size(); i++) {
			WanshotModel.tanks.get(i).render(ctx);
		}
		
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			WanshotModel.shells.get(i).render(ctx);
		}
				
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			WanshotModel.tiles.get(i).render(ctx);
		}
		
		if (this.model.getScoreKeeper().shouldUpdate()) {
			this.model.getScoreKeeper().render(ctx);
		}
	}
}