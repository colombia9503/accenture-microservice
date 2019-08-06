package dev.sumana.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.sumana.customers.model.customers.BronzeCustomer;
import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.repository.customers.CustomerRepository;

@SpringBootApplication
public class AccentureMicroserviceApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(AccentureMicroserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
