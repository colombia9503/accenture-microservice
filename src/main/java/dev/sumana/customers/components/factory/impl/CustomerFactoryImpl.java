package dev.sumana.customers.components.factory.impl;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import dev.sumana.customers.components.factory.CustomerFactory;
import dev.sumana.customers.model.customers.BronzeCustomer;
import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.customers.GoldCustomer;
import dev.sumana.customers.model.customers.SilverCustomer;
import dev.sumana.customers.model.request.CustomerRequestBody;

@Component
public class CustomerFactoryImpl implements CustomerFactory {

    @Override
    public Customer createCustomer(CustomerRequestBody requestBody) throws ResponseStatusException {
        switch (requestBody.getType()) {
            case "gold":
                Customer goldCustomer = new GoldCustomer();
                BeanUtils.copyProperties(requestBody, goldCustomer);
                return goldCustomer;

            case "silver":
                Customer silverCustomer = new SilverCustomer();
                BeanUtils.copyProperties(requestBody, silverCustomer);
                return silverCustomer;

            case "bronze":
                Customer bronzeCustomer = new BronzeCustomer();
                BeanUtils.copyProperties(requestBody, bronzeCustomer);
                return bronzeCustomer;

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unrecognized membership");
        }
    }


}
