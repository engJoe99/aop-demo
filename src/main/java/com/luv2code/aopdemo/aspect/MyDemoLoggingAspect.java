package com.luv2code.aopdemo.aspect;


import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
    // this is where we add all of our related advices for logging



    // This aspect-oriented programming code defines ==> a method that serves as a before advice
    // for the execution of the addAccount() method. When addAccount() is called,
    // the beforeAddAccountAdvice() method will be executed first, printing a message to the console.

    //@Before("execution(public void addAccount())")
    //@Before("execution(public void com.luv2code.aopdemo.dao.AccountDAO.addAccount())")
    //@Before("execution(* add*())")
    //@Before("execution(* add*(com.luv2code.aopdemo.Account))")
    //@Before("execution(* add*(..))") ===>>> will cause an error on IntelliJ Ultimate
    //@Before("execution(* add*(com.luv2code.aopdemo.Account, ..))")
    @Before("com.luv2code.aopdemo.aspect.AOPExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {

        System.out.println("\n ===>>> Executing @Before advice on addAccount()");

        // JoinPoint has metadata about method call
        // display the method signature
        MethodSignature theMethodSignature = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Method: " + theMethodSignature);

        // display the method arguments
        Object[] args = theJoinPoint.getArgs();

        for(Object tempArg : args) {
            System.out.println(tempArg);
            if(tempArg instanceof Account theAccount) {
                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
            }
        }

    }




    // @AfterReturning advice executes after the successful completion
    // of the findAccounts method in the AccountDAO class. It logs the method signature and the result
    // returned by the method, allowing for monitoring and debugging of the method's behavior.
    @AfterReturning(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result")
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n\n ===>>> Executing @AfterReturning on method: " + method);

        // print out the results of the method call
        System.out.println("\n ===>>> result is: " + result);

        // let's post-process the data ===>>> convert the account names to uppercase
        convertAccountNamesToUpperCase(result);
        System.out.println("\n ===>>> result is: " + result);
    }


    private void convertAccountNamesToUpperCase(List<Account> accounts) {
        for(Account account : accounts) {
            String oldName = account.getName();
            String upperCaseName = oldName.toUpperCase();
            account.setName(upperCaseName);
        }
    }

























}
