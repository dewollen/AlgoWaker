package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe qui sert à lire un fichier pseudo-code d'entrée .algo
 *
 * @author Win'Rs
 * @version 2017-01-09
 */
public abstract class Lecteur {
    /**
     * Initialise le tableau contenant le fichier à lire
     *
     * @param fichier à lire
     * @return le fichier sous forme de tableau
     */
    public static String[] getPseudoCode(String fichier) {
        Scanner scFichier;
        String sTemp = "";

        // Remplissage du tableau
        try {
            scFichier = new Scanner(new File(fichier));

            while (scFichier.hasNextLine())
                sTemp += scFichier.nextLine() + "\n";
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier spécifié (" + fichier + ") est introuvable.");

            // Pause avant de quitter le programme
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            System.exit(-1);
        }

        return sTemp.split("\n");
    }
}