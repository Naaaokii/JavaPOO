import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import comparator.Comparer;
import model.Contact;

public class App{
    
    // Créer un objet Scanner qui permettra de lire les entrées de l'utilisateur
    private static Scanner _scan = new Scanner(System.in);

    public static final String SEPARATEUR = ";";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
                            System.out.println("1- Par ordre alphabétique");
                            System.out.println("2- Par ordre alphabétique inversé");
                            String choixOrdreNom = _scan.nextLine();
                            switch(choixOrdreNom){
                                case "1":
                                    triNom(1);
                                    break;
                                case "2":
                                    triNom(2);
                                    break;
                            }
                            break;
                        case "2":
                            System.out.println("1- Par ordre alphabétique");
                            System.out.println("2- Par ordre alphabétique inversé");
                            String choixOrdreMail = _scan.nextLine();
                            switch(choixOrdreMail){
                                case "1":
                                    triMail(1);
                                    break;
                                case "2":
                                    triMail(2);
                                    break;
                            }
                            break;
                        case "3":
                            System.out.println("1- Par ordre alphabétique");
                            System.out.println("2- Par ordre alphabétique inversé");
                            String choixOrdreDate = _scan.nextLine();
                            switch(choixOrdreDate){
                                case "1":
                                    triDate(1);
                                    break;
                                case "2":
                                    triDate(2);
                                    break;
                            }
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
                    Contact.chercherContact(_scan.nextLine(),1);
                    break;
                case "4":
                    // Demander de saisir la date du contact recherché
                    System.out.println("Saisir la date :");

