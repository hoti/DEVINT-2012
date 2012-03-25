package jeu;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;


public class Grille extends Canvas {
    private static final int NBFIGMAX=10000;

    private static final int FREQ = 2000;
    
    private int longueur;
    private int hauteur;
    private int nbFigures=0;//Nombre de figures actuel
    
    private Objet[] tableDesObjets;
    private Timer gravity = new Timer(FREQ,new GravityListener());
    
    public Grille(int l, int h)
    {
        gravity.start();
        hauteur=h;
        longueur=l;
        tableDesObjets=new Objet[NBFIGMAX];
        File file=new File("../ressources/images/stickman.gif");
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        tableDesObjets[0]=new Image(new Point(100,100), img);
        nbFigures++;
        tableDesObjets[1]=new Rectangle(l,200, new Point(0,h-200));
        tableDesObjets[1].changerCouleur(Color.WHITE);
        nbFigures++;
        
        
    }
    
    public int getNbFigures()
    {
        return nbFigures;
    }
    
    public Objet[] getTableDesObjets()
    {
        return tableDesObjets;
    }
    
    //Méthode permettant de dessiner la grille et les figures
    public void paint(Graphics g)
    {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.black);
            g.fillRect(0,0,longueur,hauteur);//noirci le fond de la grille

            //Pour un effet d'anti-aliasing sur les figures
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                                    RenderingHints.VALUE_ANTIALIAS_ON);
            /** Antécédent : nbFigures>=0 */
            /* Rôle : dessine la tableDesFigures */
            for(int i=0; i<nbFigures; i++)
            {
                tableDesObjets[i].dessiner(g2);//on dessine les figures avec de l'anti-aliasing
            }
    }
    
    private class GravityListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            tableDesObjets[0].deplacer(new Point(tableDesObjets[0].getAbscisse(),tableDesObjets[0].getOrdonnee()+10));
        }
    }
    /*public void update(Graphics g){
    	tableDesObjets[0].dessiner(g);
    }*/
    
}
