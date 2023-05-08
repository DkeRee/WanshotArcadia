import java.util.ArrayList;

public class WanshotModel {
	//Screen Stats
	static final int WIDTH = 910;
	static final int HEIGHT = 700;
	
	//Deltatime
	static final double deltaTime = 1.0 / 60.0;
	
	public static double degreesToRadians(double degree) {
		return degree * (Math.PI / 180);
	}
	
	//CONTENT
	static ArrayList<Tank> tanks = new ArrayList<Tank>();
	static ArrayList<Tile> tiles = new ArrayList<Tile>();
	static ArrayList<Shell> shells = new ArrayList<Shell>();
	static ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public WanshotModel() {
		tanks.add(new Player(WanshotModel.WIDTH / 2, WanshotModel.HEIGHT / 2));
		this.initTiles();
	}
	
	public static boolean isPlayerAlive() {
		return WanshotModel.tanks.size() > 0 && WanshotModel.tanks.get(0) instanceof Player;
	}
	
	public void initTiles() {
		int x = 0;
		int y = 0;
		
		//top border
		for (int i = 0; i < WanshotModel.WIDTH / Tile.WIDTH; i++) {
			tiles.add(new Tile(x, y, false));
			x += Tile.WIDTH;
		}
		
		//bottom border
		x = 0;
		y = WanshotModel.HEIGHT - Tile.HEIGHT;
		
		for (int i = 0; i < WanshotModel.WIDTH / Tile.WIDTH; i++) {
			tiles.add(new Tile(x, y, false));
			x += Tile.WIDTH;
		}
		
		//left border
		x = 0;
		y = Tile.HEIGHT;
		
		for (int i = 0; i < (WanshotModel.HEIGHT / Tile.HEIGHT) - 1; i++) {
			tiles.add(new Tile(x, y, false));
			y += Tile.HEIGHT;
		}
		
		//right border
		x = WanshotModel.WIDTH - Tile.WIDTH;
		y = Tile.HEIGHT;
		
		for (int i = 0; i < (WanshotModel.HEIGHT / Tile.HEIGHT) - 1; i++) {
			tiles.add(new Tile(x, y, false));
			y += Tile.HEIGHT;
		}
	}
	
	
	public void update() {
		for (int i = 0; i < WanshotModel.particles.size(); i++) {
			Particle particle = WanshotModel.particles.get(i);
			
			if (particle.delete) {
				WanshotModel.particles.remove(i);
				i--;
				
				continue;
			}
			
			particle.update();
		}
		
		for (int i = 0; i < WanshotModel.tanks.size(); i++) {
			WanshotModel.tanks.get(i).update();
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
}