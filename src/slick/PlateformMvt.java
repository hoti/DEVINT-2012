package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class PlateformMvt extends Obstacle {

	private float vitesseDeplacement;
	private int frequence;
	private Direction dir; 
	
	private int width;
	private int height;

	
	public PlateformMvt(float posX, float posY,int frequence,int width,int height, 
			Direction dir,float vitesseDeplacement){
		super(posX*16,posY*16,false);
		
		this.frequence=frequence;
		this.dir=dir;
		this.vitesseDeplacement=vitesseDeplacement;
		
		this.width=width;
		this.height=height;
		this.polygonObstacle=new Polygon(new float[]{posX*16,posY*16,(posX+this.width)*16,posY*16,
				(posX+this.width)*16,(posY+this.height)*16,posX*16,(posY+this.height)*16});
	}
	
	
	public Direction getDir(){
		return dir;
	}
	public boolean getSens(){
		return vitesseDeplacement>0;
	}
	
	public void drawObstacle(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.green);
		g.fill(polygonObstacle);
		g.setColor(c);
		
	}
	
	public void act(int step){
		this.move();
		if(step%this.frequence==0){
			vitesseDeplacement=-vitesseDeplacement;
		}
		
	}
	
	public void move(){
		switch (dir) { 
			case Y: setPosY(vitesseDeplacement); break; 
			case X: setPosX(vitesseDeplacement); break;
		}
	}
	
}
