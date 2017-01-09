package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Classe qui sert à lire un fichier d'entrée .algo
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Lecteur {

    /**
     * HashMap comportant les lignes du pseudo-code
     * @see Lecteur#getNumLignes()
     */
    private HashMap<Integer, String> numLignes;

    /**
     * Constructeur de la classe Lecteur
     * @param fichier Fichier que l'utilisateur veut lire
     */
    public Lecteur(String fichier) {
        this.numLignes = new HashMap<Integer, String>();

        try {
            Scanner scFichier = new Scanner(new File(fichier));

            int compteur = 0;
            while(scFichier.hasNextLine()) {
                this.numLignes.put(compteur, scFichier.nextLine());

                compteur++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier spécifié (" + fichier + ") est introuvable.");
        }
    }

    /**
     * Permet de retourner la HashMap numLignes
     * @return numLignes
     */
    public HashMap<Integer, String> getNumLignes() {
        return numLignes;
    }
}