package library.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CollectionSorting {

    public static void main(String[] args) {
        var boris = new Customer("Boris", "customer1@mail.com");
        var alex = new Customer("Alex", "customer2@mail.com");
        var customers = new ArrayList<>(List.of(boris, alex));

        Collections.sort(customers);
        System.out.println("natural ordering: " + customers);

        customers.sort(new EmailComparator());
        System.out.println("total ordering: " + customers);
    }

    private record Customer(String name, String email) implements Comparable<Customer> {
        @Override
        public int compareTo(Customer o) {
            return name.compareTo(o.name);
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", name, email);
        }
    }

    private static class EmailComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.email.compareTo(o2.email);
        }
    }

}
