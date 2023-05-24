import java.awt.*;

class TankParticle extends Particle {
	Color[] possibleColors = {Color.decode("#ED4245"), Color.decode("#FFA500"), Color.decode("#808080")};
	
	static final int side = 20;
	int x;
	int y;
	int speed;
	double angle = WanshotModel.degreesToRadians(Math.random() * 360);
	int opacity = 200;
	Color color;
	
	public TankParticle(int x, int y, Color tankColor, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		
		int ind = (int)(Math.random() * (this.possibleColors.length + 1));
				
		if (ind > 2) {
			this.color = tankColor;
		} else {
			this.color = this.possibleColors[ind];
		}
	}
	
	public void update() {
		this.x += this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
		this.y += this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
		this.opacity -= 300 * WanshotModel.deltaTime;
		
		this.speed /= 2;
		
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		int randomBump = (int)(Math.random() * 10);
		
		ctx.rotate(this.angle, this.x + TankParticle.side / 2, this.y + TankParticle.side / 2);
		ctx.setColor(this.color);
		
		Rectangle p = new Rectangle(this.x, this.y, TankParticle.side + randomBump, TankParticle.side + randomBump);
		ctx.draw(p);
		ctx.fill(p);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}

class Grave extends Particle {
	static final int WIDTH = 10;
	static final int HEIGHT = 30;
	int opacity = 200;
	Color color;
	int x;
	int y;
	
	public Grave(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void update() {
		this.opacity -= WanshotModel.deltaTime / 20;
		
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		double angleLeanRight = Math.PI / 4;
		double angleLeanLeft = Math.PI / 2;
		
		ctx.setColor(this.color);
		
		ctx.rotate(angleLeanRight, this.x + Grave.WIDTH / 2, this.y + Grave.HEIGHT / 2);
		Rectangle p1 = new Rectangle(this.x, this.y, Grave.WIDTH, Grave.HEIGHT);
		ctx.draw(p1);
		ctx.fill(p1);
		
		ctx.rotate(angleLeanLeft, this.x + Grave.WIDTH / 2, this.y + Grave.HEIGHT / 2);
		Rectangle p2 = new Rectangle(this.x, this.y, Grave.WIDTH, Grave.HEIGHT);
		ctx.draw(p2);
		ctx.fill(p2);
		ctx.setTransform(WanshotView.oldTransform);
	}
}

class Track extends Particle {
	static final int WIDTH = 4;
	static final int HEIGHT = 7;
	int opacity = 200;
	Color color = Color.decode("#7B3F00");
	int x;
	int y;
	double angle;
	
	public Track(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public void update() {
		this.opacity -= WanshotModel.deltaTime;
		
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		ctx.rotate(this.angle, this.x + Track.WIDTH / 2, this.y + Track.HEIGHT / 2);
		ctx.setColor(this.color);
		
		Rectangle p1 = new Rectangle(this.x, (this.y) - Track.HEIGHT, Track.WIDTH, Track.HEIGHT);
		ctx.draw(p1);
		ctx.fill(p1);
		
		Rectangle p2 = new Rectangle(this.x, (this.y) + Track.HEIGHT, Track.WIDTH, Track.HEIGHT);
		ctx.draw(p2);
		ctx.fill(p2);
		
		ctx.setTransform(WanshotView.oldTransform);	}
}

public class Tank extends Parallelogram {
	//TANK STATIC INFO
	static final int WIDTH = 43;
	static final int HEIGHT = 33;
	static final int turretBaseSide = 19;
	static final int turretNozzleWidth = 21;
	static final int turretNozzleHeight = 10;
	
	//TANK INSTANCE INFO
	double x;
	double y;
	double centerX;
	double centerY;
	double angle;
	double turretAngle;
	double xInc;
	double yInc;
	double rotationInc;
	double speed;
	boolean delete = false;
	int stun;
	int stunCount = 0;
	int shellCap;
	int shellShot = 0;
	int shellCooldown;
	int shellCooldownCount = 0;
	int trackCooldown = -5;
	int trackCooldownCount = 0;
	Color color;
	Color turretColor;
	Color sideColor;
	
