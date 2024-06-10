package net.ouacrime.ebankingbackend.controllers;


import lombok.AllArgsConstructor;
import net.ouacrime.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountRestController {

    private BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService)
    {
        this.bankAccountService = bankAccountService;
    }



}
