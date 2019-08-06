package dev.sumana.customers.service;

import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.request.CustomerRequestBody;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CustomerService {
    public Single<Customer> saveCustomer(Customer customer);

    public Single<Customer> updateCustomer(Customer customer, CustomerRequestBody updates);

    public Flowable<Customer> getAllCustomers();

    public Maybe<Customer> getCustomerByEmail(String email);

    public Completable deleteCustomer(String id);
}
