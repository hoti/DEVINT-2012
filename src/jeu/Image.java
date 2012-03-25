package jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Image extends Rectangle /*implements ImageObserver*/ {
    protected java.awt.Image img;
    //Graphics bufferGraphics;

    public Image(Point p, java.awt.Image img) {
        super(505,425,p);
        this.img = img;
       // bufferGraphics=img.getGraphics();
    }
    /*
     * (non-Javadoc)
     *  = null;
        try {
            img = ImageIO.read(image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     */

    public void dessiner(Graphics g) {
    	//bufferGraphics.clearRect((int) origine.x,(int) origine.y, 505, 425);
    	//bufferGraphics.setColor(Color.black);    		
    	//bufferGraphics.fillRect((int) origine.x,(int) origine.y, 505, 425);
        g.drawImage(new ImageIcon("../ressources/images/stickman.gif").getImage(), (int) origine.x, (int) origine.y,this);
    }
    
    /*public void update(Graphics g){
    	dessiner(g);
    }*/
    
    
	/*@Override
	public boolean imageUpdate(java.awt.Image arg0, int arg1, int arg2,
			int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}*/


    

}
