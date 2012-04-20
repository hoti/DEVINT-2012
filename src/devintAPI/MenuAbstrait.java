/** Une classe abstraite � utiliser pour le menu de lancement du jeu
 *  La classe de menu de votre jeu doit h�riter de cette classe :
 *    - d�finir la m�thode nomOptions qui renvoie un tableau de String qui corresponds aux 
 *    options possibles de votre menu
 *    - d�finir la m�thode lancerOption(int i) qui associe des actions aux options de votre menu 
 *    - d�finir les m�thodes wavAccueil et wavAide qui d�signent le fichier wave � lire pour l'accueil
 *    et l'aide.
 *    
 *  Ne pas modifier cette classe
 *  @author helene
 */

package devintAPI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import t2s.SIVOXDevint;

public abstract class MenuAbstrait extends DevintFrameListener implements
		ActionListener {

	// -------------------------------------------------------
	// les attributs

	
	private static final long serialVersionUID = 5019181205064752768L;

	// le nom du jeu
	protected final String nomJeu;

	// les noms des options
	private String[] nomOptions;

	// les boutons associ�s aux options
	private JButton[] boutonOption;

	// le nombre d'options
	private int nbOption;

	// attributs des textes et des boutons
	// � red�finir dans la classe concr�te si vous le souhaitez
	// en gardant de forts contrastes et peu de couleurs diff�rentes
	protected Font fonteBouton;
	protected Color foregroundColor;
	protected Color backgroundColor;

	// l'option courante qui est s�lectionn�e
	protected int optionCourante;

	// �l�ments de placement des composants
	private GridBagLayout placement; // le layout
	private GridBagConstraints regles; // les regles de placement

	// elements graphiques
	protected JPanel entete;
	protected JLabel lb1;
	protected LineBorder buttonBorder;
	protected LineBorder enteteBorder;

	// -------------------------------------------------
	// les methodes abstraites � d�finir par h�ritage

	/**
	 * renvoie le tableau contenant le nom des options m�thode abstraite �
	 * d�finir par hritage
	 */
	protected abstract String[] nomOptions();

	/**
	 * lance l'action associ�e au bouton n�i m�thode abstraite � d�finir par
	 * h�ritage PRECOND : i varie entre 0 et le nombre d'options possibles moins
	 * 1
	 */
	protected abstract void lancerOption(int i);


	// -------------------------------------------------------
	/**
	 * constructeur,
	 * 
	 * @param title  : le nom du jeu
	*/
	public MenuAbstrait(String title) {
		super(title);
		nomJeu = title;
		optionCourante = -1;
		// methode a rendre concr�te par h�ritage
		nomOptions = nomOptions(); 
		nbOption = nomOptions.length;
		// on recupere les preferences
		Preferences pref = Preferences.getData();
		foregroundColor = pref.getCurrentForegroundColor();
		backgroundColor = pref.getCurrentBackgroundColor();
		// cr�� les �l�ments de la fen�tre
		creerLayout();
		creerEntete();
		creerOption(nomOptions);
	     // visible
    	this.setVisible(true);
    	// a le focus
    	this.requestFocus();
		// lit le message d'accueil
		voix.playWav(wavAccueil());
 
	}

	// -------------------------------------------------------
	// m�thodes utilis�es par le constructeur

	/**
	 * cr�� le layout pour placer les composants
	 */
	private void creerLayout() {
		placement = new GridBagLayout();
		regles = new GridBagConstraints();
		setLayout(placement);
		// par d�faut on �tire les composants horizontalement et verticalement
		regles.fill = GridBagConstraints.BOTH;
		// par d�faut, tous les composants ont un poids de 1
		// on les r�partit donc �quitablement sur la grille
		regles.weightx = 1;
		regles.weighty = 1;
		// espaces au bord des composants
		regles.insets = new Insets(10, 50, 10, 50);
		// pour placer en haut des zones
		regles.anchor = GridBagConstraints.NORTH;
	}

	/**
	 * cr�� l'ent�te avec le nom du jeu
	 */
	public void creerEntete() {

		// panel d'entete de la fen�tre
		entete = new JPanel();
		FlowLayout enteteLayout = new FlowLayout();
		enteteLayout.setAlignment(FlowLayout.CENTER);
		entete.setLayout(enteteLayout);
		enteteBorder = new LineBorder(Color.GRAY, 8);
		entete.setBorder(enteteBorder);

		// le label
		lb1 = new JLabel(nomJeu);
		lb1.setFont(new Font("Georgia", 1, 96));
		lb1.setForeground(foregroundColor);
		lb1.setBackground(backgroundColor);
		entete.add(lb1);

		// placement de l'entete en 1�re ligne, 1�re colonne
		regles.gridx = 1;
		regles.gridy = 1;
		placement.setConstraints(entete, regles);
		add(entete);
	}

	/**
	 * creer les boutons associ�s aux noms d'options
	 */
	private void creerOption(String[] noms) {
		// cr�ation des boutons
		// panel des boutons
		JPanel boutons = new JPanel();
		boutons.setLayout(new GridLayout(nbOption, 1));
		// les boutons
		fonteBouton = new Font("Tahoma", 1, 56);
		buttonBorder = new LineBorder(Color.BLACK, 8);
		boutonOption = new JButton[nbOption];
		for (int i = 0; i < nbOption; i++) {
			creerBouton(i, noms[i]);
			boutons.add(boutonOption[i]);
		}
		// poids relatif de 3 (i.e 3 fois plus grand que l'ent�te)
		regles.weighty = 4;
		// placement des boutons en 2�me ligne, 1�re colonne
		regles.gridx = 1;
		regles.gridy = 2;
		placement.setConstraints(boutons, regles);
		add(boutons);
	}

	// pour cr�er un bouton associ� � un texte
	private void creerBouton(int i, String texte) {
		boutonOption[i] = new JButton();
		boutonOption[i].setText(texte);
		setPropertiesButton(boutonOption[i]);
	}

	// mettre � jour les propri�t�s des boutons
	protected void setPropertiesButton(JButton b) {
		b.setFocusable(false);
		b.setBackground(foregroundColor);
		b.setForeground(backgroundColor);
		b.setFont(fonteBouton);
		b.setBorder(buttonBorder);
		b.addActionListener(this);
	}

	// -------------------------------------------------------
	// m�thodes pour r�agir aux �v�nements clavier


	public void keyPressed(KeyEvent e) {
		// gestion du son d'accueil, des touches F1, ESC, F3 et F4
		// g�r� par DevintFrameListener
		super.keyPressed(e);
		// enter = s�lectionner l'option
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// m�thode � rendre concr�te par h�ritage
			lancerOption(optionCourante);  
		}
		// se d�placer dans les options vers le bas
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (optionCourante == -1) {
				optionCourante = 0;
				setFocusedButton(optionCourante);
			} else {
				unFocusedButton(optionCourante);
				optionCourante = (optionCourante + 1) % nbOption;
				setFocusedButton(optionCourante);
			}
		}
		// se d�placer dans les options vers le haut
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (optionCourante == -1) {
				optionCourante = 0;
				setFocusedButton(optionCourante);
			} else {
				unFocusedButton(optionCourante);
				optionCourante = optionCourante - 1;
				if (optionCourante == -1)
					optionCourante = nbOption - 1;
				setFocusedButton(optionCourante);
			}
		}
	}

	// activer l'option si clic sur le bouton
	public void actionPerformed(ActionEvent ae) {
		voix.stop();
		Object source = ae.getSource();
		for (int i = 0; i < nbOption; i++) {
			if (source == boutonOption[i]) {
				if (optionCourante != -1)
					unFocusedButton(optionCourante);
				optionCourante = i;
				setFocusedButton(optionCourante);
				lancerOption(i);
			}
		}
	}

	// mettre le focus sur une option
	protected void setFocusedButton(int i) {
		voix.playShortText(boutonOption[i].getText());
		boutonOption[i].setBackground(backgroundColor);
		boutonOption[i].setForeground(foregroundColor);
	}

	// enlever le focus d'une option
	protected void unFocusedButton(int i) {
		boutonOption[i].setBackground(foregroundColor);
		boutonOption[i].setForeground(backgroundColor);
	}
	
	//-------------------------------------------
	// m�thodes pour g�rer les couleurs des menus

