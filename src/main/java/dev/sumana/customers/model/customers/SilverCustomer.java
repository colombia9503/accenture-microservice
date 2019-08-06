package dev.sumana.customers.model.customers;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import io.reactivex.Maybe;

@Document(collection = "customers")
@TypeAlias("silver_customer")
public class SilverCustomer extends Customer {

    @Override
    public Maybe<Customer> lvlUp() {
        return Maybe.just(this).map(c -> {
            Customer customer = new GoldCustomer();
            BeanUtils.copyProperties(c, customer);
            return customer;
        });
    }

    @Override
    public Maybe<Customer> lvlDown() {
        return Maybe.just(this).map(c -> {
            Customer customer = new BronzeCustomer();
            BeanUtils.copyProperties(c, customer);
            return customer;
        });
    }

}
