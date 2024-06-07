package net.ouacrime.ebankingbackend.repositories;

import net.ouacrime.ebankingbackend.entites.AccountOperation;
import net.ouacrime.ebankingbackend.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {


}
