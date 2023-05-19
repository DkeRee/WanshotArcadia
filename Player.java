import java.awt.*;

class Exhaust extends Particle {
	Color[] possibleColors = {Color.decode("#ED4245"), Color.decode("#FFA500"), Color.decode("#FFBF00")};	
	
	int side;
	int x;
	int y;
	double angle;
	int opacity = 250;
	int speed = 130;
	
	Color color = this.possibleColors[(int)(Math.random() * possibleColors.length)];
	
	public Exhaust(int side, int x, int y, double angle) {
		this.side = side;
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public void update() {
		this.x -= this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
		this.y -= this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
		this.opacity -= 200 * WanshotModel.deltaTime;
				
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		ctx.rotate(this.angle, this.x + this.side / 2, this.y + this.side / 2);
		ctx.setColor(this.color);
		
		Rectangle p = new Rectangle(this.x, this.y, this.side, this.side);
		ctx.draw(p);
		ctx.fill(p);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}

public class Player extends Tank {	
	private boolean W = false;
	private boolean A = false;
	private boolean S = false;
	private boolean D = false;
	
	private int exhaustDelay = -3;
	private int exhaustDelayCount = 0;	
	
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
	
	public void updateExhaust() {
		if (this.exhaustDelayCount < 0) {
			this.exhaustDelayCount++;
		} else {
			this.exhaustDelayCount = this.exhaustDelay;
			
			int heightOffset = Math.random() > 0.5 ? (int)(Math.random() * Tank.HEIGHT / 3) : -(int)(Math.random() * Tank.HEIGHT / 3);
			int randSide = (int)(Math.random() * 15);
			
			Exhaust p = new Exhaust(randSide, (int)(this.centerX - randSide / 2) + heightOffset, (int)(this.centerY - randSide / 2) + heightOffset, super.angle);
			WanshotModel.particles.add(p);
		}
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
		
		//update exhaust
		this.updateExhaust();
		
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