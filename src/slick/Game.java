package slick;


/*
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Color;
*/

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
 
public class Game extends BasicGame {
 

	private Stickman joueur;
	private Playground map;	
	private int step;
	
	public Game() {
		super("StickMan");
	}
 
	public void init(GameContainer container) throws SlickException {
	
		container.setVSync(true);
		container.setMaximumLogicUpdateInterval(15);
		container.setMinimumLogicUpdateInterval(15);
		container.setFullscreen(true);
		map = new Playground(new BlockMap("../ressources/images/map2.tmx"),0,0,0,0);
		
	

		joueur=new Stickman(340,240);
		step=0;
	}
 
	public void update(GameContainer container, int delta) throws SlickException {
		step++;

		
		for(Obstacle o : map.getListeObstacles()){
			o.act(step);
			if(o.getClass()==PlateformFlash.class){
				PlateformFlash o1=(PlateformFlash)o;
				if(o1.isVisible() && o1.getPolygon().intersects(joueur.getPlayerPolygon())){
					joueur.activeGravity();
					if(o1.getPolygon().getCenterY()<joueur.getPlayerPolygon().getCenterY()){
						joueur.changePlayerY(o1.getPolygon().getMaxY());
						
					} else joueur.changePlayerY(o1.getPolygon().getMinY());
				}
			} else if(o.getClass()==PlateformMvt.class){
				PlateformMvt o2=(PlateformMvt)o;
				if(o2.getPolygon().intersects(joueur.getPlayerPolygon())){
					joueur.activeGravity();
					if(o2.getDir().equals(Direction.X)){
						if(o2.getSens()){
							joueur.setPlayerX(1);
						} else joueur.setPlayerX(-1);
					} else{
						if(o2.getSens()){
							joueur.setPlayerY(1);
						} else joueur.setPlayerY(-1);
					}
				}
				joueur.setPlayerY(1);
				if(o2.getPolygon().intersects(joueur.getPlayerPolygon())){
					if(o2.getDir().equals(Direction.X)){
						if(o2.getSens()){
							joueur.setPlayerX(1);
						}else joueur.setPlayerX(-1);
					}else{
						if(o2.getSens()){
							joueur.setPlayerY(1);
						}else joueur.setPlayerY(-1);
					}
				}
				joueur.setPlayerY(-1);
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


	
		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)){
			container.setFullscreen(false);
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
		//Iterator itr=map.getListeObstacles().iterator();
		for(Obstacle o : map.getListeObstacles()){
			// Obstacle o=(Obstacle)itr.next();
			if(o.getClass()==PlateformFlash.class){
				PlateformFlash o1=(PlateformFlash)o;
				if(o1.isVisible() && o1.getPolygon().intersects(playerPoly)){
					return true;
				}
			}else if(o.getPolygon().intersects(playerPoly)){
				return true;
			}
		}
		return false;
	}
	
 	public void render(GameContainer container, Graphics g)  {
		BlockMap.tmap.render(0,0);
		joueur.drawPlayer(g);

		g.setColor(Color.black);
		g.setBackground(Color.white);
		// Iterator itr=map.getListeObstacles().iterator();
		for(Obstacle o : map.getListeObstacles()){
			// Obstacle o=(Obstacle)itr.next();
			o.drawObstacle(g);
		}
	}
 
	public void launch() throws SlickException{
		AppGameContainer container = 
			new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
}