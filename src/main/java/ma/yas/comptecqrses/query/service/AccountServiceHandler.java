package ma.yas.comptecqrses.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.yas.comptecqrses.common_api.enums.OperationType;
import ma.yas.comptecqrses.common_api.events.AccountActivatedEvent;
import ma.yas.comptecqrses.common_api.events.AccountCreatedEvent;
import ma.yas.comptecqrses.common_api.events.AccountCreditedEvent;
import ma.yas.comptecqrses.common_api.events.AccountDebitedEvent;
import ma.yas.comptecqrses.common_api.queries.GetAccountByIdQuery;
import ma.yas.comptecqrses.common_api.queries.GetAllAccountsQuery;
import ma.yas.comptecqrses.query.entities.Account;
import ma.yas.comptecqrses.query.entities.Operation;
import ma.yas.comptecqrses.query.repository.AccountRepository;
import ma.yas.comptecqrses.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("*****************************************");
        log.info("AccountCreatedEvent received");
        Account account= new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        account.setBalance(event.getInitialBalance());
     accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("*****************************************");
        log.info("AccountActivatedEvent received");
        Account account= accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("*****************************************");
        log.info("AccountDebitedEvent received");
        Account account= accountRepository.findById(event.getId()).get();
        Operation operation= new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()- event.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("*****************************************");
        log.info("AccountCreditedEvent received");
        Account account= accountRepository.findById(event.getId()).get();
        Operation operation= new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query)
    {
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetAccountByIdQuery query)
    {
        return accountRepository.findById(query.getId()).get();
    }
}

