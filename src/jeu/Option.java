package jeu;


import java.awt.Font;

import javax.swing.JTextArea;

import devintAPI.*;

/** Cette classe est un exemple d'interface pour les options
 * 
 */
public class Option extends FenetreAbstraite{

    public Option(String title) {
    	super(title);
    }
    
    // un label
	// est une variable d'instance car il doit �tre accessible 
	// dans la m�thode changeColor, qui g�re les pr�f�rences
	private JTextArea lb1;
    
	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueilOption.wav";
	}
	
	// renvoie le fichier wave contenant la r�gle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueilOption.wav";
	}
	
	// renvoie le fichier wave contenant la r�gle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

    public void init() {
    	String text = "Il peut �tre tr�s utile de g�rer des options.\n";
    	text += "Par exemple, la difficult� du jeu, le nombre de joueurs, ...\n\n";
    	text+= "Essayez de taper F3";
    	lb1 = new JTextArea (text);
    	lb1.setLineWrap(true);
    	lb1.setEditable(false);
    	lb1.setFont(new Font("Times",1,80));
    	this.add(lb1);
    }
    
	/**
	 * Pour modifier les couleurs de fond et de premier plan de la fen�tre
	 * Cette fonction est appel�e par la fonction "changeColor" de la classe "Preferences"
	 * � chaque fois que l'on presse F3 
	 * 
	 * on change la couleur du texte principal
	 **/
	public  void changeColor() {
    	// on r�cup�re les couleurs de base dans la classe Preferences 
		Preferences pref = Preferences.getData();
		lb1.setBackground(pref.getCurrentBackgroundColor());
		lb1.setForeground(pref.getCurrentForegroundColor());
	}

}
