package com.luv2code.aopdemo;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;
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
    public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {
        return runner -> {

            //demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
            demoTheAfterReturningAdvice(theAccountDAO);


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







}
