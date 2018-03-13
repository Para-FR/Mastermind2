/**
 * Created by marti on 14/12/2017.
 */
public class Couleur {

    public String couleur;
    public int[] proba;

    public Couleur(String _couleur,int[] _proba)
    {
        couleur=_couleur;
        proba=_proba;
    }

    public void Afficher()
    {
        System.out.print(couleur+" => ");
        for(int i=0;i<proba.length;i++)
        {
            System.out.print(proba[i]+"|");
        }
        System.out.println("");
    }

    public int[] getProba() {
        return proba;
    }

    public String getCouleur() {
        return couleur;
    }
}
