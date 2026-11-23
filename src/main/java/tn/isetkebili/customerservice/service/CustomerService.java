package tn.isetkebili.customerservice.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tn.isetkebili.customerservice.model.Customer;
import tn.isetkebili.customerservice.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Récupérer tous les clients
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // Récupérer un client par son ID
    public Optional<Customer> getCustomerById(Long id) {
        return repository.findById(id);
    }

    // Ajouter un nouveau client
    public Customer addCustomer(Customer c) {

        if (c.getEmail() == null || c.getEmail().isBlank()
                || c.getPassword() == null || c.getPassword().isBlank()) {
            throw new IllegalArgumentException("Email et mot de passe requis");
        }

        if (repository.existsByEmailIgnoreCase(c.getEmail())) {
            throw new IllegalStateException("Cet email est déjà utilisé");
        }

        // L'identifiant est généré automatiquement par PostgreSQL
        c.setId(null);

        // Hachage du mot de passe avec BCrypt
        c.setPassword(encoder.encode(c.getPassword()));

        return repository.save(c);
    }

    // Supprimer un client
    public boolean deleteCustomer(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    // Connexion d'un client
    public Customer login(String email, String password) {

        if (email == null || password == null) {
            return null;
        }

        return repository.findByEmailIgnoreCase(email)
                .filter(c -> encoder.matches(password, c.getPassword()))
                .orElse(null);
    }
}
