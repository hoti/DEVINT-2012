package jeu;
/*
 * Rectangle.java
 */ 

import java.awt.*;
import java.awt.event.*;
/** Classe Rectangle (polygone d'ordre quatre particulier)
 */
public class Rectangle extends Polygone
{
	protected double l; //Longueur
	protected double h; //Hauteur
	protected double dx; //Diamètre horizontal des arcs des quatre angles
	protected double dy; //Diamètre vertical des arcs des quatre angles
	
	//Constructeurs de Rectangle
	public Rectangle(double longueur, double hauteur)
	{
		this(longueur,hauteur,new Point());
	}
	public Rectangle(double longueur, double hauteur, Point p)
	{
		this(longueur,hauteur,p,true);
	}
	public Rectangle(double longueur, double hauteur, Point p,boolean fill)
	{
		this(longueur,hauteur,p,fill,-1,-1);
	}
	//Constructeurs de Rectangle aux bords arrondis
	public Rectangle(double longueur, double hauteur, Point p,boolean fill,double da, double db)
	{
		super(4.0,p);
		l=longueur;
		h=hauteur;
		filled=fill;
		dx=da;
		dy=db;
	}
	
	//Accesseur de la taille et des diamètres des arcs du rectangle
	public double getLongueur()
	{
		return l;
	}
	public double getHauteur()
	{
		return h;
	}
	public double getArcX()
	{
		return dx;
	}
	public double getArcY()
	{
		return dy;
	}
	
	//définit la valeur de la taille et des diamètres des arcs du rectangle
	public void changerLongueur(double longueur)
	{
		l=longueur;
	}
	public void changerHauteur(double hauteur)
	{
		h=hauteur;
	}
	public void setArcX(double da)
	{
		dx=da;
	}
	public void setArcY(double db)
	{
		dy=db;
	}
	
	//Retourne le périmètre du rectangle
	public double perimetre()
	{
		return (h+l)*2.0;
	}
	//Retourne le surface du rectangle
	public double surface()
	{
		return h*l;
	}
	
	//Méthode déplacer du rectangle
	public void deplacer(Point p)
	{
		origine=p;
	}
    
	//Appartenance ou non du point au rectangle
	public boolean appartient(Point p)
	{
		if( p.abscisse() < origine.abscisse() || p.ordonnee() < origine.ordonnee())
			return false;
		if(p.abscisse() > origine.abscisse() + l || p.ordonnee() > origine.ordonnee() + h)
			return false;
		return true;
	}
	
	public boolean intersects(Rectangle r)
	{
	    java.awt.Rectangle myR=new java.awt.Rectangle((int) r.origine.x, (int) r.origine.y, (int) r.l, (int) r.h);
	    java.awt.Rectangle myRectangle=new java.awt.Rectangle((int) origine.x, (int) origine.y, (int) l, (int) h);
            return myRectangle.intersects(myR);
	}
	
	//Méthode dessiner du rectangle
	public void dessiner(Graphics g)
	{
		g.setColor(couleur);
		if(filled==true)//Si remplie
		{
			if(dx==-1 || dy==-1)//Si rectangle normal
				g.fillRect((int) origine.x,(int) origine.y,(int) l,(int) h);
			else//Si rectangle arrondi
				g.fillRoundRect((int) origine.x,(int) origine.y,(int) l,(int) h, (int) dx, (int) dy);
		}
		else
		{
			if(dx==-1 || dy==-1)
				g.drawRect((int) origine.x,(int) origine.y,(int) l,(int) h);
			else
				g.drawRoundRect((int) origine.x,(int) origine.y,(int) l,(int) h, (int) dx, (int) dy);
		}
	}
}
