package jeu;
/*
 * Polygone.java
 */ 

import java.awt.*;
import java.awt.event.*;

/** Classe Polygone à la base de tous les polygones créables
 */
public class Polygone extends Objet{
	protected double ordre; //Ordre (nb de côtés) du polygone
	//Tableaux des coordonnées des points du polygone
	protected int[] x;
	protected int[] y;
	protected boolean polyline;//Si le "polygone" est une ligne brisée
	
	//Constructeurs de Polygone
	public Polygone(double org)
	{
		this(org, new Point(0.0,0.0));
	}
	public Polygone(double org, Point p)
	{
		super(p);
		ordre=org;
	}
	//Constructeurs pour un polygone non normalisé
	public Polygone(int[] a, int[] b, double org ,boolean fill)
	{
		this(a,b,org,fill,false);
	}
	public Polygone(int[] a, int[] b, double org ,boolean fill,boolean pline)
	{
		super(new Point(a[0],b[0]));//l'origine sera le premier point définit
		ordre=org;
		x=a;
		y=b;
		filled=fill;
		polyline=pline;
	}
	
	//Accesseurs des tableaux des coordonnées
	public int[] getAbscissePoint()
	{
		return x;
	}
	public int[] getOrdonneePoint()
	{
		return y;
	}
	//Accesseur de l'ordre du polygone
	public double getNbPoint()
	{
		return ordre;
	}
	//Accesseur pour savoir si le polygone est une ligne brisée
	public boolean getPolyline()
	{
		return polyline;
	}
	
	//Définit les tableaux des coordonnées
	public void setAbscissePoint(int[] a)
	{
		x=a;
	}
	public void setOrdonneePoint(int[] b)
	{
		y=b;
	}
	
	//Méthode pour déplacer les points du polygone
	public void deplacer(Point p)
    {
		/* Rôle : change valeur des coordonnées des points du polygone en fonction du nouveau point d'origine voulu */
		for(int i=0;i<ordre;i++)
		{
			x[i]=x[i] - (int) origine.x + (int) p.x;
			y[i]=y[i] - (int) origine.y + (int) p.y;
		}
		origine=p;
	}
	
	//Appartenance ou non du point au polygone
	public boolean appartient(Point p)
	{
		//Utilisation de Polygon pour simplifier
		Polygon myPolygone=new Polygon(x, y,(int) ordre);
		if(myPolygone.contains(p.abscisse(),p.ordonnee()))
			return true;
		else
			return false;
	}
	
	//Dessine le polygone
	public void dessiner(Graphics g)
	{
		g.setColor(couleur);
		if(polyline==true)//Si ligne brisée
		{
			g.drawPolyline(x,y,(int) ordre);
		}
		else//Si polygone non normalisé
		{
			//On remplit ou non le polygone
			if(filled==true)
			{
				g.fillPolygon(x,y,(int) ordre);
			}
			else
			{
				g.drawPolygon(x,y,(int) ordre);
			}
		}
	}
    @Override
    public boolean intersects(Rectangle r) {
        // TODO Auto-generated method stub
        return false;
    }
}
