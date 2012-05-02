package slick;

import org.newdawn.slick.SlickException;

import devintAPI.MenuAbstrait;

public class MenuJoueur extends MenuAbstrait {

	public MenuJoueur() {
		super("Choix du Joueur");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String[] nomOptions() {
		String[] noms = {"Nouveau Joueur","Sélectionner un Joueur", "Retour"};
		return noms;
	}

	@Override
	protected void lancerOption(int i) {
		switch (i){  
		case 0 : this.setVisible(false);new MenuCreationJoueur();break;
		case 1 : this.setVisible(false); break;
		case 2 : this.setVisible(false);new MenuJeu(); break;
		default: System.err.println("action non définie");
		}
		
	}

	@Override
	protected String wavAccueil() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String wavRegleJeu() {
		// TODO Auto-generated method stub
		return null;
	}

}
