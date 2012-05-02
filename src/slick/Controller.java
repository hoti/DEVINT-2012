package slick;

import javax.swing.JFrame;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import t2s.SIVOXDevint;


/**
 * Controller de niveau permettant de passer d'un niveau à un autre
 *
 * @author Gilbert
 *
 */
public class Controller extends StateBasedGame {

	

//	private ArrayList<NiveauAbstrait> listNiveau;
	private SIVOXDevint voix;
	private JFrame frameNiveau;
	private CanvasGameContainer cgc;
	private Music music;

	/**
	 * Constructeur spécifique du controller.
	 *
	 * @param name
	 *            : Nom du premier niveau
	 * @param listNiveau
	 *            : Liste des niveaux
	 * @param voix
	 *            : Synthèse vocale
	 * @throws SlickException
	 */
	 public Controller(String name, /*ArrayList<NiveauAbstrait> listNiveau,*/SIVOXDevint voix) throws SlickException {
		 super(name);
		 this.voix = voix;
	//	 this.listNiveau = listNiveau;
		 
		 /* On définit la musique du jeu */
		 this.music = new Music(
				 "../ressources/music/planet_adventure_happiness.ogg");
		 
		 /* On attache le controlleur à chaque niveau */
	/*	 for (NiveauAbstrait niveau : listNiveau) {
			niveau.setController(this);
		 }*/
	 }

	 @Override
	 public void initStatesList(GameContainer container) throws SlickException {
		 
		 /* On ajoute tous les niveaux au controller */
		/* for (NiveauAbstrait niveau : listNiveau) {
			 addState(niveau);
		 }*/
	 }
	 
	 public void run() {
		 try {
			 
			 cgc = new CanvasGameContainer(this);
			 
			 frameNiveau = new JFrame();
			 this.setFrameNiveau(frameNiveau);
			 frameNiveau.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 frameNiveau.setExtendedState(JFrame.MAXIMIZED_BOTH);
			 
			 frameNiveau.add(cgc);
			 frameNiveau.setVisible(true);
			 
			 // On se place sur le canvas contenant l'interface de jeu
			 cgc.requestFocus();
			 cgc.start();
			 
		 } catch (SlickException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 /**
	  * Lance le premier niveau du controller.
	  */
	 public void goToFirstNiveau() {
		 run();
		 // enterState(0);
		 // currentNiveau = (Niveau) this.getState(0);
	 }
	 
	 public SIVOXDevint getVoix() {
		 return voix;
	 }
	 
	 public void setVoix(SIVOXDevint voix) {
		 this.voix = voix;
	 }
	 
	 /**
	  * Permet d'accéder au prochain niveau.
	  */
	 public void goToNextNiveau() {
		 enterState(this.getCurrentStateID() + 1);
		 
		 /* On lance la fenêtre contenant les résultat du dernier niveau*/
	/*	 new ScoreNiveau("Résultats du niveau " +
				 ControlleurJeu.getProfil().getLastIdNiveau());
	  */      }	
	 
	 /**
	  * Permet d'accéder au prochain niveau après le tuto.
	  */
	 public void goToNextNiveauAfterTuto() {
		 enterState(this.getCurrentStateID() + 1);
	 }
	 
	 public boolean isNextNiveau() {
		 if (this.getState(this.getCurrentStateID() + 1) != null)
			 return true;
		 else
			 return false;
	 	}	
	 
	 public CanvasGameContainer getCgc() {
		 return cgc;
	 }
	 
	 public void setCgc(CanvasGameContainer cgc) {
		 this.cgc = cgc;
	 }
	 
	 public JFrame getFrameNiveau() {
		 return frameNiveau;
	 }
	 
	 public void setFrameNiveau(JFrame frameNiveau) {
		 this.frameNiveau = frameNiveau;
	 }
	 
	 public Music getMusic() {
		 return music;
	 }
	 
	 public void setMusic(Music music) {
		 this.music = music;
	 }
	 

	 
}	
		