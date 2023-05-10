import java.awt.Color;

public class PurpleTank extends Bot {
	static final int speed = 210;
	static final double rotationSpeed = WanshotModel.degreesToRadians(600);
	static final int stun = -5;
	static final int shellCap = 3;
	static final int shellCooldown = -3;
	static final int frm = -3;
	static final double stopAndTurn = WanshotModel.degreesToRadians(40);
	static final double uTurn = WanshotModel.degreesToRadians(160);
	static final int updateTargetCount = -3;
	static final int shellSensitivity = 100;
	static final boolean abortNonmove = false;
	static final Color color = Color.decode("#934A9E");
	static final Color turretColor = Color.decode("#80408A");
	static final Color sideColor = Color.decode("#B0896B");
	
	public PurpleTank(int x, int y) {
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