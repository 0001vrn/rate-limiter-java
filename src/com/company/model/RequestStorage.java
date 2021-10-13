package com.company.model;

import java.util.Map;
import java.util.TreeMap;

public final class RequestStorage {
    private final Map<Long, Request> requestCache;

    public RequestStorage() {
        this.requestCache = new TreeMap<>();
    }

    public Map<Long, Request> getRequestCache() {
        return requestCache;
    }
}
