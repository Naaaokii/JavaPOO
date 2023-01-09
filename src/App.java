import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static Scanner _scan = new Scanner(System.in);
        
    public static void main(String[] args) {
        afficherMenu(); 
        while(true){
            String choix = _scan.nextLine();
            switch(choix){
                case "1":
                    break;
                case "2":
                    break;
                case "q":
                    return;
                default:
                    System.out.println("NOPE !!!!");
                    break;
            }
        }
    }

    private static void ajouterContact(){

    }

    public static void afficherMenu(){
        ArrayList<String> menus = new ArrayList<>();
        menus.add(("-- MENU --"));
        menus.add(("1- Ajouter un contact"));
        menus.add(("2- Lister les contacts"));
        menus.add(("q- Quitter"));
        for (String menu : menus){
            System.out.println(menu);
        }
    }
}
