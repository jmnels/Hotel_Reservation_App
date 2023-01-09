package model;

public class Customer {
    private final String firstName;
    private final String lastName;
    public final String email;
    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        if(email.matches(regex)) {
            this.email = email;
        }
        else{
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 33 * hash + (this.firstName!=null ? this.firstName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return true;
    }
}
