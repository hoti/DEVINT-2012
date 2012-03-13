package jeu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image extends Rectangle {
    protected BufferedImage img;

    public Image(Point p, BufferedImage img) {
        super(img.getWidth(),img.getHeight(),p);
        this.img = img;
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
        g.drawImage(img, (int) origine.x, (int) origine.y, null);
    }
}
