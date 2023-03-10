package model;

// Importer des classes nécessaires pour la lecture et l'écriture de fichiers, la gestion des dates et des expressions régulières
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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



public class Contact implements Comparable<Contact>{
    
    // Déclarer une constante publique et statique nommée "SEPARATEUR" qui contient un séparateur de chaîne de caractères
    public static final String SEPARATEUR = ";"; //";" est le séparateur utilisé dans le fichier csv

    // Déclarer des attributs privés de la classe
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private Date dateNaissance;

    /**
     * Permet de récupérer le nom d'un contact
     * @return le nom du contact
     */
    public String getNom() {
        return nom;
    }


    /**
     * Permet d'assigner le paramètre (en majuscule) à l'attribut nom (str)
     * @param nom: Un nom (str)
     * Ne renvoie rien
     */
    public void setNom(String nom) {
        // Mettre le nom en majuscule avant de l'assigner à l'attribut "nom"
        this.nom = nom.toUpperCase(); 
    }

    /**
     * Permet de récupérer le prénom d'un contact
     * @return le prénom du contact
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Permet d'assigner le paramètre (première lettre en majuscule) à l'attribut prenom (str)
     * @param prenom: Un prenom (str)
     * Ne renvoie rien
     */
    public void setPrenom(String prenom) {
        // Mettre la première lettre du prénom en majuscule avant de l'assigner à l'attribut "prenom"
        this.prenom = prenom.replaceFirst(".",(prenom.charAt(0)+"").toUpperCase());
    }

    /**
     * Permet de récupérer le numéro de téléphone d'un contact
     * @return le numéro de téléphone du contact
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Permet d'assigner le paramètre à l'attribut telephone (str)
     * @param telephone: Un telephone (str)
     * Ne renvoie rien
     */
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

    /**
     * Permet de récupérer le mail d'un contact
     * @return le mail du contact
     */
    public String getMail() {
        return mail;
    }



    /**
     * Permet d'assigner le paramètre  à l'attribut mail (str)
     * @param mail: Un mail (str)
     * Ne renvoie rien
     */
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


    /**
     * Permet de récupérer la date de naissance d'un contact
     * @return la date de naissance du contact
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Permet d'assigner le paramètre à l'attribut DateNaissance (str)
     * @param dateNaissance: Une date le naissance (str)
     * Ne renvoie rien
     */
    public void setDateNaissance(String dateNaissance) throws ParseException {
        
        // Créer un format de date (jour/mois/année)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Définir l'attribut "dateNaissance" de l'objet avec la valeur de la chaîne de caractères (qui se fait parse()) selon le format de date)
        // Cette méthode renvoie une date ou une valeur nulle (dans le cas où la chaîne n'a pas été traitée en raison d'une erreur)
        this.dateNaissance = dateFormat.parse(dateNaissance); 
    }




    // Déclarer une méthode nommée "sauvegarderContact" qui peut lever une IOException
    /**
     * Permet d'inscrire les contacts dans le fichier csv
     * @throws IOException
     * Ne renvoie rien
     */
    public void sauvegarderContact() throws IOException { //exception fichier 
        
        // Créer un objet PrintWriter qui écrira dans un fichier nommé "contacts.csv" en utilisant l'option "true" pour ajouter du contenu à la fin du fichier
        PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        
        try {
            // Écrire la chaîne de caractères représentant l'objet courant dans le fichier
            printwriter.println(this.toString());
        } finally {
            // Fermer le flux de données lorsque la méthode est terminée
            printwriter.close();
        }
    }


    /**
     * Permet de lister tous les contacts du fichier csv
     * @return La liste des contacts
     * @throws IOException
     */
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




    /**
     * Permet de chercher un contact avec le nom ou la date de naissance
     * @param nom: Le nom du contact recherché (String)
     * @param x: Un entier (1 pour le nom et sinon pour la date)
     * @return Un contact
     * @throws IOException
     */
    public static ArrayList<Contact> chercherContact(String nom, int x) throws IOException { 
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

                if (x == 1){
                    if (tableauContact[0].equals(nom)){
                        // Ajouter l'objet Contact à la liste
                        contactCherche.add(contact);
                        System.out.println(contact.getNom() + " " 
                        + contact.getPrenom() + " " 
                        + contact.getMail() + " " 
                        + contact.getTelephone() + " " 
                        + contact.getDateNaissance());
                    }
                }else{
                    if (tableauContact[4].equals(nom)){
                        // Ajouter l'objet Contact à la liste
                        contactCherche.add(contact);
                        System.out.println(contact.getNom() + " " 
                        + contact.getPrenom() + " " 
                        + contact.getMail() + " " 
                        + contact.getTelephone() + " " 
                        + contact.getDateNaissance());
                    }
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
        // Renvoyer le contact
        return contactCherche;
    }


    /**
     * Permet de réécrire à l'intèrieur du fichier csv
     * @param list: Une liste de contact
     * @throws Exception
     * Ne renvoie rien
     */
    public static void refreshlist(ArrayList<Contact> list) throws Exception{
        PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", false)));
        try{
            for (Contact contact : list) {
                printwriter.println(contact.toString());
            }
        }catch (Exception exception){
            System.out.println("Problème pour refresh le csv");
        }finally{
            printwriter.close();
        }
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    // Redéfinir la méthode toString() héritée de la classe Object pour renvoyer une chaîne de caractères représentant l'objet Contact
    public String toString() {
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

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Contact c) {
        if (this.getNom().compareTo(c.getNom()) == 0){
            return this.getPrenom().compareTo(c.getPrenom());
        }else{
            return this.getNom().compareTo(c.getNom());
        }
    }
}