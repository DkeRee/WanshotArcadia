import java.util.ArrayList;

enum TankTypes {
	BrownTank,
	GreyTank,
	TealTank,
	PinkTank,
	PurpleTank,
	GreenTank
}

class TankCache {
	private TankTypes type;
	private Point coords;
	
	public TankCache(TankTypes type, Point coords) {
		this.type = type;
		this.coords = coords;
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
	private int enemiesCap = 5;
	private boolean unloading = false;
	private int unloadCounter = 0;
	private int unloadSpeed;
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
					
					//upload tank and do all the specifics
					TankCache cache = this.levelList.get(this.wave).get(0);
					
					//upload tank cache into game
					this.readTankCache(cache);
					
					//manager clear out this tank cache it has been uploded
					this.levelList.get(this.wave).remove(0);
					
					//we have reached the end of this wave, go onto the next wave and move onto the next wave once player clears this one
					if (this.levelList.get(this.wave).size() == 0) {
						this.wave++;
						this.unloading = false;
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
		int offset = 100;
		Point randomPoint = new Point((int)(Math.random() * (WanshotModel.WIDTH - offset)) + 20, (int)(Math.random() * (WanshotModel.HEIGHT - offset)) + 20);
		
		if (this.level <= 3) {
			tanksToChoose = new TankTypes[1];
			tanksToChoose[0] = TankTypes.BrownTank;
		} else if (this.level <= 6) {
			tanksToChoose = new TankTypes[2];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
		} else if (this.level <= 12) {
			tanksToChoose = new TankTypes[3];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
			tanksToChoose[2] = TankTypes.TealTank;
		} else if (this.level <= 16) {
			tanksToChoose = new TankTypes[4];
			tanksToChoose[0] = TankTypes.BrownTank;
			tanksToChoose[1] = TankTypes.GreyTank;
			tanksToChoose[2] = TankTypes.TealTank;
			tanksToChoose[3] = TankTypes.PinkTank;
		} else if (this.level <= 25) {
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
		
		WanshotModel.tanks.add(tank);
	}
	
	public int getUnloadSpeed() {
		int unloadSpeedThreshold = 20;
		return Math.min(-((unloadSpeedThreshold - this.level)), -20);
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