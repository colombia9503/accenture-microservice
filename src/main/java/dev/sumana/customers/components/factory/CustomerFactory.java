package dev.sumana.customers.components.factory;


import org.springframework.web.server.ResponseStatusException;

import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.request.CustomerRequestBody;

public interface CustomerFactory {
    public Customer createCustomer(CustomerRequestBody requestBody) throws ResponseStatusException;
}
