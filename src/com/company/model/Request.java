package com.company.model;

public final class Request {
    private final int requestId;
    private final int customerId;

    public Request(int requestId, int customerId) {
        this.requestId = requestId;
        this.customerId = customerId;
    }
}
