/*
* Copyright 2007-2011, HÃ©lÃ¨ne Collavizza, Jean-Paul Stromboni
* 
* This file is part of project 'Modele_de_Jeu'
* 
* 'Modele_de_Jeu' is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* 'Modele_de_Jeu'is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with 'Modele_de_Jeu'. If not, see <http://www.gnu.org/licenses/>.
*/
package jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;

import devintAPI.FenetreAbstraite;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Cette classe est un exemple d'utilisation d'un fichier
 * 
 * @author helene
 * @author Jean-Paul, mars 2011
 */

public class Fichier extends FenetreAbstraite implements ActionListener{

	// le bouton pour créer un fichier
	// doit être accessible dans la méthode actionPerformed 
	private JButton ecrire;

	// le bouton pour lire un fichier
	private JButton lire;

	// appel au constructeur de la classe mère
    public Fichier(String title) {
    	super(title);
     }

    // définition de la méthode abstraite "init()"
    // initialise le frame 
    protected void init() {
    	// BorderLayout, voir http://java.sun.com/docs/books/tutorial/uiswing/layout/border.html
    	setLayout(new BorderLayout());
 
    	String text = " Voici le chemin vers un fichier score.txt " +
		"pour y sauver les scores et les paramètres:\n.";
    	text += ".." + File.separator + "ressources" + File.separator + "score.txt\n\n";
    	text+= "Cliquez sur le bouton du haut et vérifiez si le fichier a été créé\n";
       	text+= "Cliquez sur le bouton du bas pour lire les scores\n\n";
      	text += "Lisez le code de la méthode actionPerformed pour savoir comment écrire dans le fichier";

     	JTextArea lb1 = new JTextArea (text); 
    	lb1.setLineWrap(true);
    	lb1.setEditable(false);
    	lb1.setFont(new Font("Georgia",1,30));
    	// on place le premier composant en bas
    	this.add(lb1,BorderLayout.CENTER);
    	
    	// bouton pour lancer l'écriture dans le fichier
    	ecrire = new JButton();
    	ecrire.setText("Cliquer pour écrire le fichier des scores");
    	ecrire.setBackground(new Color(255,105,180));
    	ecrire.setBorder(new LineBorder(Color.BLACK,5));
     	ecrire.setFont(new Font("Georgia",1,40));
     	// c'est l'objet Jeu lui-même qui réagit au clic souris
       	ecrire.addActionListener(this);
    	// on met le bouton en haut
     	this.add(ecrire,BorderLayout.NORTH);
     	
      	// bouton pour lancer la lecture dans le fichier
    	lire = new JButton();
    	lire.setText("Cliquer pour lire le fichier");
    	lire.setBackground(new Color(55,34,255));
    	lire.setForeground(new Color(250,250,210));
    	lire.setBorder(new LineBorder(Color.BLACK,10));
    	lire.setFont(new Font("Georgia",1,40));
     	// c'est l'objet Jeu lui-même qui réagit au clic souris
    	lire.addActionListener(this);
    	// on met le bouton en haut
     	this.add(lire,BorderLayout.SOUTH);
     	
   }

    // lire la question si clic sur le bouton 
    public void actionPerformed(ActionEvent ae){
       	// toujours stopper la voix avant de parler
    	voix.stop();
    	// on récupère la source de l'évènement
     	Object source = ae.getSource();
     	// si c'est le bouton "ecrire" 
    	if (source.equals(ecrire)) {
    		String text = " voici le chemin vers un fichier score.txt " +
    				"pour y sauver les scores et les paramètres.";
    		voix.playText(text);
    		String chemin = ".." + File.separator + "ressources" + File.separator + "score.txt";
    		// écriture dans le fichier score
    		try {
    			FileWriter w = new FileWriter(chemin);
    			w.write("Hélène : 15\n");
    			w.write("Jean-Paul : 20\n");
    			w.write("Catherine : 16\n");
    			w.close();
    		}
    		catch (IOException e) {
    			System.out.println("pb ecriture fichier");
    			e.printStackTrace();
    		}
    	}
    	// si c'est le bouton lire
      	if (source.equals(lire)) {
    		String chemin = ".." + File.separator + "ressources" + File.separator + "score.txt";
    		// on lit le fichier de score et on fait dire chaque ligne par la synthèse vocale
    		try {
    			BufferedReader l = new BufferedReader(new FileReader(chemin));
    			String line = l.readLine();
    	   		while (line != null) {
    	   			voix.playText(line);
    	   			line = l.readLine();
    	   			Thread.sleep(1000);
    	   		}
    			l.close();
    		}
    		catch (IOException e) {
    			System.out.println("pb lecture fichier");
    			e.printStackTrace();
    		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}	

    	
    	// on redonne le focus au JFrame principal 
    	// (après un clic, le focus est sur le bouton)
    	this.requestFocus();
    }

	@Override
	public void changeColor() {
	}

	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueilFichier.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueilFichier.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

}
