import java.awt.*;

class MissleParticle extends Particle {
	Color[] possibleColors = {Color.decode("#ED4245"), Color.decode("#FFA500"), Color.decode("#FFBF00")};	
	
	static final int side = 12;
	int x;
	int y;
	int opacity = 100;
	int speed = 400;
	Color color = this.possibleColors[(int)(Math.random() * possibleColors.length)];
	double angle = WanshotModel.degreesToRadians(Math.random() * 360);
	
	public MissleParticle(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		this.x += this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
		this.y += this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
		this.opacity -= 350 * WanshotModel.deltaTime;
		
		this.speed -= WanshotModel.deltaTime;
		
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		ctx.rotate(this.angle, this.x + MissleParticle.side / 2, this.y + MissleParticle.side / 2);
		ctx.setColor(this.color);
		
		Rectangle p = new Rectangle(this.x, this.y, MissleParticle.side, MissleParticle.side);
		ctx.draw(p);
		ctx.fill(p);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}

class HitParticle extends Particle {
	Color[] possibleColors = {Color.decode("#ED4245"), Color.decode("#FFA500"), Color.decode("#FFBF00")};	
	
	static final int side = 7;
	int x;
	int y;
	int opacity = 100;
	int speed = 350;
	Color color = this.possibleColors[(int)(Math.random() * possibleColors.length)];
	double angle = WanshotModel.degreesToRadians(Math.random() * 360);

	public HitParticle(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		this.x += this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
		this.y += this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
		this.opacity -= 350 * WanshotModel.deltaTime;
		
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		ctx.rotate(this.angle, this.x + HitParticle.side / 2, this.y + HitParticle.side / 2);
		ctx.setColor(this.color);
		
		Rectangle p = new Rectangle(this.x, this.y, HitParticle.side, HitParticle.side);
		ctx.draw(p);
		ctx.fill(p);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}

class ShellSmoke extends Particle {
	int x;
	int y;
	int radius = 10;
	int opacity = 70;
	Color color = new Color(136, 136, 136, this.opacity);
	
	public ShellSmoke(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		this.radius += 125 * WanshotModel.deltaTime;
		this.opacity -= 260 * WanshotModel.deltaTime;
		
		if (opacity <= 0) {
			super.delete = true;
			return;
		}
		
		color = new Color(0, 0, 0, this.opacity);
	}

	public void render(Graphics2D ctx) {
		ctx.setColor(this.color);
		ctx.fillOval(this.x, this.y, this.radius, this.radius);
	}	
}

public class Shell extends Parallelogram {
	//SHELL CONSTANT INFO
	static final int WIDTH = 9;
	static final int HEIGHT = 6;
	static final int REGULAR_SHELL_SPEED = 250;
	static final int MISSLE_SPEED = 600;
	static final int ULTRA_MISSLE_SPEED = 500;
	static final Color REGULAR_SHELL_COLOR = Color.decode("#D3D3D3");
	static final Color MISSLE_COLOR = Color.decode("#DE522F");
	static final Color ULTRA_MISSLE_COLOR = Color.decode("#FFBF00");
	static final int MISSLE_PARTICLE_CAP = 0;
	static final int ULTRA_MISSLE_PARTICLE_CAP = -1;
	
	//SHELL INSTANCE INFO
	double x;
	double y;
	double vX;
	double vY;
	double centerX;
	double centerY;
	double angle;
	int speed;
	Tank tankRef;
	boolean delete = false;
	boolean peace = true;
	int ricochet = 0;
	int trailRate;
	int trailRateCount = 0;
	int particleCapCount = 0;
	int particleCap;
	Color color;
	
	public Shell(double x, double y, double angle, int speed, Tank tankRef) {
		super((int)x, (int)y, Shell.WIDTH, Shell.HEIGHT, angle);
		this.x = x;
		this.y = y;
		this.vX = speed * Math.cos(angle) * WanshotModel.deltaTime;
		this.vY = speed * Math.sin(angle) * WanshotModel.deltaTime;
		this.angle = angle;
		this.speed = speed;
		this.tankRef = tankRef;
		
		switch (speed) {
			case Shell.REGULAR_SHELL_SPEED:
				WanshotMain.playSound("normalShoot.wav");
				this.color = Shell.REGULAR_SHELL_COLOR;
				this.trailRate = -5;
				break;
			case Shell.MISSLE_SPEED:
				WanshotMain.playSound("missleShoot.wav");
				this.color = Shell.MISSLE_COLOR;
				this.particleCap = Shell.MISSLE_PARTICLE_CAP;
				this.trailRate = -2;
				break;
			case Shell.ULTRA_MISSLE_SPEED:
				WanshotMain.playSound("ultraMissleShoot.wav");
				this.color = Shell.ULTRA_MISSLE_COLOR;
				this.particleCap = Shell.ULTRA_MISSLE_PARTICLE_CAP;
				this.trailRate = -1;
				break;
		}
		
		this.createHit((int)x, (int)y);
	}
	
	public void createHit() {
		this.createHit((int)this.centerX, (int)this.centerY);
	}
	
	public void createHit(int x, int y) {
		//create 50 hit particles
		for (int i = 0; i < 50; i++) {
			HitParticle p = new HitParticle(x, y);
			WanshotModel.particles.add(p);
		}
	}
	
	public boolean bounceLeft() {
		if (this.vX < 0) {
			this.vX *= -1;
			this.angle *= -1;
			this.createHit();
			return true;
		}
		
		return false;
	}
	
