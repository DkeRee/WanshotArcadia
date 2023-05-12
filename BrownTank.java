import java.awt.Color;

public class BrownTank extends Bot {
	static final int speed = 0;
	static final double rotationSpeed = 0;
	static final int stun = 0;
	static final int shellCap = 1;
	static final int shellCooldown = -1500;
	static final int frm = 0;
	static final double stopAndTurn = 0;
	static final double uTurn = 0;
	static final int updateTargetCount = 0;
	static final int shellSensitivity = 0;
	static final boolean abortNonmove = false;
	static final double turretRotationSpeed = WanshotModel.degreesToRadians(2);
	static final double turretArcSize = WanshotModel.degreesToRadians(180);
	static final int shellType = Shell.REGULAR_SHELL_SPEED;
	static final int shellBounceAmount = 1;
	static final Color color = Color.decode("#966A4B");
	static final Color turretColor = Color.decode("#8C6346");
	static final Color sideColor = Color.decode("#B0896B");
	
	public BrownTank(int x, int y) {
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