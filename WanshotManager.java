import java.util.ArrayList;
import java.awt.*;

enum TankTypes {
	BrownTank,
	GreyTank,
	TealTank,
	PinkTank,
	PurpleTank,
	GreenTank
}

class SpawnParticle extends Particle {
	static final int side = 25;
	int x;
	int y;
	int opacity = 200;
	int speed = 400;
	double angle = WanshotModel.degreesToRadians(Math.random() * 360);
	Color color;
	
	public SpawnParticle(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void update() {
		this.x += this.speed * Math.cos(this.angle) * WanshotModel.deltaTime;
		this.y += this.speed * Math.sin(this.angle) * WanshotModel.deltaTime;
		this.opacity -= 200 * WanshotModel.deltaTime;
		
		this.speed -= WanshotModel.deltaTime;
		
		this.angle += WanshotModel.degreesToRadians(5);
		this.angle %= 2 * Math.PI;
		
		if (this.opacity <= 0) {
			super.delete = true;
			return;
		}
		
		this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.opacity);
	}
	
	public void render(Graphics2D ctx) {
		ctx.rotate(this.angle, this.x + SpawnParticle.side / 2, this.y + SpawnParticle.side / 2);
		ctx.setColor(this.color);
		
		Rectangle p = new Rectangle(this.x, this.y, SpawnParticle.side, SpawnParticle.side);
		ctx.draw(p);
		ctx.fill(p);
		
		ctx.setTransform(WanshotView.oldTransform);
	}
}

class TankCache {
	private TankTypes type;
	private Point coords;
	
	public TankCache(TankTypes type, Point coords) {
		this.type = type;
		this.coords = coords;
	}
	
	public void createSpawnParticles() {
		int centerX = ((int)this.coords.x + Tank.WIDTH / 2) + 14;
		int centerY = ((int)this.coords.y + Tank.HEIGHT / 2) + 14;
		Color color = null;
		
		switch (this.type) {
			case BrownTank:
				color = BrownTank.color;
				break;
			case GreyTank:
				color = GreyTank.color;
				break;
			case TealTank:
				color = TealTank.color;
				break;
			case PinkTank:
				color = PinkTank.color;
				break;
			case PurpleTank:
				color = PurpleTank.color;
				break;
			case GreenTank:
				color = GreenTank.color;
				break;
		}
		
		for (int i = 0; i < 50; i++) {
			SpawnParticle p = new SpawnParticle(centerX - SpawnParticle.side / 2, centerY - SpawnParticle.side / 2, color);
			WanshotModel.particles.add(p);
		}
	}
	
	public TankTypes getType() {
		return this.type;
	}
	
	public Point getCoords() {
		return this.coords;
	}
}

public class WanshotManager {
	private int level = 0;
	private int waveCap = 25;
	private int wave = 0;
	private int enemiesCap = 8;
	private boolean unloading = false;
	private int unloadCounter = 0;
	private int unloadSpeed;
	private int loaderInd = 0;
	private int distImmunity = 350;
	private ArrayList<ArrayList<TankCache>> levelList = new ArrayList<ArrayList<TankCache>>();
	
	public WanshotManager() {
		this.levelList = this.populateLevelList();
	}
	
	public ArrayList<ArrayList<TankCache>> populateLevelList() {
		ArrayList<ArrayList<TankCache>> list = new ArrayList<ArrayList<TankCache>>();
		
		for (int i = 0; i < this.getMaxWaves(); i++) {
			ArrayList<TankCache> currWave = new ArrayList<TankCache>();
			
			for (int j = 0; j < this.getMaxWaveEnemies(i); j++) {
				currWave.add(this.createTank());
			}
			
			list.add(currWave);
		}
		
		return list;
	}
	
