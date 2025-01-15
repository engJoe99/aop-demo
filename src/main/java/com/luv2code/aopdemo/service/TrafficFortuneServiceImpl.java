package com.luv2code.aopdemo.service;


import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TrafficFortuneServiceImpl implements TrafficFortuneService{


    @Override
    public String getFortune() {
        // simulate a delay
        try {
            TimeUnit.SECONDS.sleep(5); //This line pauses the execution of the current thread for 5 seconds.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // return a fortune
        return "Expect Heavy Traffic this morning";
    }


    @Override
    public String getFortune(boolean tripWire) {

        if(tripWire) {
            throw new RuntimeException("Major accident!! Highway is closed!");
        }
        return getFortune();
    }



}
