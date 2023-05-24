import java.awt.event.*;

public class WanshotController implements MouseListener, MouseMotionListener, KeyListener {
	static final int ENTER = 10;
	static final int W = 87;
	static final int A = 65;
	static final int S = 83;
	static final int D = 68;
	
	private WanshotModel model;
	
	public WanshotController(WanshotModel model) {
		this.model = model;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == WanshotController.ENTER) {
			this.model.getScoreKeeper().startGame();
		}
		
		if (WanshotModel.isPlayerAlive()) {
			((Player)WanshotModel.tanks.get(0)).updateKey(e.getKeyCode(), true);
			
			/**
			 * for debug purposes
			switch (e.getKeyCode()) {
			case 49:
				WanshotModel.tanks.add(new BrownTank(400, 400));
				break;
			case 50:
				WanshotModel.tanks.add(new GreyTank(400, 400));
				break;
			case 51:
				WanshotModel.tanks.add(new YellowTank(400, 400));
				break;
			case 52:
				WanshotModel.tanks.add(new TealTank(400, 400));
				break;
			case 53:
				WanshotModel.tanks.add(new PinkTank(400, 400));
				break;
			case 54:
				WanshotModel.tanks.add(new PurpleTank(400, 400));
				break;
			case 55:
				WanshotModel.tanks.add(new GreenTank(400, 400));
				break;
			case 56:
				WanshotModel.tanks.add(new WhiteTank(400, 400));
				break;
			case 57:
				WanshotModel.tanks.add(new BlackTank(400, 400));
				break;
			}
			 */
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (WanshotModel.isPlayerAlive()) {
			((Player)WanshotModel.tanks.get(0)).updateKey(e.getKeyCode(), false);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (WanshotModel.isPlayerAlive()) {
			((Player)WanshotModel.tanks.get(0)).updateMouseCoords(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (WanshotModel.isPlayerAlive()) {
			/*
			for (int i = 1; i < WanshotModel.tanks.size(); i++) {
				((Bot)WanshotModel.tanks.get(i)).target.x = e.getX();
				((Bot)WanshotModel.tanks.get(i)).target.y = e.getY();
			}
			*/
			
			((Player)WanshotModel.tanks.get(0)).shoot();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}