	public Tank(
		double x,
		double y,
		double angle,
		double speed,
		double rotationSpeed,
		int stun,
		int shellCap,
		int shellCooldown,
		Color color,
		Color turretColor,
		Color sideColor
	) {
		super((int)x, (int)y, Tank.WIDTH, Tank.HEIGHT, angle);
		this.x = x;
		this.y = y;
		this.centerX = this.x + Tank.WIDTH / 2;
		this.centerY = this.y + Tank.HEIGHT / 2;
		this.angle = angle;
		this.turretAngle = angle;
		this.speed = speed;
		this.rotationInc = rotationSpeed * WanshotModel.deltaTime;
		this.stun = stun;
		this.shellCap = shellCap;
		this.shellCooldown = shellCooldown;
		this.color = color;
		this.turretColor = turretColor;
		this.sideColor = sideColor;
	}
	
	//this only finalizes information
	//actual updating of coords happens differently for player and bot
	
	//Player && Bot will update x and y directly, not caring for collisions
	//This will update center x and deal with collisions seperately
	
	public void tankWithTile() {
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			Tile tile = WanshotModel.tiles.get(i);
						
			//colliding with this tile
			if (super.sat_parallelogram(tile)) {
				this.x += super.getNormal().x * super.getCollisionDepth() / 2;
				this.y += super.getNormal().y * super.getCollisionDepth() / 2;
			}
		}
	}
	
	public void tankWithTank() {
		for (int i = 0; i < WanshotModel.tanks.size(); i++) {
			Tank tank = WanshotModel.tanks.get(i);
						
			//colliding with tank not self
			if (System.identityHashCode(this) != System.identityHashCode(tank)) {
				if (super.sat_parallelogram(tank)) {
					this.x += super.getNormal().x * super.getCollisionDepth() / 2;
					this.y += super.getNormal().y * super.getCollisionDepth() / 2;
					
					tank.x -= super.getNormal().x * super.getCollisionDepth() / 2;
					tank.y -= super.getNormal().y * super.getCollisionDepth() / 2;
				}
			}
		}
	}
	
	public void createTrack() {
		Track p = new Track((int)this.centerX - Track.WIDTH / 2, (int)this.centerY - Track.HEIGHT, this.angle);
		WanshotModel.particles.add(p);
		
		if (!(this instanceof Player) && !(this instanceof BrownTank) && !(this instanceof GreenTank)) {
			WanshotMain.playSound("tankMovement.wav");
		}
	}
	
	public void createExplosion() {
		Grave p = new Grave((int)this.centerX - Grave.WIDTH / 2, (int)this.centerY - Grave.HEIGHT / 2, this.color);
		WanshotModel.particles.add(p);
		
		for (int i = 0; i < 50; i++) {
			TankParticle tp = new TankParticle((int)this.centerX - TankParticle.side / 2, (int)this.centerY - TankParticle.side / 2, this.color, this.getExplosionType());
			WanshotModel.particles.add(tp);
		}
		
		WanshotMain.playSound("tankDeath.wav");
		if (this instanceof Player) {
			WanshotMain.playSound("playerDeath.wav");
		} else {
			WanshotMain.playSound("enemyDeath.wav");
		}
	}
	
	public int getExplosionType() {
		if (this instanceof BrownTank) {
			return 1800;
		}
		
		if (this instanceof GreyTank) {
			return 2200;
		}
		
		if (this instanceof YellowTank) {
			return 2600;
		}
		
		return 3000;
	}
	
	public boolean canShoot() {
		return this.shellShot < this.shellCap && this.shellCooldownCount == 0;
	}
	
	public void shoot(int shellType) {
		if (this.canShoot()) {
			double initialBoostX = (20 * Math.cos(this.turretAngle));
			double initialBoostY = (20 * Math.sin(this.turretAngle));
			Shell shell = new Shell(this.centerX - Shell.WIDTH / 2 + initialBoostX, this.centerY - Shell.HEIGHT / 2 + initialBoostY, this.turretAngle, shellType, this);
		
			WanshotModel.shells.add(shell);
			this.stunCount = this.stun;
			this.shellCooldownCount = this.shellCooldown;
			this.shellShot++;	
		}
	}
	
