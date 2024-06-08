package net.ouacrime.ebankingbackend.mappers;

import net.ouacrime.ebankingbackend.dtos.CustomerDTO;
import net.ouacrime.ebankingbackend.entites.Customer;
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
    public Customer fromCustomer(CustomerDTO customerDTO)
    {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        return customer;
    }
}
