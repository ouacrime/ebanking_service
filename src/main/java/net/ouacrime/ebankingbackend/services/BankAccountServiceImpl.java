package net.ouacrime.ebankingbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ouacrime.ebankingbackend.dtos.CustomerDTO;
import net.ouacrime.ebankingbackend.entites.*;
import net.ouacrime.ebankingbackend.enums.OperationType;
import net.ouacrime.ebankingbackend.exceptions.BalanceNotSufficientException;
import net.ouacrime.ebankingbackend.exceptions.BankAccountNotFoundException;
import net.ouacrime.ebankingbackend.exceptions.CustomerNotFoundException;
import net.ouacrime.ebankingbackend.mappers.BankAccountMapperImpl;
import net.ouacrime.ebankingbackend.repositories.AccountOperationRepository;
import net.ouacrime.ebankingbackend.repositories.BankAccountRepository;
import net.ouacrime.ebankingbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountMapperImpl dtoMapper;
    //Logger log= LoggerFactory.getLogger(this.getClass().getName());



    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving");
        Customer saveCustomer = customerRepository.save(customer);
        return saveCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initilaBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null)
        {
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initilaBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return savedBankAccount;

    }

    @Override
    public SavingAccount saveSavingBankAccount(double initilaBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null)
        {
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initilaBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return savedBankAccount;
    }


    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customersDtos = customers.stream()
                .map(cust->dtoMapper.fromCustomer(cust))
                .collect(Collectors.toList());

        /*List<CustomerDTO> customersDTOs = new ArrayList<>();
        for (Customer customer:customers)
        {
            CustomerDTO customerDTO= dtoMapper.fromCustomer(customer);
            customersDTOs.add(customerDTO);
        }*/

        return customersDtos;

    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow
                (()->new BankAccountNotFoundException("BankAccount not found"));

        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("balance not Sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }
    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }



}
