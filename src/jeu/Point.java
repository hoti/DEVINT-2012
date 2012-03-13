package jeu;

import java.io.*;

/** Classe Point pour créer les coordonnées des Figures
 */
public class Point implements Serializable
{
	protected double x; //abscisse
	protected double y; //ordonnée
	
	//Constructeurs de Point
	public Point(double a, double b)
	{
		x=a;
		y=b;
	}
	public Point()
	{
		this(0.0,0.0);
	}
	
	//Accesseurs des coordonnées
	public double abscisse()
	{
		return x;
	}
	public double ordonnee()
	{
		return y;
	}
}
