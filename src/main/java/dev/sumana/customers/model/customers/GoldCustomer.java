package dev.sumana.customers.model.customers;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import io.reactivex.Maybe;

@Document(collection = "customers")
@TypeAlias("gold_customer")
public class GoldCustomer extends Customer {

    @Override
    public Maybe<Customer> lvlUp() {
        return Maybe.just(this).flatMap(c -> {
            return Maybe.error(new UnsupportedOperationException("A gold customer cannot reach his actual lvl"));
        });
    }

    @Override
    public Maybe<Customer> lvlDown() {
        return Maybe.just(this).map(c -> {
            Customer customer = new SilverCustomer();
            BeanUtils.copyProperties(c, customer);
            return customer;
        });
    }

}
