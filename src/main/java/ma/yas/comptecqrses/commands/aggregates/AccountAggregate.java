package ma.yas.comptecqrses.commands.aggregates;

import ma.yas.comptecqrses.common_api.commands.CreateAccountCommand;
import ma.yas.comptecqrses.common_api.commands.CreditAccountCommand;
import ma.yas.comptecqrses.common_api.commands.DebitAccountCommand;
import ma.yas.comptecqrses.common_api.enums.AccountStatus;
import ma.yas.comptecqrses.common_api.events.AccountActivatedEvent;
import ma.yas.comptecqrses.common_api.events.AccountCreatedEvent;
import ma.yas.comptecqrses.common_api.events.AccountCreditedEvent;
import ma.yas.comptecqrses.common_api.events.AccountDebitedEvent;
import ma.yas.comptecqrses.common_api.exceptions.AmountNegativeException;
import ma.yas.comptecqrses.common_api.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accoundId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        //Required by AXON
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance()<0) throw new RuntimeException("Impossible ...");
        //Ok
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED));


    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event)
    {
        this.accoundId=event.getId();
        this.balance=event.getInitialBalance();
        this.currency=event.getCurrency();
        this.status=AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event)
    {
        this.status=event.getStatus();

    }
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand)
    {
        if(creditAccountCommand.getAmount()<0)throw new AmountNegativeException("Amount should not be negtaive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent creditedEvent)
    {

        this.balance+=creditedEvent.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand)
    {
        if(debitAccountCommand.getAmount()<0)throw new AmountNegativeException("Amount should not be negtaive");
        if(this.balance<debitAccountCommand.getAmount()) throw new BalanceNotSufficientException("Balance not sufficient") ;
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent debitedEvent)
    {

        this.balance-=debitedEvent.getAmount();
    }
}
