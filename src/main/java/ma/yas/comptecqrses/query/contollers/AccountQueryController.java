package ma.yas.comptecqrses.query.contollers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.yas.comptecqrses.common_api.queries.GetAccountByIdQuery;
import ma.yas.comptecqrses.common_api.queries.GetAllAccountsQuery;
import ma.yas.comptecqrses.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200") // Add this line

public class AccountQueryController {
    private QueryGateway queryGateway;

@GetMapping("/allAccounts")
    public List<Account> accountList(){
        List<Account> response = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();

        return response;
    }

    @GetMapping("/byId/{id}")
    public Account getAccount(@PathVariable String id){

        return queryGateway.query(new GetAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class)).join();

    }

}
