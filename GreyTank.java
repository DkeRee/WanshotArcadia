import java.awt.Color;

public class GreyTank extends Bot {
	public GreyTank(int x, int y) {
		super(
			x,
			y,
			80,
			WanshotModel.degreesToRadians(140),
			-5,
			1,
			-20,
			-3,
			WanshotModel.degreesToRadians(30),
			WanshotModel.degreesToRadians(120),
			Color.decode("#4A4A4A"),
			Color.decode("#4D4D4D"),
			Color.decode("#B0896B")
		);
	}
}