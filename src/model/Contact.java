// Déclarer un package nommé "model"
package model;

// Importer des classes nécessaires pour la lecture et l'écriture de fichiers, la gestion des dates et des expressions régulières
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Déclarer une classe publique nommée "Contact"
public class Contact {
    
    // Déclarer une constante publique et statique nommée "SEPARATEUR" qui contient un séparateur de chaîne de caractères
    public static final String SEPARATEUR = ";"; //";" est le séparateur utilisé dans le fichier csv

    // Déclarer des attributs privés de la classe
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private Date dateNaissance;

    // Déclarer des "getters" et des "setters" pour chaque attribut de la classe
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        // Mettre le nom en majuscule avant de l'assigner à l'attribut "nom"
        this.nom = nom.toUpperCase(); 
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    // Déclarer une méthode nommée "setTelephone" qui prend en argument une chaîne de caractères et qui peut lever une ParseException
    public void setTelephone(String telephone) throws ParseException {
    
    // Vérifier le format du numéro de téléphone
    // L'expression régulière permet de vérifier si le numéro est préfixé par "+33", "0033" ou simplement "0" et s'il est espacé par "", "." ou "-"
    Pattern patternTelephone = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$"); 
    Matcher testTelephone = patternTelephone.matcher(telephone);
    
    // Vérifier si le paramètre "telephone" correspond à l'expression régulière
    if (testTelephone.matches()) {
        
        // Si c'est le cas, définir l'attribut "telephone" de l'objet avec la valeur du paramètre "telephone"
        this.telephone = telephone;
        
    } else {
        
        // Si ce n'est pas le cas, lever une ParseException avec le message "Le format du numéro est incorrect"
        throw new ParseException("Le format du numéro est incorrect", 0);
    }
}

    public String getMail() {
        return mail;
    }

    // Déclarer une méthode nommée "setMail" qui prend en argument une chaîne de caractères et qui peut lever une ParseException
    public void setMail(String mail) throws ParseException {
        
        // Vérifier le format du mail en utilisant une expression régulière
        Pattern patternMail = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher testMail = patternMail.matcher(mail);
        
        // Vérifier si le paramètre "mail" correspond à l'expression régulière
        if (testMail.matches()) {
            
            // Si c'est le cas, définir l'attribut "mail" de l'objet avec la valeur du paramètre "mail"
            this.mail = mail;
            
        } else {
            
            // Si ce n'est pas le cas, lever une ParseException avec le message "Le format du mail est incorrect"
            throw new ParseException("Le format du mail est incorrect.", 0);
        }
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    // Déclarer une méthode nommée "setDateNaissance" qui prend en argument une chaîne de caractères et qui peut lever une ParseException
    public void setDateNaissance(String dateNaissance) throws ParseException {
        
        // Créer un format de date, jour/mois/année
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Définir l'attribut "dateNaissance" de l'objet avec la valeur de la chaîne de caractères parsée selon le format de date
        // Cette méthode renvoie une date ou une valeur nulle (dans le cas où la chaîne n'a pas été traitée en raison d'une erreur)
        this.dateNaissance = dateFormat.parse(dateNaissance); 
    }

    // Déclarer une méthode nommée "enregistrer" qui peut lever une IOException
    public void enregistrer() throws IOException {
        
        // Créer un objet PrintWriter qui écrira dans un fichier nommé "contacts.csv" en utilisant l'option "true" pour ajouter du contenu à la fin du fichier
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        
        try {
            // Écrire la chaîne de caractères représentant l'objet courant dans le fichier
            pw.println(this.toString());
        } finally {
            // Fermer le flux de données lorsque la méthode est terminée
            pw.close();
        }
    }

    // Déclarer une méthode statique nommée "lister" qui renvoie une ArrayList de Contact et qui peut lever une IOException
    public static ArrayList<Contact> lister() throws IOException {
        
        // Créer une ArrayList de Contact
        ArrayList<Contact> list = new ArrayList<>();
        
        // Créer un objet BufferedReader qui lira dans un fichier nommé "contacts.csv"
        BufferedReader buf = new BufferedReader(new FileReader("contacts.csv"));
        
        try {
            // Lire la première ligne du fichier
            String ligne = buf.readLine();
            
            // Tant qu'il y a des lignes à lire dans le fichier
            while (ligne != null) {
                
                // Séparer les différentes informations de la ligne en utilisant le séparateur défini par la constante "SEPARATEUR"
                String[] tab = ligne.split(SEPARATEUR);
                
                // Créer un objet Contact
                Contact c = new Contact();
                
                // Définir les différentes informations du contact avec les valeurs du tableau
                c.setNom(tab[0]);
                c.setPrenom(tab[1]);
                c.setMail(tab[2]);
                c.setTelephone(tab[3]);
                c.setDateNaissance(tab[4]);
                
                // Ajouter l'objet Contact à la liste
                list.add(c);
                
                // Lire la ligne suivante du fichier
                ligne = buf.readLine();
            }
        } catch (ParseException e) {
            // Afficher le message de l'exception ParseException
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // Afficher le message "Erreur de lecture sur le fichier" en cas d'exception IOException
            System.out.println("Erreur de lecture sur le fichier");
        } finally {
            // Fermer le flux de données dans tous les cas
            buf.close();
        }
        
        // Renvoyer la liste de contacts
        return list;
    }

    // Redéfinir la méthode toString() héritée de la classe Object pour renvoyer une chaîne de caractères représentant l'objet Contact
    @Override
    public String toString() {
        // Créer un objet StringBuilder vide
        StringBuilder build = new StringBuilder();
        
        // Ajouter le nom de l'objet suivi du séparateur
        build.append(getNom());
        build.append(SEPARATEUR);
        
        // Ajouter le prénom de l'objet suivi du séparateur
        build.append(getPrenom());
        build.append(SEPARATEUR);
        
        // Ajouter le mail de l'objet suivi du séparateur
        build.append(getMail());
        build.append(SEPARATEUR);
        
        // Ajouter le numéro de téléphone de l'objet suivi du séparateur
        build.append(getTelephone());
        build.append(SEPARATEUR);
        
        // Créer un objet SimpleDateFormat avec le format "dd/MM/yyyy"
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        
        // Ajouter la date de naissance de l'objet au format "dd/MM/yyyy"
        build.append(dtf.format(getDateNaissance()));
        
        // Renvoyer la chaîne de caractères contenue dans l'objet StringBuilder
        return build.toString();
    }
}