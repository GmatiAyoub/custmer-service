package tn.isetkebili.customerservice.service;

import org.springframework.stereotype.Service;
import tn.isetkebili.customerservice.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final List<Customer> customers = new ArrayList<>();
    private long nextId = 1;

    public CustomerService() {
        customers.add(new Customer(nextId++, "Ali", "Ben Salah", "ali@example.com"));
        customers.add(new Customer(nextId++, "Sarra", "Trabelsi", "sarra@example.com"));
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public Customer addCustomer(Customer customer) {
        customer.setId(nextId++);
        customers.add(customer);
        return customer;
    }

    public boolean deleteCustomer(Long id) {
        return customers.removeIf(customer -> customer.getId().equals(id));
    }
}