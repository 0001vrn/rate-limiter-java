package com.company.service;

import com.company.exception.RateLimitExceededException;
import com.company.model.CustomerRequestStorage;
import com.company.model.Request;
import com.company.model.RequestStorage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RateLimiterServiceImpl implements RateLimiterService {
    // x requests
    private final int customerRequestQuota;
    // y seconds
    private final int customerRequestTtl;
    // customer request cache
    private final List<CustomerRequestStorage> customerRequestStorageList;

    public RateLimiterServiceImpl(int customerRequestQuota, int customerRequestTtl) {
        this.customerRequestQuota = customerRequestQuota;
        this.customerRequestTtl = customerRequestTtl;
        customerRequestStorageList = new ArrayList<>();
    }

    /**
     * Notifies whether customer id should be rate limited or not
     *
     * @param customerId a unique identifier for a customer
     * @return true or false depending upon customerRequestQuota and customerRequestTtl
     */
    public boolean rateLimit(int customerId) {
        var maybeCustomerRequestStorage = customerRequestStorageList.stream()
            .filter(p -> customerId == p.getCustomerId())
            .findFirst();

        CustomerRequestStorage customerRequestStorage;
        if (maybeCustomerRequestStorage.isEmpty()) {
            customerRequestStorage = new CustomerRequestStorage(customerId, new RequestStorage());
            customerRequestStorageList.add(customerRequestStorage);
        } else {
            customerRequestStorage = maybeCustomerRequestStorage.get();
        }

        var currenTimestamp = Instant.now().toEpochMilli();
        var currentRequest = new Request(customerId, customerId);
        System.out.println("Request comes in for customer id " + customerId);

        if (customerRequestStorage.getRequestStorage().getRequestCache().isEmpty()) {
            System.out.println("Cache is empty, we can populate");
            customerRequestStorage.getRequestStorage().getRequestCache().put(currenTimestamp, currentRequest);
            return true;
        } else {
            var entryList = new ArrayList<>(customerRequestStorage.getRequestStorage().getRequestCache().entrySet());
            var lastRequestTimestamp = entryList.get(entryList.size() - 1).getKey();

            if (customerRequestTtl < lastRequestTimestamp - currenTimestamp) {
                customerRequestStorage.getRequestStorage().getRequestCache().put(currenTimestamp, currentRequest);
                return true;
            } else {
                if (customerRequestQuota < customerRequestStorage.getRequestStorage().getRequestCache().size()) {
                    var exception = new RateLimitExceededException("Rate limit exceeded for the customer id " + customerId);
                    System.out.println(exception);
                    return false;
                } else {
                    customerRequestStorage.getRequestStorage().getRequestCache().put(currenTimestamp, currentRequest);
                    return true;
                }
            }
        }
    }
}
