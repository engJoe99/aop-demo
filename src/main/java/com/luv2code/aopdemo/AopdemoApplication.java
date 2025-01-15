package com.luv2code.aopdemo;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;
import com.luv2code.aopdemo.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AopdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopdemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO,
                                               MembershipDAO theMembershipDAO,
                                               TrafficFortuneService theTrafficFortuneService) {

        return runner -> {

            //demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
            //demoTheAfterReturningAdvice(theAccountDAO);
            //demoTheAfterThrowingAdvice(theAccountDAO);
            //demoTheAfterAdvice(theAccountDAO);
            //demoTheAroundAdvice(theTrafficFortuneService);
            //demoTheAroundAdviceHandleException(theTrafficFortuneService);
            demoTheAroundAdviceRethrowTheException(theTrafficFortuneService);


        };
    }




    private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {

        theAccountDAO.addAccount(new Account("Youssef", "First"), true);
        theAccountDAO.doWork();
        theMembershipDAO.addMember();
        theAccountDAO.setName("JO");
        theAccountDAO.getName();
        theAccountDAO.setServiceCode("JO");
        theAccountDAO.getServiceCode();

    /*    System.out.println("\n let's call it again \n");
        theAccountDAO.addAccount();
        theMembershipDAO.addAccount();*/
    }

    private void demoTheAfterReturningAdvice(AccountDAO theAccountDAO) {

        List<Account> theAccounts = theAccountDAO.findAccounts();
        System.out.println("\n\n Main Program: demoTheAfterReturningAdvice");
        System.out.println("--------------");
        System.out.println(theAccounts);
        System.out.println("\n");
    }




    private void demoTheAfterThrowingAdvice(AccountDAO theAccountDAO) {

        // create a sample exception
        try {
            // add a boolean flag to simulate an exception
            boolean tripWire = true;
            theAccountDAO.findAccounts(tripWire);
        } catch (Exception exc) {
            System.out.println("\n\n Main Program: demoTheAfterThrowingAdvice");
            System.out.println("--------------");
            System.out.println("Exception: " + exc);
        }
    }


    private void demoTheAfterAdvice(AccountDAO theAccountDAO) {

        try {
            boolean tripWire = false;
            List<Account> theAccounts = theAccountDAO.findAccounts(tripWire);
            System.out.println("\n\n Main Program: ");
            System.out.println("--------------");
            System.out.println(theAccounts);
        }
        catch (Exception exc) {
            System.out.println("\n\n Main Program: ..... caught exception: " + exc);
        }
    }


    private void demoTheAroundAdvice(TrafficFortuneService theTrafficFortuneService) {

        System.out.println("\n\n Main Program: demoTheAroundAdvice");
        System.out.println("Calling getFortune()");
        System.out.println("\n My Fortune is: " + theTrafficFortuneService.getFortune());
        System.out.println("Finished");
    }


    private void demoTheAroundAdviceHandleException(TrafficFortuneService theTrafficFortuneService ) {

        System.out.println("\n\n Main Program: demoTheAroundAdviceHandleException");
        System.out.println("Calling getFortune()");

        boolean tripWire = true;
        System.out.println("\n My Fortune is: " + theTrafficFortuneService.getFortune(tripWire));
        System.out.println("Finished");
    }




    private void demoTheAroundAdviceRethrowTheException(TrafficFortuneService theTrafficFortuneService) {

        System.out.println("\n\n Main Program: demoTheAroundAdviceRethrowTheException");
        System.out.println("Calling getFortune()");

        boolean tripWire = true;
        System.out.println("\n My Fortune is: " + theTrafficFortuneService.getFortune(tripWire));
        System.out.println("Finished");

    }







}
