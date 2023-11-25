package ma.yas.comptecqrses.common_api.events;

import lombok.Getter;
import ma.yas.comptecqrses.common_api.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{

    @Getter private AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
