import java.awt.Color;

public class PinkTank extends Bot {
	static final int speed = 100;
	static final double rotationSpeed = WanshotModel.degreesToRadians(500);
	static final int stun = -5;
	static final int shellCap = 3;
	static final int shellCooldown = -7;
	static final int frm = -3;
	static final double stopAndTurn = WanshotModel.degreesToRadians(30);
	static final double uTurn = WanshotModel.degreesToRadians(170);
	static final int updateTargetCount = -3;
	static final int shellSensitivity = 400;
	static final boolean abortNonmove = false;
	static final Color color = Color.decode("#B82A55");
	static final Color turretColor = Color.decode("#B02951");
	static final Color sideColor = Color.decode("#B0896B");
	
	public PinkTank(int x, int y) {
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
			color,
			turretColor,
			sideColor
		);
	}
}