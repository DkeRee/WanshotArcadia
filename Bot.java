import java.awt.*;

public class Bot extends Tank {
	private final int MAX_RAYCAST = 1000;
	private double targetAngle;
	private double targetTurretRot = 0;
	private double turretArcSize;
	private boolean move = true;
	private double regularMove = WanshotModel.degreesToRadians(5);
	private double turretRotationSpeed;
	private int frequencyRegularMove;
	private int frequencyRegularMoveCount;
	private double stopAndTurn;
	private double uTurn;
	private boolean abortNonmove;
	private boolean dodging = false;
	private int updateTarget;
	private int updateTargetCount = 0;
	private int shellSensitivity;
	private int shellType;
	private int shellBounceAmount;
	
	private Shell closest = null;
	private double closestAngle = 0;
		
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
			int updateTarget,
			int shellSensitivity,
			boolean abortNonmove,
			double turretRotationSpeed,
			double turretArcSize,
			int shellType,
			int shellBounceAmount,
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
		this.updateTargetCount = updateTarget;
		this.shellSensitivity = shellSensitivity;
		this.targetAngle = super.angle;
		this.abortNonmove = abortNonmove;
		this.turretRotationSpeed = turretRotationSpeed;
		this.turretArcSize = turretArcSize;
		this.shellType = shellType;
		this.shellBounceAmount = shellBounceAmount;
	}
	
	public boolean dodgeShells() {
		Shell closeShell = null;
		double closestDist = Double.MAX_VALUE;
		
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			Shell shell = WanshotModel.shells.get(i);
			double dist = Parallelogram.getMagnitude(new Point(super.centerX - shell.centerX, super.centerY - shell.centerY));

			if (dist < closestDist) {
				closeShell = shell;
				closestDist = dist;
			}
		}
		
		boolean doDodge = this.closest == null ? true : (closeShell != this.closest) || (closeShell == this.closest && closeShell.angle != this.closestAngle);
		
		if (doDodge && closestDist < this.shellSensitivity && !closeShell.peace) {
			this.closest = closeShell;
			this.closestAngle = closeShell.angle;
			
			Point shellVector = new Point(Math.cos(this.closest.angle), Math.sin(this.closest.angle));
			Point tankVector = new Point(Math.cos(super.angle), Math.sin(super.angle));
			
			Point resultant = new Point(shellVector.x - tankVector.x, shellVector.y - tankVector.y);
			Parallelogram.normalizeVector(resultant);
			
			double dotNow = Parallelogram.dotProduct(shellVector, tankVector);
			double dotNext = Parallelogram.dotProduct(shellVector, resultant);

			double angleToResultant = Math.atan2(resultant.y, resultant.x);
			if (angleToResultant < 0) {
				angleToResultant = 2 * Math.PI - Math.abs(angleToResultant);
			}
			
			if (dotNow > 0 && dotNext > 0) {
				if (angleToResultant > Math.PI) {
					angleToResultant -= Math.PI;
				}				
			}
				
			this.targetAngle = angleToResultant;
			this.dodging = true;
				
			return true;	
		}
		
		return false;
	}
	
	public boolean maneuverTiles() {
		int tileCount = 0;
		Point tileVector = new Point(0, 0);
		double closestDist = Double.MAX_VALUE;
		
		for (int i = 0; i < WanshotModel.tiles.size(); i++) {
			Tile tile = WanshotModel.tiles.get(i);
			double dist = Parallelogram.getMagnitude(new Point(super.centerX - tile.centerX, super.centerY - tile.centerY));
			
			if (dist < closestDist) {
				tileCount++;
				tileVector.x += Math.cos(super.centerX - tile.centerX);
				tileVector.y += Math.cos(super.centerY - tile.centerY);
				closestDist = dist;
			}
		}
		
		tileVector.x /= tileCount;
		tileVector.y /= tileCount;
		
		if (closestDist < 200) {
			Point tankVector = new Point(Math.cos(super.angle), Math.sin(super.angle));
			
			double dotProduct = Parallelogram.dotProduct(tileVector, tankVector);
			double tileVectorMag = Parallelogram.getMagnitude(tileVector);
			double tankVectorMag = Parallelogram.getMagnitude(tankVector);
			
			double angleBetweenVectors = Math.acos(dotProduct / (tileVectorMag * tankVectorMag));
						
			if (dotProduct > 0 && angleBetweenVectors < Math.PI / 2) {
				this.targetAngle += Math.PI / 2 - angleBetweenVectors;
				this.dodging = true;
				
				return true;
			}
		}
		
		return false;
	}
	
	public void walk() {
		double change = Math.random() * WanshotModel.degreesToRadians(6);
		this.targetAngle += Math.random() > 0.5 ? change : -change; 
		this.targetAngle %= 2 * Math.PI;		
	}
	
	public void findTarget() {
		if (this.updateTargetCount < 0) {
			this.updateTargetCount++;
		} else if (!this.dodging) {
			this.updateTargetCount = this.updateTarget;
			if (this.dodgeShells()) {
				return;
			} else if (this.maneuverTiles()) {
				return;
			} else {
				this.walk();
			}
		}
	}
	
	public void followTarget() {		
		double diff = this.targetAngle - super.angle;
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
				
		if (this.dodging && diff <= WanshotModel.degreesToRadians(5)) {
			this.dodging = false;
		}
										
		if (diff >= this.uTurn && !this.move) {
			//super.angle += Math.PI;
		} else if (diff >= this.stopAndTurn) {
			this.move = false;
		} else {
			this.move = true;
		}
		
		if (this.move || this.abortNonmove) {
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
	
	public void findNewTurretRot() {
		Tank player = WanshotModel.tanks.get(0);
		double angleToPlayer = Math.atan2(super.centerY - player.centerY, super.centerX - player.centerX) - Math.PI;
		
		if (angleToPlayer < 0) {
			angleToPlayer = 2 * Math.PI - Math.abs(angleToPlayer);
		}
				
		double randOffset = Math.random() * this.turretArcSize;
		angleToPlayer += Math.random() > 0.5 ? randOffset : -randOffset;
		
		angleToPlayer %= 2 * Math.PI;
				
		this.targetTurretRot = angleToPlayer;
	}
	
	public void moveTurret() {		
		double diff = this.targetTurretRot - super.turretAngle;
		double diffOther = 2 * Math.PI - diff;
						
		if (diff >= 0) {
			if (diff < diffOther) {
				//+
				super.turretAngle += this.turretRotationSpeed;
			} else {
				//-
				super.turretAngle -= this.turretRotationSpeed;
			}
		} else {
			diff = Math.abs(diff);
			diffOther = 2 * Math.PI - diff;
			
			if (diff < diffOther) {
				//-
				super.turretAngle -= this.turretRotationSpeed;
			} else {
				//+
				super.turretAngle += this.turretRotationSpeed;
			}
		}
		
		diff = diff < diffOther ? diff : diffOther;
						
		if (diff <= WanshotModel.degreesToRadians(40)) {
			this.findNewTurretRot();
		}		
	}
	
	public boolean canShoot(Point startingPoint, double angle, int bouncesLeft) {		
		Point raycast = new Point(startingPoint.x, startingPoint.y);
		Tank player = WanshotModel.tanks.get(0);
		
		for (int i = 0; i < this.MAX_RAYCAST; i++) {						
			//check if hitting any enemy tanks, return false if in range
			for (int j = 1; j < WanshotModel.tanks.size(); j++) {
				Tank enemy = WanshotModel.tanks.get(j);
				Parallelogram hitbox = new Parallelogram((int)raycast.x, (int)raycast.y, 20, 20, 0);
			
				if (enemy.sat_parallelogram(hitbox)) {
					return false;
				}
			}
			
			//check if directly hitting player, return true if is
			Parallelogram hitbox = new Parallelogram((int)raycast.x, (int)raycast.y, Shell.WIDTH, Shell.HEIGHT, angle);
			
			if (player.sat_parallelogram(hitbox)) {
				return true;
			}
			
			//check if hitting any tiles, return recurse with new params if bounces are left, if not return false
			for (int j = 0; j < WanshotModel.tiles.size(); j++) {
				Tile tile = WanshotModel.tiles.get(j);
				
				if (tile.sat_parallelogram(hitbox)) {
					if (bouncesLeft - 1 <= 0) {
						return false;
					}
					
					if (!tile.info.bottomNeighboor || !tile.info.topNeighboor) {
						angle = 2 * Math.PI - angle;
					} else {
						angle = Math.PI - angle;
					}
					
					Point newStartingPoint = new Point(raycast.x + Math.cos(angle) * 5, raycast.y + Math.sin(angle) * 5);
					
					return this.canShoot(newStartingPoint, angle, bouncesLeft - 1);
				}
			}
			
			raycast.x += Math.cos(angle);
			raycast.y += Math.sin(angle);
		}
		
		return false;
	}
	
	public void manageShoot() {
		Point offsetStart = new Point(super.centerX + Math.cos(super.centerX) * 50, super.centerY + Math.sin(super.centerY) * 50);
		if (this.canShoot(offsetStart, super.turretAngle, this.shellBounceAmount)) {
			super.shoot(this.shellType);
		}
	}
	
	public void updateTurret() {
		if (WanshotModel.isPlayerAlive()) {
			this.moveTurret();
			this.manageShoot();
		}
	}
	
	public void update() {
		this.updateTurret();
		this.updateMovement();
		super.update();
	}
}