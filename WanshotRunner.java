public class WanshotRunner {
	//Updater and Renderer
	private WanshotModel model = new WanshotModel();
	private WanshotController controller;
	private WanshotView view = new WanshotView(this.model);
		
	public WanshotRunner() {
		this.controller = new WanshotController(this.model);
		this.view.setFocusable(true);
		this.view.requestFocus();
		this.view.addKeyListener(controller);
		this.view.addMouseListener(controller);
		this.view.addMouseMotionListener(controller);
	}
	
	//Deltatime
	private double accTime = 0.0;
	private double lastTime = 0.0;
	
	public void start() {
		while (true) {
			double time = (double)System.currentTimeMillis();
			accTime += (time - lastTime) / 1000.0;
			
			while (accTime > WanshotModel.deltaTime) {
				if (accTime > 1) {
					accTime = WanshotModel.deltaTime;
				}
				
				model.update();
				accTime -= WanshotModel.deltaTime;
			}
			lastTime = time;
			view.repaint();
		}
	}
	
	public WanshotView getView() {
		return view;
	}
}