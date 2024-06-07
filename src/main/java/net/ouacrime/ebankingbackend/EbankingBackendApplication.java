package net.ouacrime.ebankingbackend;

import net.ouacrime.ebankingbackend.entites.*;
import net.ouacrime.ebankingbackend.enums.AccountStatus;
import net.ouacrime.ebankingbackend.enums.OperationType;
import net.ouacrime.ebankingbackend.repositories.AccountOperationRepository;
import net.ouacrime.ebankingbackend.repositories.BankAccountRepository;
import net.ouacrime.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository)
    {
        return args -> {
            Stream.of("HASSAN","YASSIN","OUMAIMA").forEach(
                    name->{
                        Customer customer = new Customer();
                        customer.setName(name);
                        customer.setEmail(name+"@Gmail.com");
                        customerRepository.save(customer);
                    });
            customerRepository.findAll().forEach(customer ->
            {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*99000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                currentAccount.setCustomer(customer);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*99000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                savingAccount.setCustomer(customer);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0 ; i<10 ; i++)
                {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setBankAccount(acc);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setType(Math.random()>0.5? OperationType.CREDIT:OperationType.DEBIT);
                    accountOperation.setAmount(Math.random()*15000);
                    accountOperationRepository.save(accountOperation);


                }

                BankAccount bankAccount = bankAccountRepository.findById("0c8633eb-296d-49b8-8eb5-6329fcbaa75b").orElse(null);

                if (bankAccount!=null){
                System.out.println("**********************************");
                System.out.println(bankAccount.getId());
                System.out.println(bankAccount.getCustomer().getName());
                System.out.println(bankAccount.getBalance());
                System.out.println(bankAccount.getCreatedAt());
                if(bankAccount instanceof CurrentAccount)
                {
                    System.out.println("OverDraft : "+((CurrentAccount)bankAccount).getOverDraft());
                } else if (bankAccount instanceof SavingAccount) {
                    System.out.println("Rate : "+((SavingAccount)bankAccount).getInterestRate());

                }}


            });

        };
    }

}