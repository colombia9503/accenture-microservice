package dev.sumana.customers.repository;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.sumana.customers.model.customers.BronzeCustomer;
import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.customers.GoldCustomer;
import dev.sumana.customers.model.customers.SilverCustomer;
import dev.sumana.customers.repository.customers.CustomerRepository;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void deleteAllDocumentsFromCustomersCollection() throws InterruptedException {
        TestObserver<Void> observer = new TestObserver<Void>();
        customerRepository.deleteAll().subscribe(observer);

        observer.await();

        observer.assertComplete();
    }

    @Test
    public void savingBronzeCustomerSuccessfully() throws InterruptedException {
        Customer customer = new BronzeCustomer();
        customer.setAddress("Calle 12");
        customer.setEmail("santi950312@gmail.com");
        customer.setName("Santiago");
        customer.setSurname("Umana");

        TestObserver<Customer> observer = new TestObserver<>();
        customerRepository.save(customer).subscribe(observer);

        observer.await();

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertTerminated();
    }

    @Test
    public void savingSilverCustomerSuccessfully() throws InterruptedException {
        Customer customer = new SilverCustomer();
        customer.setAddress("cale 123");
        customer.setEmail("silver@gmail.com");
        customer.setName("Marcus");
        customer.setSurname("Villa");

        TestObserver<Customer> observer = new TestObserver<>();
        customerRepository.save(customer).subscribe(observer);

        observer.await();

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertTerminated();
    }

    @Test
    public void savingGoldCustomerSuccessfully() throws InterruptedException {
        Customer customer = new GoldCustomer();
        customer.setAddress("Calle 1234");
        customer.setEmail("goldcustomer@gmail.com");
        customer.setName("Jonh");
        customer.setSurname("Doe");

        TestObserver<Customer> observer = new TestObserver<>();
        customerRepository.save(customer).subscribe(observer);

        observer.await();

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertTerminated();
    }

    @Test
    public void findCustomerByEmail() throws InterruptedException {
        TestObserver<Customer> observer = new TestObserver<>();
        Customer customer = new BronzeCustomer();
        customer.setAddress("Calle 12");
        customer.setEmail("searchtest@gmail.com");
        customer.setName("Santiago");
        customer.setSurname("Umana");
        customerRepository.save(customer).flatMapMaybe(c -> customerRepository.findFirstByEmail("searchtest@gmail.com")).subscribe(observer);
        observer.await();

        observer.assertComplete();
        observer.assertValue(c -> c instanceof Customer && Optional.ofNullable(c).isPresent()
                && c.getEmail().equals("searchtest@gmail.com"));
    }

    @Test
    public void updateCustomer() throws InterruptedException {
        TestObserver<Customer> observer = new TestObserver<>();

        customerRepository.findFirstByEmail("santi950312@gmail.com").flatMapSingle(c -> {
            c.setName("Santiago updated");
            return customerRepository.save(c);
        }).subscribe(observer);

        observer.await();

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertTerminated();
    }

    @Test
    public void getAllCustomersSuccessful() throws InterruptedException {
        TestSubscriber<Customer> subscriber = new TestSubscriber<>();
        customerRepository.findAll().subscribe(subscriber);

        subscriber.await();

        subscriber.assertComplete();
        subscriber.assertNoErrors();
    }

}
