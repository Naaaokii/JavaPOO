package model;

// Importer des classes nécessaires pour la lecture et l'écriture de fichiers, la gestion des dates et des expressions régulières
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;



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
        // Mettre la première lettre du prénom en majuscule avant de l'assigner à l'attribut "prenom"
        this.prenom = prenom.replaceFirst(".",(prenom.charAt(0)+"").toUpperCase());
    }

    public String getTelephone() {
        return telephone;
    }

    // Déclarer une méthode nommée "setTelephone" qui prend en argument une chaîne de caractères et qui peut lever une ParseException
    public void setTelephone(String telephone) throws ParseException { //exception d'analyse
    
    // Vérifier le format du numéro de téléphone
    // L'expression régulière permet de vérifier si le numéro est préfixé par "+33", "0033" ou simplement "0" et s'il est espacé par "", "." ou "-"
    Pattern patternTelephone = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$"); 
    Matcher testTelephone = patternTelephone.matcher(telephone);
    
    // Vérifier si le paramètre "telephone" correspond à l'expression régulière
    if (testTelephone.matches()) {
        
        // Si oui, définir l'attribut "telephone" de l'objet avec la valeur du paramètre "telephone"
        this.telephone = telephone;
        
    } else {
        
        // Si non, lever une ParseException avec le message "Le format du numéro est incorrect"
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
            
            // Si oui, définir l'attribut "mail" de l'objet avec la valeur du paramètre "mail"
            this.mail = mail;
            
        } else {
            
            // Si non, lever une ParseException avec le message "Le format du mail est incorrect"
            throw new ParseException("Le format du mail est incorrect.", 0);
        }
    }



    public Date getDateNaissance() {
        return dateNaissance;
    }

    // Déclarer une méthode nommée "setDateNaissance" qui prend en argument une chaîne de caractères et qui peut lever une ParseException
    public void setDateNaissance(String dateNaissance) throws ParseException {
        
        // Créer un format de date (jour/mois/année)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Définir l'attribut "dateNaissance" de l'objet avec la valeur de la chaîne de caractères (qui se fait parse()) selon le format de date)
        // Cette méthode renvoie une date ou une valeur nulle (dans le cas où la chaîne n'a pas été traitée en raison d'une erreur)
        this.dateNaissance = dateFormat.parse(dateNaissance); 
    }




    // Déclarer une méthode nommée "sauvegarderContact" qui peut lever une IOException
    public void sauvegarderContact() throws IOException { //exception fichier 
        
        // Créer un objet PrintWriter qui écrira dans un fichier nommé "contacts.csv" en utilisant l'option "true" pour ajouter du contenu à la fin du fichier
        PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        
        try {
            // Écrire la chaîne de caractères représentant l'objet courant dans le fichier
            printwriter.println(this.strContactEnCours());
        } finally {
            // Fermer le flux de données lorsque la méthode est terminée
            printwriter.close();
        }
    }




    // Déclarer une méthode statique nommée "listerContacts" qui renvoie une ArrayList de Contact et qui peut lever une IOException
    public static ArrayList<Contact> listerContacts() throws IOException {
        
        // Créer une ArrayList de Contact
        ArrayList<Contact> listeContact = new ArrayList<>();
        
        // Créer un objet BufferedReader qui lira dans un fichier nommé "contacts.csv"
        BufferedReader lectureFichier = new BufferedReader(new FileReader("contacts.csv"));
        
        try {
            // Lire la première ligne du fichier
            String ligne = lectureFichier.readLine();
            
            // Tant qu'il y a des lignes à lire dans le fichier
            while (ligne != null) {
                
                // Séparer les différentes informations de la ligne en utilisant le séparateur défini par la constante "SEPARATEUR"
                String[] tableauContact = ligne.split(SEPARATEUR);
                
                // Créer un objet Contact
                Contact contact = new Contact();
                
                // Définir les différentes informations du contact avec les valeurs du tableau
                contact.setNom(tableauContact[0]);
                contact.setPrenom(tableauContact[1]);
                contact.setMail(tableauContact[2]);
                contact.setTelephone(tableauContact[3]);
                contact.setDateNaissance(tableauContact[4]);
                
                // Ajouter l'objet Contact à la liste
                listeContact.add(contact);                
                
                // Lire la ligne suivante du fichier
                ligne = lectureFichier.readLine();
            }
        } catch (ParseException exception) {
            // Afficher le message de l'exception ParseException
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            // Afficher le message "Erreur de lecture sur le fichier" en cas d'exception IOException
            System.out.println("Erreur de lecture sur le fichier");
        } finally {
            // Fermer le flux de données dans tous les cas
            lectureFichier.close();
        }
        // Renvoyer la liste de contacts
        return listeContact;
    }




    public static ArrayList<Contact> chercherContact(String nom) throws IOException { 
        // Mettre le nom en majuscule
        nom = nom.toUpperCase();

        // Créer une ArrayList de Contact
        ArrayList<Contact> contactCherche = new ArrayList<>();
        
        // Créer un objet BufferedReader qui lira dans un fichier nommé "contacts.csv"
        BufferedReader lectureFichier = new BufferedReader(new FileReader("contacts.csv"));
        
        try {
            // Lire la première ligne du fichier
            String ligne = lectureFichier.readLine();
            
            // Tant qu'il y a des lignes à lire dans le fichier
            while (ligne != null) {
                
                // Séparer les différentes informations de la ligne en utilisant le séparateur défini par la constante "SEPARATEUR"
                String[] tableauContact = ligne.split(SEPARATEUR);
                
                // Créer un objet Contact
                Contact contact = new Contact();
                
                // Définir les différentes informations du contact avec les valeurs du tableau
                contact.setNom(tableauContact[0]);
                contact.setPrenom(tableauContact[1]);
                contact.setMail(tableauContact[2]);
                contact.setTelephone(tableauContact[3]);
                contact.setDateNaissance(tableauContact[4]);

                if (tableauContact[0].equals(nom)){
                    // Ajouter l'objet Contact à la liste
                    contactCherche.add(contact);
                    System.out.println(contact.getNom() + " " 
                    + contact.getPrenom() + " " 
                    + contact.getMail() + " " 
                    + contact.getTelephone() + " " 
                    + contact.getDateNaissance());
                }

               
                // Lire la ligne suivante du fichier
                ligne = lectureFichier.readLine();
            }
        } catch (ParseException exception) {
            // Afficher le message de l'exception ParseException
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            // Afficher le message "Erreur de lecture sur le fichier" en cas d'exception IOException
            System.out.println("Erreur de lecture sur le fichier");
        } finally {
            // Fermer le flux de données dans tous les cas
            lectureFichier.close();
        }
        // Renvoyer la liste de contacts
        return contactCherche;
    }





    // Redéfinir la méthode strContactEnCours() héritée de la classe Object pour renvoyer une chaîne de caractères représentant l'objet Contact
    public String strContactEnCours() {
        // Créer un objet StringBuilder vide
        StringBuilder builder = new StringBuilder();
        
        // Ajouter le nom de l'objet suivi du séparateur
        builder.append(getNom());
        builder.append(SEPARATEUR);
        
        // Ajouter le prénom de l'objet suivi du séparateur
        builder.append(getPrenom());
        builder.append(SEPARATEUR);
        
        // Ajouter le mail de l'objet suivi du séparateur
        builder.append(getMail());
        builder.append(SEPARATEUR);
        
        // Ajouter le numéro de téléphone de l'objet suivi du séparateur
        builder.append(getTelephone());
        builder.append(SEPARATEUR);
        
        // Créer un objet SimpleDateFormat avec le format "dd/MM/yyyy"
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        
        // Ajouter la date de naissance de l'objet au format "dd/MM/yyyy"
        builder.append(formatDate.format(getDateNaissance()));
        
        // Renvoyer la chaîne de caractères contenue dans l'objet StringBuilder
        return builder.toString();
    }
}