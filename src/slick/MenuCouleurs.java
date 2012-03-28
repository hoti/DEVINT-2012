package slick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.SlickException;

import devintAPI.MenuAbstrait;

public class MenuCouleurs extends MenuAbstrait {

    
	private static final long serialVersionUID = -4950199227428042706L;
	String file = ".." + File.separator + "ressources" + File.separator
            + "couleurs.txt";

    /**
     * constructeur
     * @param title : le nom du jeu
     */
    public MenuCouleurs() {
        super("Choix des couleurs");
        int couleur=1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            couleur=Integer.valueOf(line);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionCourante = couleur;
        setFocusedButton(optionCourante);
    }

    /**
     * renvoie le nom des options du menu vous pouvez d�finir autant d'options
     * que vous voulez
     **/
    protected String[] nomOptions() {
        return new String[] { "Fond Noir", "Fond Blanc", "Retour" };
    }

    /**
     * lance l'action associ�e au bouton n�i la num�rotation est celle du
     * tableau renvoy� par nomOption
     */
    protected void lancerOption(int i) {
        switch (i) {
        case 0:
            try {
                FileWriter w = new FileWriter(file);
                w.write("0");
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setVisible(false); 
            new MenuJeu();
            break;
        case 1:
            try {
                FileWriter w = new FileWriter(file);
                w.write("1");
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setVisible(false); 
            new MenuJeu();
            break;

        case 2:
            this.setVisible(false); 
            new MenuJeu();
            break;
        default:
            System.err.println("action non d�finie");
        }
    }

    // renvoie le fichier wave contenant le message d'accueil
    // ces fichiers doivent �tre plac�s dans ressources/sons/
    protected String wavAccueil() {
        return "../ressources/sons/accueil.wav";
    }

    // renvoie le fichier wave contenant la r�gle du jeu
    protected String wavRegleJeu() {
        return "../ressources/sons/accueil.wav";
    }

}
