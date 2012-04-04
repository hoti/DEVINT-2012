package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class PlateformFlash extends Obstacle {

	private boolean visible;
	private int frequence;

	


	
	public PlateformFlash(int posX, int posY,int frequence,int width,int height,String pathObstacle,
			boolean danger,boolean visible) throws SlickException{
		
		super(posX,posY,width,height,pathObstacle,danger);
		this.visible=visible;
		this.frequence=frequence;
	}
	
	public boolean isVisible(){
		return visible;
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
	
	public void drawObstacle(Graphics g){
		if(visible){
			if(obstacle!=null){
				for(int i=0;i<width;i+=16){
					for(int j=0;j<height;j+=16){
						obstacle.draw((posX+i), (posY+j), 1.0f);
					}
				}	
			}else{
				g.fill(polygonObstacle);
			}
		}
	}

	
}

