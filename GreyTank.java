import java.awt.Color;

public class GreyTank extends Bot {	
	static final int speed = 80;
	static final double rotationSpeed = WanshotModel.degreesToRadians(160);
	static final int stun = -5;
	static final int shellCap = 1;
	static final int shellCooldown = -20;
	static final int frm = -3;
	static final double stopAndTurn = WanshotModel.degreesToRadians(30);
	static final double uTurn = WanshotModel.degreesToRadians(120);
	static final int updateTargetCount = -5;
	static final int shellSensitivity = 80;
	static final boolean abortNonmove = false;
	static final Color color = Color.decode("#4A4A4A");
	static final Color turretColor = Color.decode("#4D4D4D");
	static final Color sideColor = Color.decode("#B0896B");
	
	public GreyTank(int x, int y) {		
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