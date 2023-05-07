import java.awt.*;

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
	Color color;
	Color turretColor;
	Color sideColor;
	
	public Tank(
		double x,
		double y,
		double angle,
		double speed,
		double rotationSpeed,
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
		this.color = color;
		this.turretColor = turretColor;
		this.sideColor = sideColor;
	}
	
	//this only finalizes information
	//actual updating of coords happens differently for player and bot
	
	//Player && Bot will update x and y directly, not caring for collisions
	//This will update center x and deal with collisions seperately
	
	public void collideWithTile() {
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			Tile tile = WanshotModel.tiles.get(i);
						
			//colliding with this tile
			if (super.sat_parallelogram(tile)) {
				this.x += super.getNormal().x * super.getCollisionDepth() / 2;
				this.y += super.getNormal().y * super.getCollisionDepth() / 2;
			}
		}
	}
	
	public void update() {
		super.update((int)this.x, (int)this.y, this.angle);
		
		this.xInc = this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
		this.yInc = this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
		
		//Update Center
		this.centerX = this.x + Tank.WIDTH / 2;
		this.centerY = this.y + Tank.HEIGHT / 2;
		
		//Modulo rotations
		this.angle %= 2 * Math.PI;
		this.turretAngle %= 2 * Math.PI;
		
		//Collisions
		this.collideWithTile();
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