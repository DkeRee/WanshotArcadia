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

		//Render Shadows
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			WanshotModel.tiles.get(i).renderShadow(ctx);
		}
		
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			WanshotModel.shells.get(i).renderShadow(ctx);
		}
		
		//Render Assets
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			WanshotModel.shells.get(i).render(ctx);
		}
		
		for (int i = 0; i < WanshotModel.tanks.size(); i++) {
			WanshotModel.tanks.get(i).render(ctx);
		}
				
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			WanshotModel.tiles.get(i).render(ctx);
		}
	}
}