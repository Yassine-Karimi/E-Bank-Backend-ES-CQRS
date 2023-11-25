package ma.yas.comptecqrses.common_api.commands;

import lombok.Getter;
import ma.yas.comptecqrses.common_api.enums.AccountStatus;

public class CreateAccountCommand extends BaseCommand<String>{

    @Getter private double initialBalance;
    @Getter private String currency;

    public CreateAccountCommand(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
