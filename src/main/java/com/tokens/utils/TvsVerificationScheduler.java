package com.tokens.utils;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TvsVerificationScheduler {
	
	@Scheduled(fixedRate = 5000) // run every 5 seconds
    public void Verification() {
        // code to be executed at the scheduled time
        System.out.println("Job executed at " + new Date());
    }

}
