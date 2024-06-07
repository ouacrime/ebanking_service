package net.ouacrime.ebankingbackend.repositories;

import net.ouacrime.ebankingbackend.entites.BankAccount;
import net.ouacrime.ebankingbackend.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

}
