/**
 * Created by marti on 19/12/2017.
 */
public class Case {
    Boolean trouve;
    String couleur;

    Case()
    {
        trouve=false;
        couleur="";
    }

    void setTrouve(String _couleur)
    {
        trouve=true;
        couleur=_couleur;
    }

    boolean getTrouve(){
        return trouve;
    }

    String getCouleur(){
        return couleur;
    }



}
