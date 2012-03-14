package jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class Objet extends JPanel{
    protected Point origine;
    protected Color couleur; // Couleur de la figure
    protected boolean filled; //figure remplie ou pas
    
    // Constructeurs de Figure
    public Objet() {
        origine=new Point(0.0,0.0);
    }

    public Objet(Point p) {
        origine = p;
    }

    public Objet(double a, double b) {
        origine=new Point(a,b);
    }

    public Objet(double a, double b, Color c) {
        origine=new Point(a,b);
        couleur = c;
    }

    // Accesseurs des coordonnées de la figure
    public double getAbscisse() {
        return origine.x;
    }

    public double getOrdonnee() {
        return origine.y;
    }

    // Accesseur de la couleur de la figure
    public Color couleur() {
        return couleur;
    }

    // méthodes abstraites
    public abstract void dessiner(Graphics g);
    public abstract boolean appartient(Point p);
    public abstract boolean intersects(Rectangle r);
    
    // public abstract boolean appartient(double x, double y); //Appartenance ou
    // non du point à la figure

    // Pour déplacer la figure
    public void deplacer(Point p) {
        origine = p;
    }

    public void setFill(boolean fill)
    {
            filled=fill;
    }
    
    // Changer la couleur de la figure
    public void changerCouleur(Color c) {
        couleur = c;
    }
}