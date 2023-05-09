import java.awt.Color;

public class PurpleTank extends Bot {
	public PurpleTank(int x, int y) {
		super(
			x,
			y,
			150,
			WanshotModel.degreesToRadians(300),
			-5,
			5,
			-3,
			-3,
			WanshotModel.degreesToRadians(20),
			WanshotModel.degreesToRadians(100),
			Color.decode("#934A9E"),
			Color.decode("#80408A"),
			Color.decode("#B0896B")
		);
	}
}