package dev.sumana.customers.service;

import dev.sumana.customers.model.customers.Customer;
import io.reactivex.Maybe;

public interface MembershipService {
    public Maybe<Customer> upgradeCustomer(Customer customer);

    public Maybe<Customer> downGradecustomer(Customer customer);
}
