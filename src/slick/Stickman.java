package slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

public class Stickman {
	
	private Animation playerAnim;
	private Polygon playerPoly;
	
	private float playerX;
	private float playerY;
	
	private boolean alive;
	private boolean SAUT;
	private boolean GAUCHE;
	private boolean DROITE;
	private boolean enableGravity;
	
	private LastMouvement lastMouvement;
	
	private int step;
	private static final int NB_STEP_SAUT=40;
	
	private static final int WIDTH = 20;
	private static final int HEIGHT =30;
	
	
	private static final String pathAnimGauche="../ressources/images/gif3W.png";
	private static final String pathAnimDroite="../ressources/images/gif4W.png";
	private static final String pathAnimArret="../ressources/images/arretW.png";
	private static final String pathAnimSaut="../ressources/images/tombeW.png";
	private static final String pathAnimDead="../ressources/images/deadW.png";

	
	private SpriteSheet sheet1;
	private SpriteSheet sheet2;
	private SpriteSheet sheet3;
	private SpriteSheet sheet4;
	private SpriteSheet sheet5;
	
	
	/**
	 *Constructeur  
	 */	
	public Stickman(float playerX, float playerY) throws SlickException{
		
		// Position de Stickman et création du polygone servant aux collisions
		this.playerX=playerX;
		this.playerY=playerY;
	
		playerPoly = new Polygon(new float[]{
				playerX,playerY,
				playerX+WIDTH,playerY,
				playerX+WIDTH,playerY+HEIGHT,
				playerX,playerY+HEIGHT
		});
		
		sheet1 = new SpriteSheet(pathAnimArret,WIDTH,HEIGHT);
		sheet2 = new SpriteSheet(pathAnimSaut,WIDTH,HEIGHT);
		sheet3 = new SpriteSheet(pathAnimGauche,WIDTH,HEIGHT);
		sheet4 = new SpriteSheet(pathAnimDroite,WIDTH,HEIGHT);
		sheet5 = new SpriteSheet(pathAnimDead,WIDTH,HEIGHT);
		
		playerAnim = new Animation();
		playerAnim.setAutoUpdate(true);
		this.setAnimArret();
		
		// Initialisation des différents booléans
		alive=true;
		SAUT=false;
		GAUCHE=false;
		DROITE=false;
		enableGravity=false;
		step=0;
		
	}
	
	
	public Polygon getPlayerPolygon(){
		return playerPoly;
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public void activeGravity(){
		enableGravity=false;	
		step=0;
	}
	
	public void activeSaut(){
		SAUT=true;
	}
	
	public void changeAlive(){
		/*
		if(alive) {
			alive=false;
		} else alive=true;*/
		alive = !alive;
	}
	
	public void changePlayerX(float X){
		playerX=X;
		playerPoly.setX(playerX);
	}
	public void changePlayerY(float Y){
		playerY=Y;
		playerPoly.setY(playerY);
	}
	
	
	public void setPlayerX(float X){
		playerX+=X;
		playerPoly.setX(playerX);
	}
	public void setPlayerY(float Y){
		playerY+=Y;
		playerPoly.setY(playerY);
	}
		
	public void moveGauche(){
		if(alive){
			if(SAUT){
				this.setPlayerX((float)-1.5);
			} else this.setPlayerX(-1);
			if(!SAUT){
				if(!GAUCHE ){
					setAnimGauche();
				}
			GAUCHE=true;
			} else GAUCHE=false;
			lastMouvement=LastMouvement.GAUCHE;
		}
	}
	
	public void moveDroite(){
		if(alive){
			if(SAUT){
				this.setPlayerX((float)1.5);
			} else this.setPlayerX(1);
			if(!SAUT){
				if(!DROITE){
					setAnimDroite();
				}
				DROITE=true;
			} else DROITE=false;
			lastMouvement=LastMouvement.DROITE;
		}
	}
	
	public void saut(){
		if(alive){
			if(!SAUT){
				enableGravity=true;
			}
			SAUT=true;
		}
		
	}
	
	public void Gravity(){
		if(alive){
			if(enableGravity){
				if(step<(NB_STEP_SAUT/2)){
					this.setPlayerY(-2);
				}
				else if(step<(NB_STEP_SAUT-NB_STEP_SAUT/6)){
					this.setPlayerY(-1);
				}
				lastMouvement=LastMouvement.SAUT;
				step++;
				if(step>NB_STEP_SAUT){
					activeGravity();
					step=0;
				}
			} else {
				this.setPlayerY(2);
				lastMouvement=LastMouvement.CHUTE;
			}
			if(SAUT){
				setAnimSaut();
			}
		}
		
	}
	
	public void drawPlayer(Graphics g){
		g.drawAnimation(playerAnim, playerX, playerY);
	}
	
	public void cancelLastMouvement(){
		if(alive){
			switch (lastMouvement) 
			{ 
			case GAUCHE:
				if(this.SAUT){
					this.setPlayerX((float)1.5);
					activeGravity();
					step=0;
				} else this.setPlayerX(1);
				break; 
			case DROITE:
				if(this.SAUT){
					this.setPlayerX((float)-1.5);
					activeGravity();
					step=0;
				} else this.setPlayerX(-1);
				break;
			case SAUT:
				this.setPlayerY(2);
				activeGravity();
				step=0;
				break;
			case CHUTE:
				this.setPlayerY(-2);
				activeGravity();
				SAUT=false;
				if((!GAUCHE&&!DROITE) || (GAUCHE&&DROITE)){
					setAnimArret();
					GAUCHE=false;
					DROITE=false;
				}
				break;
			}
		}

	}

	public void enableGauche(){
		GAUCHE=false;
	}
	
	public void enableDroite(){
		DROITE=false;
	}
	
	public void setAnimArret(){
		// Mise en place l'animation représentant stickman
		playerAnim=new Animation();
		for (int frame=0;frame<1;frame++) {
			playerAnim.addFrame(sheet1.getSprite(frame,0), 150);
		}
	}
	public void setAnimSaut(){
		playerAnim=new Animation();
		for (int frame=0;frame<1;frame++) {
			playerAnim.addFrame(sheet2.getSprite(frame,0), 150);
		}
	}
	public void setAnimGauche(){
		playerAnim=new Animation();
		for (int frame=0;frame<6;frame++) {
			playerAnim.addFrame(sheet3.getSprite(frame,0), 150);
		}
	}
	public void setAnimDroite(){
		playerAnim=new Animation();
		for (int frame=0;frame<6;frame++) {
			playerAnim.addFrame(sheet4.getSprite(frame,0), 150);
		}
	}
	public void setAnimDead(){
		playerAnim=new Animation();
		for (int frame=0;frame<1;frame++) {
			playerAnim.addFrame(sheet5.getSprite(frame,0), 150);
		}
	}


}
