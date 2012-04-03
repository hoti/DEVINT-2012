package slick;




import java.util.Iterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Color;
 
public class Game extends BasicGame {
 

	private Stickman joueur;
	private Playground map;	





	private int step;
	
	public Game() {
		super("StickMan");
	}
 
	public void init(GameContainer container) throws SlickException {
	
		container.setVSync(true);
		container.setMaximumLogicUpdateInterval(20);
		container.setMinimumLogicUpdateInterval(21);
		container.setShowFPS(false);
		//container.setFullscreen(true);
		map = new Playground(new BlockMap("../ressources/images/map2.tmx"),0,0,0,0);
		
		
		joueur=new Stickman(340,240);
		step=0;
	}
 
	public void update(GameContainer container, int delta) throws SlickException {
		step++;


		Iterator<Obstacle> itr=map.getListeObstacles().iterator();
		while(itr.hasNext()){
			Obstacle o=(Obstacle)itr.next();
			o.act(step);
			if(o.getClass()==PlateformFlash.class){
				FlashCollision((PlateformFlash)o);
			}else if(o.getClass()==PlateformMvt.class){
				MvtCollision((PlateformMvt)o);
			}
		}
	
		
		


		
		
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			joueur.moveGauche();
			if (entityCollisionWith()){
				joueur.cancelLastMouvement();
			}
		}else{
			joueur.enableGauche();
		}
			
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			joueur.moveDroite();
			if (entityCollisionWith()){
				joueur.cancelLastMouvement();
			}
		}else{
			joueur.enableDroite();
		}

		if (container.getInput().isKeyPressed(Input.KEY_UP)){
			joueur.saut();
			
		}
		
		joueur.Gravity();
		if (entityCollisionWith()){
			joueur.cancelLastMouvement();
			
		}else joueur.activeSaut();
		
		joueur.collisionSound();
		this.Stuck();
		


	
		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)){
			container.setFullscreen(false);
		}
		if (container.getInput().isKeyDown(Input.KEY_R)){
			joueur.setAlive();
			joueur.changePlayerX(0);
			joueur.changePlayerY(0);
		}
	}
	
 
	public boolean entityCollisionWith() throws SlickException {
		
		Polygon playerPoly=joueur.getPlayerPolygon();
		
		for (int i = 0; i < BlockMap.entities.size(); i++) {
			Block entity1 = (Block) BlockMap.entities.get(i);
			if (playerPoly.intersects(entity1.poly)|| playerPoly.getX() <0 ||
					playerPoly.getMaxX() >BlockMap.mapWidth ||  playerPoly.getY() <0||
					playerPoly.getMaxY() >BlockMap.mapHeight){
				return true;
			}    
		}
		Iterator<Obstacle> itr=map.getListeObstacles().iterator();
		while(itr.hasNext()){
			Obstacle o=(Obstacle)itr.next();
			if(o.getClass()==PlateformFlash.class){
				PlateformFlash plateform=(PlateformFlash)o;
				if(plateform.isVisible() && plateform.getPolygon().intersects(playerPoly)){
					if(plateform.getDanger()){
						joueur.setDead();
					}
					return true;
				}
			}else if(o.getPolygon().intersects(playerPoly)){
				if(o.getDanger()){
					joueur.setDead();
				}
				return true;
			}
		}
		
		return false;
	}
	
	public void FlashCollision(PlateformFlash plateform) throws SlickException{

		if(plateform.isVisible() && plateform.getPolygon().intersects(joueur.getPlayerPolygon())){
			joueur.activeGravity();
			if(plateform.getDanger()){
				joueur.setDead();
			}else if(plateform.getPolygon().getCenterY()<joueur.getPlayerPolygon().getCenterY()){
				joueur.changePlayerY(plateform.getPolygon().getMinY()-joueur.getPlayerPolygon().getHeight()-1);
			}else joueur.changePlayerY(plateform.getPolygon().getMaxY());
		}
		this.Stuck();
	}
	
	public void MvtCollision(PlateformMvt plateform) throws SlickException{

		if(plateform.getPolygon().intersects(joueur.getPlayerPolygon())){
			if(plateform.getDanger()){
				joueur.setDead();
			}else{
				this.Stuck();
				joueur.activeGravity();
				if(joueur.getAlive()){
					if(plateform.getDir().equals(Direction.X)){
						if(plateform.getSens()){
							joueur.setPlayerX(1);
						}else joueur.setPlayerX(-1);
				}else{
					if(plateform.getSens()){
						joueur.setPlayerY(1);
					}else joueur.setPlayerY(-1);
				}
			}
		}
	}
		
		joueur.setPlayerY(1);
		if(plateform.getPolygon().intersects(joueur.getPlayerPolygon())&& joueur.getAlive()){
			if(plateform.getDanger()){
				joueur.setDead();
			}
			else if(plateform.getDir().equals(Direction.X)){
				if(plateform.getSens()){
					joueur.setPlayerX(1);
				}else joueur.setPlayerX(-1);
			}else{
				if(plateform.getSens()){
					joueur.setPlayerY(1);
				}else joueur.setPlayerY(-1);
			}
		}
		joueur.setPlayerY(-1);
		
	}
	
	public void Stuck() throws SlickException{
		joueur.setPlayerX(-1);
		if(entityCollisionWith()){
			joueur.setPlayerX(+2);
			if(entityCollisionWith()){
				joueur.setPlayerX(-1);
				joueur.setPlayerY(+1);
				if(entityCollisionWith()){
					joueur.setPlayerY(-2);
					if(entityCollisionWith()){
						joueur.setDead();
					}
					joueur.setPlayerY(+1);
				}else joueur.setPlayerY(-1);
			}else joueur.setPlayerX(-1);
		}else joueur.setPlayerX(+1);
		

	}
 
	public void render(GameContainer container, Graphics g)  {
		
		BlockMap.tmap.render(0,0);
		joueur.drawPlayer(g);

		g.setColor(Color.black);
		g.setBackground(Color.white);
		Iterator<Obstacle> itr=map.getListeObstacles().iterator();
		while(itr.hasNext()){
			Obstacle o=(Obstacle)itr.next();
			o.drawObstacle(g);
		}		
 
	}
 
	public void launch() throws SlickException{
		AppGameContainer container = 
			new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
}