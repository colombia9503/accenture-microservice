package dev.sumana.customers.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import dev.sumana.customers.model.customers.Customer;

public abstract class NullAwareBeanUtils {
    public static void copyProperties(Object source, Object destination) throws ResponseStatusException {
        try {
            PropertyUtils.describe(source).entrySet().stream()
                    .filter(p -> p.getValue() != null)
                    .filter(p -> !p.getKey().equals("class"))
                    .forEach(p -> {
                        try {
                            PropertyUtils.setProperty(destination, p.getKey(), p.getValue());
                        } catch (Exception e) {
                            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error copying properties", e);
                        }
                    });
        } catch (Exception e2) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error copying properties", e2);
        }

    }
}