	public void update() {
		super.update((int)this.x, (int)this.y, this.angle);
								
		if (this.shellCooldownCount < 0) {
			this.shellCooldownCount++;
		}
		
		//only create tracks for tanks that move
		if (!(this instanceof BrownTank) && !(this instanceof GreenTank)) {
			if (this.trackCooldownCount < 0) {
				this.trackCooldownCount++;
			} else {
				this.trackCooldownCount = this.trackCooldown;
				this.createTrack();
			}
		}
		
		if (this.stunCount < 0) {
			this.xInc = 0;
			this.yInc = 0;
			this.stunCount++;
		} else {
			this.xInc = this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
			this.yInc = this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;	
		}
		
		//Update Center
		this.centerX = this.x + Tank.WIDTH / 2;
		this.centerY = this.y + Tank.HEIGHT / 2;
		
		//Modulo rotations		
		this.angle %= 2 * Math.PI;
		this.turretAngle %= 2 * Math.PI;
		
		if (this.angle < 0) {
			this.angle = 2 * Math.PI - Math.abs(this.angle);
		}
		
		if (this.turretAngle < 0) {
			this.turretAngle = 2 * Math.PI - Math.abs(this.turretAngle);
		}
		
		//Collisions
		this.tankWithTile();
		this.tankWithTank();
	}
	
	public void render(Graphics2D ctx) {		
		ctx.rotate(this.angle, ((int)this.x - 5) + Tank.WIDTH / 2, ((int)this.y + (Tank.HEIGHT / 7)) + Tank.HEIGHT / 2);

		ctx.setColor(WanshotView.SHADOW);
		Rectangle shadow = new Rectangle((int)this.x - 5, (int)this.y + (Tank.HEIGHT / 7), Tank.WIDTH, Tank.HEIGHT);
		ctx.fill(shadow);
		
		ctx.setTransform(WanshotView.oldTransform);
		
		ctx.rotate(this.angle, this.centerX, this.centerY);
		Rectangle body = new Rectangle((int)this.x, (int)this.y, Tank.WIDTH, Tank.HEIGHT);
		ctx.setColor(this.color);
		ctx.draw(body);
		ctx.fill(body);
		
		ctx.setColor(this.sideColor);
		Rectangle leftWheel = new Rectangle((int)this.x, (int)this.y, Tank.WIDTH, Tank.HEIGHT / 5);
		Rectangle rightWheel = new Rectangle((int)this.x, (int)this.y + (Tank.HEIGHT - 7), Tank.WIDTH, Tank.HEIGHT / 5);
	
		ctx.draw(leftWheel);
		ctx.fill(leftWheel);
		
		ctx.draw(rightWheel);
		ctx.fill(rightWheel);
		
		//reset rotation
		ctx.setTransform(WanshotView.oldTransform);
		
		ctx.rotate(this.turretAngle, this.centerX, this.centerY);
		Rectangle turretBaseOutline = new Rectangle((int)this.centerX - (Tank.turretBaseSide / 2), (int)this.centerY - (Tank.turretBaseSide / 2), Tank.turretBaseSide, Tank.turretBaseSide);
		Rectangle turretBase = new Rectangle((int)this.centerX - ((Tank.turretBaseSide / 2)) + 2, (int)this.centerY - ((Tank.turretBaseSide / 2)) + 2, Tank.turretBaseSide - 2, Tank.turretBaseSide - 2);
		
		ctx.setStroke(new BasicStroke(2));
		
		ctx.setColor(Color.BLACK);
		ctx.draw(turretBaseOutline);
		
		ctx.setColor(this.turretColor);
		ctx.fill(turretBase);
		
		Rectangle turretNozzleOutline = new Rectangle((int)this.centerX + Tank.turretBaseSide / 2, (int)this.centerY - (Tank.turretNozzleHeight / 2), Tank.turretNozzleWidth, Tank.turretNozzleHeight);
		Rectangle turretNozzle = new Rectangle((int)this.centerX + (Tank.turretBaseSide / 2) + 1, (int)this.centerY - (Tank.turretNozzleHeight / 2) + 1, Tank.turretNozzleWidth - 1, Tank.turretNozzleHeight - 1);
	
		ctx.setColor(Color.BLACK);
		ctx.draw(turretNozzleOutline);
		
		ctx.setColor(this.turretColor);
		ctx.fill(turretNozzle);	
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}