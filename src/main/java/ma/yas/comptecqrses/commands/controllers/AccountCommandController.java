package ma.yas.comptecqrses.commands.controllers;

import lombok.AllArgsConstructor;
import ma.yas.comptecqrses.common_api.commands.CreateAccountCommand;
import ma.yas.comptecqrses.common_api.commands.CreditAccountCommand;
import ma.yas.comptecqrses.common_api.commands.DebitAccountCommand;
import ma.yas.comptecqrses.common_api.dtos.CreateAccountRequestDTO;
import ma.yas.comptecqrses.common_api.dtos.CreditAccountRequestDTO;
import ma.yas.comptecqrses.common_api.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }
    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }
    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }
 @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e)
    {
         ResponseEntity<String> responseEntity=new ResponseEntity<>(
                 e.getMessage(),
                 HttpStatus.INTERNAL_SERVER_ERROR
         );
         return responseEntity;
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventstore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
