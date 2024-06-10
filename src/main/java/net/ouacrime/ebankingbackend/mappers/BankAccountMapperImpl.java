package net.ouacrime.ebankingbackend.mappers;

import net.ouacrime.ebankingbackend.dtos.CurrentBankAccountDTO;
import net.ouacrime.ebankingbackend.dtos.CustomerDTO;
import net.ouacrime.ebankingbackend.dtos.SavingBankAccountDTO;
import net.ouacrime.ebankingbackend.entites.CurrentAccount;
import net.ouacrime.ebankingbackend.entites.Customer;
import net.ouacrime.ebankingbackend.entites.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


//MapStruct

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer)
    {
        CustomerDTO customerDTO =new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO)
    {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        return customer;
    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount)
    {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO)
    {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount)
    {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO)
    {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }


}
