package dev.sumana.customers.repository.customers;

import org.springframework.data.repository.reactive.RxJava2CrudRepository;

import dev.sumana.customers.model.customers.Customer;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CustomerRepository extends RxJava2CrudRepository<Customer, String> {
    public Flowable<Customer> findAll();

    public Maybe<Customer> findFirstByEmail(String email);

    public Single<Customer> save(Customer customer);

    public Completable delete(Customer entity);
}
