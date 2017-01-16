package metier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 11/01/2017
 */
public class Pile {
    private HashMap<String, Variable> hMVariable;
    private ArrayList<String[]> alConsole;

    public Pile() {
        this.hMVariable = new HashMap<>();
        this.alConsole = new ArrayList<>();
    }

    public void majVariable(Variable v) {
        if (v != null)
            this.hMVariable.put(v.getNom(), v);
    }

    public Variable getVariable(String nom) {
        return this.hMVariable.get(nom);
    }

    public void ecrireConsole(String ligne, String type) {
        this.alConsole.add(new String[]{ligne + "\n", type});
    }

    public ArrayList<String[]> getConsole() {
        return this.alConsole;
    }

    public ArrayList<Variable> getVariablesATracer() {
        ArrayList<Variable> variablesATracer = new ArrayList<>();

        for (String cle : this.hMVariable.keySet()) {
            if (this.hMVariable.get(cle).isSuivi())
                variablesATracer.add(this.hMVariable.get(cle));
        }

        return variablesATracer;
    }
}
