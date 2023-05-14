import java.util.ArrayList;

public class WanshotModel {
	//Screen Stats
	static final int WIDTH = 910;
	static final int HEIGHT = 700;
	
	//Deltatime
	static final double deltaTime = 1.0 / 60.0;
	
	//Score keeper
	private WanshotScorekeeper scoreKeeper;
	
	//Game manager
	private WanshotManager manager;
	
	public static double degreesToRadians(double degree) {
		return degree * (Math.PI / 180);
	}
	
	//CONTENT
	static ArrayList<Tank> tanks = new ArrayList<Tank>();
	static ArrayList<Tile> tiles = new ArrayList<Tile>();
	static ArrayList<Shell> shells = new ArrayList<Shell>();
	static ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public WanshotModel() {
		tanks.add(new Player(50, 50));
		this.scoreKeeper = new WanshotScorekeeper();
		this.initTiles();
	}
	
	public static boolean isPlayerAlive() {
		return WanshotModel.tanks.size() > 0 && WanshotModel.tanks.get(0) instanceof Player;
	}
	
	public static boolean onlyPlayerAlive() {
		return WanshotModel.isPlayerAlive() && WanshotModel.tanks.size() == 1;
	}
	
	public void initTiles() {
		TileInfo info = new TileInfo(true, true, true, false);
		int x = 0;
		int y = 0;
		
		//top border
		for (int i = 0; i < WanshotModel.WIDTH / Tile.WIDTH; i++) {
			tiles.add(new Tile(x, y, false, info));
			x += Tile.WIDTH;
		}
		
		//bottom border
		info = new TileInfo(false, true, true, true);
		x = 0;
		y = WanshotModel.HEIGHT - Tile.HEIGHT;
		
		for (int i = 0; i < WanshotModel.WIDTH / Tile.WIDTH; i++) {
			tiles.add(new Tile(x, y, false, info));
			x += Tile.WIDTH;
		}
		
		//left border
		info = new TileInfo(true, false, true, true);
		x = 0;
		y = Tile.HEIGHT;
		
		for (int i = 0; i < (WanshotModel.HEIGHT / Tile.HEIGHT) - 1; i++) {
			tiles.add(new Tile(x, y, false, info));
			y += Tile.HEIGHT;
		}
		
		//right border
		info = new TileInfo(true, true, false, true);
		x = WanshotModel.WIDTH - Tile.WIDTH;
		y = Tile.HEIGHT;
				
		for (int i = 0; i < (WanshotModel.HEIGHT / Tile.HEIGHT) - 1; i++) {
			tiles.add(new Tile(x, y, false, info));
			y += Tile.HEIGHT;
		}
	}
	
	public void reset() {
		WanshotModel.tanks.clear();
		WanshotModel.shells.clear();
		
		tanks.add(new Player(50, 50));
		this.manager = new WanshotManager(this.scoreKeeper);
	}
	
	public void update() {
		for (int i = 0; i < WanshotModel.particles.size(); i++) {
			Particle particle = WanshotModel.particles.get(i);
			
			if (particle == null || particle.delete) {
				WanshotModel.particles.remove(i);
				i--;
				
				continue;
			}
			
			particle.update();
		}
		
		if (this.scoreKeeper.shouldUpdate()) {
			this.scoreKeeper.update();
			
			if (this.scoreKeeper.getResetFlag()) {
				this.reset();
				this.scoreKeeper.resetFlagFalsify();
			}
			return;
		}
		
		if (this.manager == null) {
			this.manager = new WanshotManager(this.scoreKeeper);
		}
		
		this.manager.update();
		
		for (int i = 0; i < WanshotModel.tanks.size(); i++) {
			Tank tank = WanshotModel.tanks.get(i);
			
			if (tank.delete) {
				WanshotModel.tanks.remove(i);
				i--;
				
				continue;
			}

			tank.update();
		}
		
		for (int i = 0; i < WanshotModel.shells.size(); i++) {
			Shell shell = WanshotModel.shells.get(i);
			
			if (shell.delete) {
				WanshotModel.shells.remove(i);
				i--;
				
				continue;
			}
			
			shell.update();
		}
	}
	
	public WanshotManager getManager() {
		return this.manager;
	}
	
	public WanshotScorekeeper getScoreKeeper() {
		return this.scoreKeeper;
	}
}