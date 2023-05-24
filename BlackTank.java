import java.awt.Color;

public class BlackTank extends Bot {
	static final int speed = 240;
	static final double rotationSpeed = WanshotModel.degreesToRadians(600);
	static final int stun = -5;
	static final int shellCap = 2;
	static final int shellCooldown = -50;
	static final int frm = -3;
	static final double stopAndTurn = WanshotModel.degreesToRadians(35);
	static final double uTurn = WanshotModel.degreesToRadians(160);
	static final int updateTargetCount = -3;
	static final int shellSensitivity = 200;
	static final boolean abortNonmove = false;
	static final double turretRotationSpeed = WanshotModel.degreesToRadians(3);
	static final double turretArcSize = WanshotModel.degreesToRadians(20);
	static final int shellType = Shell.MISSLE_SPEED;
	static final int shellBounceAmount = 1;
	static final Color color = Color.BLACK;
	static final Color turretColor = Color.BLACK;
	static final Color sideColor = Color.decode("#B0896B");
	
	public BlackTank(int x, int y) {
		super(
				x,
				y,
				speed,
				rotationSpeed,
				stun,
				shellCap,
				shellCooldown,
				frm,
				stopAndTurn,
				uTurn,
				updateTargetCount,
				shellSensitivity,
				abortNonmove,
				turretRotationSpeed,
				turretArcSize,
				shellType,
				shellBounceAmount,
				color,
				turretColor,
				sideColor
			);
	}
}