//	/**
//	 * @author LOGRE Ivan, MULLER Stephane, GUYADER Erwan
//	 **/
//	private void setForegroundColor(Color foregroundColor) {
//		this.couleurBouton = foregroundColor;
//		this.couleurTexteSelectionne = foregroundColor;
//	}
//
//	/**
//	 * @author LOGRE Ivan, MULLER Stephane, GUYADER Erwan
//	 **/
//	private void setBackgroundColor(Color backgroundColor) {
//		this.couleurBoutonSelectionne = backgroundColor;
//		this.couleurTexte = backgroundColor;
//	}

	/**
	 * Modifie les couleurs de fond et de premier plan pour les menus abstraits
	 * Cette fonction est appel�e par la fonction "changeColor" de la classe "Preferences"
	 * � chaque fois que l'on presse F3 
	 * 
	 * Cette fonction peut �tre r��crite dans les classes filles si besoin
	 * @author LOGRE Ivan, MULLER Stephane, GUYADER Erwan, helen
	 **/
	public void changeColor() {
		// on est pass� au jeu de couleurs suivant dans les pr�f�rences
		// on le r�cup�re
		Preferences pref = Preferences.getData();
		foregroundColor = pref.getCurrentForegroundColor();
		backgroundColor = pref.getCurrentBackgroundColor();
		
		// et on met � jour le menu avec ces nouvelles couleurs
		//ent�te
		enteteBorder= new LineBorder(foregroundColor,8);
		entete.setForeground(foregroundColor);
		entete.setBackground(backgroundColor);
		entete.setBorder(enteteBorder);
		//label
		lb1.setForeground(foregroundColor);
		lb1.setBackground(backgroundColor);
		//panel
		this.getContentPane().setForeground(foregroundColor);
		this.getContentPane().setBackground(backgroundColor);
		//boutons
		this.buttonBorder= new LineBorder(backgroundColor,5);
		for (int i=0;i<boutonOption.length;i++) {
			if(i==optionCourante){
				boutonOption[i].setBackground(backgroundColor);
				boutonOption[i].setForeground(foregroundColor);
			}
			else{
				boutonOption[i].setBackground(foregroundColor);
				boutonOption[i].setForeground(backgroundColor);
			}
			boutonOption[i].setBorder(buttonBorder);
		}
	}
 
}
