package com.company;

import com.company.service.RateLimiterServiceImpl;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        var rateLimiterService = new RateLimiterServiceImpl(10, 1);

        for(int i=0;i<150;i++) {
            System.out.println(rateLimiterService.rateLimit(new Random().nextInt(3)));
        }
    }
}
