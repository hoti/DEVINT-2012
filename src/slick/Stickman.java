package slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

/** Classe représentant stickman, le personnage évoluant dans une niveau.
 * Cette classe permet notamment de faire évoluer le personnage sur 2 axes (gestion des gifs inclus).
 * 
 * @author François
 *
 */

public class Stickman {
	
	// Animation servant à gérer les gifs associés au personnage.
	private Animation playerAnim;
	
	// Polygon servant à la gestion des obstacles avec stickman.
	private Polygon playerPoly;
	
	//Position du joueur (correspondant aux coordonnées en haut à droite du polygon associé à ce dernier).
	private float playerX;
	private float playerY;
	
	
	private boolean alive;
	
	// Boolean permettant de mettre en place les différentes animations
	private boolean SAUT;
	private boolean GAUCHE;
	private boolean DROITE;
	
	// Boolean permettant d'activer le saut et inversement
	private boolean enableGravity;
	
	// Boolean permettant de gérer le son associé à la collision.
	private boolean collisionLastStep;
	private boolean collision;
	
	
	// Element de l'enum permettant de "sauver" le dernier mouvement effectuer afin de mieux le supprimer en cas de collision.
	private LastMouvement lastMouvement;
	
	// Element permettant de compter le nombre de tour depuis le début d'un saut.
	private int step;

	
	// Nombre de tour durant lesquels, la gravity sera désactiver pour effectuer un saut.
	private static final int NBSTEPSAUT=45;

	// Taille de l'anime et du polygon associer à stickman (en pixel).
	private static final int WIDTH = 20;
	private static final int HEIGHT =30;
	
	// Différent path pour les différentes animes.
	private static final String pathAnimGauche="../ressources/images/gif3W.png";
	private static final String pathAnimDroite="../ressources/images/gif4W.png";
	private static final String pathAnimArret="../ressources/images/arretW.png";
	private static final String pathAnimSaut="../ressources/images/tombeW.png";
	private static final String pathAnimDead="../ressources/images/deadW.png";
	
	private static final String pathAnimGaucheD="../ressources/images/gif3.png";
	private static final String pathAnimDroiteD="../ressources/images/gif4.png";
	private static final String pathAnimArretD="../ressources/images/arret.png";
	private static final String pathAnimSautD="../ressources/images/tombe.png";
	private static final String pathAnimDeadD="../ressources/images/dead.png";


