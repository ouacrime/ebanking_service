package net.ouacrime.ebankingbackend.controllers;


import lombok.AllArgsConstructor;
import net.ouacrime.ebankingbackend.dtos.AccountOperationDTO;
import net.ouacrime.ebankingbackend.dtos.BankAccountDTO;
import net.ouacrime.ebankingbackend.exceptions.BankAccountNotFoundException;
import net.ouacrime.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")

public class BankAccountRestController {

    private BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService)
    {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping({"/{accountId}/operations"})
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId)
    {
        return bankAccountService.accountHistory(accountId);
    }

}
