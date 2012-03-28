package slick;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public abstract class Obstacle {

	protected float posX;
	protected float posY;
	
	protected Polygon polygonObstacle;

	
	protected boolean danger;
	
	public Obstacle(float posX, float posY,boolean danger){
		this.posX=posX;
		this.posY=posY;
		this.danger=danger;
	}
	
	public void drawObstacle(Graphics g){
		
	}
	public void act(int step){};
	

	public Polygon getPolygon(){
		return polygonObstacle;
	}
	public boolean getDanger(){
		return danger;
	}
	
	public void setPosX(float X){
		posX+=X;
		polygonObstacle.setX(posX);
	}
	public void setPosY(float Y){
		posY+=Y;
		polygonObstacle.setY(posY);
	}
	
}