	// Différent SpriteSheet servant pour les différentes animes.
	private SpriteSheet sheet1;
	private SpriteSheet sheet2;
	private SpriteSheet sheet3;
	private SpriteSheet sheet4;
	private SpriteSheet sheet5;
	
	
	// Différents sons associés aux mouvements
	private Sound fx;
	private Sound collisionSound;
	private Sound gameOver;
	private Sound start;

	
	/**
	 *Constructeur  de base prenant en paramètre les positions du départ d'un niveau.
	 */	
	public Stickman(float playerX, float playerY,boolean darkBackground) throws SlickException{
		
		// Position de Stickman et création du polygone servant aux collisions
		this.playerX=playerX;
		this.playerY=playerY;
	
		playerPoly = new Polygon(new float[]{
				playerX,playerY,
				playerX+WIDTH,playerY,
				playerX+WIDTH,playerY+HEIGHT,
				playerX,playerY+HEIGHT
		});
		
		if(!darkBackground){
		//chargement des différentes animes.
			sheet1 = new SpriteSheet(pathAnimArret,WIDTH,HEIGHT);
			sheet2 = new SpriteSheet(pathAnimSaut,WIDTH,HEIGHT);
			sheet3 = new SpriteSheet(pathAnimGauche,WIDTH,HEIGHT);
			sheet4 = new SpriteSheet(pathAnimDroite,WIDTH,HEIGHT);
			sheet5 = new SpriteSheet(pathAnimDead,WIDTH,HEIGHT);
		}else {
			sheet1 = new SpriteSheet(pathAnimArretD,WIDTH,HEIGHT);
			sheet2 = new SpriteSheet(pathAnimSautD,WIDTH,HEIGHT);
			sheet3 = new SpriteSheet(pathAnimGaucheD,WIDTH,HEIGHT);
			sheet4 = new SpriteSheet(pathAnimDroiteD,WIDTH,HEIGHT);
			sheet5 = new SpriteSheet(pathAnimDeadD,WIDTH,HEIGHT);
		}
		
		playerAnim = new Animation();
		playerAnim.setAutoUpdate(true);
		this.setAnimArret();
		
		// Initialisation des différents booléans
		alive=true;
		SAUT=false;
		GAUCHE=false;
		DROITE=false;
		enableGravity=false;
		collisionLastStep=false;
		collision=false;
		step=0;
		
		
		//chargement des différents sons.
		fx = new Sound("../ressources/sons/3.wav");
		collisionSound = new Sound("../ressources/sons/collision.wav");
		gameOver=new Sound("../ressources/sons/dead.wav");
		start=new Sound("../ressources/sons/start.wav");
		start.play();
		
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
	

	
	public void setDead(){
		if(alive){
			alive=false;
			this.setAnimDead();
			gameOver.play();
		}
	}
	
	public void setAlive(){
		alive=true;
		this.setAnimArret();
		start.play();		

	}
	
	
	// Méthodes permettant de modifier la position du personnage en valeur absolue 
	public void changePlayerX(float X){
		playerX=X;
		playerPoly.setX(playerX);
	}
	public void changePlayerY(float Y){
		playerY=Y;
		playerPoly.setY(playerY);
	}
	
	// Méthodes permettant de modifier la position du personnage en valeur relative
	public void setPlayerX(float X){
		playerX+=X;
		playerPoly.setX(playerX);
	}
	public void setPlayerY(float Y){
		playerY+=Y;
		playerPoly.setY(playerY);
	}
	
	
	
	// Déplacement gauche et mise en place de l'anime gauche si ce mouvement n'était pas d'activer lors du dernier tour	
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
	
	
	// Déplacement droite et mise en place de l'anime gauche si ce mouvement n'était pas d'activer lors du dernier tour
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
	
	// Mise en place du saut. Attention cette méthode ne provoque aucun mouvement du personnage. Elle permet juste d'inverser les effet de la gravité et de jouer le son du saut.
	public void saut(){
		if(alive){
			if(!SAUT){
				enableGravity=true;
				fx.play();
			}
			SAUT=true;
		}
		
	}
	
	// Ceci est la méthode a appelé à chaque tour. Elle permet de gérer la chute et le saut. Elle met en place l'anime de saut si celui-ci est activé. Mise en route du compteur lié au saut.
	public void Gravity(){
		if(alive){
			if(enableGravity){
				if(step<(NBSTEPSAUT/2)){
					this.setPlayerY(-2);
				}
				else if(step<(NBSTEPSAUT-NBSTEPSAUT/6)){
					this.setPlayerY(-1);
				}
				lastMouvement=LastMouvement.SAUT;
				step++;
				if(step>NBSTEPSAUT){
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
	
	
	//Méthode permettant de dessiner sitckman
	public void drawPlayer(Graphics g){
		g.drawAnimation(playerAnim, playerX, playerY);
	}
	
	
	public void setDarkBackground(boolean darkBackground) throws SlickException{
		if(!darkBackground){
		//chargement des différentes animes.
			sheet1 = new SpriteSheet(pathAnimArret,WIDTH,HEIGHT);
			sheet2 = new SpriteSheet(pathAnimSaut,WIDTH,HEIGHT);
			sheet3 = new SpriteSheet(pathAnimGauche,WIDTH,HEIGHT);
			sheet4 = new SpriteSheet(pathAnimDroite,WIDTH,HEIGHT);
			sheet5 = new SpriteSheet(pathAnimDead,WIDTH,HEIGHT);
		}else {
			sheet1 = new SpriteSheet(pathAnimArretD,WIDTH,HEIGHT);
			sheet2 = new SpriteSheet(pathAnimSautD,WIDTH,HEIGHT);
			sheet3 = new SpriteSheet(pathAnimGaucheD,WIDTH,HEIGHT);
			sheet4 = new SpriteSheet(pathAnimDroiteD,WIDTH,HEIGHT);
			sheet5 = new SpriteSheet(pathAnimDeadD,WIDTH,HEIGHT);
		}	
		
	}
	
	
	// Méthode a appelé lors d'un collision. Elle permet d'annulé le dernier mouvement effectué.
	public void cancelLastMouvement(){
		if(alive){
			switch (lastMouvement) 
			{ 
			case GAUCHE:
				if(this.SAUT){
					this.setPlayerX((float)1.5);
					activeGravity();
					step=0;

				}else this.setPlayerX(1);
				this.collision();

				break; 
			case DROITE:
				if(this.SAUT){
					this.setPlayerX((float)-1.5);
					activeGravity();
					step=0;
				}else this.setPlayerX(-1);
				this.collision();

				break;
			case SAUT:
				this.setPlayerY(2);
				activeGravity();
				step=0;
				this.collision();
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
	
	// méthode permettant de gérer le son lié à la collision.
	public void collision(){
		collision=true;
	}
	public void collisionSound(){
		if(collision && !collisionLastStep){
			collisionSound.play();
		}
		collisionLastStep=collision;
		collision=false;
	}
	
	// Mise en place des différentes animation représentant stickman
	public void setAnimArret(){
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
