package ma.yas.comptecqrses.common_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreditAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;

}
