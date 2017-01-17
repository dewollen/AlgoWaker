package metier;

public class Tableau extends Variable {

    private String tabVar[];

    public Tableau(String nom, String type, int taille, boolean suivi) {
        super(nom, type, "", suivi);

        this.tabVar = new String[taille];
    }

    public boolean ajoutVariable(int indice, String var) {
        if (indice < this.tabVar.length && indice >= 0) {
            this.tabVar[indice] = var;
            return true;
        }

        return false;
    }

    @Override
    public String getValeur() {
        String sRet = "[";
        int i;
        for (i = 0; i < 3 && i < this.tabVar.length && this.tabVar[i] != null; i++)
            sRet += this.tabVar[i] + ", ";

        if (this.tabVar[i] != null)
            sRet += this.tabVar[i];

        return sRet + "]";
    }
}
