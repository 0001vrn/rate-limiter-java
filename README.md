# Rate-limiter-java

Perform rate limiting logic for provided customer ID. Return true if the
request is allowed, and false if it is not.

boolean rateLimit(int customerId)

constraint: Each customer can make X requests per Y seconds
