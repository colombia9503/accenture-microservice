package dev.sumana.customers.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.request.CustomerRequestBody;
import dev.sumana.customers.repository.customers.CustomerRepository;
import dev.sumana.customers.service.CustomerService;
import dev.sumana.customers.utils.NullAwareBeanUtils;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Single<Customer> saveCustomer(Customer customer) {
        log.debug("saving customer");
        return Single.just(customer).flatMap(c -> {
            return customerRepository.save(customer);
        }).onErrorResumeNext(t -> {
            if (t instanceof DuplicateKeyException)
                return Single.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer already registered"));
            return Single.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        });
    }

    @Override
    public Single<Customer> updateCustomer(Customer customer, CustomerRequestBody updates) {
        return Single.just(customer).flatMap(c -> {
            if (updates != null)
                try {
                    NullAwareBeanUtils.copyProperties(updates, c);
                } catch (Exception e) {
                    return Single.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something goes wrong", e));
                }

            return customerRepository.save(c);
        }).onErrorResumeNext(t -> {
            if (t instanceof DuplicateKeyException)
                return Single
                        .error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registereed"));
            return Single.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        });
    }

    @Override
    public Flowable<Customer> getAllCustomers() {
        log.debug("getting all customers");
        return customerRepository.findAll();
    }

    @Override
    public Maybe<Customer> getCustomerByEmail(String email) {
        log.debug("getting customer by email: {}", email);
        return customerRepository.findFirstByEmail(email);
    }

    @Override
    public Completable deleteCustomer(String id) {
        log.debug("deleting customer {}", id);
        return customerRepository.deleteById(id);
    }
}
