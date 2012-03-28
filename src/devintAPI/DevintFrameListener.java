package devintAPI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import t2s.SIVOXDevint;

/**
 * classe abstraite pour un JFrame qui r�agit aux �v�nements clavier
 * 
 * les menus des jeux DeViNT et les fen�tres des jeux DeViNT doivent r�agir 
 * aux �v�nements clavier de cette fa�on.
 * 
 * vous pouvez surcharger les m�thodes  dans les classes filles en ajoutant des
 * touches auxquelles votre jeu r�agit.
 * 
 * @author helen
 *
 */
public abstract class DevintFrameListener extends JFrame implements KeyListener {

	
	private static final long serialVersionUID = -9104164594210828832L;
	// la voix pour annoncer les actions
    protected SIVOXDevint voix; 

	/**
	 * renvoie le nom du fichier wav � lire quand la fen�tre s'ouvre
	 * Nota : le chemin est relatif au r�pertoire bin
	 */
	protected abstract String wavAccueil();

	/**
	 * renvoie le nom du fichier wav � lire pour l'objectif du jeu
	 * qui est lu en activant F1
	 */
	protected abstract String wavRegleJeu();
	
	
	/** 
	 * definit comment la fen�tre change de couleur quand on clique sur F3
	 * Cette m�thode est appel�e dans la classe Preferences, sur toutes les fen�tres ouvertes
	 */
	public abstract void changeColor() ;
	
	
	public DevintFrameListener(String title) {
		super(title);
    	// prend toute la taille de la fen�tre
    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // on ferme la fen�tre en cliquant sur la croix 
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     	// �coute les �v�nements clavier
       	addKeyListener(this);
       	//ajoute cette fen�tre aux Frame reconnus par Preferences pour g�rer 
    	// le changement de couleur/voix
    	Preferences.getData().addDevintFrame(this);
       	// on r�cup�re la voix donn�e dans les pr�f�rences
    	voix = Preferences.getData().getVoice();
 	}
	
    /** gestion des touches
     * ESC fait sortir de la fen�tre courante
     * F1 invoque l'aide
     * Cette m�thode peut �tre surcharg�e par h�ritage pour r�agir � d'autres touches
     * (voir un exemple dans la classe Jeu)
     */
    public void keyPressed(KeyEvent e) {
    	// toujours arr�ter la voix courante quand l'utilisateur fait une action
    	voix.stop();
    	// escape = sortir
    	if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
    		dispose();
    	}
    	// F1 = regle du jeu
    	if (e.getKeyCode()==KeyEvent.VK_F1){
    		voix.playWav(wavRegleJeu());
    	}
		// F3 = on passe � la couleur suivante dans le jeu des 
    	// couleurs d�fini dans Preferences
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			Preferences.getData().changeColor();
		}
		// F4 = voix suivante d�fini dans Preferences
		if (e.getKeyCode() == KeyEvent.VK_F4) {
			Preferences.getData().changeVoice();
		}
    }
    
    // m�thodes n�cessaires pour l'interface KeyListener
    // � red�finir si besoin dans les classes filles pour g�rer les clics souris
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e){} {}
}


