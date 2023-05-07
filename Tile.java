import java.awt.*;

public class Tile {
	//TILE STATIC INFO
	static final int WIDTH = 35;
	static final int HEIGHT = 35;
	
	class Splotch {
		private int side;
		private int x;
		private int y;
		Color color;
		
		public Splotch(int blockX, int blockY, boolean breakable) {
			this.side = (int)(Math.random() * Tile.WIDTH / 3) + 2;
			this.x = blockX + (int)(Math.random() * Tile.WIDTH / 1.4);
			this.y = blockY + (int)(Math.random() * Tile.HEIGHT / 1.4);
			
			if (!breakable) {
				this.color = new Color(194, 153, 93, 128);
			} else {
				this.color = new Color(255, 138, 115, 128);
			}
		}
		
		public void render(Graphics2D ctx) {
			ctx.setColor(this.color);
			Rectangle body = new Rectangle(this.x, this.y, this.side, this.side);
		
			ctx.draw(body);
			ctx.fill(body);
		}
	}
	
	int x;
	int y;
	boolean breakable;
	Color color;
	Splotch[] splotches = new Splotch[2];
	
	public Tile(int x, int y, boolean breakable) {
		this.x = x;
		this.y = y;
		this.breakable = breakable;
		
		if (!this.breakable) {
			this.color = Color.decode("#967748");
		} else {
			this.color = Color.decode("#B54B44");
		}
		
		for (int i = 0; i < this.splotches.length; i++) {
			this.splotches[i] = new Splotch(this.x, this.y, this.breakable);
		}
	}
	
	public void renderShadow(Graphics2D ctx) {
		ctx.setColor(WanshotView.SHADOW);
		Rectangle shadow = new Rectangle(this.x - 8, this.y + (Tile.HEIGHT / 5), Tile.WIDTH, Tile.HEIGHT);
		
		ctx.fill(shadow);
	}
	
	public void render(Graphics2D ctx) {
		ctx.setColor(this.color);
		Rectangle body = new Rectangle(this.x, this.y, Tile.WIDTH, Tile.HEIGHT);
		
		ctx.draw(body);
		ctx.fill(body);
		
		for (int i = 0; i < this.splotches.length; i++) {
			this.splotches[i].render(ctx);
		}
	}
}