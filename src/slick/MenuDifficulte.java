package slick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.SlickException;

import devintAPI.MenuAbstrait;

public class MenuDifficulte extends MenuAbstrait {

    String file = ".." + File.separator + "ressources" + File.separator
            + "difficulte.txt";

    /**
     * constructeur
     * 
     * @param title
     *            : le nom du jeu
     */
    public MenuDifficulte() {
        super("Choix de la difficulté");
        int difficulte=1;
        try {
            BufferedReader l = new BufferedReader(new FileReader(file));
            String line = l.readLine();
            difficulte=Integer.valueOf(line);
            l.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionCourante = difficulte;
        setFocusedButton(optionCourante);
    }

    /**
     * renvoie le nom des options du menu vous pouvez d�finir autant d'options
     * que vous voulez
     **/
    protected String[] nomOptions() {
        String[] noms = { "Facile", "Moyen", "Difficile", "Retour" };
        return noms;
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
            try {
                FileWriter w = new FileWriter(file);
                w.write("2");
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setVisible(false); 
            new MenuJeu();
            break;
        case 3:
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
