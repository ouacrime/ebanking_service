package net.ouacrime.ebankingbackend.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ouacrime.ebankingbackend.dtos.CustomerDTO;
import net.ouacrime.ebankingbackend.exceptions.CustomerNotFoundException;
import net.ouacrime.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomer();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCutomer(customerId);
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId ,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

}
