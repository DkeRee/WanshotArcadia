import java.awt.*;

public class Player extends Tank {	
	private boolean W = false;
	private boolean A = false;
	private boolean S = false;
	private boolean D = false;
	
	private Color headlightColor = new Color(254, 231, 92, 100);
	
	private int mouseX = WanshotModel.WIDTH / 2;
	private int mouseY = WanshotModel.HEIGHT / 2;
	
	public Player(int x, int y) {
		super(
			x,
			y,
			0,
			100,
			WanshotModel.degreesToRadians(180),
			-5,
			5,
			-3,
			Color.decode("#224ACF"),
			Color.decode("#1E42B8"),
			Color.decode("#0101BA")
		);
	}
	
	public void shoot() {
		super.shoot(Shell.REGULAR_SHELL_SPEED);
	}
	
	public void update() {
		//update based only x/y coords + rotation based off of input
		if (W) {
			super.x += super.xInc;
			super.y += super.yInc;
		}
		
		if (S) {
			super.x -= super.xInc;
			super.y -= super.yInc;
		}
		
		if (A) {
			super.angle -= super.rotationInc;
		}
		
		if (D) {
			super.angle += super.rotationInc;
		}
						
		super.turretAngle = Math.atan2((double)this.mouseY - super.centerY, (double)this.mouseX - super.centerX);
		
		//update tank body
		super.update();
	}
	
	public void updateKey(int keyCode, boolean change) {
		switch (keyCode) {
			case WanshotController.W:
				W = change;
				break;
			case WanshotController.A:
				A = change;
				break;
			case WanshotController.S:
				S = change;
				break;
			case WanshotController.D:
				D = change;
				break;
		}
	}
	
	public void updateMouseCoords(int x, int y) {
		this.mouseX = x;
		this.mouseY = y;
	}
	
	public void render(Graphics2D ctx) {
		ctx.setColor(this.headlightColor);
		ctx.fillOval((int)(this.centerX + Math.cos(super.angle) * 20) - Tank.HEIGHT / 2, (int)(this.centerY + Math.sin(super.angle) * 20) - Tank.HEIGHT / 2, Tank.HEIGHT, Tank.HEIGHT);
		
		super.render(ctx);
	}
}