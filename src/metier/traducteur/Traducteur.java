package metier.traducteur;

import bsh.EvalError;
import bsh.Interpreter;
import exception.CodeFormatException;
import exception.ConstantChangeException;
import exception.UnexpectedTypeException;
import metier.Constante;
import metier.Lecteur;
import metier.Pile;
import metier.Variable;
import metier.utile.Regex;
import scruter.Observeur;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe qui interprète les portions de pseudo-code afin d'agir en conséquence
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Traducteur implements scruter.Observable {
    private final String[] TAB_MODE = {"ALGORITHME", "constante:", "variable:", "DEBUT", "FIN"};

    private Interpreter interpreter;

    private Pile pile;

    private String mode;

    private Observeur observeur;

    private String[] tabPseudoCode;

    private String fichier;

    private int numLigneCourante;

    private ArrayList<Variable> alStockVariable;

    private Stack<Boolean> pile2Booleen;
    private Stack<Integer> sLigTQ;

    private int nbAction;


    public Traducteur(String fichier) throws ConstantChangeException {
        this.interpreter = new Interpreter();

        this.observeur = null;

        // Mode qui sert à rien (y)
        this.mode = TAB_MODE[0];

        this.pile = new Pile();

        this.fichier = fichier;

        this.tabPseudoCode = Lecteur.getPseudoCode(this.fichier);

        this.alStockVariable = new ArrayList<>();

        this.numLigneCourante = 1;

        this.pile2Booleen = new Stack<>();
        this.pile2Booleen.push(new Boolean(true));
        this.sLigTQ = new Stack<>();

        this.nbAction = 0;
    }

    public void traduire() throws EvalError, ConstantChangeException {
        notifierObserveur();

        this.nbAction++;

        try {
            this.determinerMode(this.tabPseudoCode[numLigneCourante + 1]);
        } catch(ArrayIndexOutOfBoundsException e) {
            this.numLigneCourante++;
        }
    }

    private void determinerMode(String ligne) throws EvalError, ConstantChangeException {
        // Formatage
        ligne = ligne.trim();
        ligne = ligne.replaceAll("◄—", "<-");

        // Détermine le mode
        for (String mode : this.TAB_MODE) {
            if (ligne.equals(mode)) {
                this.mode = mode;
                return;
            }
        }

        // Détermine l'action à executer en fonction du mode
        this.traiterLigne(ligne);
    }

    private void traiterLigne(String ligne) throws EvalError, ConstantChangeException {
        if (!(ligne.equals("") || ligne.startsWith("//"))) {
            switch (this.mode) {
                case "ALGORITHME":
                case "FIN":
                    break;
                case "constante:":
                    this.initVariable(ligne, true);
                    break;
                case "variable:":
                    this.initVariable(ligne, false);
                    break;
                case "DEBUT":
                    this.traiterAlgo(ligne);
                    break;
            }
        }
    }

    private void initVariable(String ligne, boolean estConstante) throws EvalError {
        ligne = ligne.replaceAll("[\t ]", "");

        String[] tabLigne;
        String type;

        if (estConstante) {
            tabLigne = ligne.split("<-");

            type = this.rechercheType(tabLigne[1]);
            this.pile.majVariable(new Constante(tabLigne[0], type, tabLigne[1]));
        } else {
            tabLigne = ligne.split(":");
            String[] tabNomAttribut = tabLigne[0].split(",");

            type = tabLigne[1];
            String s;
            boolean suivi;

            for (int i = 0; i < tabNomAttribut.length; i++) {
                suivi = false;
                do {
                    this.observeur.afficherMessage("Voulez-vous suivre la trace de la variable : " + tabNomAttribut[i] + " (o/n)");
                    s = this.observeur.saisieUtilisateur();

                    if (s != null && s.equals("o")) suivi = true;

                } while (s == null || !s.equals("o") && !s.equals("n"));

                this.pile.ecrireConsole(s, "lire");

                this.pile.majVariable(new Variable(tabNomAttribut[i], type, "", suivi));
            }
        }
    }

    private void traiterAlgo(String ligne) throws ConstantChangeException, EvalError {
        if (pile2Booleen.peek()) {
            //
            // Affectation
            //
            if (ligne.contains("<-")) {
                String[] tabAffectation = ligne.split("<-");
                interpreter.eval(tabAffectation[0] + " = " + tabAffectation[1]);
                tabAffectation[0] = tabAffectation[0].replaceAll("[\t ]", "");

                this.pile.getVariable(tabAffectation[0]).setValeur(String.valueOf(interpreter.get(tabAffectation[0])));
            }

            //
            // Ecrire
            //
            if (Regex.correspond(ligne, Regex.ECRIRE)) {
                String[] tabEcrire = ligne.split("[()]");
                String[] tabSOP = tabEcrire[1].split("&");

                String sTemp = "";
                for (int i = 0; i < tabSOP.length; i++) {
                    if (tabSOP[i].contains("\""))
                        sTemp += tabSOP[i].replaceAll("\"", "").trim();
                    else {
                        tabSOP[i] = tabSOP[i].replaceAll(" ", "");
                        sTemp += interpreter.get(tabSOP[i]);
                    }
                }
                this.pile.ecrireConsole(sTemp, "ecrire");
            }

            //
            // Lire
            //
            if (Regex.correspond(ligne, Regex.LIRE)) {
                String variable = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")")).replaceAll(" ", "");
                Variable tmp = this.pile.getVariable(variable);
                this.observeur.afficherMessage("Veuillez renseigner une valeur pour " + tmp.getNom() + " :");
                String valeur = this.observeur.saisieUtilisateur();
                this.pile.ecrireConsole(valeur, "lire");

                try {
                    switch (tmp.getType()) {
                        case "caractere":
                            interpreter.eval(variable + "='" + valeur + "'");
                            tmp.setValeur(String.valueOf(interpreter.get(variable)));
                            break;
                        case "chaine":
                            interpreter.eval(variable + "=\"" + valeur + "\"");
                            tmp.setValeur(String.valueOf(interpreter.get(variable)));
                            break;
                        default:
                            interpreter.eval(variable + "=" + valeur);
                            tmp.setValeur(String.valueOf(interpreter.get(variable)));
                    }
                } catch(Exception e) {
                    try {
                        throw new UnexpectedTypeException(tmp.getType());
                    } catch (UnexpectedTypeException e1) {
                        System.err.println(e1.getMessage());
                        System.exit(-1);
                    }
                }
            }

            // SI/SINON !!!
            if (Regex.correspond(ligne, Regex.SI)) {
                String verif = ligne.substring(ligne.indexOf("si") + 2, ligne.indexOf("alors"));

                verif = Convertisseur.convertirBooleen(verif);

                if (!(Boolean) interpreter.eval(verif)) this.pile2Booleen.push(new Boolean(false));
                else this.pile2Booleen.add(new Boolean(true));
                if (Regex.correspond(ligne, Regex.SINON)) {
                    this.pile2Booleen.push(new Boolean(!this.pile2Booleen.pop()));
                }
                if (Regex.correspond(ligne, Regex.FSI)) {
                    this.pile2Booleen.pop();
                }
            }

            // TANTQUE !!!
            if (Regex.correspond(ligne, Regex.TANTQUE)) {
                String verif = ligne.substring(ligne.indexOf("tantque") + 7, ligne.indexOf("faire")).replaceAll(" ", "");

                verif = Convertisseur.convertirBooleen(verif);

                if (!(Boolean) interpreter.eval(verif)) {
                    this.pile2Booleen.push(new Boolean(false));
                } else {
                    this.pile2Booleen.push(new Boolean(true));
                    this.sLigTQ.push(numLigneCourante);
                }
            }
            if (Regex.correspond(ligne, Regex.FTQ)) {
                this.pile2Booleen.pop();
                this.numLigneCourante = this.sLigTQ.pop();
            }
        } else {
            if (Regex.correspond(ligne, Regex.SI) || Regex.correspond(ligne, Regex.TANTQUE)) {
                this.pile2Booleen.push(new Boolean(false));
            }
            if (Regex.correspond(ligne, Regex.FSI) || Regex.correspond(ligne, Regex.FTQ)) {
                this.pile2Booleen.pop();
            }
            if (Regex.correspond(ligne, Regex.SINON) && this.pile2Booleen.get(this.pile2Booleen.size() - 2)) {
                this.pile2Booleen.push(!this.pile2Booleen.pop());
            }
        }
    }

    private String rechercheType(String s) throws EvalError {
        return interpreter.eval(s).getClass().getSimpleName();
    }

    public void avancer() throws ConstantChangeException, EvalError {
        if (numLigneCourante >= this.getPseudoCode().length)
            return;

        this.traduire();

        this.numLigneCourante++;
    }

    public void reculer() throws ConstantChangeException, EvalError {
        if (numLigneCourante <= 0)
            return;

        this.numLigneCourante--;

        this.reset(this.fichier);

        this.traduire();

        this.nbAction--;

        int nbActionTemp = 0;
        while (nbActionTemp < nbAction - 1) {
            this.numLigneCourante++;
            this.traduire();
            nbActionTemp++;
        }
    }

    public String evaluerExpression(String expression) {
        String tabExpression[] = expression.split("[){1}]");
        rechercherPrimitive(tabExpression[0]);
        return "";
    }

    public void reset(String fichier) {
        this.interpreter = new Interpreter();

        this.observeur = null;

        // Mode qui sert à rien (y)
        this.mode = TAB_MODE[0];

        this.pile = new Pile();

        this.fichier = fichier;

        this.tabPseudoCode = Lecteur.getPseudoCode(this.fichier);

        this.alStockVariable = new ArrayList<>();

        this.numLigneCourante = 0;

        this.pile2Booleen = new Stack<>();
        this.pile2Booleen.push(new Boolean(true));
        this.sLigTQ = new Stack<>();

        this.nbAction = 1;
    }

    public String rechercherPrimitive(String ligne) {
        for (Primitive p : Primitive.values()) {
            if (ligne.contains(p.name()))
                return p.calculer(ligne);
        }

        return ligne;
    }

    public void avancerJusqua(int ligne) throws ConstantChangeException, EvalError {
        this.numLigneCourante = 0;

        while (numLigneCourante < ligne) {
            this.numLigneCourante++;
            this.traduire();
        }
    }

    /* --------------------------------------------------- */
    /*                                                     */
    /*     Accesseurs utiles à l'interface graphique       */
    /*                                                     */
    /* --------------------------------------------------- */

    public String getNomFichier() {
        return this.fichier;
    }

    public String[] getPseudoCode() {
        return this.tabPseudoCode;
    }

    public ArrayList<String[]> getConsole() {
        return this.pile.getConsole();
    }

    public ArrayList<Variable> getVariablesATracer() {
        return this.pile.getVariablesATracer();
    }

    /* --------------------------------------------------- */
    /*                                                     */
    /*    Méthodes pour utiliser l'interface graphique     */
    /*                                                     */
    /* --------------------------------------------------- */

    @Override
    public void ajouterObserveur(Observeur obs) {
        this.observeur = obs;
    }

    @Override
    public void reinitialiserObserveur() {
        this.observeur = null;
    }

    @Override
    public void notifierObserveur() {
        notifierObserveur(null);
    }

    @Override
    public void notifierObserveur(String couleur) {
        this.observeur.majIHM(numLigneCourante, couleur);
    }

    public int getNumLigneCourante() {
        return numLigneCourante;
    }

    public String getLigneCourante() {
        return this.tabPseudoCode[numLigneCourante];
    }
}