import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;

import Comparator.Comparer;
import model.Contact;

public class App{
    
    // Créer un objet Scanner qui permettra de lire les entrées de l'utilisateur
    private static Scanner _scan = new Scanner(System.in);

    public static final String SEPARATEUR = ";";

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
                    System.out.println("1- Tri par Nom");
                    System.out.println("2- Tri par Mail");
                    System.out.println("3- Tri par Date de Naissance");
                    System.out.println("q- Quitter");
                    String choixtri = _scan.nextLine();
                    switch(choixtri){
                        case "1":
                            triNom();
                            break;
                        case "2":
                            triMail();
                            break;
                        case "3":
                            triDate();
                            break;
                        case "q":
                            // Fermer le flux de données de l'objet Scanner
                            _scan.close();
                            // Quitter la boucle infinie
                            return;
                    }
                    break;
                case "3":
                    // Demander de saisir le nom du contact recherché
                    System.out.println("Saisir le nom :");

                    // Appeler la méthode "chercherContact" de l'objet contact
                    Contact.chercherContact(_scan.nextLine(), 1);
                    break;
                case "4":
                    // Demander de saisir le nom du contact recherché
                    System.out.println("Saisir la date :");

                    // Appeler la méthode "chercherContact" de l'objet contact
                    Contact.chercherContact(_scan.nextLine(), 2);
                    break;
                case "5":
                    // Demander de saisir le nom du contact recherché
                    System.out.println("Saisir le nom :");

                    changeContact();
                    break;
                case "6":
                    System.out.println("Saisir le mail :");
                    contactDelete(_scan.nextLine());
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



    private static void changeContact() throws IOException {
       ArrayList<Contact> list = Contact.chercherContact(_scan.nextLine(), 1);
       Contact.modifierContact(list);
    }

    public static void afficherMenu() {
        // Créer une liste de chaînes de caractères pour stocker les éléments du menu
        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts");
        menus.add("3- Chercher un contact avec le nom");
        menus.add("4- Chercher un contact avec la date de naissance"); 
        menus.add("5- Modifier un contact"); // A faire
        menus.add("6- Supprimer un contact"); 
        menus.add("q- Quitter");
        // Pour chaque élément de la liste, afficher la chaîne de caractères
        for (String s : menus) {
            System.out.println(s);
        }
    }

    // 


    public static void triNom() throws IOException{
        try{
            ArrayList<Contact> list = Contact.listerContacts();
            Collections.sort(list);
            String str = list.toString().replaceAll(",", "\n").replaceAll(SEPARATEUR, " ");
            System.out.println(str);
            /*if (tri == 1 || tri == 2){
                Collections.sort(list, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact c1, Contact c2) {
                        return c1.getNom().compareTo(c2.getNom());
                    }
                });
            */
        }catch (IOException exception){
            System.out.println("Problème avec le tri des contacts");
        }   
    }

    
    public static void triMail() throws IOException{
        try{
            ArrayList<Contact> list = Contact.listerContacts();
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    return c1.getMail().compareTo(c2.getMail());
                }
            });
            String str = list.toString().replaceAll(",", "\n").replaceAll(SEPARATEUR, " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Problème avec le tri des contacts");
        }   
    }


    public static void triDate() throws IOException{
        try{
            ArrayList<Contact> list = Contact.listerContacts();
            Comparer comparer = new Comparer();
            Collections.sort(list, comparer);
            String str = list.toString().replaceAll(",", "\n").replaceAll(SEPARATEUR, " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Problème avec le tri des contacts");
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

    private static void contactDelete(String contactSupprimer) throws IOException{
        ArrayList<Contact> list = Contact.listerContacts();
        Predicate<Contact> condition = contact -> contact.getMail().startsWith(contactSupprimer);

        list.removeIf(condition);
        Contact.refreshlist(list);
        System.out.println(list);
    }

}