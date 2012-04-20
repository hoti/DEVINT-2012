package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class PlateformMvt extends Obstacle {

	private float vitesseDeplacement;
	private Direction dir; 
	private int frequence;
	


	
	public PlateformMvt(int posX, int posY,int frequence,int width,int height, 
			Direction dir,float vitesseDeplacement,String pathObstacle,boolean danger) throws SlickException{
		super(posX,posY,width,height,pathObstacle,danger);
		
		this.frequence=frequence;

		this.dir=dir;
		this.vitesseDeplacement=vitesseDeplacement;
		this.frequence = frequence;
		
	}
	
	public Direction getDir(){
		return dir;
	}
	
	public boolean getSens(){
		return vitesseDeplacement > 0;
	}
	

	public void act(int step){
		if(step%this.frequence== 0){
			vitesseDeplacement =- vitesseDeplacement;
		}
		this.move();
	}
	
	public float getVitesse(){
		return vitesseDeplacement;
	}
	
	public void move(){
		switch (dir) { 
			case Y: setPosY(vitesseDeplacement); break; 
			case X: setPosX(vitesseDeplacement); break;
		}
	}

	
}
