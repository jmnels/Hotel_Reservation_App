package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {
    private static CustomerService customerService;
    public Collection<Customer> customers = new ArrayList<>();
    private CustomerService(){}

    public static CustomerService getInstance() {
        if(customerService==null) {
            customerService = new CustomerService();
        }

        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.add(customer);
    }

    public Customer getCustomer(String customerEmail) {
        for(Customer customer : customers) {
            if(customer.email.equals(customerEmail)){
                return customer;
            }
        }
        Customer none = null;
        return none;
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }
}
