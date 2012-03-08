package devintAPI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import t2s.SIVOXDevint;

/**
 * classe abstraite pour un JFrame qui réagit aux évênements clavier
 * 
 * les menus des jeux DeViNT et les fenêtres des jeux DeViNT doivent réagir 
 * aux évênements clavier de cette façon.
 * 
 * vous pouvez surcharger les méthodes  dans les classes filles en ajoutant des
 * touches auxquelles votre jeu réagit.
 * 
 * @author helen
 *
 */
public abstract class DevintFrameListener extends JFrame implements KeyListener {

	// la voix pour annoncer les actions
    protected SIVOXDevint voix; 

	/**
	 * renvoie le nom du fichier wav à lire quand la fenêtre s'ouvre
	 * Nota : le chemin est relatif au répertoire bin
	 */
	protected abstract String wavAccueil();

	/**
	 * renvoie le nom du fichier wav à lire pour l'objectif du jeu
	 * qui est lu en activant F1
	 */
	protected abstract String wavRegleJeu();
	
	
	/** 
	 * definit comment la fenêtre change de couleur quand on clique sur F3
	 * Cette méthode est appelée dans la classe Preferences, sur toutes les fenêtres ouvertes
	 */
	public abstract void changeColor() ;
	
	
	public DevintFrameListener(String title) {
		super(title);
    	// prend toute la taille de la fenêtre
    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // on ferme la fenêtre en cliquant sur la croix 
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     	// écoute les évènements clavier
       	addKeyListener(this);
       	//ajoute cette fenêtre aux Frame reconnus par Preferences pour gérer 
    	// le changement de couleur/voix
    	Preferences.getData().addDevintFrame(this);
       	// on récupère la voix donnée dans les préférences
    	voix = Preferences.getData().getVoice();
 	}
	
    /** gestion des touches
     * ESC fait sortir de la fenêtre courante
     * F1 invoque l'aide
     * Cette méthode peut être surchargée par héritage pour réagir à d'autres touches
     * (voir un exemple dans la classe Jeu)
     */
    public void keyPressed(KeyEvent e) {
    	// toujours arrêter la voix courante quand l'utilisateur fait une action
    	voix.stop();
    	// escape = sortir
    	if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
    		dispose();
    	}
    	// F1 = regle du jeu
    	if (e.getKeyCode()==KeyEvent.VK_F1){
    		voix.playWav(wavRegleJeu());
    	}
		// F3 = on passe à la couleur suivante dans le jeu des 
    	// couleurs défini dans Preferences
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			Preferences.getData().changeColor();
		}
		// F4 = voix suivante défini dans Preferences
		if (e.getKeyCode() == KeyEvent.VK_F4) {
			Preferences.getData().changeVoice();
		}
    }
    
    // méthodes nécessaires pour l'interface KeyListener
    // à redéfinir si besoin dans les classes filles pour gérer les clics souris
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e){} {}
}


