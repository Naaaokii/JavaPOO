package model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact {
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private Date dateNaissance;

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(String dateNaissance) {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        this.dateNaissance = format.parse(dateNaissance);
    }

    
}
