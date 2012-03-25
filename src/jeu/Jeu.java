package jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;

import java.awt.*;
import java.awt.event.*;

/** Cette classe est un exemple d'interface de jeu.
 *  Elle �tend DevintFrame pour avoir un Frame et r�agir aux �v�nements claviers
 * Impl�mente ActionListener pour r�agir au clic souris sur le bouton.
 * On surchage la m�thode "keyPressed" pour associer une action � la touche F3
 * 
 * @author helene
 *
 */

public class Jeu extends FenetreAbstraite implements ActionListener{

	// le bouton pour la question
	// est une variable d'instance car il doit �tre accessible 
	// dans la m�thode actionPerformed 
	private JButton question;
	
	// un label
	// est une variable d'instance car il doit �tre accessible 
	// dans la m�thode changeColor, qui g�re les pr�f�rences
	private JTextArea lb1;
	
	// appel au constructeur de la classe m�re
    public Jeu(String title) {
    	super(title);
     }
    
	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueil.wav";
	}
	
	// renvoie le fichier wave contenant la r�gle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/aideF1.wav";
	}
	
	// renvoie le fichier wave contenant la r�gle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

    // d�finition de la m�thode abstraite "init()"
    // initialise le frame 
    protected void init() {
    	// BorderLayout, voir http://java.sun.com/docs/books/tutorial/uiswing/layout/border.html
    	setLayout(new BorderLayout());
 
    	// premier label
    	// ce label est g�r� par les pr�f�rences (cf m�thode changeColor)
    	String text = "L'interface de votre jeu est totalement libre mais vous devez suivre ";
    	text += "les conventions d'utilisation des touches.\n";
    	text += "En h�ritant de \"FenetreAbstraite\", la touche ESC vous permez de sortir,\n"; 
    	text += "la touche F1 d'�couter la r�gle du jeu et la touche F2 d'�couter l'aide\n.";
    	text += "La touche F3 permet de changer le jeu de couleurs, et la touche F4 la voix";
    	text+= "de la synth�se vocale\n";
    	text+= "\nESSAYEZ DE TAPER F1 puis F3\n";
    	text += "\nOn a utilis� un BorderLayout pour placer les �l�ments. Ce texte est au nord.";
     	lb1 = new JTextArea (text); 
    	lb1.setLineWrap(true);
    	lb1.setEditable(false);
    	lb1.setFont(new Font("Georgia",1,30));
    	// on r�cup�re les couleurs de base dans la classe Preferences 
		Preferences pref = Preferences.getData();
		Color foregroundColor = pref.getCurrentForegroundColor();
		Color backgroundColor = pref.getCurrentBackgroundColor();
		lb1.setBackground(backgroundColor);
		lb1.setForeground(foregroundColor);
    	
    	// on place le premier composant en haut
    	this.add(lb1,BorderLayout.NORTH);

    	// deuxi�me label, qui n'est pas g�r� par les pr�f�rences
       	text = "C'est rigolo les jeux DeViNT";
       	text += "\nIci c'est un JLabel avec un bord gris.\n";
       	text += "Il est plac� au centre.";
       	text += "\n\nExemple d'utilisation d'une touche : essayez de taper F5";
    	JTextArea lb2 = new JTextArea (text);
    	lb2.setLineWrap(true);
    	lb2.setEditable(false);
    	lb2.setFont(new Font("Georgia",1,30));
    	// on met un contour gris fonc�
       	lb2.setBorder(new LineBorder(Color.GRAY,5));
       	// on met un fond noir
    	lb2.setBackground(Color.BLACK);
    	// le composant doit �tre opaque pour qu'on voit le fond
       	lb2.setOpaque(true);
    	// on �crit en blanc
       	lb2.setForeground(Color.WHITE);  	
       	// on place ce composant au centre
       	this.add(lb2,BorderLayout.CENTER);

    	// bouton pour poser une question
    	question = new JButton();
    	question.setText("Cliquez sur ce bouton pour �couter la question");
    	question.setBackground(new Color(50,50,255));
    	question.setBorder(new LineBorder(Color.BLACK,10));
     	question.setFont(new Font("Georgia",1,40));
     	// c'est l'objet Jeu lui-m�me qui r�agit au clic souris
       	question.addActionListener(this);
    	// on met le bouton � droite
     	this.add(question,BorderLayout.EAST);
   }

    // lire la question si clic sur le bouton 
    public void actionPerformed(ActionEvent ae){
       	// toujours stopper la voix avant de parler
    	voix.stop();
    	// on r�cup�re la source de l'�v�nement
     	Object source = ae.getSource();
     	// si c'est le bouton "question" on lit la question
     	// le contenu des questions est variable donc on les lit avec SI_VOX
    	if (source.equals(question)) {
    		String text = "les questions sont longues et ont un contenu variable.";
    		text += "Il ne faut pas g�n�rer un fichier wave mais lire directement les textes";
    		voix.playText(text);
    	}	
    	// on redonne le focus au JFrame principal 
    	// (apr�s un clic, le focus est sur le bouton)
    	this.requestFocus();
    }
 
    // �v�nements clavier
    public void keyPressed(KeyEvent e) {
    	// appel � la m�thode m�re qui g�re les �v�nements ESC, F1, F3, F4
    	super.keyPressed(e);
    	// cas particulier pour ce jeu : la touche F5
    	if (e.getKeyCode()==KeyEvent.VK_F5){
    	   	voix.playText("Vous venez d'appuyer sur EFFE 5");
    	}
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
