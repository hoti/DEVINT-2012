package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class PlateformFlash extends Plateform {

	private boolean visible;

	public PlateformFlash(float posX, float posY,int frequence,int width,int height,boolean visible){
		super(posX,posY,frequence,width,height);
		this.visible=visible;
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
		if(step%this.getFrequence()==0){
			this.changeVisible();
		}
	}
	
	private void changeVisible(){
		/*
		if(visible){
			visible=false;
		}else visible=true;*/
		visible = !visible;
	}
}

