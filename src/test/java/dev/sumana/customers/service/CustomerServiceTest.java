package dev.sumana.customers.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.sumana.customers.model.customers.BronzeCustomer;
import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.request.CustomerRequestBody;
import dev.sumana.customers.repository.customers.CustomerRepository;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void init() {
        customerRepository.deleteAll().blockingAwait();
    }

    @Test
    public void saveCustomer() {
        TestObserver<Customer> observer = new TestObserver<>();
        Customer customer = new BronzeCustomer();
        customer.setName("John");
        customer.setEmail("jdow@mail.com");
        customer.setSurname("Doe");
        customer.setAddress("Street test");

        customerService.saveCustomer(customer).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertComplete();
        observer.assertNoErrors();
    }

    @Test
    public void updateCustomer() {
        TestObserver<Customer> observer = new TestObserver<>();
        Customer customer = new BronzeCustomer();
        customer.setName("John");
        customer.setEmail("jdow@mail.com");
        customer.setSurname("Doe");
        customer.setAddress("Street test");

        CustomerRequestBody requestBody = new CustomerRequestBody();
        requestBody.setEmail("updated@mail.com");

        customerService.updateCustomer(customer, requestBody).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertComplete();
        observer.assertNoErrors();
    }

    @Test
    public void getAllCustomers() {
        TestSubscriber<Customer> subscriber = new TestSubscriber<>();
        customerService.getAllCustomers().subscribe(subscriber);

        subscriber.awaitTerminalEvent();

        subscriber.assertSubscribed();
    }
}
