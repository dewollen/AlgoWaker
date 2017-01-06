package metier;

import bsh.Interpreter;
import util.Lecteur;
import util.donnee.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Classe qui interprète les portions de pseudo-code afin d'agir en conséquence
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Traducteur {
    private Interpreter interpreter;
    private Lecteur     lecteur;
    private ArrayList<Donnee> alDonnee;

    public Traducteur(Lecteur lecteur) {
        this.lecteur = lecteur;
        this.alDonnee = new ArrayList<Donnee>();
    }

    public void chercherConstantes(HashMap<Integer, String> numLignes) {
        for(Integer i:numLignes.keySet()) {
            if(numLignes.get(i).toUpperCase().contains("CONSTANTE")) {
                i++;
                while(!numLignes.get(i).toUpperCase().contains("VARIABLE")) {
                    ArrayList<String> alCons    = new ArrayList<String>();
                    String s = numLignes.get(i).replaceAll(",", " ");
                    String sTemp;
                    String type="";
                    Scanner sc = new Scanner(s);
                    if(sc.hasNext()) {
                        while (!(sTemp = sc.next()).equals(":")) {
                            System.out.println(sTemp);
                            alCons.add(sTemp);
                        }

                        type = sc.next().toLowerCase();
                    }

                    for(String sCons:alCons) {
                        switch(type) {
                            case "booleen" : alDonnee.add(new Booleen(sCons, true, true)); break;
                            case "caractere" : alDonnee.add(new Caractere(sCons, true, true)); break;
                            case "chaine" : alDonnee.add(new Chaine(sCons, true, true)); break;
                            case "chaine de caractere" : alDonnee.add(new Chaine(sCons, true, true)); break;
                            case "entier" : alDonnee.add(new Entier(sCons, true, true)); break;
                            case "reel" : alDonnee.add(new Reel(sCons, true, true));
                            default : break;
                        }
                    }
                    i++;
                }
            }
        }
    }

    public void chercherVariables(HashMap<Integer, String> numLignes) {
        for(Integer i:numLignes.keySet()) {
            if(numLignes.get(i).toUpperCase().contains("VARIABLE")) {
                i++;
                while(!numLignes.get(i).toUpperCase().contains("DEBUT")) {
                    ArrayList<String> alVar    = new ArrayList<String>();
                    String s = numLignes.get(i).replaceAll(",", " ");
                    String sTemp;
                    String type="";
                    Scanner sc = new Scanner(s);
                    if(sc.hasNext()) {
                        while (!(sTemp = sc.next()).equals(":")) {
                            System.out.println(sTemp);
                            alVar.add(sTemp);
                        }

                        type = sc.next().toLowerCase();
                    }

                    for(String sVar:alVar) {
                        switch(type) {
                            case "booleen" : alDonnee.add(new Booleen(sVar, true, false)); break;
                            case "caractere" : alDonnee.add(new Caractere(sVar, true, false)); break;
                            case "chaine" : alDonnee.add(new Chaine(sVar, true, false)); break;
                            case "chaine de caractere" : alDonnee.add(new Chaine(sVar, true, false)); break;
                            case "entier" : alDonnee.add(new Entier(sVar, true, false)); break;
                            case "reel" : alDonnee.add(new Reel(sVar, true, false));
                            default : break;
                        }
                    }
                    i++;
                }
            }
        }
    }

    public ArrayList<Donnee> getAlDonnee() {
        return this.alDonnee;
    }
}
