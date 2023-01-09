package model;
import java.util.Date;

public class Contact {
    private String _nom;
    private String _prenom;
    private String _mail;
    private String _telephone;
    private Date _dateNaissance;

    public String getNom(){
        return this._nom;
    }

    public void setNom(String valeur){
        this._nom = valeur; 
    }
}