                    // Appeler la méthode "chercherContact" de l'objet contact
                    Contact.chercherContact(_scan.nextLine(), 2);
                    break;
                case "5":
                    // Demander de saisir le mail du contact recherché
                    System.out.println("Saisir mail :");
                    changeContact(_scan.nextLine());
                    break;
                case "6":
                    System.out.println("Saisir le mail :");
                    contactDelete(_scan.nextLine());
                    break;
                case "7":
                    System.out.println("Saisir pour la recherche :");
                    String prenom = _scan.nextLine();
                    prenom = prenom.replaceFirst(".",(prenom.charAt(0)+"").toUpperCase());
                    searchContactByPrenom(prenom);
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
        menus.add(ANSI_PURPLE_BACKGROUND + "----------------------- MENU -----------------------");
        menus.add("| 1- Ajouter un contact                            |");
        menus.add("| 2- Lister les contacts                           |");
        menus.add("| 3- Chercher un contact avec le nom               |");
        menus.add("| 4- Chercher un contact avec la date de naissance |"); 
        menus.add("| 5- Modifier un contact                           |");
        menus.add("| 6- Supprimer un contact                          |"); 
        menus.add("| 7- Recherche sur le prenom                       |");
        menus.add("| q- Quitter                                       |");
        menus.add("----------------------------------------------------");
        menus.add(ANSI_RESET);
        // Pour chaque élément de la liste, afficher la chaîne de caractères
        for (String s : menus) {
            System.out.println(s);
        }
    }



    /**
     * Permet de trier les contacts par noms dans l'ordre croissant ou non
     * @param ordre: un entier (1 pour croissant et 2 pour non)
     * @throws IOException
     * Ne renvoie rien
     */
    private static void triNom(int ordre) throws IOException{
        try{
            ArrayList<Contact> list = Contact.listerContacts();
            Collections.sort(list);
            if (ordre == 2){
                Collections.reverse(list);
            }
            String str = list.toString().replaceAll(",", "\n").replaceAll(SEPARATEUR, " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Problème avec le tri par nom");
        }   
    }

    
    /**
     * Permet de trier les contacts par mails dans l'ordre alphabétique
     * @throws IOException
     * Ne renvoie rien
     */
    private static void triMail(int ordre) throws IOException{
        try{
            ArrayList<Contact> list = Contact.listerContacts();
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    return c1.getMail().compareTo(c2.getMail());
                }
            });
            if (ordre == 2){
                Collections.reverse(list);
            }
            String str = list.toString().replaceAll(",", "\n").replaceAll(SEPARATEUR, " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Problème avec le tri par mail");
        }   
    }


    private static void triDate(int ordre) throws IOException{
        try{
            ArrayList<Contact> list = Contact.listerContacts();
            Comparer comparer = new Comparer();
            Collections.sort(list, comparer);
            if (ordre == 2){
                Collections.reverse(list);
            }
            String str = list.toString().replaceAll(",", "\n").replaceAll(SEPARATEUR, " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Problème avec le tri par date de naissance");
        }   
    }


    // Méthode qui permet d'ajouter un contact
    private static Contact ajouterContact() {
        
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
        return contact;
    }


    private static void changeContact(String contactAModifier) throws IOException, Exception{
        try {
            ArrayList<Contact> list = Contact.listerContacts();
            for (Contact contact : list) {
                String str = contact.toString();
                String[] contactList = str.split(SEPARATEUR);
                if (contactList[2].equals(contactAModifier)){
                    String[] tableauContactRecherche = contactList;
                    contactDelete(contactAModifier);
                    list.add(ajouterContactModif(tableauContactRecherche));
                }
            }
            Contact.refreshlist(list);
            System.out.println("Le contact a bien été modifié");
        } catch (IOException exception) {
            System.out.println("Erreur de modification du contact - IO");
        }catch (Exception exception) {
            System.out.println("");
        }
    }


    private static Contact ajouterContactModif(String[] contactAModifer) throws ParseException{
        // Créer un objet Contact
        Contact contact = new Contact();
        
        // Demander de saisir le nom et appeler la méthode "setNom" de l'objet Contact
        System.out.println("Saisir le nom :");
        System.out.println(contactAModifer[0]);
        String nom = _scan.nextLine();
        if(nom == ""){
            contact.setNom(contactAModifer[0]);
        }else{
            contact.setNom(nom);
        }
        
        // Demander de saisir le prénom et appeler la méthode "setPrenom" de l'objet Contact
        System.out.println("Saisir le prénom :");
        System.out.println(contactAModifer[1]);
        String prenom = _scan.nextLine();
        if(prenom == ""){
            contact.setPrenom(contactAModifer[1]);
        }else{
            contact.setPrenom(prenom);
        }

        // Boucle "infinie"
        do {
            // Essayer de saisir le numéro de téléphone et appeler la méthode "setTelephone" de l'objet Contact
            try {
                System.out.println("Saisir le numéro de téléphone :");
                System.out.println(contactAModifer[3]);
                String telephone = _scan.nextLine();
                if(telephone == ""){
                    contact.setTelephone(contactAModifer[3]);
                }else{
                    contact.setTelephone(telephone);
                }
                break;
            } catch (Exception exception) {
                // Si une erreur se produit, afficher le message d'erreur
                System.out.println(exception.getMessage());
            }
        } while (true); // boucler tant que la valeur de "true" est vraie

        do {
            // Essayer de saisir l'adresse mail et appeler la méthode "setMail" de l'objet Contact
            try {
                System.out.println("Saisir le mail :");
                System.out.println(contactAModifer[2]);
                String mail = _scan.nextLine();
                if(mail == ""){
                    contact.setMail(contactAModifer[2]);
                }else{
                    contact.setMail(mail);
                }
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
                System.out.println(contactAModifer[4]);
                String date = _scan.nextLine();
                if(date == ""){
                    contact.setDateNaissance(contactAModifer[4]);
                }else{
                    contact.setDateNaissance(date);
                }
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
        return contact;
    }


    private static void contactDelete(String contactSupprimer) throws IOException{
        try {
            ArrayList<Contact> list = Contact.listerContacts();
            Predicate<Contact> condition = contact -> contact.getMail().startsWith(contactSupprimer);
            list.removeIf(condition);
            Contact.refreshlist(list);
        } catch (Exception e) {
            System.out.println("Erreur de supression du contact");
        }
    }

    private static void searchContactByPrenom(String prenom) throws IOException {
        ArrayList<Contact> list = Contact.listerContacts();

        List<Contact> contactFind = list.stream()
                .filter((contact) -> contact.getPrenom().startsWith(prenom))
                .toList();

        System.out.println(contactFind);
    }
}