	public void update() {
		if (WanshotModel.isPlayerAlive()) {
			if (WanshotModel.onlyPlayerAlive() && !this.unloading) {
				this.unloading = true;
				this.unloadSpeed = this.getUnloadSpeed();
			}
			
			if (this.unloading) {
				if (this.unloadCounter < 0) {
					this.unloadCounter++;
				} else {
					this.unloadCounter = this.unloadSpeed;
					
					ArrayList<TankCache> levelWave = this.levelList.get(this.wave);
					
					//upload tank and do all the specifics
					TankCache cache = levelWave.get(this.loaderInd);
					
					//upload tank cache into game
					this.readTankCache(cache);
					this.loaderInd++;
					
					//we have reached the end of this wave, go onto the next wave and move onto the next wave once player clears this one
					if (this.loaderInd == levelWave.size()) {
						this.wave++;
						this.unloading = false;
						levelWave.clear();
						this.loaderInd = 0;
					}
					
					//we have reached the end of this level, go onto the next level
					if (this.wave == this.levelList.size()) {
						this.wave = 0;
						this.level++;
						this.levelList = this.populateLevelList();
					}
				}
			}	
		}
	}
	
	public TankCache createTank() {
		//creates tank based off of current level
		TankTypes[] tanksToChoose = null;
		Point randomPoint = this.getRandomCoords();
		
		if (this.level <= 3) {
			tanksToChoose = new TankTypes[1];
			tanksToChoose[0] = TankTypes.BrownTank;
		} else if (this.level <= 6) {
			tanksToChoose = new TankTypes[2];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
		} else if (this.level <= 8) {
			tanksToChoose = new TankTypes[3];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
			tanksToChoose[2] = TankTypes.TealTank;
		} else if (this.level <= 10) {
			tanksToChoose = new TankTypes[4];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
			tanksToChoose[2] = TankTypes.TealTank;
			tanksToChoose[3] = TankTypes.PinkTank;
		} else if (this.level <= 12) {
			tanksToChoose = new TankTypes[5];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
			tanksToChoose[2] = TankTypes.TealTank;
			tanksToChoose[3] = TankTypes.PinkTank;
			tanksToChoose[4] = TankTypes.PurpleTank;
		} else {
			tanksToChoose = new TankTypes[6];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
			tanksToChoose[2] = TankTypes.TealTank;
			tanksToChoose[3] = TankTypes.PinkTank;
			tanksToChoose[4] = TankTypes.PurpleTank;
			tanksToChoose[5] = TankTypes.GreenTank;	
		}
		
		return new TankCache(tanksToChoose[(int)(Math.random() * tanksToChoose.length)], randomPoint);
	}
	
	public void readTankCache(TankCache cache) {
		Tank tank = null;
		Point coords = cache.getCoords();
		
		switch (cache.getType()) {
			case BrownTank:
				tank = new BrownTank((int)coords.x, (int)coords.y);
				break;
			case GreyTank:
				tank = new GreyTank((int)coords.x, (int)coords.y);
				break;
			case TealTank:
				tank = new TealTank((int)coords.x, (int)coords.y);
				break;
			case PinkTank:
				tank = new PinkTank((int)coords.x, (int)coords.y);
				break;
			case PurpleTank:
				tank = new PurpleTank((int)coords.x, (int)coords.y);
				break;
			case GreenTank:
				tank = new GreenTank((int)coords.x, (int)coords.y);
				break;
		}
		
		cache.createSpawnParticles();
		WanshotModel.tanks.add(tank);
	}
	
	public int getUnloadSpeed() {
		int unloadSpeedThreshold = 20;
		return Math.min(-((unloadSpeedThreshold - this.level)), -20);
	}
	
	public Point getRandomCoords() {
		Tank player = WanshotModel.tanks.get(0);
		
		int offset = 100;
		Point randomPoint = null;
		
		while (randomPoint == null || Parallelogram.getMagnitude(new Point(player.centerX - randomPoint.x, player.centerY - randomPoint.y)) < this.distImmunity) {
			randomPoint = new Point((int)(Math.random() * (WanshotModel.WIDTH - offset)) + 20, (int)(Math.random() * (WanshotModel.HEIGHT - offset)) + 20);
		}
		
		return randomPoint;
	}
	
	public int getMaxWaves() {
		return Math.min((int)(Math.random() * this.level) + 1, this.waveCap);
	}
	
	public int getMaxWaveEnemies(int waveCount) {
		int levelOffset = (this.level * 2) + (waveCount * 2);
		return Math.min((int)(Math.random() * levelOffset) + 1, this.enemiesCap);
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getWave() {
		return this.wave + 1;
	}
	
	public int getMaxWave() {
		return this.levelList.size();
	}
}