package com.luv2code.aopdemo.aspect;


import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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




    // This method converts the names of a list of Account objects to uppercase.
    // It iterates through each Account in the provided list, retrieves the current name,
    // converts it to uppercase, and sets the new uppercase name back to the Account object.
    private void convertAccountNamesToUpperCase(List<Account> accounts) {
        for(Account account : accounts) {
            String oldName = account.getName();
            String upperCaseName = oldName.toUpperCase();
            account.setName(upperCaseName);
        }
    }





    // the @AfterThrowing annotation to specify that the advice should run after an exception is thrown.
    // The method logs the name of the method being advised and the exception that was thrown.
    @AfterThrowing(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theException")
    public void afterThrowingFindAccountAdvice(JoinPoint theJoinPoint, Throwable theException) {
        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n\n ===>>> Executing @AfterThrowing on method: " + method);
        // log the exception
        System.out.println("\n ===>>> the exception is: " + theException);
    }




    // This method is an advice that runs after the execution of the `findAccounts` method in the `AccountDAO` class,
    // regardless of whether the method completes successfully or throws an exception (i.e., it runs in a "finally" block manner).
    //It prints out the name of the method being advised to provide logging or debugging information.
    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n ===>>> Executing @After (finally) on method: " + method);
    }





    /**
     * This method is an @Around advice that intercepts the execution of any method matching the pattern
     * `com.luv2code.aopdemo.service.*.getFortune(..)`. It wraps the target method's execution, allowing
     * for custom behavior before and after the method is invoked.

     * Key functionalities:
     * 1. Logs the method being advised for debugging or monitoring purposes.
     * 2. Measures the execution time of the target method by capturing timestamps before and after its execution.
     * 3. Computes and logs the duration of the method execution in seconds.
     * 4. Returns the result of the target method to ensure the original behavior is preserved.
     */
    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {

        // print out method we are advising on
        String method = theProceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n ===>>> Executing @Around on method: " + method);

        // get begin timeStamp
        long begin = System.currentTimeMillis();

        // now let's execute the method
        Object result =null;

        try {
            result = theProceedingJoinPoint.proceed();
        }
        catch (Exception exc) {
            // log the exception
            System.out.println(exc.getMessage());

            // give user a custom message
            //result = "Major Accident! your AOP is on the way";

            // rethrow the exception
            throw  exc;
        }

        // get end timeStamp
        long end = System.currentTimeMillis();

        // compute duration and display it
        long duration = end - begin;
        System.out.println("\n ===>>> Duration: " + duration/1000.0 + " seconds");

        return result;
    }




























}
