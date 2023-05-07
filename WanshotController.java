import java.awt.event.*;

public class WanshotController implements MouseListener, MouseMotionListener, KeyListener {
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
		if (this.model.tanks.size() > 0 && this.model.tanks.get(0) instanceof Player) {
			((Player)this.model.tanks.get(0)).updateKey(e.getKeyCode(), true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (this.model.tanks.size() > 0 && this.model.tanks.get(0) instanceof Player) {
			((Player)this.model.tanks.get(0)).updateKey(e.getKeyCode(), false);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (this.model.tanks.size() > 0 && this.model.tanks.get(0) instanceof Player) {
			((Player)this.model.tanks.get(0)).updateMouseCoords(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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