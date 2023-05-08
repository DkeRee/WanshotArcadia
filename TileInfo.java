public class TileInfo {
	boolean topNeighboor;
	boolean rightNeighboor;
	boolean leftNeighboor;
	boolean bottomNeighboor;
	
	public TileInfo(
				boolean t,
				boolean r,
				boolean l,
				boolean b
			) {
		this.topNeighboor = t;
		this.rightNeighboor = r;
		this.leftNeighboor = l;
		this.bottomNeighboor = b;
	}
}