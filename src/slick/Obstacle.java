package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public  class Obstacle {

	protected int posX;
	protected int posY;
	

	protected int width;
	protected int height;
	
	protected Image obstacle;
	protected Polygon polygonObstacle;
	
	protected boolean danger;
	
	public Obstacle(int posX, int posY,int width,int height,String pathObstacle,boolean danger) throws SlickException{
		this.posX=posX*16;
		this.posY=posY*16;
		this.danger=danger;
		
		this.width=width*16;
		this.height=height*16;
		
		this.polygonObstacle=new Polygon(new float[]{this.posX,this.posY,(this.posX+this.width),this.posY,
				(this.posX+this.width),(this.posY+this.height),this.posX,(this.posY+this.height)});
		if(pathObstacle==null){
			obstacle=null;
		}else obstacle=new Image(pathObstacle);

		
	}
	

	public void drawObstacle(Graphics g){
		if(obstacle!=null){
			for(int i=0;i<width/16;i++){
				for(int j=0;j<height/16;j++){
					obstacle.draw(posX+i*16, posY+j*16, 1.0f);			
				}
			}	
		}else{
			g.fill(polygonObstacle);
		}
	}
	public void act(int step){};

	

	
	public Polygon getPolygon() {
		return this.polygonObstacle;
	}
	
	public boolean getDanger() {
		return danger;
	}
	
	public void setPosX(float X) {
		posX+=X;
		polygonObstacle.setX(posX);
	}
	
	public void setPosY(float Y) {
		posY+=Y;
		polygonObstacle.setY(posY);
	}
	
}
