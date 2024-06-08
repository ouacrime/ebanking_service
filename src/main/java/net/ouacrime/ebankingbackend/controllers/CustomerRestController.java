package net.ouacrime.ebankingbackend.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ouacrime.ebankingbackend.dtos.CustomerDTO;
import net.ouacrime.ebankingbackend.entites.Customer;
import net.ouacrime.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomer();
    }

}
