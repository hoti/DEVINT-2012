package slick;





import java.util.ArrayList;
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


import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
 
public class Game extends BasicGame {
 

	private Stickman joueur;

	private ArrayList<Playground> listMap;
	private Playground map;
	

	private int step;
	private int nbMap;
	
	private boolean begin;
	
	public Game() {
		super("StickMan");	
		begin=true;
		
	}
 
	public void init(GameContainer container) throws SlickException {
	
		if(begin){
			nbMap=0;
			listMap=new ArrayList<Playground>();
			Playground map1 = new Playground(new BlockMap("../ressources/images/map1.tmx"),0,25,21,26);
			Playground map2 = new Playground(new BlockMap("../ressources/images/map2.tmx"),0,25,1,3);
			Playground map3 = new Playground(new BlockMap("../ressources/images/map3.tmx"),0,3,37,18);
			
			map1.addObstacle(new PlateformMvt(27,20,135,4,1,Direction.Y,1,null,false));
			
			
			map2.addObstacle(new PlateformMvt(15,20,64,4,1,Direction.X,1,null,false));
			map2.addObstacle(new PlateformMvt(37,5,335,3,1,Direction.Y,1,null,false));
			map2.addObstacle(new Obstacle(14,25,26,1,"../ressources/images/pique.png",true));
			
			
			map3.addObstacle(new Obstacle(39,3,1,10,"../ressources/images/piqueG.png",true));
			map3.addObstacle(new Obstacle(22,17,1,5,"../ressources/images/piqueG.png",true));
			map3.addObstacle(new Obstacle(22,28,3,1,"../ressources/images/pique.png",true));
			map3.addObstacle(new PlateformFlash(28,12,200,4,1,"../ressources/images/pique.png",true,true));
			map3.addObstacle(new PlateformFlash(19,12,200,4,1,"../ressources/images/pique.png",true,true));
			map3.addObstacle(new PlateformFlash(10,12,200,4,1,"../ressources/images/pique.png",true,true));
			map3.addObstacle(new PlateformFlash(3,13,250,4,2,null,false,true));
			map3.addObstacle(new PlateformMvt(-8,25,90,24,3,Direction.X,1,null,false));
			map3.addObstacle(new PlateformMvt(30,20,135,4,1,Direction.Y,1,null,false));
			
			listMap.add(map1);
			listMap.add(map2);
			listMap.add(map3);
			begin=false;
		}
		
		container.setVSync(true);
		container.setMaximumLogicUpdateInterval(22);
		container.setMinimumLogicUpdateInterval(22);
		container.setShowFPS(false);
		container.setFullscreen(true);

		
		map=(Playground)listMap.get(nbMap);
		
		joueur=new Stickman(map.getXStart(),map.getYStart());
		step=0;
		
	}
 
	public void update(GameContainer container, int delta) throws SlickException {
		

		if(map.endLevel(joueur.getPlayerPolygon())){
			if(nbMap<2){
				nbMap++;
				this.init(container);
			}else{ /*nbMap=0;begin=false;*/
				joueur.changePlayerX(map.getXStart());
				joueur.changePlayerY(map.getYStart());
			}
			
			/*joueur.setAlive();
			map=(Playground)listMap.get(nbMap);
			joueur.changePlayerX(map.getXStart());
			joueur.changePlayerY(map.getYStart());
			*/
		}
		
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
			joueur.setPlayerY(1);
			if(entityCollisionWith()){
				joueur.setPlayerY(-1);
			}
		}else joueur.activeSaut();
		
		joueur.collisionSound();
		//this.Stuck();
		

		if (container.getInput().isKeyPressed(Input.KEY_F11)){
			if(container.isFullscreen()){
				container.setFullscreen(false);
			}else container.setFullscreen(true);
			
		}
		if (container.getInput().isKeyPressed(Input.KEY_R)){
			//container.reinit();
			joueur.setAlive();
			joueur.changePlayerX(map.getXStart());
			joueur.changePlayerY(map.getYStart());
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			container.exit();
		}
	}
	
	
	
 
	public boolean entityCollisionWith() throws SlickException {
		
		Polygon playerPoly=joueur.getPlayerPolygon();
		
		for (int i = 0; i < map.getMap().entities.size(); i++) {
			Block entity1 = (Block) map.getMap().entities.get(i);
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
			}else if(plateform.getPolygon().getCenterY()>joueur.getPlayerPolygon().getCenterY()){
				joueur.changePlayerY(plateform.getPolygon().getMinY()-joueur.getPlayerPolygon().getHeight());
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
				if(joueur.getAlive()){
					joueur.activeGravity();
					if(plateform.getDir().equals(Direction.X)){
						//if(plateform.getSens()){
							joueur.setPlayerX(plateform.getVitesse());
						//}else joueur.setPlayerX(-1);
					/*}else{
						//if(plateform.getSens()){
							joueur.setPlayerY(plateform.getVitesse());
						//}else joueur.setPlayerY(-1);*/
				}
			}
		}
	}
		
		joueur.setPlayerY(1);
		if(plateform.getPolygon().intersects(joueur.getPlayerPolygon())&& joueur.getAlive()){
			joueur.setPlayerY(-1);
			if(plateform.getDanger()){
				joueur.setDead();
			}
			else if(plateform.getDir().equals(Direction.X)){
				if(plateform.getSens()){
					joueur.setPlayerX(1);
					if(entityCollisionWith()){
						joueur.setPlayerX(-1);
					}
				}else {
					joueur.setPlayerX(-1);
					if(entityCollisionWith()){
						joueur.setPlayerX(1);
					}
				}
			}else{
				if(plateform.getSens()){
					joueur.setPlayerY(+1);
					if(entityCollisionWith()){
						joueur.setPlayerY(-1);
					}
				}else{
					joueur.setPlayerY(-1);
					if(entityCollisionWith()){
						joueur.setPlayerY(1);
					}
				}
			}
		}else joueur.setPlayerY(-1);
		
	}
	
	public void Stuck() throws SlickException{
		if(entityCollisionWith()){
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
		

	}
 
	public void render(GameContainer container, Graphics g)  {
		
		map.getMap().tmap.render(0,0);
		joueur.drawPlayer(g);

		g.setColor(Color.black);
		g.setBackground(Color.white);

		map.draw(g);

	}
	
	public void launch() throws SlickException{
		AppGameContainer container = 
			new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
 

}