	public boolean bounceRight() {
		if (this.vX > 0) {
			this.vX *= -1;
			this.angle *= -1;
			this.createHit();
			return true;
		}
		
		return false;
	}
	
	public boolean bounceBottom() {						
		if (this.vY < 0) {
			this.vY *= -1;
			this.angle *= -1;
			this.createHit();
			return true;
		}
		
		return false;
	}
	
	public boolean bounceTop() {				
		if (this.vY > 0) {
			this.vY *= -1;
			this.angle *= -1;
			this.createHit();
			return true;
		}
		
		return false;
	}
	
	public void decrementShellTankRef() {
		if (this.tankRef != null) {
			this.tankRef.shellShot--;
		}
	}

	public void shellWithTank() {
		for (int i = 0; i < WanshotModel.tanks.size(); i++) {
			Tank tank = WanshotModel.tanks.get(i);
			
			//wait for shell to leave contact of tank then remove peace mode
			if (this.peace) {
				if (!super.sat_parallelogram(tank) && (this.tankRef == null || System.identityHashCode(this.tankRef) == System.identityHashCode(tank))) {
					this.peace = false;
					return;
				}
			}
			
			if (super.sat_parallelogram(tank) && !this.peace) {
				this.delete = true;
				this.createHit();
				this.decrementShellTankRef();
				
				tank.createExplosion();
				tank.delete = true;
				
				WanshotMain.playSound("shellOut.wav");
				
				if (tank instanceof Player) {
					WanshotMain.playSound("openPause.wav");
				}
				break;
			}
		}
	}
	
	public void shellWithShell() {
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			Shell shell = WanshotModel.shells.get(i);
			if (System.identityHashCode(this) != System.identityHashCode(shell)) {
				if (super.sat_parallelogram(shell)) {
					this.delete = true;
					shell.delete = true;
					
					this.createHit();
					shell.createHit();
					
					this.decrementShellTankRef();
					shell.decrementShellTankRef();
					
					WanshotMain.playSound("shellOut.wav");
					break;
				}
			}
		}
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
				
				if (crossWidth > crossHeight) {
					//bottom						
					if (!tile.info.bottomNeighboor) {
						bounced = this.bounceBottom();
					}
					
					if (!tile.info.leftNeighboor) {
						//right
						bounced = this.bounceRight();
					}
				} else {
					//left
					if (!tile.info.rightNeighboor) {
						bounced = this.bounceLeft();
					}
					
					if (!tile.info.topNeighboor) {
						//top
						bounced = this.bounceTop();
					}
				}
				
				if (bounced) {
					this.ricochet++;
										
					this.peace = false;
					
					//Mark shells for deletion
					switch (this.speed) {
						case Shell.REGULAR_SHELL_SPEED:
							if (this.ricochet >= 2) {
								this.delete = true;
								this.decrementShellTankRef();
							}
							break;
						case Shell.MISSLE_SPEED:
							if (this.ricochet >= 1) {
								this.delete = true;
								this.decrementShellTankRef();
							}
							break;
						case Shell.ULTRA_MISSLE_SPEED:
							if (this.ricochet >= 3) {
								this.delete = true;
								this.decrementShellTankRef();
							}
							break;
					}
					
					if (this.delete) {
						WanshotMain.playSound("shellOut.wav");
						return;
					}
					
					if (this.speed != Shell.ULTRA_MISSLE_SPEED) {
						WanshotMain.playSound("shellDink.wav");
					} else {
						WanshotMain.playSound("ultraMissleDink.wav");
					}
					
					//once a shell hits a tile on one hit, wait till next tick before checking collision again or double collisions could happen
					return;
				}
			}
		}
	}
	
	public void update() {
		if (WanshotModel.isPlayerAlive()) {
			super.update(this.x, this.y, this.angle);
			this.x += this.vX;
			this.y += this.vY;
			
			this.centerX = this.x + Shell.WIDTH / 2;
			this.centerY = this.y + Shell.HEIGHT / 2;
			
			//Update particles
			if (this.trailRateCount < 0) {
				this.trailRateCount++;
			} else {
				this.trailRateCount = this.trailRate;
				WanshotModel.particles.add(new ShellSmoke((int)this.centerX - 5, (int)this.centerY - 5));
			}
			
			if (this.speed != Shell.REGULAR_SHELL_SPEED) {
				if (this.particleCapCount < 0) {
					this.particleCapCount++;
				} else {
					this.particleCapCount = this.particleCap;
					WanshotModel.particles.add(new MissleParticle((int)this.x, (int)this.y));
				}
			}
			
			//Collisions
			this.shellWithTile();
			this.shellWithShell();
			this.shellWithTank();
			
			//remove escapees
			if (this.x < 0 || this.x > WanshotModel.WIDTH || this.y < 0 || this.y > WanshotModel.HEIGHT) {
				this.delete = true;
				this.decrementShellTankRef();
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
		
		Rectangle body = new Rectangle((int)this.x, (int)this.y, Shell.WIDTH, Shell.HEIGHT);
		
		if (this.speed == Shell.REGULAR_SHELL_SPEED) {
			ctx.setStroke(new BasicStroke(3));
			ctx.setColor(WanshotView.SHADOW);
		}
		
		ctx.draw(body);
		
		ctx.setColor(this.color);
		ctx.fill(body);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}