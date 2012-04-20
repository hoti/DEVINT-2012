package slick;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class Playground {

	
	private BlockMap map;
	
	private int posXStart;
	private int posYStart;
	private int posXFinish;
	private int posYFinish;
	
	private ArrayList<Obstacle> listeObstacles;


	
	public Playground(BlockMap map,int posXStart, int posYStart, int posXFinish, int posYFinish) throws SlickException{

		
		this.map=map;
		
		this.posXStart=posXStart*16;
		this.posYStart=posYStart*16;
		this.posXFinish=posXFinish*16;
		this.posYFinish=posYFinish*16;
		
		this.listeObstacles=new ArrayList<Obstacle>();
		
		
		

	}
	
	
	public ArrayList<Obstacle> getListeObstacles(){
		return this.listeObstacles;
	}
	
	public void addObstacle(Obstacle o){
		listeObstacles.add(o);
	}
	
	
	public void draw(Graphics g){
		
		map.draw(g);
		Iterator<Obstacle> itr=listeObstacles.iterator();
		while(itr.hasNext()){
			Obstacle o=(Obstacle)itr.next();
			o.drawObstacle(g);
		}
		
		
		
	}
	
	public int getXStart(){
		return this.posXStart;
	}
	public int getYStart(){
		return this.posYStart;
	}
	
	public int getXFinish(){
		return this.posXFinish;
	}
	public int getYFinish(){
		return this.posYFinish;
	}
	
	
	
	public BlockMap getMap(){
		return map;
	}
	public void setMap(BlockMap m){
		map=m;
	}
	
	public boolean endLevel(Polygon p){
		return p.intersects(new Polygon(new float[]{
				posXFinish,posYFinish,
				posXFinish+32,posYFinish,
				posXFinish+32,posYFinish+32,
				posXFinish,posYFinish+32
		}));		
	}
}
