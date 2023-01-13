package comparator;

import java.util.Comparator;

import model.Contact;

public class Comparer implements Comparator<Contact> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Contact c1, Contact c2) {
        return c1.getDateNaissance().compareTo(c2.getDateNaissance());
    }
}
