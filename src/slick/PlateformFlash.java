package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class PlateformFlash extends Obstacle {

	private boolean visible;
	private int frequence;

	
	private int width;
	private int height;

	
	public PlateformFlash(float posX, float posY,int frequence,int width,int height,boolean visible){
		super(posX,posY,false);
		
		this.visible=visible;
		this.frequence=frequence;
		
		this.width=width;
		this.height=height;
		this.polygonObstacle=new Polygon(new float[]{posX*16,posY*16,(posX+this.width)*16,posY*16,
				(posX+this.width)*16,(posY+this.height)*16,posX*16,(posY+this.height)*16});
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	
	public void drawObstacle(Graphics g){
		if(visible){
			Color c=g.getColor();
			g.setColor(Color.red);
			g.fill(polygonObstacle);
			g.setColor(c);
		}
		
	}
	
	public void act(int step){
		if(step%this.frequence==0){
			this.changeVisible();
		}
		
	}
	
	private void changeVisible(){
		if(visible){
			visible=false;
		}else visible=true;
	}
	

	
}
