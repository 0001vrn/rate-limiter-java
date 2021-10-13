package com.company.model;

public final class CustomerRequestStorage {
    private final int customerId;
    private final RequestStorage requestStorage;

    public CustomerRequestStorage(int customerId, RequestStorage requestStorage) {
        this.customerId = customerId;
        this.requestStorage = requestStorage;
    }

    public int getCustomerId() {
        return customerId;
    }

    public RequestStorage getRequestStorage() {
        return requestStorage;
    }
}
