package slick;

import java.util.ArrayList;

public class Playground {

	
	private BlockMap map;
	
	private int posXStart;
	private int posYStart;
	private int posXFinish;
	private int posYFinish;
	
	
	private ArrayList<Obstacle> listeObstacles;

	
	public Playground(BlockMap map,int posXStart, int posYStart, int posXFinish, int posYFinish){
		
		this.map=map;
		
		this.posXStart=posXStart;
		this.posYStart=posYStart;
		this.posXFinish=posXFinish;
		this.posYFinish=posYFinish;
		
		this.listeObstacles=new ArrayList<Obstacle>();
		this.listeObstacles.add(new PlateformMvt(6,3,300,10,1,Direction.X,1));
		this.listeObstacles.add(new PlateformFlash(6,24,150,4,1,true));
		this.listeObstacles.add(new PlateformFlash(18,20,150,4,1,true));
		this.listeObstacles.add(new PlateformFlash(24,13,150,4,1,true));
		this.listeObstacles.add(new PlateformFlash(12,9,150,4,1,true));
		
	}
	
	
	public ArrayList<Obstacle> getListeObstacles(){
		return this.listeObstacles;
	}
}
