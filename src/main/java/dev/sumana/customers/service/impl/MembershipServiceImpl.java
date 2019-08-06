package dev.sumana.customers.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.service.MembershipService;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Override
    public Maybe<Customer> upgradeCustomer(Customer customer) {
        return Single.just(customer).flatMapMaybe(c -> c.lvlUp()).onErrorResumeNext(t -> {
            if (t instanceof UnsupportedOperationException)
                return Maybe.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        ((UnsupportedOperationException) t).getMessage()));
            return Maybe.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something goes wrong", t));
        });
    }

    @Override
    public Maybe<Customer> downGradecustomer(Customer customer) {
        return Single.just(customer).flatMapMaybe(c -> c.lvlDown()).onErrorResumeNext(t -> {
            if (t instanceof UnsupportedOperationException)
                return Maybe.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        ((UnsupportedOperationException) t).getMessage()));
            return Maybe.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something goes wrong", t));
        });
    }

}
