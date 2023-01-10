import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Contact;

public class App {
    
    // Créer un objet Scanner qui permettra de lire les entrées de l'utilisateur
    private static Scanner _scan = new Scanner(System.in);

    // Déclarer une méthode nommée "main" qui prend en argument une chaîne de caractères et qui peut lever une Exception
    public static void main(String[] args) throws Exception {
        
        // Afficher le menu
        afficherMenu();
        
        // Boucle tant que
        while (true) {
            // Lire la ligne entrée par l'utilisateur
            String choix = _scan.nextLine();
            
            // Selon la valeur de "choix"
            switch (choix) {
                case "1":
                    // Appeler la méthode "ajouterContact"
                    ajouterContact();
                    break;
                case "2":
                    // Appeler la méthode "listerContact"
                    listerContact();
                    break;
                case "3":
                    // Créer un objet Contact
                  
                    
                    // Demander de saisir le nom du contact recherché
                    System.out.println("Saisir le nom :");

                    // Appeler la méthode "chercherContact" de l'objet contact
                    Contact.chercherContact(_scan.nextLine());
                    break;
                case "q":
                    // Fermer le flux de données de l'objet Scanner
                    _scan.close();
                    // Quitter la boucle infinie
                    return;
                default:
                    System.out.println("Fais gaffe, ce n'est point la bonne touche !!");
                    break;
            }
            afficherMenu();
        }
    }



    public static void afficherMenu() {
        // Créer une liste de chaînes de caractères pour stocker les éléments du menu
        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts");
        menus.add("3- Chercher un contact");
        menus.add("q- Quitter");
        // Pour chaque élément de la liste, afficher la chaîne de caractères
        for (String s : menus) {
            System.out.println(s);
        }
    }




    // Méthode qui permet d'ajouter un contact
    private static void ajouterContact() {
        
        // Créer un objet Contact
        Contact contact = new Contact();
        
        // Demander de saisir le nom et appeler la méthode "setNom" de l'objet Contact
        System.out.println("Saisir le nom :");
        contact.setNom(_scan.nextLine());
        
        // Demander de saisir le prénom et appeler la méthode "setPrenom" de l'objet Contact
        System.out.println("Saisir le prénom :");
        contact.setPrenom(_scan.nextLine());

        // Boucle "infinie"
        do {
            // Essayer de saisir le numéro de téléphone et appeler la méthode "setTelephone" de l'objet Contact
            try {
                System.out.println("Saisir le téléphone :");
                contact.setTelephone(_scan.nextLine());
                break;
            } catch (ParseException exception) {
                // Si une erreur se produit, afficher le message d'erreur
                System.out.println(exception.getMessage());
            }
        } while (true); // boucler tant que la valeur de "true" est vraie

        do {
            // Essayer de saisir l'adresse mail et appeler la méthode "setMail" de l'objet Contact
            try {
                System.out.println("Saisir le mail :");
                contact.setMail(_scan.nextLine());
                break;
            } catch (ParseException exception) {
                // Si une erreur se produit, afficher le message d'erreur
                System.out.println(exception.getMessage());
            }
        } while (true); // boucler tant que la valeur de "true" est vraie

        do {
            // Essayer de saisir la date de naissance et appeler la méthode "setDateNaissance" de l'objet Contact
            try {
                System.out.println("Saisir la date de naissance :");
                contact.setDateNaissance(_scan.nextLine());
                break;
            } catch (ParseException exception) {
                // Si une erreur se produit, afficher le message d'erreur
                System.out.println("Date de naissance non valide !!!");
            }
        } while (true); // boucler tant que la valeur de "true" est vraie

        // Essayer d'enregistrer l'objet Contact
        try {
            contact.sauvegarderContact();
            // Si l'enregistrement a réussi, afficher un message de confirmation
            System.out.println("Le contact à bien été enregistré");
        } catch (IOException exception) {
            // Si une erreur se produit, afficher un message d'erreur
            System.out.println("Erreur d'enregistrement");
        }
    }




    // Méthode qui affiche la liste des contacts
    private static void listerContact() {
        // Essayer de récupérer la liste des contacts
        try {
            // Appeler la méthode "lister" de la classe Contact qui retourne une ArrayList d'objets Contact
            ArrayList<Contact> listeContact = Contact.listerContacts();

            // Pour chaque objet Contact de la liste
            for (Contact contact : listeContact) {
                // Afficher le prénom et le nom de l'objet Contact
                System.out.println(contact.getNom() + " " + contact.getPrenom());
            }
        } catch (IOException exception) {
            // Si une erreur se produit avec le fichier, afficher un message d'erreur
            System.out.println("Erreur avec le fichier");
        }
    }
}