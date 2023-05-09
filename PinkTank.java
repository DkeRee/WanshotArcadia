import java.awt.Color;

public class PinkTank extends Bot {
	public PinkTank(int x, int y) {
		super(
			x,
			y,
			100,
			WanshotModel.degreesToRadians(200),
			-5,
			3,
			-7,
			-3,
			WanshotModel.degreesToRadians(20),
			WanshotModel.degreesToRadians(160),
			Color.decode("#B82A55"),
			Color.decode("#B02951"),
			Color.decode("#B0896B")
		);
	}
}