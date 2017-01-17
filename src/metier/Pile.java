package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 11/01/2017
 */
public class Pile {
    private Stack<HashMap<String, Variable>> pileEtatVariables;
    private ArrayList<String[]> alConsole;

    public Pile() {
        this.pileEtatVariables = new Stack<>();
        this.pileEtatVariables.push(new HashMap<>());
        this.alConsole = new ArrayList<>();
    }

    public void majVariable(Variable v) {
        if (v != null)
            this.pileEtatVariables.peek().put(v.getNom(), v);
    }

    public void ajouterEtat() {
        HashMap<String, Variable> nvHashMap = new HashMap<>();
        HashMap<String, Variable> hashMap   = this.pileEtatVariables.peek();

        hashMap.forEach((s, variable) -> nvHashMap.put(s, new Variable(variable)));

        this.pileEtatVariables.push(nvHashMap);
    }

    public void retirerEtat() {
        this.pileEtatVariables.pop();

        if (this.pileEtatVariables.isEmpty())
            this.pileEtatVariables.push(new HashMap<>());
    }

    public Variable getVariable(String nom) {
        return this.pileEtatVariables.peek().get(nom);
    }

    public void ecrireConsole(String ligne, String type) {
        this.alConsole.add(new String[]{ligne, type});
    }

    public ArrayList<String[]> getConsole() {
        return this.alConsole;
    }

    public ArrayList<Variable> getVariablesATracer() {
        ArrayList<Variable> variablesATracer = new ArrayList<>();

        for (String cle : this.pileEtatVariables.peek().keySet()) {
            if (this.pileEtatVariables.peek().get(cle).isSuivi())
                variablesATracer.add(this.pileEtatVariables.peek().get(cle));
        }

        return variablesATracer;
    }
}
