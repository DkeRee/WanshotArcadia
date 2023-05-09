import java.util.ArrayList;

public class Parallelogram {
	private ArrayList<Point> vertices = new ArrayList<Point>();
	private ArrayList<Point> edgeVectors = new ArrayList<Point>();
	
	final int TOP_LEFT = 0;
	final int TOP_RIGHT = 1;
	final int BOTTOM_LEFT = 2;
	final int BOTTOM_RIGHT = 3;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private double rotation;
	private int centerX;
	private int centerY;
	
	private double collisionDepth = 0;
	private Point normal = null;
	
	public Parallelogram(
				int x,
				int y, 
				int width,
				int height,
				double rotation
			) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.centerX = this.x + (this.width / 2);
		this.centerY = this.y + (this.height / 2);
		
		//add rotated vertices
	
		//top left vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x, this.y, this.rotation));
		
		//top right vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x + this.width, this.y, this.rotation));
		
		//bottom left vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x, this.y + this.height, this.rotation));
		
		//bottom right vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x + this.width, this.y + this.height, this.rotation));
	
		//add edges vectors
		
		//left edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(TOP_LEFT).x - this.vertices.get(BOTTOM_LEFT).x, 
				this.vertices.get(TOP_LEFT).y - this.vertices.get(BOTTOM_LEFT).y
			)
		);
		
		//top edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(TOP_LEFT).x - this.vertices.get(TOP_RIGHT).x,
				this.vertices.get(TOP_LEFT).y - this.vertices.get(TOP_RIGHT).y
			)
		);
		
		//right edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(TOP_RIGHT).x - this.vertices.get(BOTTOM_RIGHT).x,
				this.vertices.get(TOP_RIGHT).y - this.vertices.get(BOTTOM_RIGHT).y
			)
		);
		
		//bottom edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(BOTTOM_RIGHT).x - this.vertices.get(BOTTOM_LEFT).x,
				this.vertices.get(BOTTOM_RIGHT).y - this.vertices.get(BOTTOM_LEFT).y
			)
		);
	}
	
	static Point getVertex(
				int cx,
				int cy,
				int vx,
				int vy,
				double rotatedAngle
			) {
		//get distance from center to specified vertex
		int distanceX = vx - cx;
		int distanceY = vy - cy;
		double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	
		double angleToVertex = Math.atan2(distanceY, distanceX);
		
		double rotatedVertexX = cx + distance * Math.cos(angleToVertex - rotatedAngle);
		double rotatedVertexY = cy + distance * Math.sin(angleToVertex - rotatedAngle);
		
		//return vertex with rotation
		return new Point(rotatedVertexX, rotatedVertexY);
	}
	
	static double dotProduct(Point vectorA, Point vectorB) {
		return vectorA.x * vectorB.x + vectorA.y * vectorB.y;
	}
	
	static double getMagnitude(Point vector) {
		return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
	}
	
	static void normalizeVector(Point vector) {
		double invLen = 1 / Parallelogram.getMagnitude(vector);
		vector.x *= invLen;
		vector.y *= invLen;		
	}
	
	static Point mean(Parallelogram p) {
		int xSum = 0;
		int ySum = 0;
		
		for (int i = 0; i < p.vertices.size(); i++) {
			xSum += p.vertices.get(i).x;
			ySum += p.vertices.get(i).y;
		}
		
		return new Point(xSum / p.vertices.size(), ySum / p.vertices.size());
	}
	
	public double getCollisionDepth() {
		return this.collisionDepth;
	}
	
	public Point getNormal() {
		return this.normal;
	}
	
	public void update(int x, int y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.centerX = this.x + this.width / 2;
		this.centerY = this.y + this.height / 2;
		
		//update edges and vertices
		this.edgeVectors.clear();
		this.vertices.clear();
		
		//add rotated vertices
		
		//top left vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x, this.y, this.rotation));
		
		//top right vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x + this.width, this.y, this.rotation));
		
		//bottom left vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x, this.y + this.height, this.rotation));
		
		//bottom right vertex
		this.vertices.add(Parallelogram.getVertex(this.centerX, this.centerY, this.x + this.width, this.y + this.height, this.rotation));
	
		//add edges vectors
		
		//left edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(TOP_LEFT).x - this.vertices.get(BOTTOM_LEFT).x, 
				this.vertices.get(TOP_LEFT).y - this.vertices.get(BOTTOM_LEFT).y
			)
		);
				
		//top edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(TOP_LEFT).x - this.vertices.get(TOP_RIGHT).x,
				this.vertices.get(TOP_LEFT).y - this.vertices.get(TOP_RIGHT).y
			)
		);
		
		//right edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(TOP_RIGHT).x - this.vertices.get(BOTTOM_RIGHT).x,
				this.vertices.get(TOP_RIGHT).y - this.vertices.get(BOTTOM_RIGHT).y
			)
		);
		
		//bottom edge
		this.edgeVectors.add(
			new Point(
				this.vertices.get(BOTTOM_RIGHT).x - this.vertices.get(BOTTOM_LEFT).x,
				this.vertices.get(BOTTOM_RIGHT).y - this.vertices.get(BOTTOM_LEFT).y
			)
		);
	}
		
	public boolean sat_parallelogram(Parallelogram other) {
		//set up perpendicular edge vectors
		ArrayList<Point> perpEdgeVectors = new ArrayList<Point>();
				
		for (int i = 0; i < this.edgeVectors.size(); i++) {
			Point v = this.edgeVectors.get(i);
			perpEdgeVectors.add(new Point(-v.y, v.x));
		}
		for (int i = 0; i < other.edgeVectors.size(); i++) {
			Point v = other.edgeVectors.get(i);
			perpEdgeVectors.add(new Point(-v.y, v.x));
		}
		
		//System.out.println(perpEdgeVectors);
				
		//loop through all perpendicular edges and vertices of both parallelograms
		//use this to compare with dot product to cast and see if they're colliding
		
		Point shortestCollisionVector = null;
		double depthOfCollision = Double.MAX_VALUE;
				
		for (int i = 0; i < perpEdgeVectors.size(); i++) {
			Point perpVec = perpEdgeVectors.get(i);
			
			double aMin = Integer.MAX_VALUE;
			double aMax = Integer.MIN_VALUE;
			
			double bMin = Integer.MAX_VALUE;
			double bMax = Integer.MIN_VALUE;
						
			for (Point vertixVec : this.vertices) {
				double dot = Parallelogram.dotProduct(vertixVec, perpVec);
				
				if (dot > aMax) {
					aMax = dot;
				}
				
				if (dot < aMin) {
					aMin = dot;
				}
			}
			
			for (Point vertixVec : other.vertices) {
				double dot = Parallelogram.dotProduct(vertixVec, perpVec);
				
				if(dot > bMax) {
					bMax = dot;
				}
				
				if (dot < bMin) {
					bMin = dot;
				}
			}
						
			//check if parallelograms are touching from the ultimate dot products
			if ((aMin < bMax && aMin > bMin) || (bMin < aMax && bMin > aMin)) {
				double axisDepth = Math.min(bMax - aMin, aMax - bMin);
				
				if (axisDepth < depthOfCollision) {
					shortestCollisionVector = perpVec;
					depthOfCollision = axisDepth;
				}				
			} else {
				return false;
			}
		}
		
		depthOfCollision /= Parallelogram.getMagnitude(shortestCollisionVector);
		this.collisionDepth = depthOfCollision;
		
		Parallelogram.normalizeVector(shortestCollisionVector);
		this.normal = shortestCollisionVector;
		
		//calculate centers of polygons to ensure our normal is pointing the right way
		Point centerA = Parallelogram.mean(this);
		Point centerB = Parallelogram.mean(other);
		
		//vector from center of polygon one to two
		Point vectorAToB = new Point(centerA.x - centerB.x, centerA.y - centerB.y);
	
		//reverse normal if this is colliding from opposite side of other parallelogram
		if (Parallelogram.dotProduct(vectorAToB, this.normal) < 0) {
			this.normal.x *= -1;
			this.normal.y *= -1;
		}
		
		return true;
	}	
}