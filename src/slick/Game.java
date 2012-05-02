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
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Color;


import org.newdawn.slick.*;

import com.sun.xml.internal.ws.api.server.Container;


public class Game extends BasicGame {
 

	private Stickman joueur;

	//Liste des différentes maps.

	private Playground map;
	

	private int step;
	private int nbMap;
	private int score;

	private UnicodeFont font;
	
	private String pathPique;
	private String pathPiqueG;
	
	private Sound win;
	
	private boolean begin;
	private boolean endLevel;
	private boolean dark;
	public Game() {
		super("StickMan");	
		begin=true;
		nbMap=1;
		pathPique="../ressources/images/pique.png";
		pathPiqueG="../ressources/images/piqueG.png";
		
	}
 
	public void init(GameContainer container) throws SlickException {
	
		if(begin){
			container.setVSync(true);
			container.setMaximumLogicUpdateInterval(15);
			container.setMinimumLogicUpdateInterval(15);
			container.setShowFPS(false);
			//container.setFullscreen(true);
			
			
			 this.font = new UnicodeFont("../ressources/images/impact.ttf", 48,
					    false, false);
			 this.font.getEffects().add(new ColorEffect(java.awt.Color.white));
			 this.font.addAsciiGlyphs();
			 this.font.loadGlyphs();
			
			begin=false;			
			win=new Sound("../ressources/sons/gagne.wav");
			dark=false;
			
		
		}
		
		//Chargement des map et des obstacles.
		switch(nbMap){
		case 1:
			Playground map1 = new Playground(new BlockMap("../ressources/images/map1.tmx"),0,25,21,26);
			map1.addObstacle(new PlateformMvt(27,20,135,4,1,Direction.Y,1,null,false));
			map=map1;
			break;
		case 2:
			Playground map2 = new Playground(new BlockMap("../ressources/images/map2.tmx"),0,25,1,3);
			map2.addObstacle(new PlateformMvt(15,20,64,4,1,Direction.X,1,null,false));
			map2.addObstacle(new PlateformMvt(37,5,335,3,1,Direction.Y,1,null,false));
			map2.addObstacle(new Obstacle(14,25,26,1,pathPique,true));
			map=map2;
			break;
		case 3: 
			Playground map3 = new Playground(new BlockMap("../ressources/images/map3.tmx"),0,3,37,18);
			map3.addObstacle(new Obstacle(39,3,1,10,pathPiqueG,true));
			map3.addObstacle(new Obstacle(22,17,1,5,pathPiqueG,true));
			map3.addObstacle(new Obstacle(22,28,3,1,pathPique,true));
			map3.addObstacle(new PlateformFlash(28,12,200,4,1,pathPique,true,true));
			map3.addObstacle(new PlateformFlash(19,12,200,4,1,pathPique,true,false));
			map3.addObstacle(new PlateformFlash(10,12,200,4,1,pathPique,true,true));
			map3.addObstacle(new PlateformFlash(3,13,150,4,2,null,false,true));
			map3.addObstacle(new PlateformMvt(-8,25,90,25,3,Direction.X,1,null,false));
			map3.addObstacle(new PlateformMvt(30,20,135,4,1,Direction.Y,1,null,false));
			map=map3;
			break;
		}
		
		
		joueur=new Stickman(map.getXStart(),map.getYStart(),dark);
		step=0;
		score=5000;
		endLevel=false;
		

		
	}
 
