package metier;

import bsh.EvalError;
import bsh.Interpreter;
import util.donnee.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui interprète les portions de pseudo-code afin d'agir en conséquence
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Traducteur {
    private Interpreter interpreter;

    // $$$$$$$$$$$$$$$$$$$$$$$$$
    // $$$$$$$$$$$$$$$$$$$$$$$$$
    // $$$$$$$$$$$$$$$$$$$$$$$$$
    //
    // Effectuer les changements liés à la double arraylist
    // Découper traiterLigne dans initConstante et initVariable
    //
    // $$$$$$$$$$$$$$$$$$$$$$$$$
    // $$$$$$$$$$$$$$$$$$$$$$$$$
    // $$$$$$$$$$$$$$$$$$$$$$$$$

    private ArrayList<Donnee> alEtatConstante;
    private ArrayList<ArrayList<Donnee>> alEtatVariable;

    public Traducteur() {
        this.interpreter = new Interpreter();

        this.alEtatConstante = new ArrayList<>();
        this.alEtatVariable = new ArrayList<>();
    }

    public void initConstante(String ligne) {

    }

    public void initVariable(String ligne) {

    }

    public void traiterLigne(String ligne) {
        boolean creerCons = false;
        boolean creerVar = false;
        boolean debutAlgo = false;

        String[] tabLigne;
        String type = "";
        String ligneCourante = ligne.replaceAll("◄—", "<-");
        if (!ligneCourante.equals("")) {
            if (ligneCourante.toLowerCase().contains("constante")) {

                creerCons = true;

            } else if (ligneCourante.toLowerCase().contains("variable")) {

                creerCons = false;
                creerVar = true;

            } else if (ligneCourante.toLowerCase().contains("debut")) {

                creerVar = false;
                debutAlgo = true;

            } else if (debutAlgo) {

                // Affectation !!!
                if (ligneCourante.contains("<-")) {
                    String[] tabAffectation = ligneCourante.split("<-");
                    tabAffectation[0] = tabAffectation[0].replaceAll("[\t ]", "");
                    tabAffectation[1] = tabAffectation[1].replaceAll(" ", "");
                    System.out.println(tabAffectation[0] + " --> " + tabAffectation[1]);
                    try {
                        interpreter.eval("" + tabAffectation[0] + " = " + tabAffectation[1]);
                        System.out.println(interpreter.get(tabAffectation[0]));
                        for (int i = 0; i < alVariable.size(); i++) {
                            if (alEtatVariable.get(i).getNom().equals(tabAffectation[0])) {
                                alEtatVariable.get(i).setValeur("" + interpreter.get(tabAffectation[0]));
                            }
                        }
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

                // ECRIRE !!!
                if (ligneCourante.contains("\tecrire ")) {
                    String[] tabEcrire = ligneCourante.split("[()]");
                    tabEcrire[1] = tabEcrire[1].replaceAll(" ", "");
                    String[] tabSOP = tabEcrire[1].split("&");

                    try {
                        String sTemp = "";
                        for (int i = 0; i < tabSOP.length; i++) {
                            if (tabSOP[i].contains("\"")) {
                                sTemp += tabSOP[i];
                            } else {
                                sTemp += interpreter.get(tabSOP[i]);
                            }
                        }
                        System.out.println(sTemp);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

            } else if (creerVar) {

                tabLigne = ligneCourante.split(":");
                tabLigne[0] = tabLigne[0].replaceAll("[\t ]", "");
                tabLigne[1] = tabLigne[1].replaceAll(" ", "");
                String[] tabNomVar = tabLigne[0].split(",");
                type = tabLigne[1];

                ajouteVariable(tabNomVar, type);

            } else if (creerCons) {

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
        } else if (s.contains("\"")) {
            return "Chaîne de caractère";
        } else if (s.contains("\'")) {
            return "caractère";
        } else if (s.contains(",")) {
            return "réel";
        } else if (s.matches("[0-9]+")) {
            return "entier";
        } else if (s.toLowerCase().equals("vrai") || s.toLowerCase().equals("faux")) {
            return "booléen";
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
                    alEtatConstante.add(new Booleen(tab[i], suivi, true));
                    try {
                        interpreter.eval("final boolean " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "caractere":
                    alEtatConstante.add(new Caractere(tab[i], suivi, true));
                    try {
                        interpreter.eval("final char " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "chaine de caractere":
                    alEtatConstante.add(new Chaine(tab[i], suivi, true));
                    try {
                        interpreter.eval("final String " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "entier":
                    alEtatConstante.add(new Entier(tab[i], suivi, true));
                    try {
                        interpreter.eval("final int " + tab[i].toUpperCase() + " = " + valeur);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "reel":
                    alEtatConstante.add(new Reel(tab[i], suivi, true));
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
                    alEtatVariable.add(new Booleen(tab[i], suivi, false));
                    try {
                        interpreter.eval("boolean " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "caractere":
                    alEtatVariable.add(new Caractere(tab[i], suivi, false));
                    try {
                        interpreter.eval("char " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "chaine de caractere":
                    alEtatVariable.add(new Chaine(tab[i], suivi, false));
                    try {
                        interpreter.eval("String " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "entier":
                    alEtatVariable.add(new Entier(tab[i], suivi, false));
                    try {
                        interpreter.eval("int " + tab[i]);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                    break;

                case "reel":
                    alEtatVariable.add(new Reel(tab[i], suivi, false));
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
        return this.alEtatConstante;
    }

    public ArrayList<ArrayList<Donnee>> getAlVariable() {
        return this.alEtatVariable;
    }
}