package metier;

import bsh.EvalError;
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
    private Lecteur lecteur;
    private HashMap<Integer, String> numLignes;
    private boolean creeCons;
    private boolean creeVar;
    private boolean debutAlgo;

    private ArrayList<Donnee> alConstante;
    private ArrayList<Donnee> alVariable;

    public Traducteur(Lecteur lecteur) {
        this.interpreter = new Interpreter();
        this.lecteur = lecteur;

        this.creeCons = false;
        this.creeVar = false;
        this.debutAlgo = false;

        this.numLignes = lecteur.getNumLignes();

        this.alConstante = new ArrayList<>();
        this.alVariable = new ArrayList<>();
    }

    public void traiteLigne(Integer ligne) {
        String[] tabLigne;
        String type = "";
        String ligneCourante = this.numLignes.get(ligne).replaceAll("◄—", "<-");
        if (!ligneCourante.equals("")) {
            if (ligneCourante.toLowerCase().contains("constante")) {

                creeCons = true;

            } else if (ligneCourante.toLowerCase().contains("variable")) {

                creeCons = false;
                creeVar = true;

            } else if (ligneCourante.toLowerCase().contains("debut")) {

                creeVar = false;
                debutAlgo = true;

            } else if (debutAlgo) {

                if (ligneCourante.contains("<-")) {
                    String[] tabAffectation = ligneCourante.split("<-");
                    tabAffectation[0] = tabAffectation[0].replaceAll("[\t ]", "");
                    tabAffectation[1] = tabAffectation[1].replaceAll(" ", "");
                    System.out.println(tabAffectation[0] + " --> " + tabAffectation[1]);
                    try {
                        interpreter.eval("" + tabAffectation[0] + " = " + tabAffectation[1]);
                        System.out.println(interpreter.get(tabAffectation[0]));
                        for(int i=0; i<alVariable.size(); i++) {
                            if(alVariable.get(i).getNom().equals(tabAffectation[0])) {
                                alVariable.get(i).setValeur("" + interpreter.get(tabAffectation[0]));
                            }
                        }
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

            } else if (creeVar) {

                tabLigne = ligneCourante.split(":");
                tabLigne[0] = tabLigne[0].replaceAll("[\t ]", "");
                tabLigne[1] = tabLigne[1].replaceAll(" ", "");
                String[] tabNomVar = tabLigne[0].split(",");
                type = tabLigne[1];

                ajouteVariable(tabNomVar, type);

            } else if (creeCons) {

                tabLigne = ligneCourante.split("<-");
                tabLigne[0] = tabLigne[0].replaceAll("[\t ]", "");
                tabLigne[1] = tabLigne[1].replaceAll(" ", "");
                String[] tabNomCons = tabLigne[0].split(",");
                type = rechercheType(tabLigne[1]);

                ajouteConstante(tabNomCons, type, tabLigne[1]);
            }
        }
    }

    private String rechercheType(String s) {
        if (s.contains("\"")) {
            return "Chaine de caractere";
        } else if (s.contains("\'")) {
            return "Caractere";
        } else if (s.contains(",")) {
            return "Reel";
        } else if (s.matches("[0-9]+")) {
            return "Entier";
        } else if (s.toLowerCase().equals("vrai") || s.toLowerCase().equals("faux")) {
            return "booleen";
        } else {
            System.out.println("Variable non trouvable");
            return "";
        }
    }

    private void ajouteConstante(String[] tab, String type, String valeur) {
        type = type.toLowerCase();
        String s = "";
        boolean suivi = false;
        for (int i = 0; i < tab.length; i++) {
            switch (type) {
                case "booleen":
                    alConstante.add(new Booleen(tab[i], suivi, true));
                    try {
                        interpreter.eval("final boolean " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "caractere":
                    alConstante.add(new Caractere(tab[i], suivi, true));
                    try {
                        interpreter.eval("final char " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "chaine de caractere":
                    alConstante.add(new Chaine(tab[i], suivi, true));
                    try {
                        interpreter.eval("final String " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "entier":
                    alConstante.add(new Entier(tab[i], suivi, true));
                    try {
                        interpreter.eval("final int " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "reel":
                    alConstante.add(new Reel(tab[i], suivi, true));
                    try {
                        interpreter.eval("final double " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                default:
                    System.out.println("TYPE NON TROUVE");
                    break;
            }
        }
    }

    public void ajouteVariable(String[] tab, String type) {
        type = type.toLowerCase();
        String s = "";
        boolean suivi = false;
        for (int i = 0; i < tab.length; i++) {
            System.out.println("Voulez-vous suivre la trace de la variable : " + tab[i] + " (o/n)");
            int cpt = 0;
            do {
                if (cpt == 3) System.out.println("Veuillez entrer o ou n");
                Scanner sc = new Scanner(System.in);
                s = sc.next().toLowerCase();
                if (s.equals("o")) {
                    suivi = true;
                }
                if (s.equals("n")) {
                    suivi = false;
                }
                cpt++;
            } while (!s.equals("o") && !s.equals("n"));
            switch (type) {
                case "booleen":
                    alVariable.add(new Booleen(tab[i], suivi, false));
                    try {
                        interpreter.eval("boolean " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "caractere":
                    alVariable.add(new Caractere(tab[i], suivi, false));
                    try {
                        interpreter.eval("char " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "chaine de caractere":
                    alVariable.add(new Chaine(tab[i], suivi, false));
                    try {
                        interpreter.eval("String " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "entier":
                    alVariable.add(new Entier(tab[i], suivi, false));
                    try {
                        interpreter.eval("int " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "reel":
                    alVariable.add(new Reel(tab[i], suivi, false));
                    try {
                        interpreter.eval("double " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public ArrayList<Donnee> getAlConstante() {
        return alConstante;
    }

    public ArrayList<Donnee> getAlVariable() {
        return alVariable;
    }
}
