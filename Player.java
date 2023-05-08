import java.awt.Color;

public class Player extends Tank {	
	private boolean W = false;
	private boolean A = false;
	private boolean S = false;
	private boolean D = false;
	
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
		super.shoot(Shell.ULTRA_MISSLE_SPEED);
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
		
		double turretAngle = Math.atan2((double)this.mouseY - super.centerY, (double)this.mouseX - super.centerX);
		if (turretAngle < 0) {
			turretAngle = 2 * Math.PI - Math.abs(turretAngle);
		}
				
		super.turretAngle = turretAngle;
		
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
}