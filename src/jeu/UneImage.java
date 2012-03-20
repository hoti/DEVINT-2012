package jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import devintAPI.FenetreAbstraite;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Etend DevintFrame pour avoir un Frame et r�agir aux �v�nements claviers
 * Contient un exemple d'affichage d'image proportionnel � la taille de l'�cran
 * 
 * @author helene
 * 
 */

public class UneImage extends FenetreAbstraite {
    JLabel jl2;
    Grille grille;

    public UneImage(String title) {
        super(title);
    }

    // renvoie le fichier wave contenant le message d'accueil
    protected String wavAccueil() {
        return "../ressources/sons/accueilImage.wav";
    }

    // renvoie le fichier wave contenant la r�gle du jeu
    protected String wavRegleJeu() {
        return "../ressources/sons/accueilImage.wav";
    }

    // renvoie le fichier wave contenant la r�gle du jeu
    protected String wavAide() {
        return "../ressources/sons/aide.wav";
    }

    // initialise le frame
    protected void init() {
        // FlowLayout : les composants ont leur taille fix�e par
        // setPreferredSize
        // et sont ajout�s de gauche � droite, de haut en bas

        // la largeur et la hauteur actuelle de la fen�tre
        // si vous fixez la taille des �l�ments graphiques
        // faites le en utilisant des valeurs proportionnelles � la taille
        // de la fen�tre pour que diff�rentes r�solutions d'�cran soient
        // possibles
        int largeur = Toolkit.getDefaultToolkit().getScreenSize().width;
        int hauteur = Toolkit.getDefaultToolkit().getScreenSize().height;

        // une image, voir
        // http://java.sun.com/docs/books/tutorial/uiswing/components/icon.html
        /*
         * ImageIcon icon = new ImageIcon("../ressources/images/stickman.gif");
         * JLabel jl = new JLabel(icon,JLabel.CENTER); jl.setAutoscrolls(true);
         * jl.add(new Scrollbar()); // fond bleu jl.setBackground(Color.BLUE);
         * //composant opaque pour voir le fond bleu jl.setOpaque(true); //
         * (largeur de la fenetre)/2 et (hauteur fenetre)/3
         * jl.setPreferredSize(new Dimension(largeur/2,hauteur/3)); add(jl);
         */

        // L�a
        /*
         * ImageIcon icon = new ImageIcon("../ressources/images/stickman.gif");
         * jl2 = new JLabel(icon); jl2.setLocation(300,100); add(jl2);
         */

        grille = new Grille(largeur, hauteur);
        grille.addKeyListener(this);
        add(grille);

        setVisible(true);
        // addKeyListener(this);
    }

    @Override
    /** 
     * pour cette fen�tre, changer la couleur n'a pas de sens, alors la m�thode
     * ne fait rien
     */
    public void changeColor() {
        // TODO Auto-generated method stub
    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        Point nextPosition = new Point(
                grille.getTableDesObjets()[0].getAbscisse(),
                grille.getTableDesObjets()[0].getOrdonnee());
        Point nextPosition2 = new Point(
                grille.getTableDesObjets()[0].getAbscisse(),
                grille.getTableDesObjets()[0].getOrdonnee());
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            nextPosition2 = new Point(
                    grille.getTableDesObjets()[0].getAbscisse() - 10,
                    grille.getTableDesObjets()[0].getOrdonnee());
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            nextPosition2 = new Point(
                    grille.getTableDesObjets()[0].getAbscisse() + 10,
                    grille.getTableDesObjets()[0].getOrdonnee());
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            nextPosition2 = new Point(
                    grille.getTableDesObjets()[0].getAbscisse(),
                    grille.getTableDesObjets()[0].getOrdonnee() + 10);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            nextPosition2 = new Point(
                    grille.getTableDesObjets()[0].getAbscisse(),
                    grille.getTableDesObjets()[0].getOrdonnee() - 10);
            /*
            Thread t = new Thread() {
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        grille.getTableDesObjets()[0]
                                .deplacer(new Point(grille.getTableDesObjets()[0]
                                        .getAbscisse(),
                                        grille.getTableDesObjets()[0]
                                                .getOrdonnee() - 10));
                        grille.repaint();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 10; i++) {
                        grille.getTableDesObjets()[0]
                                .deplacer(new Point(grille.getTableDesObjets()[0]
                                        .getAbscisse(),
                                        grille.getTableDesObjets()[0]
                                                .getOrdonnee() + 10));
                        grille.repaint();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
            */
        }
        boolean check=true;
        for(int i=1; i<grille.getNbFigures();i++)
        {   
            if(((Rectangle) grille.getTableDesObjets()[i]).intersects(new Rectangle(((Rectangle) grille.getTableDesObjets()[0]).getLongueur(),((Rectangle) grille.getTableDesObjets()[0]).getHauteur() ,nextPosition2 )))
            {
                check=false;
            }                
        }
        
        if(check)
        {
            nextPosition=nextPosition2;
        }
        
        int x=(int)((jeu.Rectangle) grille.getTableDesObjets()[0]).getLongueur();
        int y=(int)((jeu.Rectangle) grille.getTableDesObjets()[0]).getHauteur();
        grille.getTableDesObjets()[0].deplacer(nextPosition2);
       grille.repaint((int)nextPosition.abscisse(),(int)nextPosition.ordonnee(),
        		x,y);
        grille.repaint((int)nextPosition2.abscisse(),(int)nextPosition2.ordonnee(),
        		x,y);
        
       //grille.repaint();
    }

    
}
