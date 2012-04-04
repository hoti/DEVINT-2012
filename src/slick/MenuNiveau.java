package slick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import devintAPI.MenuAbstrait;

public class MenuNiveau extends MenuAbstrait {

    private static final long serialVersionUID = 953412138516837540L;
	String file;
    String file2 = ".." + File.separator + "ressources" + File.separator
            + "niveauChoisi.txt";

    private int dernierNiveauAtteint;

    /**
     * Constructeur
     * @param title: le nom du jeu
     */
    public MenuNiveau() {
        super("Choix du niveau");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file2));
            String line = br.readLine();

            optionCourante = Integer.valueOf(line);
            setFocusedButton(optionCourante);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * renvoie le nom des options du menu vous pouvez définir autant d'options
     * que vous voulez
     **/
    protected String[] nomOptions() {
        file = ".." + File.separator + "ressources" + File.separator
                + "dernierNiveau.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            dernierNiveauAtteint=Integer.valueOf(line);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        String[] noms = new String[this.dernierNiveauAtteint + 2];
        for (int i = 0; i < this.dernierNiveauAtteint + 1; i++) {
            // int j = i + 1;
            noms[i] = "Niveau " + (i+1);
        }
        noms[this.dernierNiveauAtteint + 1] = "Retour";
        return noms;
    }

    /**
     * lance l'action associï¿½e au bouton nï¿½i la numï¿½rotation est celle du
     * tableau renvoyï¿½ par nomOption
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
    // ces fichiers doivent ï¿½tre placï¿½s dans ressources/sons/
    protected String wavAccueil() {
        return "../ressources/sons/accueil.wav";
    }

    // renvoie le fichier wave contenant la rï¿½gle du jeu
    protected String wavRegleJeu() {
        return "../ressources/sons/accueil.wav";
    }

}
