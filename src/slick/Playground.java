package slick;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Playground {

	
	private BlockMap map;
	
	private int posXStart;
	private int posYStart;
	private int posXFinish;
	private int posYFinish;
	
	
	private ArrayList<Obstacle> listeObstacles;

	
	
	public Playground(BlockMap map,int posXStart, int posYStart, int posXFinish, int posYFinish) throws SlickException{
		
		this.map=map;
		
		this.posXStart=posXStart;
		this.posYStart=posYStart;
		this.posXFinish=posXFinish;
		this.posYFinish=posYFinish;
		
		this.listeObstacles=new ArrayList<Obstacle>();
		this.listeObstacles.add(new PlateformMvt(4,4,300,10,1,Direction.X,1,null,false));
		this.listeObstacles.add(new PlateformFlash(6,25,50,4,1,null,false,true));
		this.listeObstacles.add(new PlateformFlash(18,20,150,4,1,null,false,true));
		this.listeObstacles.add(new PlateformFlash(24,13,150,4,1,null,false,true));
		this.listeObstacles.add(new PlateformFlash(12,9,150,4,1,null,false,true));
		this.listeObstacles.add(new Obstacle(20,27,4,1,"../ressources/images/pique.png",true));
		this.listeObstacles.add(new PlateformMvt(36,16,176,4,1,Direction.Y,1,"../ressources/images/pique.png",true));
		this.listeObstacles.add(new PlateformFlash(12,9,150,4,1,"../ressources/images/pique.png",true,false));
	}
	
	
	public ArrayList<Obstacle> getListeObstacles(){
		return this.listeObstacles;
	}
}
