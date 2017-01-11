package metier;

import bsh.EvalError;
import bsh.Interpreter;
import org.w3c.dom.events.EventException;
import util.donnee.*;
import vue.IVue;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui interprète les portions de pseudo-code afin d'agir en conséquence
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Traducteur {

    /**
     * Variables de la classe Interpreter de la librairie bsh
     * @see Traducteur#traiterLigne(String, int)
     * @see Traducteur#ajouterConstante(String[], String, String)
     * @see Traducteur#ajouterVariable(String[], String)
     */
    private Interpreter interpreter;

    /**
     * Variables permettant de savoir si l'utilisateur utilise la méthode d'affichage CUI ou GUI
     * @see Traducteur#traiterLigne(String, int)
     */
    private IVue vue;

    /**
     * Booleen permettant de savoir si le programme est à la création des constantes
     * @see Traducteur#initAttribut(String, boolean)
     * @see Traducteur#traiterLigne(String, int)
     */
    private boolean creerCons;

    /**
     * Booleen permettant de savoir si le programme est à la création des variables
     * @see Traducteur#traiterLigne(String, int)
     */
    private boolean creerVar;

    /**
     * Booleen permettant de savoir si le programme à commencer à interpreter le programme de pseudo-code
     * @see Traducteur#traiterLigne(String, int)
     */
    private boolean debutAlgo;

    /**
     * ArrayList répertoriant toutes les constantes du programme
     * @see Traducteur#ajouterConstante(String[], String, String)
     * @see Traducteur#getAlConstante()
     */
    private ArrayList<Donnee> alConstante;

    /**
     * ArrayList répertoriant toutes les variables du programme
     * @see Traducteur#initAttribut(String, boolean)
     * @see Traducteur#traiterLigne(String, int)
     * @see Traducteur#getAlVariable()
     */
    private ArrayList<Donnee> alVariable;

    /**
     * ArrayList répertoriant toutes ce qui sera afficher dans la console
     * @see Traducteur#traiterLigne(String, int)
     * @see Traducteur#getAlConsole()
     */
    private ArrayList<String> alConsole;

    /**
     * Permet de savoir si les conditions des "si" et "tant que" sont vrai ou pas
     * @see Traducteur#traiterLigne(String, int)
     */
    private  boolean bOk;

    /**
     * compteur qui permet de compter les "si"
     * @see Traducteur#traiterLigne(String, int)
     */
    private int nbSi;

    /**
     * Constructeur de la classe Traducteur
     * @param vue Permet de savoir si l'interface utilisé est en CUI ou en GUI
     */
    public Traducteur(IVue vue) {
        this.vue = vue;
        this.interpreter = new Interpreter();

        this.creerCons = false;
        this.creerVar = false;
        this.debutAlgo = false;

        this.alConstante = new ArrayList<>();
        this.alVariable = new ArrayList<>();

        this.alConsole = new ArrayList<>();
        this.nbSi = 0;

        this.bOk = true;
    }

    /**
     * Permet d'initialiser les attributs et de les mettre dans les ArrayList correspondante
     * @param ligne Ligne à interpreter
     * @param creerCons Permet de savoir l'attribut est une constante
     */
    public void initAttribut(String ligne, boolean creerCons) {
        if (!ligne.equals("")) {
            String[] tabLigne;
            String type;

            if (creerCons)
                tabLigne = ligne.split("<-");
            else
                tabLigne = ligne.split(":");

            tabLigne[0] = tabLigne[0].replaceAll("[\t ]", "");
            tabLigne[1] = tabLigne[1].replaceAll(" ", "");
            String[] tabNomAttribut = tabLigne[0].split(",");

            if (creerCons) {
                type = rechercheType(tabLigne[1]);
                ajouterConstante(tabNomAttribut, type, tabLigne[1]);
            } else {
                type = tabLigne[1];
                ajouterVariable(tabNomAttribut, type);
            }
        }
    }

    /**
     * Permet d'interpreter chaque ligne avec l'affectation, lire , écrire, si , tant que
     * @param ligne Ligne à interpreter
     * @param numLigne Numéro de la ligne à interpreter
     */
    public void traiterLigne(String ligne, int numLigne) {
        ligne = ligne.replaceAll("◄—", "<-").toLowerCase();

        if (ligne.toLowerCase().contains("constante")) {
            this.creerCons = true;
        } else if (ligne.toLowerCase().contains("variable")) {
            this.creerCons = false;
            this.creerVar = true;
        } else if (ligne.toLowerCase().contains("debut")) {
            this.creerVar = false;
            this.debutAlgo = true;
        } else if (creerCons || creerVar) {
            if (!ligne.equals("")) {
                initAttribut(ligne, creerCons);
            }
        } else if (debutAlgo) {

            if (bOk) {

                // Affectation !!!
                if (ligne.contains("<-")) {
                    String[] tabAffectation = ligne.split("<-");
                    tabAffectation[0] = tabAffectation[0].replaceAll("[\t ]", "");
                    tabAffectation[1] = tabAffectation[1].replaceAll(" ", "");
                    try {
                        interpreter.eval("" + tabAffectation[0] + " = " + tabAffectation[1]);
                        for (int i = 0; i < this.alVariable.size(); i++) {
                            if (this.alVariable.get(i).getNom().equals(tabAffectation[0])) {
                                this.alVariable.get(i).setValeur("" + interpreter.get(tabAffectation[0]));
                            }
                        }
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

                // ECRIRE !!!
                if (ligne.contains("\tecrire ")) {
                    String[] tabEcrire = ligne.split("[()]");
                    String[] tabSOP = tabEcrire[1].split("&");

                    try {
                        String sTemp = "";
                        for (int i = 0; i < tabSOP.length; i++) {
                            if (tabSOP[i].contains("\"")) {
                                sTemp += tabSOP[i].replaceAll("\"", "");
                            } else {
                                tabSOP[i] = tabSOP[i].replaceAll(" ", "");
                                sTemp += interpreter.get(tabSOP[i]);
                            }
                        }
                        alConsole.add(sTemp);
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

                // LIRE !!!
                if (ligne.contains("lire")) {
                    String valeur = vue.lire();
                    this.alConsole.add(valeur);
                    String variable = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")")).replaceAll(" ","");
                    try {
                        for (int i = 0; i < alVariable.size(); i++) {
                            if (this.alVariable.get(i).getNom().equals(variable))
                                if(this.alVariable.get(i) instanceof Caractere) {
                                    interpreter.eval("" + variable + "='" + valeur + "'");
                                    this.alVariable.get(i).setValeur("" + interpreter.get(variable));
                                }
                                else if(this.alVariable.get(i) instanceof  Chaine) {
                                    interpreter.eval("" + variable + "=\"" + valeur + "\"");
                                    this.alVariable.get(i).setValeur("" + interpreter.get(variable));
                                }
                                else {
                                    interpreter.eval("" + variable + "=" + valeur);
                                    this.alVariable.get(i).setValeur("" + interpreter.get(variable));
                                }
                        }
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

                // SI/SINON !!!
                if (ligne.contains("\tsi ")) {
                    this.nbSi++;
                    String verif = ligne.substring(ligne.indexOf("si") + 2, ligne.indexOf("alors"));

                    verif = verif.replaceAll(" ", "").replaceAll("=", "==").replaceAll("et", "&&").replaceAll("ou", "||");
                    if (verifier(verif)/*!(Boolean) interpreter.eval(verif)*/) {
                        bOk = false;
                    }
                }
                if(ligne.contains("sinon")) {
                    this.bOk = !bOk;
                }
                if(ligne.contains("fsi")) {
                    this.nbSi--;
                }


                // TANTQUE !!!
            }
            else {
                if(ligne.contains("\tsi ")) {
                    this.nbSi++;
                }
                if((ligne.contains("\tfsi") || ligne.contains("\tsinon")) && this.nbSi==1){

                    this.bOk = true;
                }
                if(ligne.contains("\tfsi")) {
                    this.nbSi--;
                }
            }

        }
    }

    /**
     * Permet de rechercher le type de la constante
     * @param s La ligne à interpreter
     * @return Le type de la constante
     */
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

    /**
     * Permet de créer une constante et de l'ajouter à l'ArrayList alConstante
     * @param tab La/les constante(s) à créer
     * @param type le type de la constante à créer
     * @param valeur La valeur de la constante à créer
     */
    private void ajouterConstante(String[] tab, String type, String valeur) {
        type = type.toLowerCase();
        String typeTemp = "";
        boolean suivi = false;
        for (int i = 0; i < tab.length; i++) {
            switch (type) {
                case "booleen":
                    alConstante.add(new Booleen(tab[i], suivi, true));
                    typeTemp = "final boolean ";
                    break;

                case "caractere":
                    alConstante.add(new Caractere(tab[i], suivi, true));
                    typeTemp = "final char ";
                    break;

                case "chaine de caractere":
                    alConstante.add(new Chaine(tab[i], suivi, true));
                    typeTemp = "final String ";
                    break;

                case "entier":
                    alConstante.add(new Entier(tab[i], suivi, true));
                    typeTemp = "final int ";
                    break;

                case "reel":
                    alConstante.add(new Reel(tab[i], suivi, true));
                    typeTemp = "final double";
                    break;

                default:
                    typeTemp = "error";
                    break;
            }

            if (!typeTemp.equals("error")) {
                try {
                    interpreter.eval(typeTemp + tab[i].toUpperCase() + " = " + valeur);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
            }
        }
    }

    /**
     * Permet de créer une variables et de l'ajouter à l'ArrayList alEtatVariable
     * @param tab La/les variables à créer
     * @param type Type de la variable à créer
     * @return L'ArrayList des variables créées
     */
    public void ajouterVariable(String[] tab, String type) {
        type = type.toLowerCase();
        String s = "";
        boolean suivi = false;
        for (int i = 0; i < tab.length; i++) {


            System.out.println("Voulez-vous suivre la trace de la variable : " + tab[i] + " (o/n)");
            int cpt = 0;
            do {
                if (cpt % 3 == 0 && cpt != 0) System.out.println("Veuillez entrer o ou n");
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

            String typeTemp = "";
            switch (type) {
                case "booleen":
                    this.alVariable.add(new Booleen(tab[i], suivi, false));
                    typeTemp = "boolean ";
                    break;

                case "caractere":
                    this.alVariable.add(new Caractere(tab[i], suivi, false));
                    typeTemp = "char ";
                    break;

                case "chaine de caractere":
                    this.alVariable.add(new Chaine(tab[i], suivi, false));
                    typeTemp = "String ";
                    break;

                case "entier":
                    this.alVariable.add(new Entier(tab[i], suivi, false));
                    typeTemp = "int ";
                    break;

                case "reel":
                    this.alVariable.add(new Reel(tab[i], suivi, false));
                    typeTemp = "double ";
                    break;

                default:
                    typeTemp = "error";
                    break;
            }

            if (!typeTemp.equals("error")) {
                try {
                    interpreter.eval(typeTemp + tab[i]);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
            }
        }
    }

    public boolean verifier(String expression) {
        try {
            return (boolean)this.interpreter.eval(expression);
        } catch (EvalError evalError){
            evalError.printStackTrace();
        }
        return false;
    }

    /**
     * Retourne l'ArrayList des variables du programme
     * @return L'ArrayList des variables
     */
    public ArrayList<Donnee> getAlVariable() {
        return alVariable;
    }

    /**
     * Retourne l'ArrayList des constantes du programme
     * @return L'ArrayList des constantes
     */
    public ArrayList<Donnee> getAlConstante() {
        return alConstante;
    }

    /**
     * Retourne l'affichage de la console de l'interpreteur
     * @return L'ArrayList des String à afficher dans la console de l'interpreteur
     */
    public ArrayList<String> getAlConsole() {
        return alConsole;
    }
}