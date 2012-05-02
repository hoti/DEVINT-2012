/*  Classe de menu de lancement de l'exemple de jeu.
 *  Cette classe h�rite de la classe abstraite MenuAbstrait en d�finissant les m�thodes :
 *     - nomOptions qui renvoie la liste des options possibles pour le menu 
 *     - lancerOption qui associe une action � chaque option du menu
 *     - wavAccueil() qui renvoie le nom du fichier wav lu lors de l'accueil dans le menu
 *     - wavAide() qui renvoie le nom du fichier wav lu lors de l'activation de la touche F1
 */

package slick; 

import org.newdawn.slick.SlickException;

import devintAPI.MenuAbstrait;

public class MenuJeu extends MenuAbstrait {

	
	private static final long serialVersionUID = 4221219143089907105L;

	/** constructeur
	 * @param title : le nom du jeu 
	 */
	public MenuJeu() {
		super("STICKMAN");
	}

	/** renvoie le nom des options du menu
     * vous pouvez definir autant d'options que vous voulez
     **/
	protected String[] nomOptions() {

		String[] noms = {"Jouer", "Niveau de difficulte", "Choix des couleurs","Scores", "Quitter"};
		return noms;

	}

	/** lance l'action associ�e au bouton n�i
	 * la num�rotation est celle du tableau renvoy� par nomOption
	 */
	protected void lancerOption(int i) {
		switch (i){  
			case 0 : 
				/*try {
					
					new Game().launch();
				} catch (SlickException e) {}
		    	*/
				this.setVisible(false);new MenuJoueur();
				break;
			case 1 : this.setVisible(false); new MenuDifficulte(); break;
			case 2 : this.setVisible(false); new MenuCouleurs(); break;
			case 3 : this.setVisible(false); /*new Scores();*/ break; 
			case 4 : System.exit(0); break;
			default: System.err.println("action non d�finie");
		}
	} 

	// renvoie le fichier wave contenant le message d'accueil
	// ces fichiers doivent �tre plac�s dans ressources/sons/
	protected  String wavAccueil() {
		return "../ressources/sons/accueil.wav";
	}

	// renvoie le fichier wave contenant la r�gle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueil.wav";
	}
	
}
