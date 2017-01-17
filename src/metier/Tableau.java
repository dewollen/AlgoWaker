package metier;

public class Tableau {

    private Variable tabVar[];
    private String nom;
    private String type;
    private int taille;
    private boolean suivi;


    public Tableau(String nom,String type,int taille,boolean suivi){
        tabVar = new Variable[taille];
        this.nom = nom;
        this.taille = taille;
        this.type = type;
        this.suivi = suivi;
    }

    public boolean ajoutVariable(int indice,Variable var ){
        if(indice < tabVar.length && type.equals(var.getType())) {
            tabVar[indice] = var;
            return true;
        }

        return false;
    }


    public Variable getVariable(int indice){
        if(indice < tabVar.length)
            return tabVar[indice];

        return null;
    }

    public int getTaille()    { return this.taille; }
    public String getType()   { return this.type;   }
    public String getNom()    { return this.nom;    }
    public boolean getSuivi() { return this.suivi;  }

}
