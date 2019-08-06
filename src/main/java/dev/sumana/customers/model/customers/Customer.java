package dev.sumana.customers.model.customers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.reactivex.Maybe;

@Document(collection = "customers")
public abstract class Customer {

    @Id
    private String id;
    private String name;
    private String surname;
    private String address;
    @Indexed(unique = true)
    private String email;

    public Customer() {
        // TODO Auto-generated constructor stub
    }

    public Customer(String name, String surname, String address, String email) {
        super();
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract Maybe<Customer> lvlUp();

    public abstract Maybe<Customer> lvlDown();
}
