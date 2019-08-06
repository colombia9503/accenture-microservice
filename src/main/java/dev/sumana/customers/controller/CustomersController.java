package dev.sumana.customers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.sumana.customers.components.factory.CustomerFactory;
import dev.sumana.customers.model.customers.Customer;
import dev.sumana.customers.model.request.CustomerRequestBody;
import dev.sumana.customers.service.CustomerService;
import dev.sumana.customers.service.MembershipService;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomersController {

    private CustomerService customerService;
    private CustomerFactory customerFactory;
    private MembershipService membershipService;

    public CustomersController(CustomerService customerService, CustomerFactory customerFactory,
                               MembershipService membershipService) {
        this.customerService = customerService;
        this.customerFactory = customerFactory;
        this.membershipService = membershipService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody CustomerRequestBody customer) {
        return RxJava2Adapter.singleToMono(customerService.saveCustomer(customerFactory.createCustomer(customer)));
    }

    @PutMapping("/{email}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Customer> updateCustomerByEmail(@PathVariable("email") String email,
                                                @RequestBody CustomerRequestBody customer) {
        return RxJava2Adapter.singleToMono(customerService.getCustomerByEmail(email)
                .flatMapSingle(c -> customerService.updateCustomer(c, customer)));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomerByEmail(@PathVariable("email") String email) {
        return RxJava2Adapter.completableToMono(customerService.getCustomerByEmail(email)
                .flatMapCompletable(c -> customerService.deleteCustomer(c.getId())));
    }

    @GetMapping("/{email}")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<Customer> getCustomerByEmail(@PathVariable("email") String email) {
        return RxJava2Adapter.maybeToMono(customerService.getCustomerByEmail(email));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<Customer> getCustomers() {
        return RxJava2Adapter.flowableToFlux(customerService.getAllCustomers());
    }

    @GetMapping("/upgrade/{email}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Mono<Customer> upgradeCustomer(@PathVariable("email") String email) {
        return RxJava2Adapter.singleToMono(
                customerService.getCustomerByEmail(email).flatMap(c -> membershipService.upgradeCustomer(c))
                        .flatMapSingle(c -> customerService.updateCustomer(c, null)));
    }

    @GetMapping("/downgrade/{email}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Mono<Customer> downgradeCustomer(@PathVariable("email") String email) {
        return RxJava2Adapter.singleToMono(
                customerService.getCustomerByEmail(email).flatMap(c -> membershipService.downGradecustomer(c))
                        .flatMapSingle(c -> customerService.updateCustomer(c, null)));
    }
}
