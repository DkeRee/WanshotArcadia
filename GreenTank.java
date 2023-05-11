import java.awt.Color;

public class GreenTank extends Bot {
	static final int speed = 0;
	static final double rotationSpeed = 0;
	static final int stun = 0;
	static final int shellCap = 4;
	static final int shellCooldown = -10;
	static final int frm = 0;
	static final double stopAndTurn = 0;
	static final double uTurn = 0;
	static final int updateTargetCount = 0;
	static final int shellSensitivity = 0;
	static final boolean abortNonmove = false;
	static final double turretRotationSpeed = WanshotModel.degreesToRadians(3);
	static final double turretArcSize = WanshotModel.degreesToRadians(180);
	static final int shellType = Shell.ULTRA_MISSLE_SPEED;
	static final int shellBounceAmount = 2;
	static final Color color = Color.decode("#3AB02E");
	static final Color turretColor = Color.decode("#37A62B");
	static final Color sideColor = Color.decode("#B0896B");
	
	public GreenTank(int x, int y) {
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