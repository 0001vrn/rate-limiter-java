package com.company;

import com.company.service.RateLimiterServiceServiceImpl;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        var rateLimiterService = new RateLimiterServiceServiceImpl(10, 1);

        for(int i=0;i<150;i++) {
            System.out.println(rateLimiterService.rateLimit(new Random().nextInt(3)));
        }
    }
}
