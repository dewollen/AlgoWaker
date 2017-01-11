package metier;

import bsh.EvalError;
import bsh.Interpreter;
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
    private Interpreter interpreter;

    private IVue vue;

    private boolean creerCons;
    private boolean creerVar;
    private boolean debutAlgo;

    private ArrayList<Donnee> alConstante;
    private ArrayList<ArrayList<Donnee>> alEtatVariable;

    private ArrayList<String> alConsole;

    private  boolean bOk;
    private ArrayList<String> alNbSi;

    public Traducteur(IVue vue) {
        this.vue = vue;
        this.interpreter = new Interpreter();

        this.creerCons = false;
        this.creerVar = false;
        this.debutAlgo = false;

        this.alConstante = new ArrayList<>();
        this.alEtatVariable = new ArrayList<>();

        this.alConsole = new ArrayList<>();
        this.alNbSi = new ArrayList<>();

        this.bOk = true;
    }

    public void initAttribut(String ligne, boolean creerCons, ArrayList<Donnee> alVarTemp) {
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
                ArrayList<Donnee> alVariable = ajouterVariable(tabNomAttribut, type);
                for (int i = 0; i < alVariable.size(); i++) {
                    alVarTemp.add(alVariable.get(i));
                }
                this.alEtatVariable.add(alVarTemp);
            }
        }
    }

    public void traiterLigne(String ligne, int numLigne) {
        ligne = ligne.replaceAll("◄—", "<-").toLowerCase();
        ArrayList<Donnee> alVarTemp;
        if (numLigne != 0) {
            alVarTemp = (ArrayList<Donnee>) this.getAlEtatVariable().get(numLigne - 1).clone();
        } else {
            alVarTemp = new ArrayList<>();
        }
        if (ligne.toLowerCase().contains("constante")) {
            alEtatVariable.add(alVarTemp);
            this.creerCons = true;
        } else if (ligne.toLowerCase().contains("variable")) {
            alEtatVariable.add(alVarTemp);
            this.creerCons = false;
            this.creerVar = true;
        } else if (ligne.toLowerCase().contains("debut")) {
            alEtatVariable.add(alVarTemp);
            this.creerVar = false;
            this.debutAlgo = true;
        } else if (creerCons || creerVar) {
            if (!ligne.equals("")) {
                if (creerCons) this.alEtatVariable.add(alVarTemp);
                initAttribut(ligne, creerCons, alVarTemp);
            } else {
                this.alEtatVariable.add(alVarTemp);
            }
        } else if (debutAlgo) {

            this.alEtatVariable.add(alVarTemp);

            if (bOk) {

                // Affectation !!!
                if (ligne.contains("<-")) {
                    String[] tabAffectation = ligne.split("<-");
                    tabAffectation[0] = tabAffectation[0].replaceAll("[\t ]", "");
                    tabAffectation[1] = tabAffectation[1].replaceAll(" ", "");
                    try {
                        ArrayList<Donnee> alVariable = alEtatVariable.get(numLigne);
                        interpreter.eval("" + tabAffectation[0] + " = " + tabAffectation[1]);
                        for (int i = 0; i < alVariable.size(); i++) {
                            if (alVariable.get(i).getNom().equals(tabAffectation[0])) {
                                alVariable.get(i).setValeur("" + interpreter.get(tabAffectation[0]));
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
                        ArrayList<Donnee> alVariable = alEtatVariable.get(numLigne);
                        for (int i = 0; i < alVariable.size(); i++) {
                            if (alVariable.get(i).getNom().equals(variable))
                                if(alVariable.get(i) instanceof Caractere) {
                                    interpreter.eval("" + variable + "='" + valeur + "'");
                                    alVariable.get(i).setValeur("" + interpreter.get(variable));
                                }
                                else if(alVariable.get(i) instanceof  Chaine) {
                                    interpreter.eval("" + variable + "=\"" + valeur + "\"");
                                    alVariable.get(i).setValeur("" + interpreter.get(variable));
                                }
                                else {
                                    interpreter.eval("" + variable + "=" + valeur);
                                    alVariable.get(i).setValeur("" + interpreter.get(variable));
                                }
                        }
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }

                // SI/SINON !!!
                if (ligne.contains("\tsi ")) {
                    this.alNbSi.add("si");
                    String verif = ligne.substring(ligne.indexOf("si") + 2, ligne.indexOf("alors"));

                    verif = verif.replaceAll(" ", "").replaceAll("=", "==").replaceAll("et", "&&").replaceAll("ou", "||");
                    try {
                        if (!(Boolean) interpreter.eval(verif)) {
                            bOk = false;
                        }
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }
                }
                if(ligne.contains("sinon")) {
                    this.bOk = !bOk;
                }
                if(ligne.contains("fsi")) {
                    this.alNbSi.remove(0);
                }


                // TANTQUE !!!
            }
            else {
                if(ligne.contains("\tsi ")) {
                    this.alNbSi.add("si");
                }
                if((ligne.contains("\tfsi") || ligne.contains("\tsinon")) && this.alNbSi.size()==1){

                    this.bOk = true;
                }
                if(ligne.contains("\tfsi")) {
                    this.alNbSi.remove(0);
                }

                this.alEtatVariable.add(alVarTemp);
            }

        } else {
            alEtatVariable.add(alVarTemp);
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

    public ArrayList<Donnee> ajouterVariable(String[] tab, String type) {
        ArrayList<Donnee> alVariable = new ArrayList<Donnee>();
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

            String typeTemp = "";
            switch (type) {
                case "booleen":
                    alVariable.add(new Booleen(tab[i], suivi, false));
                    typeTemp = "boolean ";
                    break;

                case "caractere":
                    alVariable.add(new Caractere(tab[i], suivi, false));
                    typeTemp = "char ";
                    break;

                case "chaine de caractere":
                    alVariable.add(new Chaine(tab[i], suivi, false));
                    typeTemp = "String ";
                    break;

                case "entier":
                    alVariable.add(new Entier(tab[i], suivi, false));
                    typeTemp = "int ";
                    break;

                case "reel":
                    alVariable.add(new Reel(tab[i], suivi, false));
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

        return alVariable;
    }

    public ArrayList<ArrayList<Donnee>> getAlEtatVariable() {
        return alEtatVariable;
    }

    public ArrayList<Donnee> getAlConstante() {
        return alConstante;
    }

    public ArrayList<String> getAlConsole() {
        return alConsole;
    }
}