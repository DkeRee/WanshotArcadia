import java.awt.Color;

public class YellowTank extends Bot {
	static final int speed = 100;
	static final double rotationSpeed = WanshotModel.degreesToRadians(500);
	static final int stun = -5;
	static final int shellCap = 2;
	static final int shellCooldown = -80;
	static final int frm = -3;
	static final double stopAndTurn = WanshotModel.degreesToRadians(30);
	static final double uTurn = WanshotModel.degreesToRadians(170);
	static final int updateTargetCount = -3;
	static final int shellSensitivity = 400;
	static final boolean abortNonmove = false;
	static final double turretRotationSpeed = WanshotModel.degreesToRadians(3);
	static final double turretArcSize = WanshotModel.degreesToRadians(95);
	static final int shellType = Shell.REGULAR_SHELL_SPEED;
	static final int shellBounceAmount = 1;
	static final Color color = Color.decode("#DEC951");
	static final Color turretColor = Color.decode("#C4B248");
	static final Color sideColor = Color.decode("#B0896B");
	
	public YellowTank(int x, int y) {
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