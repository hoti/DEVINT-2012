package slick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import devintAPI.MenuAbstrait;

public class MenuDifficulte extends MenuAbstrait {

    
	private static final long serialVersionUID = 177608830448719527L;
	String file = ".." + File.separator + "ressources" + File.separator
            + "difficulte.txt";

    /**
     * constructeur
     * @param title: le nom du jeu
     */
    public MenuDifficulte() {
        super("Choix de la difficulte");
        int difficulte=1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            difficulte=Integer.valueOf(line);
            br.close();
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
        return new String[] { "Facile", "Moyen", "Difficile", "Retour" };
    }

    /**
     * lance l'action associee au bouton n�i la num�rotation est celle du
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
            System.err.println("action non definie");
        }
    }

    /** renvoie le fichier wave contenant le message d'accueil
    /* ces fichiers doivent etre places dans ressources/sons/
     */
    protected String wavAccueil() {
        return "../ressources/sons/accueil.wav";
    }

    /** renvoie le fichier wave contenant la regle du jeu
     */
    protected String wavRegleJeu() {
        return "../ressources/sons/accueil.wav";
    }

}
