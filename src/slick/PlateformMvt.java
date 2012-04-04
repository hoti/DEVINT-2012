package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class PlateformMvt extends Obstacle {

	private float vitesseDeplacement;
	private int frequence;
	private Direction dir; 
	


	
	public PlateformMvt(int posX, int posY,int frequence,int width,int height, 
			Direction dir,float vitesseDeplacement,String pathObstacle,boolean danger) throws SlickException{
		super(posX,posY,width,height,pathObstacle,danger);
		
		this.frequence=frequence;
		this.dir=dir;
		this.vitesseDeplacement=vitesseDeplacement;
		
	}
	
	
	public Direction getDir(){
		return dir;
	}
	public boolean getSens(){
		if(vitesseDeplacement>0){
			return true;
		}else return false;
	}
	
	public void act(int step){
		this.move();
		if(step%this.frequence==0){
			vitesseDeplacement=-vitesseDeplacement;
		}
		
	}
	
	public float getVitesse(){
		return vitesseDeplacement;
	}
	
	public void move(){
		switch (dir) 
		{ 
		case Y: setPosY(vitesseDeplacement);
			break; 
		case X: setPosX(vitesseDeplacement);
			break;
		}
	}
	
}