	public void update(GameContainer container, int delta) throws SlickException {
		
		
		// Gestion de différentes touches.
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)){
			if(endLevel){
				if(nbMap<3){
					nbMap++;		
				}else{ 
					nbMap=1;
				}
				container.resume();
				this.init(container);
			}
			
		}
		if (container.getInput().isKeyPressed(Input.KEY_F11)){
			if(container.isFullscreen()){
				container.setFullscreen(false);
			}else container.setFullscreen(true);
			
		}
		if(container.getInput().isKeyPressed(Input.KEY_BACK)){
			this.init(container);
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			container.exit();
		}
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)){			
			if(!container.isPaused()){
				container.setPaused(true);
			}else container.resume();
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_F3)){
			dark=!dark;
			joueur.setDarkBackground(dark);
			if(dark){
				pathPique="../ressources/images/piqueD.png";
				pathPiqueG="../ressources/images/piqueGD.png";
			}else{
				pathPique="../ressources/images/pique.png";
				pathPiqueG="../ressources/images/piqueG.png";
			}
					
			this.init(container);
			
			
		}
		

		
		
		if(container.isPaused()){
			return;
		}
		
		if(map.endLevel(joueur.getPlayerPolygon())){
			win.play();
			endLevel=true;
			container.pause();
		}
		
		step++;
		if((step%10)==0 && score>0){
			score-=10;
		}
		
		
		//Gestion des conflits liés à l'apparition d'obstacles ou au mouvement de ces derniers.
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
		/* Gestion des mouvements de Stickman :

		Si(appui sur la touche)
			déplacement adéquates du personnage
			Si(collision)
				annultion du mouvement effectué via la méthode cancelLastMouvement().		
		*/
		
		// Gestion de l'appui sur la touche directionnelle gauche.
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			joueur.moveGauche();
			if (entityCollisionWith()){
				joueur.cancelLastMouvement();
			}
		}else{
			joueur.enableGauche();
		}
			
		// Gestion de l'appui sur la touche directionnelle droite.
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			joueur.moveDroite();
			if (entityCollisionWith()){
				joueur.cancelLastMouvement();
			}
		}else{
			joueur.enableDroite();
		}

		// Gestion de l'appui sur la touche directionnelle haut.
		if (container.getInput().isKeyPressed(Input.KEY_UP) || container.getInput().isKeyPressed(Input.KEY_SPACE)){
			joueur.saut();
		}
		
		
		// Appel de la méthode Gravity permettant de gérer la chute et le saut.
		joueur.Gravity();
		if (entityCollisionWith()){
			joueur.cancelLastMouvement();
			joueur.setPlayerY(1);
			if(entityCollisionWith()){
				joueur.setPlayerY(-1);
			}
		}else joueur.activeSaut();
		
		
		// Si il ya eu une "nouvelle" collision lors ce tour, le son lié à la collision est joué.
		joueur.collisionSound();
		
		


		
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
				
				if(joueur.getAlive()){
					joueur.activeGravity();
					if(plateform.getDir().equals(Direction.X)){
						joueur.setPlayerX(plateform.getVitesse());
					}else{
						joueur.setPlayerY(plateform.getVitesse());

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
					joueur.setPlayerX(plateform.getVitesse());
					if(entityCollisionWith()){
						joueur.setPlayerX(-plateform.getVitesse());
					}
				}else {
					joueur.setPlayerX(plateform.getVitesse());
					if(entityCollisionWith()){
						joueur.setPlayerX(-plateform.getVitesse());
					}
				}
			}else{
				if(plateform.getSens()){
					joueur.setPlayerY(plateform.getVitesse());
					if(entityCollisionWith()){
						joueur.setPlayerY(-plateform.getVitesse());
					}
				}else{
					joueur.setPlayerY(plateform.getVitesse());
					if(entityCollisionWith()){
						joueur.setPlayerY(-plateform.getVitesse());
					}
				}
			}
		}else joueur.setPlayerY(-1);
		
		this.Stuck();
	}
	
	// méthode permettant de vérifier si le joueur n'est pas "coincé". Si tel est le cas, mise à mort du personnage.
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
		
		Color c1;
		Color c2;
		Color c3;
		if(dark){
			c1=Color.black;
			c2=Color.white;
			c3=Color.cyan;
		}else{
			c1=Color.white;
			c2=Color.black;
			c3=Color.blue;
		}


		g.setColor(c2);
		g.setBackground(c1);

		
		map.getMap().tmap.render(0,0);
		joueur.drawPlayer(g);
		map.draw(g);
		
		g.setColor(c1);
		g.drawString("Score : "+score,10, 450);
		g.setColor(c2);
		
		g.setColor(c3);
		g.fillRect(map.getXFinish(), map.getYFinish()-16, 32, 48);
		g.setColor(c2);
		
		
		if(container.isPaused()){
			
			g.setColor(c2);
			g.fillRect(100,100, 440,280);
			g.setColor(c1);
			g.drawRect(104,104, 432,272);
			
			
			String Line1;
			if(endLevel){
				Line1="WIN !!!";
			}else Line1="PAUSE";
			
			
			font.drawString(640/2-(font.getWidth(Line1) / 2), 150,Line1,c1);
			String Line2="LEVEL 0"+nbMap+"; Score : "+score;
			font.drawString(640/2-(font.getWidth(Line2) / 2), 150+2*font.getHeight("A"),Line2,c1);
		}

	}
	
	public void launch() throws SlickException{
		AppGameContainer container = 
			new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
 

}