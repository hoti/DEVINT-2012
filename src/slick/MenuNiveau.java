package slick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.SlickException;

import devintAPI.MenuAbstrait;

public class MenuNiveau extends MenuAbstrait {

    String file;
    String file2 = ".." + File.separator + "ressources" + File.separator
            + "niveauChoisi.txt";

    private int dernierNiveauAtteint;

    /**
     * constructeur
     * 
     * @param title
     *            : le nom du jeu
     */
    public MenuNiveau() {
        super("Choix du niveau");
        try {
            BufferedReader l = new BufferedReader(new FileReader(file2));
            String line = l.readLine();

            optionCourante = Integer.valueOf(line);
            setFocusedButton(optionCourante);
            l.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * renvoie le nom des options du menu vous pouvez d�finir autant d'options
     * que vous voulez
     **/
    protected String[] nomOptions() {
        file = ".." + File.separator + "ressources" + File.separator
                + "dernierNiveau.txt";
        try {
            BufferedReader l = new BufferedReader(new FileReader(file));
            String line = l.readLine();
            dernierNiveauAtteint=Integer.valueOf(line);
            l.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        String[] noms = new String[this.dernierNiveauAtteint + 2];
        for (int i = 0; i < this.dernierNiveauAtteint + 1; i++) {
            int j = i + 1;
            noms[i] = "Niveau " + j;
        }
        noms[this.dernierNiveauAtteint + 1] = "Retour";
        return noms;
    }

    /**
     * lance l'action associ�e au bouton n�i la num�rotation est celle du
     * tableau renvoy� par nomOption
     */
    protected void lancerOption(int i) {
       
        if (i >= 0 && i < dernierNiveauAtteint + 1) {
            try {
                System.out.println("HELLO "+i);
                FileWriter w = new FileWriter(file2);
                w.write(String.valueOf(i));
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.setVisible(false);
        new MenuJeu();
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
