import java.awt.Color;

public class TealTank extends Bot {
	public TealTank(int x, int y) {
		super(
			x,
			y,
			60,
			WanshotModel.degreesToRadians(800),
			-8,
			1,
			-35,
			-13,
			WanshotModel.degreesToRadians(20),
			WanshotModel.degreesToRadians(160),
			Color.decode("#154734"),
			Color.decode("#0E4732"),
			Color.decode("#B0896B")
		);
	}
}