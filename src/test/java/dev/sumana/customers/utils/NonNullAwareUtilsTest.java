package dev.sumana.customers.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.customers.GoldCustomer;
import dev.sumana.customers.model.request.CustomerRequestBody;

public class NonNullAwareUtilsTest {

    @Test
    public void NullAwareCopyTest() {
        CustomerRequestBody body = new CustomerRequestBody();
        body.setAddress("123");
        body.setEmail("santiago@1234.com");

        Customer customer = new GoldCustomer();
        customer.setName("Santiago");
        customer.setAddress("calle123");
        customer.setSurname("Umana");
        customer.setEmail("santi950312@gmail.com");

        NullAwareBeanUtils.copyProperties(body, customer);

        assertTrue(customer.getEmail().equals(body.getEmail()) && customer.getName() != null);
    }

}
