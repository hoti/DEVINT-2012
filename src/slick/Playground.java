package slick;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
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

		/*
		this.listeObstacles.add(new PlateformMvt(4,4,300,10,1,Direction.X,1,null,false));
		this.listeObstacles.add(new PlateformFlash(6,25,50,4,1,null,false,true));
		this.listeObstacles.add(new PlateformFlash(18,20,150,4,1,null,false,true));
		this.listeObstacles.add(new PlateformFlash(24,13,150,4,1,null,false,true));
		this.listeObstacles.add(new PlateformFlash(12,9,150,4,1,null,false,true));
		this.listeObstacles.add(new Obstacle(20,27,4,1,"ressources/images/pique.png",true));
		this.listeObstacles.add(new PlateformMvt(36,16,176,4,1,Direction.Y,1,"ressources/images/pique.png",true));
		this.listeObstacles.add(new PlateformFlash(12,9,150,4,1,"ressources/images/pique.png",true,false));*/

	}
	
	
	public ArrayList<Obstacle> getListeObstacles(){
		return this.listeObstacles;
	}
	
	public void addObstacle(Obstacle o){
		listeObstacles.add(o);
	}
	
	
	public void draw(Graphics g){
		
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
	public BlockMap getMap(){
		return map;
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
