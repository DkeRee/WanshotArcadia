import java.awt.Color;

public class Bot extends Tank {
	Point target = new Point(0, 0);
	private boolean move = true;
	private double regularMove = WanshotModel.degreesToRadians(5);
	private int frequencyRegularMove;
	private int frequencyRegularMoveCount;
	private double stopAndTurn;
	private double uTurn;
	
	public Bot(
			int x, 
			int y,
			int speed,
			double rotationSpeed,
			int stun,
			int shellCap,
			int shellCooldown,
			int frm,
			double stopAndTurn,
			double uTurn,
			Color color, 
			Color turretColor, 
			Color sideColor) {
		super(
				x,
				y,
				0,
				speed,
				rotationSpeed,
				stun,
				shellCap,
				shellCooldown,
				color,
				turretColor,
				sideColor
			);
		
		this.stopAndTurn = stopAndTurn;
		this.uTurn = uTurn;
		this.frequencyRegularMove = frm;
	}
	
	public void findTarget() {
		
	}
	
	public void followTarget() {
		double angleToTarget = Math.atan2(target.y - super.centerY, target.x - super.centerX);
		if (angleToTarget < 0) {
			angleToTarget = 2 * Math.PI - Math.abs(angleToTarget);
		}
		
		double diff = angleToTarget - super.angle;
		double diffOther = 2 * Math.PI - diff;
				
		if (diff >= 0) {
			if (diff < diffOther) {
				if (!this.move) {
					super.angle += super.rotationInc;
				}
							
				if (this.move && this.frequencyRegularMoveCount == 0) {
					super.angle += this.regularMove;
				}
			} else {
				if (!this.move) {
					super.angle -= super.rotationInc;
				}
				
				if (this.move && this.frequencyRegularMoveCount == 0) {
					super.angle -= this.regularMove;
				}
			}
		} else {
			diff = Math.abs(diff);
			diffOther = 2 * Math.PI - diff;
			
			if (diff < diffOther) {
				if (!this.move) {
					super.angle -= super.rotationInc;
				}
							
				if (this.move && this.frequencyRegularMoveCount == 0) {
					super.angle -= this.regularMove;
				}
			} else {
				if (!this.move) {
					super.angle += super.rotationInc;
				}
				
				if (this.move && this.frequencyRegularMoveCount == 0) {
					super.angle += this.regularMove;
				}
			}
		}
				
		if (this.frequencyRegularMoveCount < 0) {
			this.frequencyRegularMoveCount++;
		} else {
			this.frequencyRegularMoveCount = this.frequencyRegularMove;
		}
		
		diff = diff < diffOther ? diff : diffOther;
				
		if (diff >= this.uTurn && this.move) {
			super.angle += Math.PI;
		} else if (diff >= this.stopAndTurn) {
			this.move = false;
		} else {
			this.move = true;
		}
		
		if (this.move) {
			super.x += super.xInc;
			super.y += super.yInc;
		}
	}
	
	public void updateMovement() {
		if (this.speed > 0) {
			//not a stationary tank
			this.findTarget();
			this.followTarget();
		}
	}
	
	public void update() {
		this.updateMovement();
		super.update();
	}
}