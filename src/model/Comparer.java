package model;

import java.util.Comparator;

public class Comparer implements Comparator<Contact> {
    public int compare(Contact c1, Contact c2) {
        return c1.getDateNaissance().compareTo(c2.getDateNaissance());
    }
}
