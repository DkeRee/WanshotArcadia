import java.awt.Color;

public class TealTank extends Bot {
	static final int speed = 60;
	static final double rotationSpeed = WanshotModel.degreesToRadians(800);
	static final int stun = -8;
	static final int shellCap = 1;
	static final int shellCooldown = -15;
	static final int frm = -13;
	static final double stopAndTurn = WanshotModel.degreesToRadians(35);
	static final double uTurn = WanshotModel.degreesToRadians(160);
	static final int updateTargetCount = -2;
	static final int shellSensitivity = 140;
	static final boolean abortNonmove = true;
	static final Color color = Color.decode("#154734");
	static final Color turretColor = Color.decode("#0E4732");
	static final Color sideColor = Color.decode("#B0896B");
	
	public TealTank(int x, int y) {
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