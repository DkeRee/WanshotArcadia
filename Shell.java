import java.awt.*;

public class Shell extends Parallelogram {
	//SHELL CONSTANT INFO
	static final int WIDTH = 9;
	static final int HEIGHT = 6;
	static final int REGULAR_SHELL_SPEED = 250;
	static final Color REGULAR_SHELL_COLOR = Color.decode("#D3D3D3");
	
	//SHELL INSTANCE INFO
	double x;
	double y;
	double centerX;
	double centerY;
	double angle;
	int speed;
	Tank tankRef;
	boolean delete = false;
	boolean peace = true;
	int ricochet = 0;
	Color color;
	
	public Shell(double x, double y, double angle, int speed, Tank tankRef) {
		super((int)x, (int)y, Shell.WIDTH, Shell.HEIGHT, angle);
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
		this.tankRef = tankRef;
		
		switch (speed) {
			case Shell.REGULAR_SHELL_SPEED:
				this.color = Shell.REGULAR_SHELL_COLOR;
				break;
		}
	}
	
	public boolean bounceLeft() {
		double newDirection = Math.abs(Math.PI - this.angle) % (2 * Math.PI);
		if (newDirection < Math.PI / 2 || newDirection > Math.PI * (3.0 / 2.0)) {
			this.angle = Math.PI - this.angle;
			return true;
		}
		
		return false;
	}
	
	public boolean bounceRight() {
		double newDirection = Math.abs(Math.PI - this.angle) % (2 * Math.PI);
		if (newDirection >= Math.PI / 2 || newDirection <= Math.PI * (3.0 / 2.0)) {
			this.angle = Math.PI - this.angle;
			return true;
		}
		
		return false;
	}
	
	public boolean bounceBottom() {
		double newDirection = (2 * Math.PI - this.angle) % (2 * Math.PI);
		if (newDirection < Math.PI) {
			this.angle = 2 * Math.PI - this.angle;
			return true;
		}
		
		return false;
	}
	
	public boolean bounceTop() {
		double newDirection = (2 * Math.PI - this.angle) % (2 * Math.PI);
		if (newDirection >= Math.PI) {
			this.angle = 2 * Math.PI - this.angle;
			this.angle %= 2 * Math.PI;
			return true;
		}
		
		return false;
	}
	
	public void shellWithTile() {
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			Tile tile = WanshotModel.tiles.get(i);
			
			//initial collision detection
			if (super.sat_parallelogram(tile)) {
				boolean bounced = false;
				
				double dx = this.centerX - tile.centerX;
				double dy = this.centerY - tile.centerY;
				double meanWidth = (Shell.WIDTH + Tile.WIDTH) / 2;
				double meanHeight = (Shell.HEIGHT + Tile.HEIGHT) / 2;
				double crossWidth = meanWidth * dy;
				double crossHeight = meanHeight * dx;
				
				boolean posXDir = Math.cos(this.angle) >= 0;
				boolean posYDir = Math.sin(this.angle) >= 0;
				
				if (crossWidth > crossHeight) {
					if (crossWidth > -crossHeight) {
						//bottom
						if (!posYDir) {
							bounced = this.bounceBottom();
						}
						
						if (!bounced) {
							bounced = this.bounceRight();
						}
					} else {
						//right
						if (posXDir) {
							bounced = this.bounceRight();
						}
					}
				} else {
					if (crossWidth > -crossHeight) {
						//left
						if (!posXDir) {
							bounced = this.bounceLeft();
						}
					} else {
						//top
						if (posYDir) {
							bounced = this.bounceTop();
						}
					}
				}
				
				if (bounced) {
					this.ricochet++;
					
					this.peace = false;
					
					//once a shell hits a tile on one hit, wait till next tick before checking collision again or double collisions could happen
					break;	
				}
			}
		}
	}
	
	public void update() {
		if (WanshotModel.isPlayerAlive()) {
			super.update((int)this.x, (int)this.y, this.angle);
			this.x += this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
			this.y += this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
			
			this.centerX = this.x + Shell.WIDTH / 2;
			this.centerY = this.y + Shell.HEIGHT / 2;
			
			//Collisions
			this.shellWithTile();
			
			//Mark shells for deletion
			switch (this.speed) {
				case Shell.REGULAR_SHELL_SPEED:
					if (this.ricochet >= 2) {
						this.delete = true;
						this.tankRef.shellShot--;
					}
					break;
			}
		}
	}
	
	public void renderShadow(Graphics2D ctx) {
		ctx.rotate(this.angle, ((int)this.x - 5) + Shell.WIDTH / 2, ((int)this.y + (Shell.HEIGHT / 7)) + Shell.HEIGHT / 2);
	
		ctx.setColor(WanshotView.SHADOW);
		Rectangle shadow = new Rectangle((int)this.x - 5, (int)this.y + (Shell.HEIGHT / 7), Shell.WIDTH, Shell.HEIGHT);
		ctx.fill(shadow);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
	
	public void render(Graphics2D ctx) {
		ctx.rotate(this.angle, this.centerX, this.centerY);
		
		ctx.setColor(this.color);
		Rectangle body = new Rectangle((int)this.x, (int)this.y, Shell.WIDTH, Shell.HEIGHT);
		
		ctx.draw(body);
		ctx.fill(body);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}